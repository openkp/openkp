package pl.openkp.business.uzytkownicy.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Uzytkownik implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Version
    @Column(name = "version")
    private int version;
    private String login;
    @NotNull(message = "Imię użytkownika jest wymagane")
    private String imie;
    @NotNull(message = "Nazwisko użytkownika jest wymagane")
    private String nazwisko;
    private String email;
    private byte[] haslo;
}
