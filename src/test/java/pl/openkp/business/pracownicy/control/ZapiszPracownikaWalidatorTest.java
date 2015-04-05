package pl.openkp.business.pracownicy.control;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pl.openkp.business.pracownicy.entity.Pracownik;
import pl.openkp.business.walidacja.WynikWalidacji;

public class ZapiszPracownikaWalidatorTest {

    @Test
    public void testWaliduj() {
        ZapiszPracownikaWalidator walidator = new ZapiszPracownikaWalidator();
        WynikWalidacji wynikWalidacji = walidator.waliduj(new Object[] { new Pracownik() });

        assertEquals("Brak daty zatrudnienia!", wynikWalidacji.getBledy().get(0).getTresc());
        assertEquals("Brak imienia pracownika!", wynikWalidacji.getBledy().get(1).getTresc());
        assertEquals("Brak nazwiska pracownika!", wynikWalidacji.getBledy().get(2).getTresc());
        assertEquals("Brak wynagrodzenia pracownika!", wynikWalidacji.getBledy().get(3).getTresc());
    }

}
