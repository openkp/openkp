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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import pl.openkp.business.absencje.entity.Absencja;
import pl.openkp.business.absencje.entity.TypAbsencji;
import pl.openkp.business.pracownicy.entity.Pracownik;
import pl.openkp.business.wyplata.control.KalkulatorWynagrodzen;
import pl.openkp.business.wyplata.entity.Wyplata;

/**
 * 
 */
@Stateless
@Path("/pracownik")
public class PracownikResource {

    private static final Logger LOG = Logger.getLogger(PracownikResource.class.getName());

    @PersistenceContext(unitName = "openkp-persistence-unit")
    private EntityManager em;

    @Inject
    private KalkulatorWynagrodzen kalkulatorWynagrodzen;

    @POST
    @Consumes("application/json")
    public Response create(Pracownik entity) {
        em.persist(entity);
        return Response.created(UriBuilder.fromResource(PracownikResource.class).path(String.valueOf(entity.getId())).build()).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") Long id) {
        Pracownik entity = em.find(Pracownik.class, id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        em.remove(entity);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces("application/json")
    public Response findById(@PathParam("id") Long id) {
        TypedQuery<Pracownik> findByIdQuery = em.createQuery("SELECT DISTINCT p FROM Pracownik p WHERE p.id = :entityId ORDER BY p.id",
                Pracownik.class);
        findByIdQuery.setParameter("entityId", id);
        Pracownik entity;
        try {
            entity = findByIdQuery.getSingleResult();
        } catch (NoResultException nre) {
            LOG.fine("Brak pracownika o id " + id);
            entity = null;
        }
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(entity).build();
    }

    @GET
    @Produces("application/json")
    public List<Pracownik> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {
        TypedQuery<Pracownik> findAllQuery = em.createQuery("SELECT DISTINCT p FROM Pracownik p LEFT JOIN FETCH p.absencje ORDER BY p.id",
                Pracownik.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        final List<Pracownik> results = findAllQuery.getResultList();
        return results;
    }

    @GET
    @Path("/{pracownikId:[0-9][0-9]*}/absencja{p:/?}{absencjaId:([0-9]*)}")
    @Produces("application/json")
    public JsonArray absencje(@PathParam("pracownikId") Long pracownikId, @PathParam("absencjaId") String absencjaId) {
        TypedQuery<Pracownik> findByIdQuery = em.createQuery(
                "SELECT DISTINCT p FROM Pracownik p LEFT JOIN FETCH p.absencje WHERE p.id = :entityId ORDER BY p.id", Pracownik.class);
        findByIdQuery.setParameter("entityId", pracownikId);
        Pracownik entity;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        try {
            entity = findByIdQuery.getSingleResult();
        } catch (NoResultException nre) {
            entity = null;
        }
        if (entity == null) {
            return builder.build();
        }

        for (Absencja absencja : entity.getAbsencje()) {
            builder.add(buildAbsencja(absencja));
        }
        return builder.build();
    }

    private JsonObjectBuilder buildAbsencja(Absencja absencja) {
        Calendar cal = (Calendar) absencja.getDataDo().clone();
        if (!absencja.getDataOd().equals(absencja.getDataDo())) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return Json.createObjectBuilder().add("id", absencja.getId()).add("title", absencja.getTypAbsencji().getOpis())
                .add("start", javax.xml.bind.DatatypeConverter.printDateTime(absencja.getDataOd()))
                .add("end", javax.xml.bind.DatatypeConverter.printDateTime(cal))
                .add("dataOd", javax.xml.bind.DatatypeConverter.printDateTime(absencja.getDataOd()))
                .add("dataDo", javax.xml.bind.DatatypeConverter.printDateTime(absencja.getDataDo())).add("allDay", true)
                .add("version", absencja.getVersion());
    }

    @DELETE
    @Path("/{pracownikId:[0-9][0-9]*}/absencja{p:/?}{absencjaId:([0-9]*)}")
    @Produces("application/json")
    public JsonObject usunAbsencje(@PathParam("pracownikId") Long pracownikId, @PathParam("absencjaId") String absencjaId) {
        em.remove(em.find(Absencja.class, Long.parseLong(absencjaId)));
        return Json.createObjectBuilder().add("id", absencjaId).build();
    }

    @POST
    @Path("/{pracownikId:[0-9][0-9]*}/absencja")
    @Produces("application/json")
    public JsonObject zapiszAbsencje(@PathParam("pracownikId") Long pracownikId, JsonObject entity) {
        Absencja absencja = new Absencja();
        absencja.setDataOd(new GregorianCalendar(entity.getInt("rokOd"), entity.getInt("miesiacOd"), entity.getInt("dzienOd")));
        absencja.setDataDo(new GregorianCalendar(entity.getInt("rokDo"), entity.getInt("miesiacDo"), entity.getInt("dzienDo")));
        absencja.setPracownik(em.getReference(Pracownik.class, pracownikId));
        absencja.setTypAbsencji(TypAbsencji.fromOpis(entity.getString("title")));
        absencja.setId(asLong(entity.get("id") == null ? null : entity.get("id").toString()));
        absencja.setVersion(entity.get("version") == null ? 0 : entity.getInt("version"));

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

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes("application/json")
    public Response update(Pracownik entity) {
        try {
            em.merge(entity);
        } catch (OptimisticLockException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
        }

        return Response.noContent().build();
    }

    @GET
    @Path("/{pracownikId:[0-9][0-9]*}/wyplata/{miesiac:[0-9][0-9]*}/{rok:[0-9][0-9]*}")
    @Produces("application/json")
    public Wyplata wyplata(@PathParam("pracownikId") long pracownikId, @PathParam("miesiac") int miesiac, @PathParam("rok") int rok) {
        return kalkulatorWynagrodzen.oblicz(pracownikId, rok, miesiac);
    }
}
