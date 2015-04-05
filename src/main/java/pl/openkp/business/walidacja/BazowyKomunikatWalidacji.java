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

public abstract class BazowyKomunikatWalidacji implements KomunikatWalidacji {

    private Poziom poziom;
    private String tresc;
    private Object informacje;

    public BazowyKomunikatWalidacji() {
    }

    protected BazowyKomunikatWalidacji(String tresc, Poziom poziom) {
        this(tresc, poziom, null);
    }

    protected BazowyKomunikatWalidacji(String tresc, Poziom poziom, Object informacje) {
        this.tresc = tresc;
        this.poziom = poziom;
        setInformacje(informacje);
    }

    @Override
    public Poziom getPoziom() {
        return poziom;
    }

    public void setPoziom(Poziom poziom) {
        this.poziom = poziom;
    }

    @Override
    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    @Override
    public Object getInformacje() {
        return informacje;
    }

    public void setInformacje(Object informacje) {
        this.informacje = informacje;
    }

    @Override
    public String toString() {
        return getClass().getName() + ": " + getTresc();
    }

}
