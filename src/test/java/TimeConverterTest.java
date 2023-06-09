import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import static org.junit.Assert.*;



class TimeConverterTest {

    TimeConverter timeConverter = new TimeConverter();
    WerteSpeichern werteSpeichern = new WerteSpeichern();
    @Test
    void filtrierungNachLeander() {

    }

    @Test
    void filtrierungNachKontinent() {
    }

    @Test
    void gmt_funktion() {
    }
    void t004() {
        String result = timeConverter.fehlermeldung("b");
        assertEquals(0, result);
    }
}