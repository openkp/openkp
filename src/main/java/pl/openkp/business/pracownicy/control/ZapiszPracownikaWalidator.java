/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.openkp.business.pracownicy.control;

import pl.openkp.business.pracownicy.entity.Pracownik;
import pl.openkp.business.walidacja.Walidator;
import pl.openkp.business.walidacja.WynikWalidacji;

public class ZapiszPracownikaWalidator extends Walidator {

    @Override
    protected WynikWalidacji waliduj(Object[] parameters) {
        WynikWalidacji result = new WynikWalidacji();
        Pracownik pracownik = (Pracownik) parameters[0];
        if (pracownik.getDataZatrudnienia() == null) {
            result.dodajBlad("Brak daty zatrudnienia!");
        }
        if (pracownik.getImie() == null || pracownik.getImie().trim().isEmpty()) {
            result.dodajBlad("Brak imienia pracownika!");
        }
        if (pracownik.getNazwisko() == null || pracownik.getNazwisko().trim().isEmpty()) {
            result.dodajBlad("Brak nazwiska pracownika!");
        }
        if (pracownik.getWynagrodzenie() == null) {
            result.dodajBlad("Brak wynagrodzenia pracownika!");
        }
        return result;
    }

}
