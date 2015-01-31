package pl.openkp.business.wyplata.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pl.openkp.business.wyplata.control.KalkulatorWynagrodzen;
import pl.openkp.business.wyplata.entity.Wyplata;

@Path("/wyplaty")
@Stateless
public class WyplataResource {

	@Inject
	KalkulatorWynagrodzen kalkulatorWynagrodzen;
	
	@GET
	@Path("/{pracownikId:[0-9][0-9]*}/{rok:[0-9][0-9]*}/{miesiac:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Wyplata oblicz(@PathParam("pracownikId") Long pracownikId, @PathParam("rok") Integer rok, @PathParam("miesiac") Integer miesiac) {
		return kalkulatorWynagrodzen.oblicz(pracownikId, rok, miesiac);
	}
}
