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
package pl.openkp.business.pracownicy.boundary;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.openkp.business.pracownicy.entity.Pracownik;

@RunWith(Arquillian.class)
public class PracownikResourceTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addPackages(true, "pl.openkp")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
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
        newMember.setDataZatrudnienia(Calendar.getInstance().getTime());
        newMember.setDataZwolnienia(Calendar.getInstance().getTime());
        newMember.setEmail("fasdfasdf");
        newMember.setImie("fasdfasdf");
        newMember.setKosztPodwyzszony(false);
        newMember.setNazwisko("fasdfasfdasf");
        newMember.setTelefon("afsdfasf");
        newMember.setWynagrodzenie(BigDecimal.TEN);
        pracownikResource.nowy(newMember);
        assertNotNull(newMember.getId());

    }
}