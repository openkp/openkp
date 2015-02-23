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
package pl.openkp.business.wyplata.entity;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Wyplata {

    private BigDecimal skladkaEmerytalnaPracownika;
    private BigDecimal skladkaRentowaPracownika;
    private BigDecimal skladkaChorobowaPracownika;
    private BigDecimal skladkaWypadkowa;
    private BigDecimal kosztyUzyskaniaPrzychodu;
    private BigDecimal zaliczkaPodatkowa;
    private BigDecimal podatek;
    private BigDecimal skladkaZdrowotna;
    private BigDecimal skladkaZdrowotnaOdliczona;
    private BigDecimal wyngrodzenieNetto;
    private BigDecimal wynagrodzenieZasadnicze;
    private BigDecimal sumaSkladek;
    private BigDecimal przychod;
    private BigDecimal podstawa;

    public BigDecimal getSkladkaEmerytalnaPracownika() {
        return skladkaEmerytalnaPracownika;
    }

    public void setSkladkaEmerytalnaPracownika(BigDecimal skladkaEmerytalnaPracownika) {
        this.skladkaEmerytalnaPracownika = skladkaEmerytalnaPracownika;
    }

    public BigDecimal getSkladkaRentowaPracownika() {
        return skladkaRentowaPracownika;
    }

    public void setSkladkaRentowaPracownika(BigDecimal skladkaRentowaPracownika) {
        this.skladkaRentowaPracownika = skladkaRentowaPracownika;
    }

    public BigDecimal getSkladkaChorobowaPracownika() {
        return skladkaChorobowaPracownika;
    }

    public void setSkladkaChorobowaPracownika(BigDecimal skladkaChorobowaPracownika) {
        this.skladkaChorobowaPracownika = skladkaChorobowaPracownika;
    }

    public BigDecimal getSkladkaWypadkowa() {
        return skladkaWypadkowa;
    }

    public void setSkladkaWypadkowa(BigDecimal skladkaWypadkowa) {
        this.skladkaWypadkowa = skladkaWypadkowa;
    }

    public BigDecimal getKosztyUzyskaniaPrzychodu() {
        return kosztyUzyskaniaPrzychodu;
    }

    public void setKosztyUzyskaniaPrzychodu(BigDecimal kosztyUzyskaniaPrzychodu) {
        this.kosztyUzyskaniaPrzychodu = kosztyUzyskaniaPrzychodu;
    }

    public BigDecimal getZaliczkaPodatkowa() {
        return zaliczkaPodatkowa;
    }

    public void setZaliczkaPodatkowa(BigDecimal zaliczkaPodatkowa) {
        this.zaliczkaPodatkowa = zaliczkaPodatkowa;
    }

    public BigDecimal getSkladkaZdrowotna() {
        return skladkaZdrowotna;
    }

    public void setSkladkaZdrowotna(BigDecimal skladkaZdrowotna) {
        this.skladkaZdrowotna = skladkaZdrowotna;
    }

    public BigDecimal getSkladkaZdrowotnaOdliczona() {
        return skladkaZdrowotnaOdliczona;
    }

    public void setSkladkaZdrowotnaOdliczona(BigDecimal skladkaZdrowotnaOdliczona) {
        this.skladkaZdrowotnaOdliczona = skladkaZdrowotnaOdliczona;
    }

    public BigDecimal getWyngrodzenieNetto() {
        return wyngrodzenieNetto;
    }

    public void setWyngrodzenieNetto(BigDecimal wyngrodzenieNetto) {
        this.wyngrodzenieNetto = wyngrodzenieNetto;
    }

    public BigDecimal getPodatek() {
        return podatek;
    }

    public void setPodatek(BigDecimal podatek) {
        this.podatek = podatek;
    }

    public BigDecimal getWynagrodzenieZasadnicze() {
        return wynagrodzenieZasadnicze;
    }

    public void setWynagrodzenieZasadnicze(BigDecimal wynagrodzenie) {
        this.wynagrodzenieZasadnicze = wynagrodzenie;
    }

    public BigDecimal getSumaSkladek() {
        return sumaSkladek;
    }

    public void setSumaSkladek(BigDecimal sumaSkladek) {
        this.sumaSkladek = sumaSkladek;
    }

    public void setPrzychod(BigDecimal przychod) {
        this.przychod = przychod;
    }

    public BigDecimal getPrzychod() {
        return przychod;
    }

    public void setPodstawa(BigDecimal podstawa) {
        this.podstawa = podstawa;
    }

    public BigDecimal getPodstawa() {
        return podstawa;
    }

}
