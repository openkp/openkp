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

import pl.openkp.test.utils.ArchiveHelper;

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
        dataZatrudnienia.sendKeys("01.01.2000");
        wynagrodzenie.sendKeys("3000");
        Assert.assertTrue(save.isEnabled());
        save.click();

        Assert.assertEquals("Jan", imie.getAttribute("value"));
        Assert.assertEquals("Nowak", nazwisko.getAttribute("value"));
        Assert.assertEquals("01.01.2000", dataZatrudnienia.getAttribute("value"));
        Assert.assertEquals("3000", wynagrodzenie.getAttribute("value"));

        email.sendKeys("test@gmail.com");

        save.click();

        Assert.assertEquals("test@gmail.com", email.getAttribute("value"));

        remove.click();

        Assert.assertEquals("http://127.0.0.1:8080/openkp/app.html#/pracownicy", browser.getCurrentUrl());
    }

}