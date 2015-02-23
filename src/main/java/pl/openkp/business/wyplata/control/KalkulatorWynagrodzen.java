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
package pl.openkp.business.wyplata.control;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import pl.openkp.business.pracownicy.entity.Pracownik;
import pl.openkp.business.wyplata.entity.Wyplata;

@RequestScoped
public class KalkulatorWynagrodzen {

    private static final BigDecimal PROCENT_EMERYTALNA_PRACOWNIKA = new BigDecimal("9.76");
    private static final BigDecimal PROCENT_RENTOWA_PRACOWNIKA = new BigDecimal("1.5");
    private static final BigDecimal PROCENT_CHOROBOWA_PRACOWNIKA = new BigDecimal("2.45");
    private static final BigDecimal KOSZT_PODWYZSZONY = new BigDecimal("139.06");
    private static final BigDecimal KOSZT_ZWYKLY = new BigDecimal("111.25");
    private static final BigDecimal STO = new BigDecimal("100.0");

    @Inject
    EntityManager em;

    public Wyplata oblicz(Long pracownikId, Integer rok, Integer miesiac) {
        Pracownik pracownik = em.find(Pracownik.class, pracownikId);
        Wyplata wyplata = new Wyplata();
        wyplata.setWynagrodzenieZasadnicze(pracownik.getWynagrodzenie());
        wyplata.setSkladkaEmerytalnaPracownika(pracownik.getWynagrodzenie().multiply(PROCENT_EMERYTALNA_PRACOWNIKA).divide(STO)
                .setScale(2, RoundingMode.HALF_UP));
        wyplata.setSkladkaChorobowaPracownika(pracownik.getWynagrodzenie().multiply(PROCENT_CHOROBOWA_PRACOWNIKA).divide(STO)
                .setScale(2, RoundingMode.HALF_UP));
        wyplata.setSkladkaRentowaPracownika(pracownik.getWynagrodzenie().multiply(PROCENT_RENTOWA_PRACOWNIKA).divide(STO)
                .setScale(2, RoundingMode.HALF_UP));
        wyplata.setSumaSkladek(wyplata.getSkladkaEmerytalnaPracownika().add(wyplata.getSkladkaChorobowaPracownika())
                .add(wyplata.getSkladkaRentowaPracownika()));
        wyplata.setPrzychod(pracownik.getWynagrodzenie().subtract(wyplata.getSumaSkladek()));
        wyplata.setKosztyUzyskaniaPrzychodu(pracownik.isKosztPodwyzszony() ? KOSZT_PODWYZSZONY : KOSZT_ZWYKLY);
        wyplata.setPodstawa(wyplata.getPrzychod().subtract(wyplata.getKosztyUzyskaniaPrzychodu()).setScale(0, RoundingMode.HALF_UP));
        wyplata.setZaliczkaPodatkowa(wyplata.getPodstawa().multiply(new BigDecimal("18.0")).divide(STO).subtract(new BigDecimal("46.34"))
                .setScale(2, RoundingMode.HALF_UP));
        wyplata.setSkladkaZdrowotna(wyplata.getPrzychod().multiply(new BigDecimal("9.0")).divide(STO).setScale(2, RoundingMode.HALF_UP));
        wyplata.setSkladkaZdrowotnaOdliczona(wyplata.getPrzychod().multiply(new BigDecimal("7.75")).divide(STO).setScale(2, RoundingMode.HALF_UP));
        wyplata.setPodatek(wyplata.getZaliczkaPodatkowa().subtract(wyplata.getSkladkaZdrowotnaOdliczona()).setScale(0, RoundingMode.HALF_UP));
        wyplata.setWyngrodzenieNetto(pracownik.getWynagrodzenie().subtract(wyplata.getSumaSkladek()).subtract(wyplata.getSkladkaZdrowotna())
                .subtract(wyplata.getPodatek()).setScale(2, RoundingMode.HALF_UP));
        return wyplata;
    }

}
