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
package pl.openkp.business.uzytkownicy.boundary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

import pl.openkp.business.uzytkownicy.entity.Uzytkownik;

@RunWith(Arquillian.class)
public class UzytkownikResourceTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addPackages(true, "pl.openkp")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    UzytkownikResource uzytkownikResource;

    @Test
    public void testNowy() throws Exception {
        Uzytkownik encja = utworz();
        uzytkownikResource.zapisz(encja);
        assertNotNull(encja.getId());

    }

    @Test
    public void testUsun() throws Exception {
        Uzytkownik encja = utworz();
        uzytkownikResource.zapisz(encja);
        assertNotNull(encja.getId());
        Response response = uzytkownikResource.usun(encja.getId());
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        response = uzytkownikResource.uzytkownik(encja.getId());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    private Uzytkownik utworz() throws IOException {
        Uzytkownik encja = new Uzytkownik();
        encja.setEmail("test@wp.pl");
        encja.setHaslo("xyz".getBytes());
        encja.setImie("Jan");
        encja.setLogin("jkowalski");
        encja.setNazwisko("Kowalski");
        encja.setZdjecie(Files.readAllBytes(Paths.get("src/test/resources/avatar2.png")));
        return encja;
    }
}