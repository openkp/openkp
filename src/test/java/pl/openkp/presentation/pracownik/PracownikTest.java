package pl.openkp.presentation.pracownik;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.angular.findby.FindByNg;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pl.openkp.testutils.ArchiveHelper;

@RunWith(Arquillian.class)
@RunAsClient
public class PracownikTest {

	@Deployment
	public static WebArchive createTestArchive() {
		return ArchiveHelper.fullApp();
	}

	@Drone
	WebDriver browser;

	@ArquillianResource
	URL contextRoot;

	@FindByNg(model = "pracownik.imie")
	WebElement imie;
	@FindByNg(model = "pracownik.nazwisko")
	WebElement nazwisko;
	@FindByNg(model = "pracownik.dataZatrudnienia")
	WebElement dataZatrudnienia;
	@FindByNg(model = "pracownik.wynagrodzenie")
	WebElement wynagrodzenie;
	@FindByNg(model = "pracownik.email")
	WebElement email;
	

	@FindByNg(action = "save()")
	WebElement save;
	@FindByNg(action = "remove()")
	WebElement remove;

	@Test
	public void nowy() {
		browser.navigate().to(contextRoot + "app.html#/pracownik/nowy");
		imie.sendKeys("Jan");
		nazwisko.sendKeys("Nowak");
		dataZatrudnienia.sendKeys("2000-01-01");
		wynagrodzenie.sendKeys("3000");
		Assert.assertTrue(save.isEnabled());
		save.click();
		
		Assert.assertEquals("Jan", imie.getAttribute("value"));
		Assert.assertEquals("Nowak", nazwisko.getAttribute("value"));
		Assert.assertEquals("2000-01-01", dataZatrudnienia.getAttribute("value"));
		Assert.assertEquals("3000", wynagrodzenie.getAttribute("value"));
		
		email.sendKeys("test@gmail.com");
		
		save.click();
		
		Assert.assertEquals("test@gmail.com", email.getAttribute("value"));
		
		remove.click();
		
		Assert.assertEquals("http://127.0.0.1:8080/openkp/app.html#/pracownicy", browser.getCurrentUrl());
	}

}