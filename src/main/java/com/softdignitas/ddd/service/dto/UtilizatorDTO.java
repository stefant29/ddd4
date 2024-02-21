package com.softdignitas.ddd.service.dto;

import com.softdignitas.ddd.domain.User;
import com.softdignitas.ddd.domain.enumeration.Functie;
import java.io.Serializable;

/**
 * A DTO representing a Utilizator, with only the public attributes.
 */
public class UtilizatorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String nume;
    private String prenume;
    private String email;
    private Functie functie;
    private User user;

    public UtilizatorDTO() {
        // Empty constructor needed for Jackson.
    }

    public UtilizatorDTO(String id, String nume, String prenume, String email) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Functie getFunctie() {
        return functie;
    }

    public void setFunctie(Functie functie) {
        this.functie = functie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
