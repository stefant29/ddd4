package com.softdignitas.ddd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softdignitas.ddd.domain.enumeration.Functie;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Utilizator.
 */
@Entity
@Table(name = "utilizator")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Utilizator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @NotNull
    @Column(name = "nume", nullable = false)
    private String nume;

    @NotNull
    @Column(name = "prenume", nullable = false)
    private String prenume;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "functie", nullable = false)
    private Functie functie;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operator")
    @JsonIgnoreProperties(value = { "jTMaterialProcesVerbals", "companie", "client", "operator" }, allowSetters = true)
    private Set<ProcesVerbal> proceseVerbales = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "utilizatoris", "clientis", "materiales", "proceseVerbales" }, allowSetters = true)
    private Companie companie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Utilizator id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNume() {
        return this.nume;
    }

    public Utilizator nume(String nume) {
        this.setNume(nume);
        return this;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return this.prenume;
    }

    public Utilizator prenume(String prenume) {
        this.setPrenume(prenume);
        return this;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Functie getFunctie() {
        return this.functie;
    }

    public Utilizator functie(Functie functie) {
        this.setFunctie(functie);
        return this;
    }

    public void setFunctie(Functie functie) {
        this.functie = functie;
    }

    public Set<ProcesVerbal> getProceseVerbales() {
        return this.proceseVerbales;
    }

    public void setProceseVerbales(Set<ProcesVerbal> procesVerbals) {
        if (this.proceseVerbales != null) {
            this.proceseVerbales.forEach(i -> i.setOperator(null));
        }
        if (procesVerbals != null) {
            procesVerbals.forEach(i -> i.setOperator(this));
        }
        this.proceseVerbales = procesVerbals;
    }

    public Utilizator proceseVerbales(Set<ProcesVerbal> procesVerbals) {
        this.setProceseVerbales(procesVerbals);
        return this;
    }

    public Utilizator addProceseVerbale(ProcesVerbal procesVerbal) {
        this.proceseVerbales.add(procesVerbal);
        procesVerbal.setOperator(this);
        return this;
    }

    public Utilizator removeProceseVerbale(ProcesVerbal procesVerbal) {
        this.proceseVerbales.remove(procesVerbal);
        procesVerbal.setOperator(null);
        return this;
    }

    public Companie getCompanie() {
        return this.companie;
    }

    public void setCompanie(Companie companie) {
        this.companie = companie;
    }

    public Utilizator companie(Companie companie) {
        this.setCompanie(companie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utilizator)) {
            return false;
        }
        return getId() != null && getId().equals(((Utilizator) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Utilizator{" +
            "id=" + getId() +
            ", nume='" + getNume() + "'" +
            ", prenume='" + getPrenume() + "'" +
            ", functie='" + getFunctie() + "'" +
            "}";
    }
}
