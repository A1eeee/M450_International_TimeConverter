import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        TimeConverter timeConverter = new TimeConverter();

        boolean gueltig = false;

        Scanner scanner = new Scanner(System.in);


        //Dient um die Console zu Clearen doch leider Funktioniert dies nicht bei der Konsolen in einer IDE wie IntelliJ
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Schleife, um das Menü anzuzeigen und Benutzereingaben zu verarbeiten
        while (!gueltig){

            System.out.println("Zahl 1: Filtrierung nach Land anhand eines Ländercodes!\n" +
                    "Zahl 2: Filtrierung nach Kontinent!\n" +
                    "Zahl 3: GMT Zeit berechnen anhand eines Ländercodes!\n" +
                    "Zahl 4: Alle gültigen Ländercodes ausgeben!\n" +
                    "B/b: Programm beenden!\n");

            System.out.print("Wählen sie: ");
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
                    System.out.println("Möchten Sie die Eingabe der Auswahl wiederholen? (Ja/Nein) | Program beenden (B/b)");
                    String inputAuswahlW = scanner.nextLine();

                    if(inputAuswahlW.equalsIgnoreCase("nein") || inputAuswahlW.equalsIgnoreCase("no")){
                        System.exit(0);
                    }
                    timeConverter.fehlermeldung(inputAuswahlW);
            }

        }


    }
}
