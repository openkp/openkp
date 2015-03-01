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
package pl.openkp.business.absencje.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import pl.openkp.business.pracownicy.entity.Pracownik;

@Entity
@XmlRootElement
public class Absencja implements Serializable {

    private static final long serialVersionUID = 2537398382916609462L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    public static final String PROP_ID = "id";

    @Version
    @Column(name = "version")
    private int version;
    public static final String PROP_VERSION = "version";

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataOd;
    public static final String PROP_DATA_OD = "dataOd";

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataDo;
    public static final String PROP_DATA_DO = "dataDo";

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @XmlTransient
    private Pracownik pracownik;
    public static final String PROP_PRACOWNIK = "pracownik";

    @Enumerated(EnumType.STRING)
    private TypAbsencji typAbsencji;
    public static final String PROP_TYP_ABSENCJI = "typAbsencji";

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

    public Date getDataOd() {
        return dataOd;
    }

    public void setDataOd(Date dataOd) {
        this.dataOd = dataOd;
    }

    public Date getDataDo() {
        return dataDo;
    }

    public void setDataDo(Date dataDo) {
        this.dataDo = dataDo;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public TypAbsencji getTypAbsencji() {
        return typAbsencji;
    }

    public void setTypAbsencji(TypAbsencji typAbsencji) {
        this.typAbsencji = typAbsencji;
    }
}