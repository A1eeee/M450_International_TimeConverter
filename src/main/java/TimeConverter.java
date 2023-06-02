import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TimeConverter {

    String csvFile = "src/main/data/InternationalTimeConverter_Data.csv";

    WerteSpeichern werteSpeichern = new WerteSpeichern();

    Scanner scanner = new Scanner(System.in);

    LocalDateTime localDateTime = LocalDateTime.now();

    public TimeConverter() {

    }

    public String fehlermeldung(String input) {

            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ja")) {
                return "Y";
            } else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("nein")) {
                Main.main(null);
                return "N";
            } else if (input.equalsIgnoreCase("b")) {
                System.exit(0);
            }
            else {
                System.out.println("Keine gueltige eingabe!");
                System.out.println("Eingabe wiederholen: ");
                String inputW = scanner.nextLine();

                fehlermeldung(inputW);
            }

            return null;
    }

    public String weiterFuehren(String input){
        if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("Ja")){
            return "Y";
        } else if (input.equalsIgnoreCase("no") || input.equalsIgnoreCase("nein")) {
            Main.main(null);
            return "M";
        }else if (input.equalsIgnoreCase("b")) {
            System.exit(0);
            return "B";
        }else {
            System.out.println("Ungültige Eingabe!");
            System.out.println("Bitte geben Sie eine gültige Option ein.");
            return weiterFuehren(scanner.nextLine());
        }
    }

    public void zurueck(){

        System.out.println("Wollen sie einen neuen Prozess starten? (Ja | Nein -> ) | Programm beenden (Nein/b/B)");
        String auswahl = scanner.nextLine();

        if(auswahl.equalsIgnoreCase("yes") || auswahl.equalsIgnoreCase("ja")){
            Main.main(null);
        } else if (auswahl.equalsIgnoreCase("nein") || auswahl.equalsIgnoreCase("no") || auswahl.equalsIgnoreCase("b")) {
            System.exit(0);
        }else {
            System.out.println("Ungültige Eingabe!");
            System.out.println("Bitte geben Sie eine gültige Option ein.");
            zurueck();
        }

    }


    public void filtrierungNachLeander() {

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
                        continue;  // Skip the first line (column headers)
                    }

                    String[] data = line.split(",");
                    String country = data[0].trim();  // Assuming "Land" is at index 0
                    String countrycode = data[1].trim();  // Assuming "Ländercode" is at index 1
                    String offset = data[2].trim();

                    if (countrycode.equalsIgnoreCase(inputCountryCode)) {
                        werteSpeichern.setLandesCode(countrycode);
                        ZoneOffset zoneOffset = ZoneOffset.ofHours(Integer.parseInt(offset));
                        LocalDateTime adjustedDateTime = localDateTime.plusHours(zoneOffset.getTotalSeconds() / 3600).minusHours(1);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                        System.out.println("Country: " + country);
                        System.out.println("Country Code: " + werteSpeichern.getLandesCode());
                        System.out.println("Time: " + adjustedDateTime.format(formatter));
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Kein gültiger Ländercode.");
                    System.out.println("Möchten Sie die Eingabe des Ländercodes wiederholen? (Ja/Nein) | Program beenden (B/b)");
                    String wahl = scanner.nextLine();

                    fehlermeldung(wahl);

                }
                else {
                    zurueck();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void filtrierungNachKontinent() {

        boolean gueltig = false;

        while (!gueltig) {
            System.out.print("Kontinent eingeben: ");
            String inputContinent = scanner.nextLine().toUpperCase();
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

                String line;
                boolean isFirstLine = true;
                boolean found = false;

                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;  // Skip the first line (column headers)
                    }

                    String[] data = line.split(",");
                    String country = data[0].trim();
                    String continent = data[3].trim();
                    String countryCode = data[1].trim();


                    if (continent.equalsIgnoreCase(inputContinent)) {
                        werteSpeichern.setLandesCode(countryCode);
                        String output = String.format("Country: %-2s | %s", werteSpeichern.getLandesCode(), country);
                        System.out.println(output);
                        found = true;

                    }
                }


                if (!found) {
                    System.out.println("Kein gültiges Kontinent.");
                    System.out.println("Möchten Sie die Eingabe des Kontinentes wiederholen? (Ja/Nein) | Program beenden (B/b)");
                    String wahl = scanner.nextLine();

                    fehlermeldung(wahl);

                }
                else {
                    System.out.println("Wohlen sie nach einem Land Filtrieren? (Ja/Nein) | Program beenden (B/b)");


                    while (true){
                        String weiterWahl = scanner.nextLine();
                        if(weiterFuehren(weiterWahl).equals("Y")){
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

    }

    public void gmt_funktion() {

        boolean gueltig = false;

        while (!gueltig){
            System.out.print("Ländercode eingeben: ");
            String inputCountryCode = scanner.nextLine().toUpperCase();
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                br.readLine(); // Skip header line

                boolean found = false;

                String line;
                boolean isFirstLine = true;

                while ((line = br.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;  // Skip the first line (column headers)
                    }

                    String[] data = line.split(",");
                    String country = data[0].trim();
                    String countrycode = data[1].trim();
                    String offset = data[2].trim();
                    String continent = data[3].trim();

                    if (countrycode.equalsIgnoreCase(inputCountryCode)) {

                        ZoneOffset zoneOffset = ZoneOffset.of(offset.replaceAll("[^0-9+-]", ""));
                        LocalDateTime adjustedDateTime = localDateTime.plusSeconds(zoneOffset.getTotalSeconds()).minusHours(1);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                        System.out.println("Country Name: " + country);
                        System.out.println("Country Code: " + countrycode);
                        System.out.println("Offset Adjusted Local Time: " + adjustedDateTime.format(formatter));
                        System.out.println("Continent: " + continent);
                        System.out.println("Zeit unterschied:" + offset);
                        found = true;
                        break;
                    }
                }


                if (!found) {
                    System.out.println("Kein gültiger Ländercode.");
                    System.out.println("Möchten Sie die Eingabe des Ländercodes wiederholen? (Ja/Nein) | Program beenden (B/b)");
                    String wahl = scanner.nextLine();

                    fehlermeldung(wahl);

                }else {
                    zurueck();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
                        continue;  // Skip the first line (column headers)
                    }

                    String[] data = line.split(",");
                    String country = data[0].trim();  // Assuming "Land" is at index 0
                    String countryCode = data[1].trim();  // Assuming "Ländercode" is at index 1
                    String countryContinent = data[3].trim();  // Assuming "Kontinent" is at index 3

                    if (countryCode.equalsIgnoreCase(inputCountryCode) && countryContinent.equalsIgnoreCase(continent)) {
                        werteSpeichern.setLandesCode(countryCode);
                        System.out.println("Country: " + country);
                        System.out.println("Country Code: " + werteSpeichern.getLandesCode());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Kein gültiger Ländercode für den ausgewählten Kontinent.");
                    System.out.println("Möchten Sie die Eingabe des Ländercodes wiederholen? (Ja/Nein) | Programm beenden (B/b)");
                    String wahl = scanner.nextLine();

                    fehlermeldung(wahl);
                }else {
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
                    continue;  // Skip the first line (column headers)
                }

                String[] data = line.split(",");
                String countrycode = data[1].trim();
                String country = data[0].trim();

                System.out.println(countrycode + " " + country);


            }
            zurueck();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
