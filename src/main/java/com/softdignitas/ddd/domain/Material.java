package com.softdignitas.ddd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softdignitas.ddd.domain.enumeration.Procedura;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Material.
 */
@Entity
@Table(name = "material")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "procedura")
    private Procedura procedura;

    @NotNull
    @Column(name = "factura", nullable = false)
    private String factura;

    @NotNull
    @Column(name = "denumire", nullable = false)
    private String denumire;

    @NotNull
    @Column(name = "lot", nullable = false)
    private String lot;

    @Column(name = "data_achizitionare")
    private LocalDate dataAchizitionare;

    @Column(name = "data_expirare")
    private LocalDate dataExpirare;

    @Column(name = "dilutie")
    private String dilutie;

    @Column(name = "timp_contact")
    private Integer timpContact;

    @Column(name = "metoda_aplicare")
    private String metodaAplicare;

    @Column(name = "gramaj")
    private Integer gramaj;

    @Column(name = "cantitate")
    private Integer cantitate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produs")
    @JsonIgnoreProperties(value = { "produs", "procesVerbal" }, allowSetters = true)
    private Set<JTMaterialProcesVerbal> jTMaterialProcesVerbals = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "utilizatoris", "clientis", "materiales", "proceseVerbales" }, allowSetters = true)
    private Companie companie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Material id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Procedura getProcedura() {
        return this.procedura;
    }

    public Material procedura(Procedura procedura) {
        this.setProcedura(procedura);
        return this;
    }

    public void setProcedura(Procedura procedura) {
        this.procedura = procedura;
    }

    public String getFactura() {
        return this.factura;
    }

    public Material factura(String factura) {
        this.setFactura(factura);
        return this;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getDenumire() {
        return this.denumire;
    }

    public Material denumire(String denumire) {
        this.setDenumire(denumire);
        return this;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getLot() {
        return this.lot;
    }

    public Material lot(String lot) {
        this.setLot(lot);
        return this;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public LocalDate getDataAchizitionare() {
        return this.dataAchizitionare;
    }

    public Material dataAchizitionare(LocalDate dataAchizitionare) {
        this.setDataAchizitionare(dataAchizitionare);
        return this;
    }

    public void setDataAchizitionare(LocalDate dataAchizitionare) {
        this.dataAchizitionare = dataAchizitionare;
    }

    public LocalDate getDataExpirare() {
        return this.dataExpirare;
    }

    public Material dataExpirare(LocalDate dataExpirare) {
        this.setDataExpirare(dataExpirare);
        return this;
    }

    public void setDataExpirare(LocalDate dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    public String getDilutie() {
        return this.dilutie;
    }

    public Material dilutie(String dilutie) {
        this.setDilutie(dilutie);
        return this;
    }

    public void setDilutie(String dilutie) {
        this.dilutie = dilutie;
    }

    public Integer getTimpContact() {
        return this.timpContact;
    }

    public Material timpContact(Integer timpContact) {
        this.setTimpContact(timpContact);
        return this;
    }

    public void setTimpContact(Integer timpContact) {
        this.timpContact = timpContact;
    }

    public String getMetodaAplicare() {
        return this.metodaAplicare;
    }

    public Material metodaAplicare(String metodaAplicare) {
        this.setMetodaAplicare(metodaAplicare);
        return this;
    }

    public void setMetodaAplicare(String metodaAplicare) {
        this.metodaAplicare = metodaAplicare;
    }

    public Integer getGramaj() {
        return this.gramaj;
    }

    public Material gramaj(Integer gramaj) {
        this.setGramaj(gramaj);
        return this;
    }

    public void setGramaj(Integer gramaj) {
        this.gramaj = gramaj;
    }

    public Integer getCantitate() {
        return this.cantitate;
    }

    public Material cantitate(Integer cantitate) {
        this.setCantitate(cantitate);
        return this;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public Set<JTMaterialProcesVerbal> getJTMaterialProcesVerbals() {
        return this.jTMaterialProcesVerbals;
    }

    public void setJTMaterialProcesVerbals(Set<JTMaterialProcesVerbal> jTMaterialProcesVerbals) {
        if (this.jTMaterialProcesVerbals != null) {
            this.jTMaterialProcesVerbals.forEach(i -> i.setProdus(null));
        }
        if (jTMaterialProcesVerbals != null) {
            jTMaterialProcesVerbals.forEach(i -> i.setProdus(this));
        }
        this.jTMaterialProcesVerbals = jTMaterialProcesVerbals;
    }

    public Material jTMaterialProcesVerbals(Set<JTMaterialProcesVerbal> jTMaterialProcesVerbals) {
        this.setJTMaterialProcesVerbals(jTMaterialProcesVerbals);
        return this;
    }

    public Material addJTMaterialProcesVerbal(JTMaterialProcesVerbal jTMaterialProcesVerbal) {
        this.jTMaterialProcesVerbals.add(jTMaterialProcesVerbal);
        jTMaterialProcesVerbal.setProdus(this);
        return this;
    }

    public Material removeJTMaterialProcesVerbal(JTMaterialProcesVerbal jTMaterialProcesVerbal) {
        this.jTMaterialProcesVerbals.remove(jTMaterialProcesVerbal);
        jTMaterialProcesVerbal.setProdus(null);
        return this;
    }

    public Companie getCompanie() {
        return this.companie;
    }

    public void setCompanie(Companie companie) {
        this.companie = companie;
    }

    public Material companie(Companie companie) {
        this.setCompanie(companie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Material)) {
            return false;
        }
        return getId() != null && getId().equals(((Material) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Material{" +
            "id=" + getId() +
            ", procedura='" + getProcedura() + "'" +
            ", factura='" + getFactura() + "'" +
            ", denumire='" + getDenumire() + "'" +
            ", lot='" + getLot() + "'" +
            ", dataAchizitionare='" + getDataAchizitionare() + "'" +
            ", dataExpirare='" + getDataExpirare() + "'" +
            ", dilutie='" + getDilutie() + "'" +
            ", timpContact=" + getTimpContact() +
            ", metodaAplicare='" + getMetodaAplicare() + "'" +
            ", gramaj=" + getGramaj() +
            ", cantitate=" + getCantitate() +
            "}";
    }
}
