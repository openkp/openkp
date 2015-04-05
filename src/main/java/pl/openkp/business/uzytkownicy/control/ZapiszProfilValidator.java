package pl.openkp.business.uzytkownicy.control;

import pl.openkp.business.walidacja.Walidator;
import pl.openkp.business.walidacja.WynikWalidacji;

public class ZapiszProfilValidator extends Walidator {

    @Override
    protected WynikWalidacji waliduj(Object[] parameters) {
        WynikWalidacji result = new WynikWalidacji();
        return result;
    }

}
