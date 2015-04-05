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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class WynikWalidacji implements Iterable<KomunikatWalidacji> {

    private List<KomunikatWalidacji> komunikatyWalidacji;

    public WynikWalidacji() {
        this(new ArrayList<KomunikatWalidacji>());
    }

    private WynikWalidacji(List<KomunikatWalidacji> komunikatyWalidacji) {
        this.komunikatyWalidacji = komunikatyWalidacji;
    }

    public void dodaj(KomunikatWalidacji komunikatWalidacji) {
        if (komunikatWalidacji == null) {
            throw new NullPointerException("Komunikat walidacji nie może być null.");
        }
        komunikatyWalidacji.add(komunikatWalidacji);
    }

    public void dodajBlad(String text) {
        dodajBlad(text, null);
    }

    public void dodajBlad(String text, Object key) {
        dodaj(new ProstyKomunikatWalidacji(text, Poziom.BLAD, key));
    }

    public void dodajOstrzezenie(String text) {
        dodajOstrzezenie(text, null);
    }

    public void dodajOstrzezenie(String text, Object key) {
        dodaj(new ProstyKomunikatWalidacji(text, Poziom.OSTRZEZENIE, key));
    }

    public boolean pusty() {
        return komunikatyWalidacji.isEmpty();
    }

    public boolean zawiera(KomunikatWalidacji komunikatWalidacji) {
        return komunikatyWalidacji.contains(komunikatWalidacji);
    }

    @Override
    public Iterator<KomunikatWalidacji> iterator() {
        return komunikatyWalidacji.iterator();
    }

    public Poziom getPoziom() {
        return getPoziom(komunikatyWalidacji);
    }

    public boolean saBledy() {
        return jestPoziom(komunikatyWalidacji, Poziom.BLAD);
    }

    public boolean saOstrzezenia() {
        return jestPoziom(komunikatyWalidacji, Poziom.OSTRZEZENIE);
    }

    public List<KomunikatWalidacji> getKomunikaty() {
        return Collections.unmodifiableList(komunikatyWalidacji);
    }

    public List<KomunikatWalidacji> getBledy() {
        return dajKomunikatyDlaPoziomu(komunikatyWalidacji, Poziom.BLAD);
    }

    public List<KomunikatWalidacji> getOstrzezenia() {
        return dajKomunikatyDlaPoziomu(komunikatyWalidacji, Poziom.OSTRZEZENIE);
    }

    @Override
    public String toString() {
        if (pusty()) {
            return "Brak komunikatów walidacji";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(" ValidationResult:");
        for (KomunikatWalidacji message : komunikatyWalidacji) {
            builder.append("\n\t").append(message);
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof WynikWalidacji)) {
            return false;
        }
        return komunikatyWalidacji.equals(((WynikWalidacji) o).komunikatyWalidacji);
    }

    @Override
    public int hashCode() {
        return komunikatyWalidacji.hashCode();
    }

    private static Poziom getPoziom(List<KomunikatWalidacji> komunikaty) {
        for (KomunikatWalidacji komunikat : komunikaty) {
            if (komunikat.getPoziom() == Poziom.BLAD) {
                return Poziom.BLAD;
            }
        }
        return Poziom.OSTRZEZENIE;
    }

    private static boolean jestPoziom(List<KomunikatWalidacji> komunikaty, Poziom poziom) {
        for (KomunikatWalidacji komunikat : komunikaty) {
            if (komunikat.getPoziom() == poziom) {
                return true;
            }
        }
        return false;
    }

    private static List<KomunikatWalidacji> dajKomunikatyDlaPoziomu(List<KomunikatWalidacji> komunikaty, Poziom poziom) {
        List<KomunikatWalidacji> komunikatyWalidacji = new ArrayList<KomunikatWalidacji>();
        for (KomunikatWalidacji komunikat : komunikaty) {
            if (komunikat.getPoziom() == poziom) {
                komunikatyWalidacji.add(komunikat);
            }
        }
        return Collections.unmodifiableList(komunikatyWalidacji);
    }

}
