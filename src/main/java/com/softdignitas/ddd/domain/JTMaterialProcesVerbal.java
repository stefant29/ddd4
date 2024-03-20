package com.softdignitas.ddd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softdignitas.ddd.domain.enumeration.Procedura;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.UuidGenerator;

/**
 * A JTMaterialProcesVerbal.
 */
@Entity
@Table(name = "jt_material_proces_verbal")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JTMaterialProcesVerbal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "procedura")
    private Procedura procedura;

    @Column(name = "cantitate")
    private Integer cantitate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "jTMaterialProcesVerbals", "companie" }, allowSetters = true)
    private Material produs;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "jTMaterialProcesVerbals", "companie", "client", "operator" }, allowSetters = true)
    private ProcesVerbal procesVerbal;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public JTMaterialProcesVerbal id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Procedura getProcedura() {
        return this.procedura;
    }

    public JTMaterialProcesVerbal procedura(Procedura procedura) {
        this.setProcedura(procedura);
        return this;
    }

    public void setProcedura(Procedura procedura) {
        this.procedura = procedura;
    }

    public Integer getCantitate() {
        return this.cantitate;
    }

    public JTMaterialProcesVerbal cantitate(Integer cantitate) {
        this.setCantitate(cantitate);
        return this;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public Material getProdus() {
        return this.produs;
    }

    public void setProdus(Material material) {
        this.produs = material;
    }

    public JTMaterialProcesVerbal produs(Material material) {
        this.setProdus(material);
        return this;
    }

    public ProcesVerbal getProcesVerbal() {
        return this.procesVerbal;
    }

    public void setProcesVerbal(ProcesVerbal procesVerbal) {
        this.procesVerbal = procesVerbal;
    }

    public JTMaterialProcesVerbal procesVerbal(ProcesVerbal procesVerbal) {
        this.setProcesVerbal(procesVerbal);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JTMaterialProcesVerbal)) {
            return false;
        }
        return getId() != null && getId().equals(((JTMaterialProcesVerbal) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JTMaterialProcesVerbal{" +
            "id=" + getId() +
            ", procedura='" + getProcedura() + "'" +
            ", cantitate=" + getCantitate() +
            "}";
    }
}
