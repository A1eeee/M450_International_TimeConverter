import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class TimeConverter {

    String csvFile = "src/main/data/InternationalTimeConverter_Data.csv";

    public TimeConverter(){

    }

    public void filtrierungNachLeander(String x){

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

                if (countrycode.equalsIgnoreCase(x)) {
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

    }

    public void filtrierungNachKontinent(String x){

    }

    public void gmt_funktion(String x){

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

                System.out.println(countrycode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
