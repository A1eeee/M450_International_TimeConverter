import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;



class TimeConverterTest {

    TimeConverter timeConverter = new TimeConverter();

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;
    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }


    @Test
    void fehlermeldung() {
        // Arrange
        String input = "b";
        testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
        timeConverter.werteSpeichern.setNumberSpeicher(0); // Beispielwert

        // Act
        String result = timeConverter.fehlermeldung(input);

        // Assert
        assertNull("Der Rückgabewert sollte null sein.", result);
        assertEquals("Die NumberSpeicher-Variable sollte auf 0 gesetzt sein.", 0, timeConverter.werteSpeichern.getNumberSpeicher());
        // Überprüfe die Ausgabe
        assertEquals("Es sollte keine Ausgabe geben.", 0, testOut.toString().length());
    }

    @Test
    void filtrierungNachLeander() {
    }

    @Test
    void filtrierungNachKontinent() {
    }

    @Test
    void gmt_funktion() {
    }
}