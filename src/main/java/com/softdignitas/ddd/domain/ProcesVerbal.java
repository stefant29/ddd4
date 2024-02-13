package com.softdignitas.ddd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProcesVerbal.
 */
@Entity
@Table(name = "proces_verbal")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProcesVerbal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "ora")
    private Instant ora;

    @NotNull
    @Column(name = "numar_proces_verbal", nullable = false)
    private Integer numarProcesVerbal;

    @Column(name = "reprezentant")
    private String reprezentant;

    @Column(name = "spatii")
    private String spatii;

    @Column(name = "suprafata")
    private Integer suprafata;

    @Column(name = "rapel_dezinsectie")
    private Integer rapelDezinsectie;

    @Column(name = "rapel_deratizare")
    private Integer rapelDeratizare;

    @Column(name = "garantie_dezinsectie")
    private Boolean garantieDezinsectie;

    @Column(name = "garantie_deratizare")
    private Boolean garantieDeratizare;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "procesVerbal")
    @JsonIgnoreProperties(value = { "produs", "procesVerbal" }, allowSetters = true)
    private Set<JTMaterialProcesVerbal> jTMaterialProcesVerbals = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "utilizatoris", "clientis", "materiales", "proceseVerbales" }, allowSetters = true)
    private Companie companie;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "proceseVerbales", "companie", "categorie" }, allowSetters = true)
    private Client client;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "proceseVerbales", "companie" }, allowSetters = true)
    private Utilizator operator;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ProcesVerbal id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getData() {
        return this.data;
    }

    public ProcesVerbal data(LocalDate data) {
        this.setData(data);
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Instant getOra() {
        return this.ora;
    }

    public ProcesVerbal ora(Instant ora) {
        this.setOra(ora);
        return this;
    }

    public void setOra(Instant ora) {
        this.ora = ora;
    }

    public Integer getNumarProcesVerbal() {
        return this.numarProcesVerbal;
    }

    public ProcesVerbal numarProcesVerbal(Integer numarProcesVerbal) {
        this.setNumarProcesVerbal(numarProcesVerbal);
        return this;
    }

    public void setNumarProcesVerbal(Integer numarProcesVerbal) {
        this.numarProcesVerbal = numarProcesVerbal;
    }

    public String getReprezentant() {
        return this.reprezentant;
    }

    public ProcesVerbal reprezentant(String reprezentant) {
        this.setReprezentant(reprezentant);
        return this;
    }

    public void setReprezentant(String reprezentant) {
        this.reprezentant = reprezentant;
    }

    public String getSpatii() {
        return this.spatii;
    }

    public ProcesVerbal spatii(String spatii) {
        this.setSpatii(spatii);
        return this;
    }

    public void setSpatii(String spatii) {
        this.spatii = spatii;
    }

    public Integer getSuprafata() {
        return this.suprafata;
    }

    public ProcesVerbal suprafata(Integer suprafata) {
        this.setSuprafata(suprafata);
        return this;
    }

    public void setSuprafata(Integer suprafata) {
        this.suprafata = suprafata;
    }

    public Integer getRapelDezinsectie() {
        return this.rapelDezinsectie;
    }

    public ProcesVerbal rapelDezinsectie(Integer rapelDezinsectie) {
        this.setRapelDezinsectie(rapelDezinsectie);
        return this;
    }

    public void setRapelDezinsectie(Integer rapelDezinsectie) {
        this.rapelDezinsectie = rapelDezinsectie;
    }

    public Integer getRapelDeratizare() {
        return this.rapelDeratizare;
    }

    public ProcesVerbal rapelDeratizare(Integer rapelDeratizare) {
        this.setRapelDeratizare(rapelDeratizare);
        return this;
    }

    public void setRapelDeratizare(Integer rapelDeratizare) {
        this.rapelDeratizare = rapelDeratizare;
    }

    public Boolean getGarantieDezinsectie() {
        return this.garantieDezinsectie;
    }

    public ProcesVerbal garantieDezinsectie(Boolean garantieDezinsectie) {
        this.setGarantieDezinsectie(garantieDezinsectie);
        return this;
    }

    public void setGarantieDezinsectie(Boolean garantieDezinsectie) {
        this.garantieDezinsectie = garantieDezinsectie;
    }

    public Boolean getGarantieDeratizare() {
        return this.garantieDeratizare;
    }

    public ProcesVerbal garantieDeratizare(Boolean garantieDeratizare) {
        this.setGarantieDeratizare(garantieDeratizare);
        return this;
    }

    public void setGarantieDeratizare(Boolean garantieDeratizare) {
        this.garantieDeratizare = garantieDeratizare;
    }

    public Set<JTMaterialProcesVerbal> getJTMaterialProcesVerbals() {
        return this.jTMaterialProcesVerbals;
    }

    public void setJTMaterialProcesVerbals(Set<JTMaterialProcesVerbal> jTMaterialProcesVerbals) {
        if (this.jTMaterialProcesVerbals != null) {
            this.jTMaterialProcesVerbals.forEach(i -> i.setProcesVerbal(null));
        }
        if (jTMaterialProcesVerbals != null) {
            jTMaterialProcesVerbals.forEach(i -> i.setProcesVerbal(this));
        }
        this.jTMaterialProcesVerbals = jTMaterialProcesVerbals;
    }

    public ProcesVerbal jTMaterialProcesVerbals(Set<JTMaterialProcesVerbal> jTMaterialProcesVerbals) {
        this.setJTMaterialProcesVerbals(jTMaterialProcesVerbals);
        return this;
    }

    public ProcesVerbal addJTMaterialProcesVerbal(JTMaterialProcesVerbal jTMaterialProcesVerbal) {
        this.jTMaterialProcesVerbals.add(jTMaterialProcesVerbal);
        jTMaterialProcesVerbal.setProcesVerbal(this);
        return this;
    }

    public ProcesVerbal removeJTMaterialProcesVerbal(JTMaterialProcesVerbal jTMaterialProcesVerbal) {
        this.jTMaterialProcesVerbals.remove(jTMaterialProcesVerbal);
        jTMaterialProcesVerbal.setProcesVerbal(null);
        return this;
    }

    public Companie getCompanie() {
        return this.companie;
    }

    public void setCompanie(Companie companie) {
        this.companie = companie;
    }

    public ProcesVerbal companie(Companie companie) {
        this.setCompanie(companie);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ProcesVerbal client(Client client) {
        this.setClient(client);
        return this;
    }

    public Utilizator getOperator() {
        return this.operator;
    }

    public void setOperator(Utilizator utilizator) {
        this.operator = utilizator;
    }

    public ProcesVerbal operator(Utilizator utilizator) {
        this.setOperator(utilizator);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcesVerbal)) {
            return false;
        }
        return getId() != null && getId().equals(((ProcesVerbal) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcesVerbal{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", ora='" + getOra() + "'" +
            ", numarProcesVerbal=" + getNumarProcesVerbal() +
            ", reprezentant='" + getReprezentant() + "'" +
            ", spatii='" + getSpatii() + "'" +
            ", suprafata=" + getSuprafata() +
            ", rapelDezinsectie=" + getRapelDezinsectie() +
            ", rapelDeratizare=" + getRapelDeratizare() +
            ", garantieDezinsectie='" + getGarantieDezinsectie() + "'" +
            ", garantieDeratizare='" + getGarantieDeratizare() + "'" +
            "}";
    }
}
