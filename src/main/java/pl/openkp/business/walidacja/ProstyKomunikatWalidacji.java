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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProstyKomunikatWalidacji extends BazowyKomunikatWalidacji {

    public ProstyKomunikatWalidacji() {

    }

    public ProstyKomunikatWalidacji(String text) {
        this(text, Poziom.OSTRZEZENIE);
    }

    public ProstyKomunikatWalidacji(String text, Poziom poziom) {
        this(text, poziom, null);
    }

    public ProstyKomunikatWalidacji(String text, Poziom poziom, Object key) {
        super(text, poziom, key);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ProstyKomunikatWalidacji)) {
            return false;
        }
        ProstyKomunikatWalidacji other = (ProstyKomunikatWalidacji) o;
        return getPoziom().equals(other.getPoziom()) && getInformacje() == other.getInformacje()
                || (getInformacje() != null && getInformacje().equals(other.getInformacje())) && getTresc() == other.getTresc()
                || (getTresc() != null && getTresc().equals(other.getTresc()));
    }

    @Override
    public int hashCode() {
        String formattedText = getTresc();
        int result = 17;
        result = 37 * result + getPoziom().hashCode();
        result = 37 * result + (getInformacje() == null ? 0 : getInformacje().hashCode());
        result = 37 * result + (formattedText == null ? 0 : formattedText.hashCode());
        return result;
    }

}
