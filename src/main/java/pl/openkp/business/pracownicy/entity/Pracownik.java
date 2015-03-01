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
package pl.openkp.business.pracownicy.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import pl.openkp.business.absencje.entity.Absencja;

@NamedQueries({ @NamedQuery(name = "Pracownik.PRACOWNICY", query = "SELECT DISTINCT p FROM Pracownik p LEFT JOIN FETCH p.absencje ORDER BY p.id") })
@Entity
@XmlRootElement
public class Pracownik implements Serializable {

    private static final long serialVersionUID = -8032911264024186651L;

    public static final String PRACOWNICY = "Pracownik.PRACOWNICY";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Version
    @Column(name = "version")
    private int version;
    @NotNull(message = "Imię pracownika jest wymagane")
    private String imie;
    @NotNull(message = "Nazwisko pracownika jest wymagane")
    private String nazwisko;
    private String email;
    private String telefon;
    @NotNull(message = "Data zatrudnienia jest wymagana")
    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_ZATRUDNIENIA")
    private Date dataZatrudnienia;
    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_ZWOLNIENIA")
    private Date dataZwolnienia;
    @NotNull(message = "Wynagrodzenie jest wymagane")
    @Min(value = 0, message = "Wynagrodzenie nie może być ujemne")
    @Column(precision = 11, scale = 2)
    private BigDecimal wynagrodzenie;
    @Column(name = "KOSZT_PODWYZSZONY")
    private boolean kosztPodwyzszony;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pracownik")
    private List<Absencja> absencje;

    @PrePersist
    @PreUpdate
    void preSave() {
        if (dataZwolnienia == null) {
            dataZwolnienia = new GregorianCalendar(9999, 11, 31).getTime();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Date getDataZatrudnienia() {
        return dataZatrudnienia;
    }

    public void setDataZatrudnienia(Date dataZatrudnienia) {
        this.dataZatrudnienia = dataZatrudnienia;
    }

    public Date getDataZwolnienia() {
        return dataZwolnienia;
    }

    public void setDataZwolnienia(Date dataZwolnienia) {
        this.dataZwolnienia = dataZwolnienia;
    }

    public BigDecimal getWynagrodzenie() {
        return wynagrodzenie;
    }

    public void setWynagrodzenie(BigDecimal wynagrodzenie) {
        this.wynagrodzenie = wynagrodzenie;
    }

    public boolean isKosztPodwyzszony() {
        return kosztPodwyzszony;
    }

    public void setKosztPodwyzszony(boolean kosztPodwyzszony) {
        this.kosztPodwyzszony = kosztPodwyzszony;
    }

    @XmlTransient
    public List<Absencja> getAbsencje() {
        return absencje;
    }

    public void setAbsencje(List<Absencja> absencje) {
        this.absencje = absencje;
    }

}