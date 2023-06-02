import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        TimeConverter timeConverter = new TimeConverter();


        Scanner scanner = new Scanner(System.in);
        System.out.print("Wählen sie eine Zahl von 1 - 4: ");
        String inputNumber = scanner.nextLine();


        switch (inputNumber){

                case "1":
                    timeConverter.filtrierungNachLeander();
                    break;

                case "2":
                    timeConverter.filtrierungNachKontinent();
                    break;

                case "3":
                    timeConverter.gmt_funktion();
                    break;

                case "4":
                    timeConverter.leanderCodes_ausgabe();
                    break;

                case "b":
                    System.exit(0);
                    break;

                case "B":
                    System.exit(0);
                    break;

                default:
            }

    }
}
