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


    @Test
    void t001() {

        String wert = timeConverter.filtrierungNachKontinent("Europa");

        assertEquals("VA", wert);

    }

    @Test
    void t002() {

       String wert = timeConverter.filtrierungNachLeander("CH");


        assertEquals("Schweiz", wert);

    }

    @Test
    void t003() {

        String wert = timeConverter.gmt_funktion("CH");

        assertEquals("Schweiz", wert);
    }

    @Test
    void t004(){

        String wert = timeConverter.fehlermeldung("B");

        assertEquals(0, wert);

    }
}