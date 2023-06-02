import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TimeConverter {

    String csvFile = "src/main/data/InternationalTimeConverter_Data.csv";

    WerteSpeichern werteSpeichern = new WerteSpeichern();

    Scanner scanner = new Scanner(System.in);

    public TimeConverter() {

    }


    public String fehlermeldung(String input) {
        if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ja")) {
            return "Y";
        } else {
            return "N";
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

                    if (countrycode.equalsIgnoreCase(inputCountryCode)) {
                        werteSpeichern.setLandesCode(countrycode);
                        System.out.println("Country: " + country);
                        System.out.println("Country Code: " + werteSpeichern.getLandesCode());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Kein gültiger Ländercode.");
                    System.out.println("Möchten Sie die Eingabe des Ländercodes wiederholen? (Ja/Nein)");
                    String wahl = scanner.nextLine();

                    if (fehlermeldung(wahl).equals("N")) {
                        System.out.println("Zurück zur Main-Klasse.");
                        Main.main(null);
                    }

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
                    System.out.println("Möchten Sie die Eingabe des Kontinents wiederholen? (Ja/Nein)");
                    String wahl = scanner.nextLine();

                    if (fehlermeldung(wahl).equals("N")) {
                        System.out.println("Zurück zur Main-Klasse.");
                        Main.main(null);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void gmt_funktion() {
        System.out.print("Ländercode eingeben: ");
        String inputCountryCode = scanner.nextLine().toUpperCase();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip header line

            boolean found = false;
            String countryCode = "";
            String countryName = "";
            String offset = "";
            String continent = "";


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

                if (countrycode.equalsIgnoreCase(inputCountryCode)) {
                    countryCode = countrycode;
                    countryName = country;
                    offset = data[2].trim();
                    continent = data[3].trim();
                    found = true;
                    break;
                }
            }


            if (found) {
                LocalDateTime localDateTime = LocalDateTime.now();
                ZoneOffset zoneOffset = ZoneOffset.of(offset.replaceAll("[^0-9+-]", ""));
                LocalDateTime adjustedDateTime = localDateTime.plusSeconds(zoneOffset.getTotalSeconds()).minusHours(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                System.out.println("Country Name: " + countryName);
                System.out.println("Country Code: " + countryCode);
                System.out.println("Offset Adjusted Local Time: " + adjustedDateTime.format(formatter));
                System.out.println("Continent: " + continent);
            } else {
                System.out.println("Country code not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
