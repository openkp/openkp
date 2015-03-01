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
package pl.openkp.business.absencje.boundary;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import pl.openkp.business.absencje.entity.Absencja;
import pl.openkp.business.absencje.entity.TypAbsencji;
import pl.openkp.business.pracownicy.entity.Pracownik;

@Stateless
@Path("/absencja")
public class AbsencjaResource {

    private static final Logger LOG = Logger.getLogger(AbsencjaResource.class.getName());

    @PersistenceContext(unitName = "openkp-persistence-unit")
    private EntityManager em;

    @GET
    @Path("/{pracownikId:[0-9][0-9]*}/absencja{p:/?}{absencjaId:([0-9]*)}")
    @Produces("application/json")
    public JsonArray absencje(@PathParam("pracownikId") Long pracownikId, @PathParam("absencjaId") String absencjaId) {
        Pracownik entity = em.find(Pracownik.class, pracownikId);
        JsonArrayBuilder builder = Json.createArrayBuilder();
        if (entity == null) {
            return builder.build();
        }

        for (Absencja absencja : entity.getAbsencje()) {
            builder.add(buildAbsencja(absencja));
        }
        return builder.build();
    }

    @DELETE
    @Path("/{pracownikId:[0-9][0-9]*}/absencja{p:/?}{absencjaId:([0-9]*)}")
    @Produces("application/json")
    public JsonObject usun(@PathParam("pracownikId") Long pracownikId, @PathParam("absencjaId") String absencjaId) {
        em.remove(em.find(Absencja.class, Long.parseLong(absencjaId)));
        return Json.createObjectBuilder().add(Absencja.PROP_ID, absencjaId).build();
    }

    @POST
    @Path("/{pracownikId:[0-9][0-9]*}/absencja")
    @Produces("application/json")
    public JsonObject zapisz(@PathParam("pracownikId") Long pracownikId, JsonObject entity) {
        Absencja absencja = new Absencja();
        absencja.setDataOd(new GregorianCalendar(entity.getInt("rokOd"), entity.getInt("miesiacOd"), entity.getInt("dzienOd")).getTime());
        absencja.setDataDo(new GregorianCalendar(entity.getInt("rokDo"), entity.getInt("miesiacDo"), entity.getInt("dzienDo")).getTime());
        absencja.setPracownik(em.getReference(Pracownik.class, pracownikId));
        absencja.setTypAbsencji(TypAbsencji.fromOpis(entity.getString("title")));
        absencja.setId(asLong(entity.get(Absencja.PROP_ID) == null ? null : entity.get(Absencja.PROP_ID).toString()));
        absencja.setVersion(entity.get(Absencja.PROP_VERSION) == null ? 0 : entity.getInt(Absencja.PROP_VERSION));

        if (absencja.getId() == null) {
            em.persist(absencja);
        } else {
            absencja = em.merge(absencja);
        }
        em.flush();
        return buildAbsencja(absencja).build();
    }

    private Long asLong(String string) {
        if (string == null || string.trim().isEmpty()) {
            return null;
        }
        return Long.parseLong(string);
    }

    private JsonObjectBuilder buildAbsencja(Absencja absencja) {
        Calendar cal = (Calendar) asCalendar(absencja.getDataDo()).clone();
        if (!absencja.getDataOd().equals(absencja.getDataDo())) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return Json.createObjectBuilder().add("id", absencja.getId()).add("title", absencja.getTypAbsencji().getOpis())
                .add("start", javax.xml.bind.DatatypeConverter.printDateTime(asCalendar(absencja.getDataOd())))
                .add("end", javax.xml.bind.DatatypeConverter.printDateTime(cal))
                .add(Absencja.PROP_DATA_OD, javax.xml.bind.DatatypeConverter.printDateTime(asCalendar(absencja.getDataOd())))
                .add(Absencja.PROP_DATA_DO, javax.xml.bind.DatatypeConverter.printDateTime(asCalendar(absencja.getDataDo()))).add("allDay", true)
                .add(Absencja.PROP_VERSION, absencja.getVersion());
    }

    private Calendar asCalendar(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        return cal;
    }

}
