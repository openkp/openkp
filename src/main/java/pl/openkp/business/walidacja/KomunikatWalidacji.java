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
package pl.openkp.business.walidacja;

public interface KomunikatWalidacji {

    /**
     * Zwraca poziom komunikatu walidacji. Może to być błąd lub ostrzeżenie
     * 
     * @return
     */
    Poziom getPoziom();

    /**
     * Zwraca tekst komunikatu walidacji
     * 
     * @return
     */
    String getTresc();

    /**
     * Zwraca informacje dodatkowe na temat komunikatu walidacji. Może to być
     * walidowany obiekt
     * 
     * @return
     */
    Object getInformacje();
}
