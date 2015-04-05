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
package pl.openkp.business.wyplata.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pl.openkp.business.wyplata.control.KalkulatorWynagrodzen;
import pl.openkp.business.wyplata.entity.Wyplata;

@Path("/wyplata")
@Stateless
public class WyplataResource {

    @Inject
    KalkulatorWynagrodzen kalkulatorWynagrodzen;

    @GET
    @Path("/{pracownikId:[0-9][0-9]*}/{rok:[0-9][0-9]*}/{miesiac:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Wyplata oblicz(
            @PathParam("pracownikId") Long pracownikId,
            @PathParam("rok") Integer rok,
            @Valid @PathParam("miesiac") @Min(value = 0, message = "Minimalna wartość dla miesiąca to 0") @Max(value = 11, message = "Maksymalna wartość dla miesiąca to 11") Integer miesiac) {
        return kalkulatorWynagrodzen.oblicz(pracownikId, rok, miesiac);
    }
}
