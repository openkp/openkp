package pl.openkp.business.absencje.entity;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;
import java.util.Calendar;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;

import pl.openkp.business.absencje.entity.TypAbsencji;
import pl.openkp.business.pracownicy.entity.Pracownik;

import javax.persistence.EnumType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
public class Absencja implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Version
	@Column(name = "version")
	private int version;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar dataOd;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar dataDo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@XmlTransient
	private Pracownik pracownik;

	@Enumerated(EnumType.STRING)
	private TypAbsencji typAbsencji;
	
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

	public Calendar getDataOd() {
		return dataOd;
	}

	public void setDataOd(Calendar dataOd) {
		this.dataOd = dataOd;
	}

	public Calendar getDataDo() {
		return dataDo;
	}

	public void setDataDo(Calendar dataDo) {
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