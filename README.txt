README fuer das Trio 
Validation Suite, Tpt Jenkins Plugin, External B2B.

Die Validation Suite und das Tpt Jenkins Plugin bilden eine Einheit, die in der Konfiguration 
im Jenkins, durch ein Hacken bei "back2back", unterschieden wird.

Die grundlegende Konfiguration ist gleich. 
Man gibt den Pfad zur "tpt.exe" und die Argumente mit denen man Tpt aufrufen moechte.
Voreingestellt ist als Argument "--run build". 

Danach kann man einen Report Ordner angeben, dieser bezieht sich auf den Workspace Ordner,
des Jenkins Projekts. Bei einem leeren Feld wird der Workspace genommen, 
ansonsten wird der Workspace Ordner mit dem im Feld angegeben Pfad konkateniert und ggf. der
Ordner erstellt. Der hier angegebene Report Ordner ist gleichzeitig der Ordner, der beim
"Post-Build" von Junit angegeben werden muss.

Danach folgt die Konfiguration der einzelnen Testdurchlaeufe.
Sie bestehen jeweils aus einer Tpt-File ("foo.tpt") und der Platform Konfiguration.

Die Validation Suite und das Tpt Jenkins Plugin interpretieren diese 
Konfigurationen nun unterschiedlich.

1. Validation Suite

Die Validation Suite führt bei einem Testdurchlauf, die angegebene Tpt-File mit der angegebenen
Platform Konfiguration aus.
Das Plugin nimmt nun an, dass Tpt seine generierten Testdaten neben die angegebene Tpt-File legt.
In einen Ordner der "tesdata" heißt. Darunter liegen nun ein Ordner der genauso heisst wie das 
zuvor ausgeführte Tpt-File, ohne ".tpt". In diesem Ordner liegt nun ein weiterer Ordner mit der
Platform Konfiguration als Namen (Whitespaces werden automatisch durch Unterstriche ersetzt).
In diesem Ordner findet das Plugin nun die Testdaten.

Die Referenzdaten werden auf die gleiche Weise abgelegt in einem Ordner der "refdata" heisst.
Bei den Referenzdaten darf der nach der Konfiguration benannte Ordner auch "data" heißen, wenn die 
Referenzdaten fuer verchiedene Platformen gleich sind.

Beispiel:
Wir fuehren die Datei "foo.tpt" mit der Konfiguration "bar" aus.
"foo.tpt" und die Referenzdaten befindet sich in dem Ordner "~/test".

Der Ordner vor dem Testdurchlauf:

~/test/ > foo.tpt
        > refdata/ > foo/ > bar/ > testcase1/ > testcase_information.xml
                                 > testcase2/ > testcase_information.xml
                                 > testcase3/ > testcase_information.xml
--------------------------------------------------------------------------
Mit dem "data" Ordner anstatt der Platform Konfiguration

~/test/ > foo.tpt
        > refdata/ > foo/ > data/ > testcase1/ > testcase_information.xml
                                  > testcase2/ > testcase_information.xml
                                  > testcase3/ > testcase_information.xml
--------------------------------------------------------------------------

Nach dem Testdurchlauf sieht der Ordner dann wie folgt aus:

~/test/ > foo.tpt
        > refdata/ > foo/ > bar/ > testcase1/ > testcase_information.xml
                                 > testcase2/ > testcase_information.xml
                                 > testcase3/ > testcase_information.xml
        > testdata/ > foo/ > bar/ > testcase1/ > testcase_information.xml
                                  > testcase2/ > testcase_information.xml
                                  > testcase3/ > testcase_information.xml
-------------------------------------------------------------------------
Oder:

~/test/ > foo.tpt
        > refdata/ > foo/ > data/ > testcase1/ > testcase_information.xml
                                  > testcase2/ > testcase_information.xml
                                  > testcase3/ > testcase_information.xml
        > testdata/ > foo/ > bar/ > testcase1/ > testcase_information.xml
                                  > testcase2/ > testcase_information.xml
                                  > testcase3/ > testcase_information.xml
-------------------------------------------------------------------------

Die Daten werden vom Plugin nur ausgewertet, wenn sie in dieser Form vorliegen, alles
andere führt zu bekannten und unbekannten Fehlern.

2. Tpt Jenkins Plugin

Das Tpt Jenkins Plugin führt kein Vergleich zwischen Referenz- und Testdaten durch, sondern
verabeitet nur die Testdaten und bereitet sie fuer Junit vor.

Die Arbeitsweise und Handhabung ist momentan die gleiche, wie bei der Validation Suite 
ohne Referenzdaten. 

Zur weiteren Verbesserung des Plugins, sollte der Ort an dem Tpt die Testdaten ablegt 
dynamisch ermittelt werden, anstatt diesen an die Tpt-File zu binden.

================================================================================
Der External B2B Test

Der external_b2b_test ist eine aus einer Executable Jar heraus laufende lokale Variante der
Validation Suite.
Sie fuehrt Tpt nicht aus, dies muss von Hand erledigt werden.
Der Jar werden drei Argumente uebergeben:
1. Die Referenzdaten
2. Die Testdaten
3. Der Report Ordner

Der Report Ordner kann frei gewählt werden, in ihm wird das Ergebnis in einer 
"*.html" Datei gespeichert. (Beispiel: bei "foo.tpt" heißt die resultierende Datei, je nach Ergebnis,
"foo_FAILURE.html" oder "foo_XYZ.html". Wobei XYZ das ExecutionDate der Testdaten ist.)

Die Testdaten und Referenzdaten können ebenso frei von der Tpt-File liegen. 
Dabei ist nur zu beachten, dass die Hierarchie erhalten bleibt.
Beispiel:
Die Tpt-File ist "foo.tpt" die Platform Konfiguration ist "bar" und unsere Referenzdaten
liegen unter "ref" und die Testdaten unter "actual-run".

Die Hierarchy:
~/ref/ > foo/ > bar/ > testcase1/ > testcase_information.xml
                     > testcase2/ > testcase_information.xml
                     > testcase3/ > testcase_information.xml
~/actual-run/ > foo/ > bar > testcase1/ > testcase_information.xml
                           > testcase2/ > testcase_information.xml
                           > testcase3/ > testcase_information.xml

Angegeben, als Argument, wird nun der eindeutige Pfad zu den Daten.
Referenz: ~/ref/foo/bar                           
Test: ~/actual-run/foo/bar

Ein paar Worte zu Jenkins

1. Jenkins ist extrem vom Umland abhängig, sprich vom Kernel auf dem es läuft und von der installierten
Java Version. Zudem können Funktionen die in einer Jenkins Version funktionieren in einer anderen
Version nicht oder anders funktionieren.
Kurz gesagt Jenkins ist nicht gleich Jenkins.

2. Beim Einpflegen eines Plugin muss Jenkins neu gestartet werden. 
Es kann zu dem Fehler kommen, dass trotz Neustart das neue Plugin nicht richtig übernommen wird.
Dann einfach das Plugin neu hochladen und Jenkins erneut neustarten.
Wenn das nicht hilft, das Plugi komplett entfernen, Neustart, Einpflegen, Neustart.

3. InterruptedException, sollten bei Jenkins immer abgefangen, wenn nicht sogar weiter nach oben gereicht werden.
Sonst kann es zu Fehlern beim Abbruch eines Builds kommen.

4. Funktionen die in der Jenkins-API festgelegt sind, den Eigenen vorziehen.
Spart meistens Code und garantiert eine gute Interaktion mit Jenkins selbst.
