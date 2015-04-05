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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import pl.openkp.business.pracownicy.entity.Pracownik;

@Stateless
@Path("/pracownik")
public class PracownikResource {

    private static final Logger LOG = Logger.getLogger(PracownikResource.class.getName());

    @PersistenceContext(unitName = "openkp-persistence-unit")
    private EntityManager em;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    // @Interceptors({ ZapiszPracownikaWalidator.class })
    public Response nowy(@Valid Pracownik entity) {
        em.persist(entity);
        return Response.created(UriBuilder.fromResource(PracownikResource.class).path(String.valueOf(entity.getId())).build()).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response usun(@PathParam("id") Long id) {
        Pracownik entity = em.find(Pracownik.class, id);
        if (entity == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        em.remove(entity);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pracownik(@PathParam("id") Long id) {
        Pracownik pracownik = znajdz(id);
        if (pracownik == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(pracownik).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pracownik> pracownicy(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {
        TypedQuery<Pracownik> findAllQuery = em.createNamedQuery(Pracownik.PRACOWNICY, Pracownik.class);
        if (startPosition != null) {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null) {
            findAllQuery.setMaxResults(maxResult);
        }
        return findAllQuery.getResultList();
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response aktualizuj(Pracownik entity) {
        try {
            em.merge(entity);
        } catch (OptimisticLockException e) {
            LOG.log(Level.FINER, "Próba aktualizacji zmodyfikowanego rekordu", e);
            return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
        }

        return Response.noContent().build();
    }

    private Pracownik znajdz(Long id) {
        Pracownik pracownik = null;
        try {
            pracownik = em.find(Pracownik.class, id);
        } catch (EntityNotFoundException ex) {
            LOG.log(Level.FINER, "Brak pracownika o id " + id, ex);
            pracownik = null;
        }
        return pracownik;
    }
}
