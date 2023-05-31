import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String csvFile = "src/main/data/InternationalTimeConverter_Data.csv";  // Replace with the path to your CSV file

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter a Ländercode: ");
            String inputCountryCode = scanner.nextLine().toUpperCase();

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
                    System.out.println("Country: " + country);
                    System.out.println("Country Code: " + countrycode);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("No information found for the entered Ländercode.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;  // Skip the first line (column headers)
                }

                String[] data = line.split(",");
                String country = data[0].trim();  // Assuming "Land" is at index 0
                String countrycode = data[1].trim();  // Assuming "Ländercode" is at index 1

                System.out.println(country + ", " + countrycode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
