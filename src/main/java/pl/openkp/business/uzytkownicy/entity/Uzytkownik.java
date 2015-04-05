package pl.openkp.business.uzytkownicy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Uzytkownik implements Serializable {

    private static final long serialVersionUID = 2590249849150464232L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Version
    @Column(name = "version", nullable = false)
    private int version;
    @Column(name = "login", nullable = false, length = 50)
    private String login;
    @NotNull(message = "Imię użytkownika jest wymagane")
    @Column(name = "imie", nullable = false, length = 100)
    private String imie;
    @NotNull(message = "Nazwisko użytkownika jest wymagane")
    @Column(name = "nazwisko", nullable = false, length = 100)
    private String nazwisko;
    @Column(name = "email", nullable = true, length = 100)
    private String email;
    @NotNull(message = "Hasło jest wymagane")
    @Column(name = "haslo", nullable = false, length = 100)
    private byte[] haslo;
    @Lob
    private byte[] zdjecie;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public byte[] getHaslo() {
        return haslo;
    }

    public void setHaslo(byte[] haslo) {
        this.haslo = haslo;
    }

    public byte[] getZdjecie() {
        return zdjecie;
    }

    public void setZdjecie(byte[] zdjecie) {
        this.zdjecie = zdjecie;
    }

}
