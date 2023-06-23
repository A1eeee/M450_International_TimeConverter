import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeConverter {

    //Pfad der CSV Datei in eine Variabel Speichern
    String csvFile = "src/main/data/InternationalTimeConverter_Data.csv";

    WerteSpeichern werteSpeichern = new WerteSpeichern();

    Scanner scanner = new Scanner(System.in);

    LocalDateTime localDateTime = LocalDateTime.now();

    public TimeConverter() {

    }

    // Methode zur Behandlung von Fehlereingaben
    public String fehlermeldung(String input) {
        if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ja")) {
            return "Y";
        } else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("nein")) {
            Main.main(null);
            return "N";
        } else if (input.equalsIgnoreCase("b")) {
            werteSpeichern.setNumberSpeicher(0);
            System.exit(werteSpeichern.getNumberSpeicher());
        } else {
            System.out.println("Keine gültige Eingabe!");
            System.out.println("Eingabe wiederholen: ");
            String inputW = scanner.nextLine();

            fehlermeldung(inputW);
        }

        return null;
    }

    // Methode zur Weiterführung des Programms basierend auf der Benutzereingabe
    public String weiterFuehren(String input) {
        if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("Ja")) {
            return "Y";
        } else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("nein")) {
            Main.main(null);
            return "M";
        } else if (input.equalsIgnoreCase("b")) {
            System.exit(0);
            return "B";
        } else {
            System.out.println("Ungültige Eingabe!");
            System.out.println("Bitte geben Sie eine gültige Option ein.");
            return weiterFuehren(scanner.nextLine());
        }
    }

    // Methode zur Rückkehr zum Hauptmenü oder Beenden des Programms
    public void zurueck() {
        System.out.println("Möchten Sie einen neuen Prozess starten? (Ja/Nein) | Programm beenden (Nein/b/B)");
        String auswahl = scanner.nextLine();

        if (auswahl.equalsIgnoreCase("yes") || auswahl.equalsIgnoreCase("ja")) {
            Main.main(null);
        } else if (auswahl.equalsIgnoreCase("nein") || auswahl.equalsIgnoreCase("no") || auswahl.equalsIgnoreCase("b")) {
            System.exit(0);
        } else {
            System.out.println("Ungültige Eingabe!");
            System.out.println("Bitte geben Sie eine gültige Option ein:");
            zurueck();
        }
    }

    // Methode zur Filterung nach Ländercode
    public String filtrierungNachLeander(String inputCountryCode) {
        boolean gueltig = false;
        while (!gueltig) {
            if (inputCountryCode == null || inputCountryCode.isEmpty()) {
                System.out.print("Ländercode eingeben: ");
                inputCountryCode = scanner.nextLine().toUpperCase();
            }

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                boolean isFirstLine = true;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;  // Überspringe die erste Zeile (Spaltenüberschriften)
                    }

                    String[] data = line.split(","); // Die Daten der CSV Datei anhand Kommas trennen
                    String country = data[0].trim();  // CSV Datei: "Land" befindet sich an Index 0
                    String countrycode = data[1].trim();  // CSV Datei: "Ländercode" befindet sich an Index 1
                    String offset = data[2].trim(); // CSV Datei: "Offset" befindet sich an Index 1
                    String continent = data[3].trim();

                    if (countrycode.equalsIgnoreCase(inputCountryCode)) {
                        werteSpeichern.setLandesCode(countrycode);
                        // Berechnung der Zeit mit Stunden - 1 Stunde da die Lokale Zeit des Projektes in der Schweiz ist
                        ZoneOffset zoneOffset = ZoneOffset.ofHours(Integer.parseInt(offset));
                        LocalDateTime adjustedDateTime = localDateTime.plusHours(zoneOffset.getTotalSeconds() / 3600).minusHours(1);
                        // Konvertierung der Ausgabe, das nur Stunden und Minuten ausgegeben werden
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                        werteSpeichern.setZeitSpeichern(adjustedDateTime.format(formatter));

                        System.out.println("Land: " + country);
                        System.out.println("Ländercode: " + werteSpeichern.getLandesCode());
                        System.out.println("Zeit: " + werteSpeichern.getZeitSpeichern());
                        System.out.println("Kontinent: " + continent);
                        werteSpeichern.setLandesName(country);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Kein gültiger Ländercode.");
                    System.out.println("Möchten Sie die Eingabe des Ländercodes wiederholen? (Ja/Nein) | Programm beenden (B/b)");
                    String wahl = scanner.nextLine();

                    if (fehlermeldung(wahl).equals("N")) {
                        inputCountryCode = "";
                    } else {
                        inputCountryCode = null;
                    }
                } else {
                    zurueck();
                    return werteSpeichern.getLandesName();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return werteSpeichern.getLandesName();
    }



    // Methode zur Filterung nach Kontinent
    public String filtrierungNachKontinent(String inputContinent) {
        boolean gueltig = false;

        while (!gueltig) {
            if (inputContinent == null || inputContinent.isEmpty()) {
                System.out.print("Kontinent eingeben: ");
                inputContinent = scanner.nextLine().toUpperCase();
            }

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                boolean isFirstLine = true;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;  // Überspringe die erste Zeile (Spaltenüberschriften)
                    }

                    String[] data = line.split(","); // Die Daten der CSV Datei anhand Kommas trennen
                    String country = data[0].trim(); // CSV Datei: "Land" befindet sich an Index 0
                    String continent = data[3].trim(); // CSV Datei: "Kontinent" befindet sich an Index 3
                    String countryCode = data[1].trim(); // CSV Datei: "Ländercode" befindet sich an Index 1

                    if (continent.equalsIgnoreCase(inputContinent)) {
                        werteSpeichern.setLandesCode(countryCode);
                        String output = String.format("Land: %-2s | %s", werteSpeichern.getLandesCode(), country);
                        System.out.println(output);
                        found = true;
                    }
                }

                if (!found) {
                    System.out.println("Kein gültiger Kontinent.");
                    System.out.println("Möchten Sie die Eingabe des Kontinents wiederholen? (Ja/Nein) | Programm beenden (B/b)");
                    String wahl = scanner.nextLine();

                    if (fehlermeldung(wahl).equals("N")) {
                        inputContinent = "";
                    } else {
                        inputContinent = null;
                    }
                } else {
                    System.out.println("Möchten Sie nach einem Land filtern? (Ja/Nein) | Programm beenden (B/b)");

                    while (true) {
                        String weiterWahl = scanner.nextLine();
                        if (weiterFuehren(weiterWahl).equals("Y")) {
                            filtrierungNachLeanderNachKontinent(inputContinent);
                            break;
                        } else if (weiterFuehren(weiterWahl).equals("F")) {
                            System.out.println("Keine gültige Eingabe!");
                            System.out.println("Eingabe wiederholen: ");
                            String inputW = scanner.nextLine();
                            weiterWahl = weiterFuehren(inputW);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputContinent;
    }


    //Methode um nach Landesname zu Filtrieren
    public void filtrierung_landesname() {
        boolean gueltig = false;
        while (!gueltig) {
            System.out.print("Land eingeben: ");
            String inputCountryName = scanner.nextLine().toUpperCase();

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                boolean isFirstLine = true;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;  // Überspringe die erste Zeile (Spaltenüberschriften)
                    }

                    String[] data = line.split(","); //Die Daten der CSV Datei anhand Kommas Trennen
                    String country = data[0].trim();  // CSV Datei: "Land" befindet sich an Index 0
                    String countrycode = data[1].trim();  // CSV Datei: "Ländercode" befindet sich an Index 1
                    String offset = data[2].trim(); // CSV Datei: "Offset" befindet sich an Index 1
                    String continent = data[3].trim();

                    if (country.equalsIgnoreCase(inputCountryName)) {
                        werteSpeichern.setLandesCode(countrycode);
                        //Berrechnung der Zeit mit Stunden - 1 Stunde da die Lokale Zeit des Projektes in der Schweiz ist
                        ZoneOffset zoneOffset = ZoneOffset.ofHours(Integer.parseInt(offset));
                        LocalDateTime adjustedDateTime = localDateTime.plusHours(zoneOffset.getTotalSeconds() / 3600).minusHours(1);
                        //Konvertierung der ausgabe, das nur Stunden und Minuten ausgegeben werde
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                        werteSpeichern.setZeitSpeichern(adjustedDateTime.format(formatter));

                        System.out.println("Land: " + country);
                        System.out.println("Ländercode: " + werteSpeichern.getLandesCode());
                        System.out.println("Zeit: " + werteSpeichern.getZeitSpeichern());
                        System.out.println("Kontinent: " + continent);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Kein gültiger Landesname.");
                    System.out.println("Möchten Sie die Eingabe des Landesnamens wiederholen? (Ja/Nein) | Programm beenden (B/b)");
                    String wahl = scanner.nextLine();

                    fehlermeldung(wahl);
                } else {
                    zurueck();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Methode zur Verarbeitung der GMT-Funktion
    public String gmt_funktion(String inputCountryCode) {
        boolean gueltig = false;

        while (!gueltig) {
            if (inputCountryCode == null || inputCountryCode.isEmpty()) {
                System.out.print("Ländercode eingeben: ");
                inputCountryCode = scanner.nextLine().toUpperCase();
            }

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                boolean found = false;
                String line;
                boolean isFirstLine = true;

                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;  // Überspringe die erste Zeile (Spaltenüberschriften)
                    }

                    String[] data = line.split(","); // Die Daten der CSV Datei anhand Kommas trennen
                    String country = data[0].trim(); // CSV Datei: "Land" befindet sich an Index 0
                    String countryCode = data[1].trim(); // CSV Datei: "Ländercode" befindet sich an Index 1
                    String offset = data[2].trim(); // CSV Datei: "Offset" befindet sich an Index 2
                    String continent = data[3].trim(); // CSV Datei: "Kontinent" befindet sich an Index 3

                    if (countryCode.equalsIgnoreCase(inputCountryCode)) {
                        // Berechnung der Zeit mit Sekunden - 1 Stunde da die Lokale Zeit des Projektes in der Schweiz ist
                        ZoneOffset zoneOffset = ZoneOffset.of(offset.replaceAll("[^0-9+-]", ""));
                        LocalDateTime adjustedDateTime = localDateTime.plusSeconds(zoneOffset.getTotalSeconds()).minusHours(1);
                        // Konvertierung der Ausgabe, dass nur Stunden und Minuten ausgegeben werden
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                        werteSpeichern.setZeitSpeichern(adjustedDateTime.format(formatter));

                        System.out.println("Land: " + country);
                        System.out.println("Ländercode: " + countryCode);
                        System.out.println("Zeit des Landescode: " + werteSpeichern.getZeitSpeichern());
                        System.out.println("Kontinent: " + continent);
                        System.out.println("Zeitunterschied: " + offset);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Kein gültiger Ländercode.");
                    System.out.println("Möchten Sie die Eingabe des Ländercodes wiederholen? (Ja/Nein) | Programm beenden (B/b)");
                    String wahl = scanner.nextLine();

                    fehlermeldung(wahl);
                } else {
                    zurueck();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null; // Rückgabe, wenn kein gültiger Ländercode gefunden wurde
    }



    // Methode zur Filterung nach Ländercode und Kontinent
    public void filtrierungNachLeanderNachKontinent(String continent) {
        boolean gueltig = false;
        while (!gueltig) {
            System.out.print("Ländercode eingeben: ");
            String inputCountryCode = scanner.nextLine().toUpperCase();

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                String line;
                boolean isFirstLine = true;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;  // Überspringe die erste Zeile (Spaltenüberschriften)
                    }

                    String[] data = line.split(","); //Die Daten der CSV Datei anhand Kommas Trennen
                    String country = data[0].trim();  // CSV Datei: "Land" befindet sich an Index 0
                    String countryCode = data[1].trim();  // CSV Datei: "Ländercode" befindet sich an Index 1
                    String offset = data[2].trim(); // CSV Datei: "Offset" befindet sich an Index 1
                    String countryContinent = data[3].trim();  // CSV Datei: "Kontinent" befindet sich an Index 3

                    if (countryCode.equalsIgnoreCase(inputCountryCode) && countryContinent.equalsIgnoreCase(continent)) {
                        werteSpeichern.setLandesCode(countryCode);
                        ///Berrechnung der Zeit mit Stunden - 1 Stunde da die Lokale Zeit des Projektes in der Schweiz ist
                        ZoneOffset zoneOffset = ZoneOffset.ofHours(Integer.parseInt(offset));
                        LocalDateTime adjustedDateTime = localDateTime.plusHours(zoneOffset.getTotalSeconds() / 3600).minusHours(1);
                        //Konvertierung der ausgabe, das nur Stunden und Minuten ausgegeben werden
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                        werteSpeichern.setZeitSpeichern(adjustedDateTime.format(formatter));

                        System.out.println("Land: " + country);
                        System.out.println("Ländercode: " + werteSpeichern.getLandesCode());
                        System.out.println("Zeit: " + werteSpeichern.getZeitSpeichern());
                        System.out.println("Kontinent: " + countryContinent);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Kein gültiger Ländercode.");
                    System.out.println("Möchten Sie die Eingabe des Ländercodes wiederholen? (Ja/Nein) | Programm beenden (B/b)");
                    String wahl = scanner.nextLine();

                    fehlermeldung(wahl);
                } else {
                    zurueck();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void leanderCodes_ausgabe(){
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;  // Überspringe die erste Zeile (Spaltenüberschriften)
                }

                String[] data = line.split(","); //Die Daten der CSV Datei anhand Kommas Trennen
                String country = data[0].trim();  // CSV Datei: "Land" befindet sich an Index 0
                String countryCode = data[1].trim();  // CSV Datei: "Ländercode" befindet sich an Index 1

                //Sepziele Formatierung das die Ausgabe zwischen Ländercode und Land so '|' getrent sind
                String output =  String.format("%-2s | %s",countryCode, country);
                System.out.println(output);
            }
            zurueck();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


