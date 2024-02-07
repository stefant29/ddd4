package com.softdignitas.ddd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Companie.
 */
@Entity
@Table(name = "companie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Companie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "i_d")
    private String iD;

    @Column(name = "n_ume")
    private String nUME;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companie")
    @JsonIgnoreProperties(value = { "proceseVerbales", "companie" }, allowSetters = true)
    private Set<Utilizator> utilizatoris = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companie")
    @JsonIgnoreProperties(value = { "proceseVerbales", "companie", "categorie" }, allowSetters = true)
    private Set<Client> clientis = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companie")
    @JsonIgnoreProperties(value = { "jTMaterialProcesVerbals", "companie" }, allowSetters = true)
    private Set<Material> materiales = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companie")
    @JsonIgnoreProperties(value = { "jTMaterialProcesVerbals", "companie", "client", "operator" }, allowSetters = true)
    private Set<ProcesVerbal> proceseVerbales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Companie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getiD() {
        return this.iD;
    }

    public Companie iD(String iD) {
        this.setiD(iD);
        return this;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getnUME() {
        return this.nUME;
    }

    public Companie nUME(String nUME) {
        this.setnUME(nUME);
        return this;
    }

    public void setnUME(String nUME) {
        this.nUME = nUME;
    }

    public Set<Utilizator> getUtilizatoris() {
        return this.utilizatoris;
    }

    public void setUtilizatoris(Set<Utilizator> utilizators) {
        if (this.utilizatoris != null) {
            this.utilizatoris.forEach(i -> i.setCompanie(null));
        }
        if (utilizators != null) {
            utilizators.forEach(i -> i.setCompanie(this));
        }
        this.utilizatoris = utilizators;
    }

    public Companie utilizatoris(Set<Utilizator> utilizators) {
        this.setUtilizatoris(utilizators);
        return this;
    }

    public Companie addUtilizatori(Utilizator utilizator) {
        this.utilizatoris.add(utilizator);
        utilizator.setCompanie(this);
        return this;
    }

    public Companie removeUtilizatori(Utilizator utilizator) {
        this.utilizatoris.remove(utilizator);
        utilizator.setCompanie(null);
        return this;
    }

    public Set<Client> getClientis() {
        return this.clientis;
    }

    public void setClientis(Set<Client> clients) {
        if (this.clientis != null) {
            this.clientis.forEach(i -> i.setCompanie(null));
        }
        if (clients != null) {
            clients.forEach(i -> i.setCompanie(this));
        }
        this.clientis = clients;
    }

    public Companie clientis(Set<Client> clients) {
        this.setClientis(clients);
        return this;
    }

    public Companie addClienti(Client client) {
        this.clientis.add(client);
        client.setCompanie(this);
        return this;
    }

    public Companie removeClienti(Client client) {
        this.clientis.remove(client);
        client.setCompanie(null);
        return this;
    }

    public Set<Material> getMateriales() {
        return this.materiales;
    }

    public void setMateriales(Set<Material> materials) {
        if (this.materiales != null) {
            this.materiales.forEach(i -> i.setCompanie(null));
        }
        if (materials != null) {
            materials.forEach(i -> i.setCompanie(this));
        }
        this.materiales = materials;
    }

    public Companie materiales(Set<Material> materials) {
        this.setMateriales(materials);
        return this;
    }

    public Companie addMateriale(Material material) {
        this.materiales.add(material);
        material.setCompanie(this);
        return this;
    }

    public Companie removeMateriale(Material material) {
        this.materiales.remove(material);
        material.setCompanie(null);
        return this;
    }

    public Set<ProcesVerbal> getProceseVerbales() {
        return this.proceseVerbales;
    }

    public void setProceseVerbales(Set<ProcesVerbal> procesVerbals) {
        if (this.proceseVerbales != null) {
            this.proceseVerbales.forEach(i -> i.setCompanie(null));
        }
        if (procesVerbals != null) {
            procesVerbals.forEach(i -> i.setCompanie(this));
        }
        this.proceseVerbales = procesVerbals;
    }

    public Companie proceseVerbales(Set<ProcesVerbal> procesVerbals) {
        this.setProceseVerbales(procesVerbals);
        return this;
    }

    public Companie addProceseVerbale(ProcesVerbal procesVerbal) {
        this.proceseVerbales.add(procesVerbal);
        procesVerbal.setCompanie(this);
        return this;
    }

    public Companie removeProceseVerbale(ProcesVerbal procesVerbal) {
        this.proceseVerbales.remove(procesVerbal);
        procesVerbal.setCompanie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Companie)) {
            return false;
        }
        return getId() != null && getId().equals(((Companie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Companie{" +
            "id=" + getId() +
            ", iD='" + getiD() + "'" +
            ", nUME='" + getnUME() + "'" +
            "}";
    }
}
