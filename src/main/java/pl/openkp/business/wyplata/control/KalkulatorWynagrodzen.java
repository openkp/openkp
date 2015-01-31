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
	private static final BigDecimal PROCENT_SUMA_SPOLECZNE = PROCENT_EMERYTALNA_PRACOWNIKA.add(PROCENT_RENTOWA_PRACOWNIKA).add(PROCENT_CHOROBOWA_PRACOWNIKA);
	private static final BigDecimal KOSZT_PODWYZSZONY = new BigDecimal("139.06");
	private static final BigDecimal KOSZT_ZWYKLY = new BigDecimal("111.25");
	
	@Inject
	EntityManager em;
	
	public Wyplata oblicz(Long pracownikId, Integer rok, Integer miesiac) {
		Pracownik pracownik = em.find(Pracownik.class, pracownikId);
		Wyplata wyplata = new Wyplata();
		wyplata.setWynagrodzenieZasadnicze(pracownik.getWynagrodzenie());
		wyplata.setSkladkaEmerytalnaPracownika(pracownik.getWynagrodzenie().multiply(PROCENT_EMERYTALNA_PRACOWNIKA).divide(new BigDecimal("100.0")).setScale(2, RoundingMode.HALF_UP));
		wyplata.setSkladkaChorobowaPracownika(pracownik.getWynagrodzenie().multiply(PROCENT_CHOROBOWA_PRACOWNIKA).divide(new BigDecimal("100.0")).setScale(2, RoundingMode.HALF_UP));
		wyplata.setSkladkaRentowaPracownika(pracownik.getWynagrodzenie().multiply(PROCENT_RENTOWA_PRACOWNIKA).divide(new BigDecimal("100.0")).setScale(2, RoundingMode.HALF_UP));
		wyplata.setSumaSkladek(wyplata.getSkladkaEmerytalnaPracownika().add(wyplata.getSkladkaChorobowaPracownika()).add(wyplata.getSkladkaRentowaPracownika()));
		wyplata.setPrzychod(pracownik.getWynagrodzenie().subtract(wyplata.getSumaSkladek()));
		wyplata.setKosztyUzyskaniaPrzychodu(pracownik.isKosztPodwyzszony() ? KOSZT_PODWYZSZONY : KOSZT_ZWYKLY);
		wyplata.setPodstawa(wyplata.getPrzychod().subtract(wyplata.getKosztyUzyskaniaPrzychodu()).setScale(0, RoundingMode.HALF_UP));
		wyplata.setZaliczkaPodatkowa(wyplata.getPodstawa().multiply(new BigDecimal("18.0")).divide(new BigDecimal("100.0")).subtract(new BigDecimal("46.34")).setScale(2, RoundingMode.HALF_UP));
		wyplata.setSkladkaZdrowotna(wyplata.getPrzychod().multiply(new BigDecimal("9.0")).divide(new BigDecimal("100.0")).setScale(2, RoundingMode.HALF_UP));
		wyplata.setSkladkaZdrowotnaOdliczona(wyplata.getPrzychod().multiply(new BigDecimal("7.75")).divide(new BigDecimal("100.0")).setScale(2, RoundingMode.HALF_UP));
		wyplata.setPodatek(wyplata.getZaliczkaPodatkowa().subtract(wyplata.getSkladkaZdrowotnaOdliczona()).setScale(0, RoundingMode.HALF_UP));
		wyplata.setWyngrodzenieNetto(pracownik.getWynagrodzenie().subtract(wyplata.getSumaSkladek()).subtract(wyplata.getSkladkaZdrowotna()).subtract(wyplata.getPodatek()).setScale(2, RoundingMode.HALF_UP));
		return wyplata;
	}

}
