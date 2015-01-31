package pl.openkp.business.pracownicy.boundary;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.openkp.Resources;
import pl.openkp.business.absencje.entity.Absencja;
import pl.openkp.business.absencje.entity.TypAbsencji;
import pl.openkp.business.pracownicy.entity.Pracownik;
import pl.openkp.business.wyplata.control.KalkulatorWynagrodzen;
import pl.openkp.business.wyplata.entity.Wyplata;

@RunWith(Arquillian.class)
public class PracownikResourceTest {
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackages(true, "pl.openkp")
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml");
	}

	@Inject
	PracownikResource pracownikResource;
	@Inject
	Logger log;

	@Test
	public void testRegister() throws Exception {
		Pracownik newMember = new Pracownik();
		newMember.setDataZatrudnienia(new Date());
		newMember.setDataZwolnienia(new Date());
		newMember.setEmail("fasdfasdf");
		newMember.setImie("fasdfasdf");
		newMember.setKosztPodwyzszony(false);
		newMember.setNazwisko("fasdfasfdasf");
		newMember.setTelefon("afsdfasf");
		newMember.setWynagrodzenie(BigDecimal.TEN);
		pracownikResource.create(newMember);
		assertNotNull(newMember.getId());
		
	}
}