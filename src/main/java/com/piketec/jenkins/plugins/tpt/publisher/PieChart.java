package com.piketec.jenkins.plugins.tpt.publisher;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;
import javax.swing.ImageIcon;

import org.apache.commons.lang.ArrayUtils;

import jenkins.model.Jenkins;

public class PieChart {

  public static final Color BRIGHT_GRAY = new Color(240, 242, 240);

  public static final Font NORMALFONT = new Font("Dialog", Font.PLAIN, 12);

  private static void checkLegendSegmentOrder(List<Segment> segments, int[] legendSegmentOrder) {
    if (legendSegmentOrder == null) {
      return;
    }
    assert legendSegmentOrder.length == segments.size();
    for (int i = 0; i < legendSegmentOrder.length; ++i) {
      assert ArrayUtils.contains(legendSegmentOrder, i);
    }
  }

  final int centerX = 296;

  final int centerY = 287;

  final int radius = 263;

  final int totalHeight = 616;

  final int totalWidth = 1232;

  double zoom = 1;

  private final List<Segment> segments;

  private final boolean showTotalInLegend;

  private final double total;

  private final boolean withSubSegments;

  private final String subTotalTextOrNull;

  private final double subTotal;

  private final DecimalFormat legendPortionFormat;

  private final int[] legendSegmentOrder;

  private final String totalText = "in total";

  private ImageIcon pieShadow;

  private ImageIcon keyShadow;

  public PieChart(List<Segment> segments, int fractionalDigits, boolean showTotalInLegend)
      throws MalformedURLException {
    this(segments, null, fractionalDigits, showTotalInLegend, false, null);
    pieShadow = new ImageIcon(new File(Jenkins.getInstance().getRootDir(),
        "\\plugins\\piketec-tpt\\TorteImag\\Shadow.png").toURI().toURL());
    keyShadow = new ImageIcon(new File(Jenkins.getInstance().getRootDir(),
        "\\plugins\\piketec-tpt\\TorteImag\\Shadow2.png").toURI().toURL());
  }

  public PieChart(List<Segment> segments, @Nullable int[] legendSegmentOrder, int fractionalDigits,
                  boolean showTotalInLegend, boolean withSubSegments, String subTotalTextOrNull) {
    checkLegendSegmentOrder(segments, legendSegmentOrder); // Pruefung der Konsistenz zwischen
    // segments and legendSegmentOrder
    this.segments = segments;
    this.showTotalInLegend = showTotalInLegend;
    this.legendSegmentOrder = legendSegmentOrder;
    this.withSubSegments = withSubSegments;
    this.subTotalTextOrNull = subTotalTextOrNull;

    double sum = 0;
    double subSum = 0;
    if (showTotalInLegend) {
      for (Segment seg : segments) {
        sum += seg.getPortion();
        subSum += seg.getSubPortion();
      }
    }
    this.total = sum;
    this.subTotal = subSum;

    StringBuilder pattern = new StringBuilder("#,##0");
    if (fractionalDigits > 0) {
      pattern.append('.');
      for (int i = 0; i < fractionalDigits; ++i) {
        pattern.append('0');
      }
    }
    legendPortionFormat = new DecimalFormat(pattern.toString());
  }

  public BufferedImage render(int height) {
    BufferedImage image = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = image.createGraphics();
    g2.scale(zoom, zoom);
    // fill background to white
    g2.setColor(Color.WHITE);
    g2.fill(new Rectangle(totalWidth, totalHeight));
    // prepare render hints
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    // draw shadow image
    g2.drawImage(pieShadow.getImage(), 0, 0, pieShadow.getImageObserver());

    double start = 0;
    List<Arc2D> pies = new ArrayList<>();
    // pie segmente erzeugen und fuellen
    if (total == 0) {
      g2.setColor(BRIGHT_GRAY);
      g2.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
      g2.setColor(Color.WHITE);
      g2.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    } else {
      for (Segment s : segments) {
        double portionDegrees = s.getPortion() / total;
        Arc2D pie = paintPieSegment(g2, start, portionDegrees, s.getColor());
        if (withSubSegments) {
          double smallRadius = radius * s.getSubSegmentRatio();
          paintPieSegment(g2, start, portionDegrees, smallRadius, s.getColor().darker());
        }
        start += portionDegrees;
        // portion degree jetzt noch als String (z.B. "17.3%" oder "20%" zusammenbauen)
        String p = String.format(Locale.ENGLISH, "%.1f", Math.rint(portionDegrees * 1000) / 10.0);
        p = removeSuffix(p, ".0"); // evtl. ".0" bei z.B. "25.0" abschneiden (-> "25")
        s.setPercent(p + "%");
        pies.add(pie);
      }
      // weissen Rahmen um die pie segmente zeichen
      g2.setColor(Color.WHITE);
      for (Arc2D pie : pies) {
        g2.draw(pie);
      }
    }
    // Legende zeichnen
    renderLegend(g2);
    // "xx%" Label direkt auf die pie segmente zeichen
    g2.setColor(Color.WHITE);
    float fontSize = 32f;
    g2.setFont(NORMALFONT.deriveFont(fontSize).deriveFont(Font.BOLD));
    start = 0;
    for (Segment s : segments) {
      if (s.getPortion() < 1E-6) {
        continue; // ignore segments with portions that are extremely small
      }
      double portionDegrees = s.getPortion() / total;
      double angle = start + portionDegrees / 2; // genau in der Mitte des Segments
      double xOffsetForCenteredTxt = 8 * s.getPercent().length(); // assume roughly 8px per char
      int x = (int)(centerX + 0.6 * radius * Math.sin(2 * Math.PI * angle) - xOffsetForCenteredTxt);
      int y = (int)(centerY - 0.6 * radius * Math.cos(2 * Math.PI * angle) + fontSize / 2);
      g2.drawString(s.getPercent(), x, y);
      start += portionDegrees;
    }
    int width = (int)((double)totalWidth / totalHeight * height); // compute width to preserve ratio
    return image;// resize(image, width, height);
  }

  private void renderLegend(Graphics2D g2) {
    g2.setFont(NORMALFONT.deriveFont(32f).deriveFont(Font.BOLD));

    // erst die Breite der Zahlen fuer die Einrueckung berechnen
    Font font = g2.getFont();
    FontRenderContext fontRenderContext = g2.getFontRenderContext();
    Map<Segment, Double> numberWidths = new HashMap<>();
    double maxNumberWidth = 0;
    for (Segment seg : segments) {
      double numberWidth =
          font.getStringBounds(legendPortionFormat.format(seg.getPortion()), fontRenderContext)
              .getWidth();
      numberWidths.put(seg, numberWidth);
      maxNumberWidth = Math.max(maxNumberWidth, numberWidth);
    }
    if (showTotalInLegend) {
      double numberWidth =
          font.getStringBounds(legendPortionFormat.format(total), fontRenderContext).getWidth();
      numberWidths.put(null, numberWidth);
      maxNumberWidth = Math.max(maxNumberWidth, numberWidth);
    }

    // jetzt die Zeilen in die Legende malen
    int verticalOffset = 0;
    for (int row = 0; row < segments.size(); ++row) {
      Segment seg = getLegendSegment(row);
      // Zahlen rechtsbuendig
      double indentation = maxNumberWidth - numberWidths.get(seg);
      String segmentPortionNumber = legendPortionFormat.format(seg.getPortion());
      String segmentText = seg.getText();
      Color segmentColor = seg.getColor();
      String subSegmentText = seg.getSubSegmentText();
      String subNumberText = legendPortionFormat.format(seg.subPortion);
      drawLegendLine(g2, verticalOffset, indentation, segmentColor, segmentText,
          segmentPortionNumber, seg.getPortion() != 1d, seg.getSubPortion() > 0, subSegmentText,
          subNumberText, seg.getSubPortion() != 1d);
      verticalOffset += 85;
    }
    if (showTotalInLegend) {
      double indentation = maxNumberWidth - numberWidths.get(null);
      String subNumberText = legendPortionFormat.format(subTotal);
      drawLegendLine(g2, verticalOffset, indentation, null, totalText,
          legendPortionFormat.format(total), total != 1, subTotalTextOrNull != null,
          subTotalTextOrNull, subNumberText, subTotal != 1);
    }
  }

  public static BufferedImage resize(BufferedImage source, int width, int height) {
    assert source != null;
    assert width >= 0 : "width < 0";
    assert height >= 0 : "height < 0";
    int swidth = source.getWidth();
    int sheight = source.getHeight();
    double xScale = ((double)width) / (double)swidth;
    double yScale = ((double)height) / (double)sheight;
    if (width <= 0) {
      xScale = yScale;
      width = (int)Math.rint(xScale * swidth);
    }
    if (height <= 0) {
      yScale = xScale;
      height = (int)Math.rint(yScale * sheight);
    }
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    BufferedImage result = gd.getDefaultConfiguration().createCompatibleImage(width, height,
        source.getColorModel().getTransparency());
    Graphics2D g2d = null;
    try {
      g2d = result.createGraphics();
      g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
          RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      AffineTransform at = AffineTransform.getScaleInstance(xScale, yScale);
      g2d.drawRenderedImage(source, at);
    } finally {
      if (g2d != null) {
        g2d.dispose();
      }
    }
    return result;
  }

  private Segment getLegendSegment(int row) {
    if (legendSegmentOrder == null) {
      return segments.get(row);
    } else {
      return segments.get(legendSegmentOrder[row]);
    }
  }

  private void drawLegendLine(Graphics2D g2, int verticalOffset, double horizontalNumberOffset,
                              Color col, String txt, String numberText, boolean textIsPlural,
                              boolean withSubSegment, String subSegmentText, String subNumberText,
                              boolean subTextIsPlural) {
    int left = 620;
    // col == null --> total --> kein Rechteck
    if (col != null) {
      g2.drawImage(keyShadow.getImage(), left, 30 + verticalOffset, keyShadow.getImageObserver());
      g2.setColor(col);
      g2.fillRect(left + 13, 37 + verticalOffset, 45, 45);
      if (withSubSegment) {
        Polygon p = new Polygon(new int[] { left + 13 + 45, left + 13 + 45, left + 13 },
            new int[] { verticalOffset + 37, verticalOffset + 37 + 45, verticalOffset + 37 + 45 },
            3);
        g2.setColor(col.darker());
        g2.fillPolygon(p);
      }
    }
    g2.setColor(Color.BLACK);
    StringBuffer sb = new StringBuffer(numberText);
    sb.append("  ").append(plural(textIsPlural, txt));
    if (withSubSegment) {
      sb.append(" with ");
      sb.append(subNumberText);
      sb.append(" ");
      sb.append(plural(subTextIsPlural, subSegmentText));
    }
    g2.drawString(sb.toString(), (int)(left + 80 + horizontalNumberOffset),
        30 + 41 + verticalOffset);

  }

  private Arc2D paintPieSegment(Graphics2D g2, double startRatio, double endRatio,
                                Color fillColor) {
    return paintPieSegment(g2, startRatio, endRatio, radius, fillColor);
  }

  private Arc2D paintPieSegment(Graphics2D g2, double startRatio, double endRatio, double radius,
                                Color fillColor) {
    Arc2D pie = new Arc2D.Double(Arc2D.PIE);
    double startAngle = 90 - 360 * startRatio; // top (= north)
    double endAngle = -360 * endRatio; // mit dem Uhrzeigersinn
    pie.setArcByCenter(centerX, centerY, radius, startAngle, endAngle, Arc2D.PIE);
    g2.setColor(fillColor);
    g2.fill(pie);
    return pie;
  }

  public static final String removeSuffix(String text, String suffix) {
    if (text.endsWith(suffix)) {
      return text.substring(0, text.length() - suffix.length());
    }
    return text;
  }

  public static final String plural(boolean plural, CharSequence text) {
    StringBuilder b = new StringBuilder();
    int mode = 0;
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      switch (mode) {
        case 0: // regular mode
        {
          if (c == '{') {
            mode = 1;
          } else {
            b.append(c); // always append
          }
          break;
        }
        case 1: // singular mode
        {
          if (c == '|') {
            mode = 2;
          } else if (!plural) {
            b.append(c); // append if singular
          }
          break;
        }
        case 2: // plural mode
        {
          if (c == '}') {
            mode = 0;
          } else if (plural) {
            b.append(c); // append if plural
          }

          break;
        }
        default:
          throw new RuntimeException();
      }
    }
    return b.toString();
  }

  // ------------------------------------------------------------------------------------------

  public static class Segment {

    private final String text;

    private final String subSegmentText;

    private final Color color;

    private final double portion;

    private final double subPortion;

    private final double subSegmentRatio;

    private String percent = null;

    public Segment(String text, double portion, Color color) {
      this(text, null, portion, 0, color);
    }

    public Segment(String text, String subSegmentText, double portion, double subPortion,
                   Color color)
        throws IllegalArgumentException {
      assert portion >= 0 && subPortion >= 0 && portion >= subPortion;
      this.text = text;
      this.subSegmentText = subSegmentText;
      this.portion = portion;
      this.subPortion = subPortion;
      this.subSegmentRatio = portion > 0 ? subPortion / portion : 0;
      this.color = color;
    }

    public String getText() {
      return text;
    }

    public String getSubSegmentText() {
      return subSegmentText;
    }

    public double getPortion() {
      return portion;
    }

    public double getSubPortion() {
      return subPortion;
    }

    public double getSubSegmentRatio() {
      return subSegmentRatio;
    }

    public Color getColor() {
      return color;
    }

    public String getPercent() {
      return percent;
    }

    public void setPercent(String percent) {
      this.percent = percent;

    }

  }
}
