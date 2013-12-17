package com.piketec.jenkins.plugins.tpt;


public class HtmlWriter {
  //
  // private final StringBuilder header;
  //
  // private final StringBuilder tableLine;
  //
  // private final StringBuilder error;
  //
  // private final StringBuilder end;
  //
  // public HtmlWriter() {
  // this.header = new StringBuilder();
  // this.tableLine = new StringBuilder();
  // this.end = new StringBuilder();
  // this.error = new StringBuilder();
  // }
  //
  // private static final String NEW_LINE = System.getProperty("line.separator");
  //
  // public void appendHtmlHeader(String name) {
  // header
  // .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>")
  // .append(NEW_LINE)
  // .append(
  // "<html xmlns=\"http://www.w3.org/1999/xhtml\" version=\"-//W3C//DTD XHTML 1.2//EN\" xml:lang=\"en\">")
  // .append(NEW_LINE)
  // .append("<head>")
  // .append(NEW_LINE)
  // .append(
  // "<meta content=\"application/xhtml+xml; CHARSET=UTF-8\" http-equiv=\"Content-Type\"/>")
  // .append(NEW_LINE).append("<title>Diff Report</title>")
  // .append(NEW_LINE)
  // .append("<link href=\"report.css\" rel=\"stylesheet\" type=\"text/css\"/>")
  // .append(NEW_LINE)
  // .append("</head>")
  // .append(NEW_LINE)
  // .append("<body>")
  // .append(NEW_LINE)
  // // write tpt filename
  // .append("<div class=\"header\">External Back 2 Back Test: ")
  // .append(name)
  // .append("</div>")
  // .append(NEW_LINE)
  // .append("<div class=\"clear\"/>")
  // .append(NEW_LINE)
  // // write current date
  // .append("<div class=\"section\">Summary  ").append(new java.util.Date()).append("</div>")
  // .append(NEW_LINE).append("<table class=\"scenario-tree\">").append(NEW_LINE).append("<tr>")
  // .append(NEW_LINE).append("<th class=\"scenario-tree\">Description</th>").append(NEW_LINE)
  // .append("<th class=\"scenario-tree\">Test Files</th>").append(NEW_LINE)
  // .append("<th class=\"scenario-tree\">Reference Files</th>").append(NEW_LINE)
  // .append("</tr>").append(NEW_LINE);
  // }
  //
  // public void appendTableHeader(int testdataSize, int refdataSize, int errorCount) {
  // header
  // .append("<tr>")
  // .append(NEW_LINE)
  // .append(
  // "<td align=\"left\" style=\"height:40px\" class=\"scenario-tree-Name\">Analyzed TestCases: </td>")
  // .append(NEW_LINE)
  // .append("<td align=\"left\" class=\"scenario-tree-TestFile\"><font color=\"red\">")
  // // coll size test
  // .append(testdataSize)
  // .append("</td>")
  // .append(NEW_LINE)
  // .append("<td align=\"left\" class=\"scenario-tree-ReferenzFile\"><font color=\"red\">")
  // // coll size ref
  // .append(refdataSize)
  // .append("</td>")
  // .append(NEW_LINE)
  // .append("</tr>")
  // .append(NEW_LINE)
  // .append("<tr>")
  // .append(NEW_LINE)
  // .append("<td align=\"left\" style=\"height:40px\" class=\"scenario-tree-Name\">")
  // // if coll1 != coll2 && error count
  // .append("Size difference (no. of new test cases): ")
  // .append((testdataSize - refdataSize))
  // .append("  Errors: ")
  // .append(errorCount)
  // .append("</td>")
  // .append(NEW_LINE)
  // .append(
  // "<td align=\"left\" style=\"height:40px\" class=\"scenario-tree-Testfile\"><font color=\"red\">")
  // .append("</td>").append(NEW_LINE)
  // .append("<td align=\"left\" class=\"scenario-tree-ReferenzFile\"><font color=\"red\">")
  // .append("</td>").append(NEW_LINE).append("</tr>").append(NEW_LINE);
  // }
  //
  // public void appendTableLine(Testcase ref, Testcase tc) {
  // tableLine
  // .append("<tr>" + NEW_LINE)
  // .append("<td align=\"left\" class=\"scenario-tree-Name\">Difference in Testfile:</td>")
  // .append(NEW_LINE)
  // .append("<td align=\"left\" class=\"scenario-tree-TestFile\">")
  // // filesystem paths
  // .append(tc.getFilesystemPath()).append("</td>").append(NEW_LINE)
  // .append("<td align=\"left\" class=\"scenario-tree-ReferenzFile\">")
  // .append(ref.getFilesystemPath()).append("</td>").append(NEW_LINE)
  // .append("</tr>")
  // .append(NEW_LINE)
  // .append("<tr>")
  // .append(NEW_LINE)
  // // testcase name
  // .append("<td align=\"left\" class=\"scenario-tree-Name\">")
  // .append(ref.getName())
  // .append("</td>")
  // .append(NEW_LINE)
  // // error logs
  // .append("<td align=\"left\" class=\"scenario-tree-TestFile\">").append(tc.toString())
  // .append("</td>").append(NEW_LINE)
  // .append("<td align=\"left\" class=\"scenario-tree-ReferenzFile\">").append(ref.toString())
  // .append("</td>").append(NEW_LINE).append("</tr>").append(NEW_LINE).append(blackLine());
  // }
  //
  // public void appendError(File refPath, File testPath, String failure) {
  // appendError(refPath.getAbsolutePath(), testPath.getAbsolutePath(), failure);
  // }
  //
  // public void appendError(String refPath, String testPath, String failure) {
  // error
  // .append("<tr>")
  // .append(NEW_LINE)
  // .append("<td align=\"left\" style=\"height:40px\" class=\"scenario-tree-Name\"></td>")
  // .append(NEW_LINE)
  // .append("<td align=\"left\" style=\"height:40px\" class=\"scenario-tree-Testfile\">")
  // .append(testPath)
  // .append("</td>")
  // .append(NEW_LINE)
  // .append("<td align=\"left\" style=\"height:40px\" class=\"scenario-tree-Referenzfile\">")
  // .append(refPath)
  // .append("</td>")
  // .append(NEW_LINE)
  // .append("</tr>")
  // .append(NEW_LINE)
  // .append("<tr>")
  // .append(NEW_LINE)
  // .append("<td align=\"left\" style=\"height:40px\" class=\"scenario-tree-Name\"></td>")
  // .append(NEW_LINE)
  // .append(
  // "<td align=\"left\" style=\"height:40px\" class=\"scenario-tree-Testfile\"><font color=\"red\">")
  // .append(failure).append("</td>").append(NEW_LINE).append("</tr>").append(NEW_LINE);
  // }
  //
  // public void appendHtmlEnd() {
  // end.append("</table>").append(NEW_LINE).append("</body>").append(NEW_LINE).append("</html>");
  // }
  //
  // private static String blackLine() {
  // StringBuilder sb = new StringBuilder();
  // sb.append("<tr style=\"background-color:#000000\">").append(NEW_LINE)
  // .append("<td style=\"height:3px\" class=\"scenario-tree\"></td>").append(NEW_LINE)
  // .append("<td style=\"height:3px\" class=\"scenario-tree\"></td>").append(NEW_LINE)
  // .append("<td style=\"height:3px\" class=\"scenario-tree\"></td>").append(NEW_LINE)
  // .append("</tr>").append(NEW_LINE);
  // return sb.toString();
  // }
  //
  // @Override
  // public String toString() {
  // return header.append(error.toString()).append(tableLine.toString()).append(end.toString())
  // .toString();
  // }
  //
  // public void toFile(File reportName) throws IOException {
  // Writer fw = null;
  // Writer bw = null;
  // try {
  // copyRessource(new File(reportName.getParentFile().getAbsolutePath(), "report.css")
  // .getAbsolutePath());
  // fw = new FileWriter(reportName);
  // bw = new BufferedWriter(fw);
  // bw.write(toString());
  // bw.close();
  // fw.close();
  // } catch (IOException ie) {
  // throw new IOException("Could not write Report to filesystem. " + ie.getMessage());
  // }
  // }
  //
  // public void copyRessource(String path) throws IOException {
  // FileWriter f = new FileWriter(path);
  // try {
  // f.write("/* ********************************************* */\r\n"
  // + "/* **** general **** */\r\n" + "body {\r\n" + "  font-family: \"arial\";\r\n"
  // + "  font-weight: normal;\r\n" + "  font-size: 10pt;\r\n" + "}\r\n" + "\r\n" + "a {\r\n"
  // + "  text-decoration: none;\r\n" + "}\r\n" + "\r\n" + "a:hover {\r\n"
  // + "  text-decoration: underline;\r\n" + "}\r\n" + "\r\n" + "a:visited {\r\n" + "  \r\n"
  // + "}\r\n" + "\r\n" + ".gray-box {\r\n" + "  background-color: #F0F0F0;\r\n"
  // + "  border: 1px solid #A0A0A0;\r\n" + "  margin: 4px;\r\n"
  // + "  margin-bottom: 32px;\r\n" + "  padding: 4px;\r\n" + "}\r\n" + "\r\n"
  // + "td.numeric {\r\n" + "  width: 64px;\r\n" + "  text-align: right;\r\n"
  // + "  padding-right: 4px;\r\n" + "}\r\n" + "\r\n" + "tr:hover {\r\n"
  // + "  background-color: #efeff8; /* table row hovering */\r\n" + "}\r\n" + "\r\n"
  // + "/* ********************************************* */\r\n"
  // + "/* **** header area **** */\r\n" + "div.header {\r\n" + "  font-size: 28pt;\r\n"
  // + "  padding-top: 10px;\r\n" + "  margin: 4px;\r\n" + "  margin-bottom: 30px;\r\n"
  // + "  display: block;\r\n" + "  height: 50px;\r\n"
  // + "  background-image: url(tpt_logo.png);\r\n" + "  background-position: right top;\r\n"
  // + "  background-repeat: no-repeat;\r\n" + "}\r\n" + "\r\n" + "div.clear {\r\n"
  // + "  clear: both;\r\n" + "}\r\n" + "\r\n"
  // + "/* ********************************************* */\r\n"
  // + "/* **** sections and subsections styles **** */\r\n" + "div.section {\r\n"
  // + "  border-bottom: 1px solid #9090AD;\r\n" + "  margin-bottom: 12px;\r\n"
  // + "  font-size: 20px;\r\n" + "  font-weight: bold;\r\n" + "  line-height: 25px;\r\n"
  // + "}\r\n" + "\r\n" + "div.section2 {\r\n" + "  border-bottom: 1px solid #9090AD;\r\n"
  // + "  margin-bottom: 12px;\r\n" + "  font-size: 15px;\r\n" + "  font-weight: bold;\r\n"
  // + "  line-height: 20px;\r\n" + "}\r\n" + "\r\n" + "div.section3 {\r\n"
  // + "  border-bottom: 1px solid #9090AD;\r\n" + "  margin-bottom: 12px;\r\n"
  // + "  font-size: 12px;\r\n" + "  font-weight: bold;\r\n" + "  line-height: 17px;\r\n"
  // + "}\r\n" + "\r\n" + "div.sectionX {\r\n" + "  border-bottom: 1px solid #9090AD;\r\n"
  // + "  margin-bottom: 12px;\r\n" + "  font-size: 10px;\r\n" + "  font-weight: bold;\r\n"
  // + "  line-height: 15px;\r\n" + "}\r\n" + "\r\n"
  // + "/* ********************************************* */\r\n"
  // + "/* **** general table style settings  **** */\r\n" + "table {\r\n"
  // + "  font-size: 10pt;\r\n" + "  font-weight: normal;\r\n" + "  width: 100%;\r\n"
  // + "  border-spacing: 1px;\r\n" + "  padding: 1px;\r\n"
  // + "  border: 1px solid #A0A0A0;\r\n" + "  margin-bottom: 32px;\r\n"
  // + "  background-color: #F8F8F8;\r\n" + "  empty-cells: show;\r\n"
  // + "  border-collapse: collapse;\r\n" + "}\r\n" + "\r\n" + "caption {\r\n"
  // + "  color: black;\r\n" + "  font-size: 9pt;\r\n" + "  font-style: italic;\r\n"
  // + "  color: #AC9560;\r\n" + "  text-align: left;\r\n" + "}\r\n" + "\r\n" + "td {\r\n"
  // + "  font-weight: normal;\r\n" + "  font-size: 10pt;\r\n" + "  padding: 2px;\r\n"
  // + "  margin: 0px;\r\n" + "  border: 1px solid #A0A0A0;\r\n" + "}\r\n" + "\r\n"
  // + "th {\r\n" + "  border: 1px solid #C0C0C0;\r\n" + "  background-color: #e8e8F0;\r\n"
  // + "  text-align: left;\r\n" + "  font-weight: bold;\r\n" + "  font-size: 10pt;\r\n"
  // + "  padding: 4px;\r\n" + "}\r\n" + "\r\n" + "");
  //
  // } catch (Exception e) {
  // throw new IOException("Could not copy ressource: " + e.getMessage());
  // } finally {
  // f.flush();
  // f.close();
  // }
  // }
}
