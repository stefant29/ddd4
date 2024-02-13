package com.softdignitas.ddd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CategorieClient.
 */
@Entity
@Table(name = "categorie_client")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategorieClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @Column(name = "nume")
    private String nume;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categorie")
    @JsonIgnoreProperties(value = { "proceseVerbales", "companie", "categorie" }, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public CategorieClient id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNume() {
        return this.nume;
    }

    public CategorieClient nume(String nume) {
        this.setNume(nume);
        return this;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        if (this.clients != null) {
            this.clients.forEach(i -> i.setCategorie(null));
        }
        if (clients != null) {
            clients.forEach(i -> i.setCategorie(this));
        }
        this.clients = clients;
    }

    public CategorieClient clients(Set<Client> clients) {
        this.setClients(clients);
        return this;
    }

    public CategorieClient addClient(Client client) {
        this.clients.add(client);
        client.setCategorie(this);
        return this;
    }

    public CategorieClient removeClient(Client client) {
        this.clients.remove(client);
        client.setCategorie(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieClient)) {
            return false;
        }
        return getId() != null && getId().equals(((CategorieClient) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieClient{" +
            "id=" + getId() +
            ", nume='" + getNume() + "'" +
            "}";
    }
}
