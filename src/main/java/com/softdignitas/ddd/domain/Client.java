package com.softdignitas.ddd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private String id;

    @NotNull
    @Column(name = "denumire", nullable = false)
    private String denumire;

    @NotNull
    @Column(name = "cod_fiscal", nullable = false)
    private String codFiscal;

    @Column(name = "numar_registru_comert")
    private String numarRegistruComert;

    @NotNull
    @Column(name = "adresa_punct_lucru", nullable = false)
    private String adresaPunctLucru;

    @NotNull
    @Column(name = "telefon", nullable = false)
    private String telefon;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "persoana_contact", nullable = false)
    private String persoanaContact;

    @Column(name = "contract")
    private String contract;

    @Column(name = "deratizare")
    private Boolean deratizare;

    @Column(name = "dezinsectie")
    private Boolean dezinsectie;

    @Column(name = "dezinfectie")
    private Boolean dezinfectie;

    @Column(name = "frecventa_deratizare")
    private Integer frecventaDeratizare;

    @Column(name = "frecventa_dezinsectie")
    private Integer frecventaDezinsectie;

    @Column(name = "frecventa_dezinfectie")
    private Integer frecventaDezinfectie;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @JsonIgnoreProperties(value = { "jTMaterialProcesVerbals", "companie", "client", "operator" }, allowSetters = true)
    private Set<ProcesVerbal> proceseVerbales = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "utilizatoris", "clientis", "materiales", "proceseVerbales" }, allowSetters = true)
    private Companie companie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "clients" }, allowSetters = true)
    private CategorieClient categorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Client id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDenumire() {
        return this.denumire;
    }

    public Client denumire(String denumire) {
        this.setDenumire(denumire);
        return this;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getCodFiscal() {
        return this.codFiscal;
    }

    public Client codFiscal(String codFiscal) {
        this.setCodFiscal(codFiscal);
        return this;
    }

    public void setCodFiscal(String codFiscal) {
        this.codFiscal = codFiscal;
    }

    public String getNumarRegistruComert() {
        return this.numarRegistruComert;
    }

    public Client numarRegistruComert(String numarRegistruComert) {
        this.setNumarRegistruComert(numarRegistruComert);
        return this;
    }

    public void setNumarRegistruComert(String numarRegistruComert) {
        this.numarRegistruComert = numarRegistruComert;
    }

    public String getAdresaPunctLucru() {
        return this.adresaPunctLucru;
    }

    public Client adresaPunctLucru(String adresaPunctLucru) {
        this.setAdresaPunctLucru(adresaPunctLucru);
        return this;
    }

    public void setAdresaPunctLucru(String adresaPunctLucru) {
        this.adresaPunctLucru = adresaPunctLucru;
    }

    public String getTelefon() {
        return this.telefon;
    }

    public Client telefon(String telefon) {
        this.setTelefon(telefon);
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return this.email;
    }

    public Client email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersoanaContact() {
        return this.persoanaContact;
    }

    public Client persoanaContact(String persoanaContact) {
        this.setPersoanaContact(persoanaContact);
        return this;
    }

    public void setPersoanaContact(String persoanaContact) {
        this.persoanaContact = persoanaContact;
    }

    public String getContract() {
        return this.contract;
    }

    public Client contract(String contract) {
        this.setContract(contract);
        return this;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Boolean getDeratizare() {
        return this.deratizare;
    }

    public Client deratizare(Boolean deratizare) {
        this.setDeratizare(deratizare);
        return this;
    }

    public void setDeratizare(Boolean deratizare) {
        this.deratizare = deratizare;
    }

    public Boolean getDezinsectie() {
        return this.dezinsectie;
    }

    public Client dezinsectie(Boolean dezinsectie) {
        this.setDezinsectie(dezinsectie);
        return this;
    }

    public void setDezinsectie(Boolean dezinsectie) {
        this.dezinsectie = dezinsectie;
    }

    public Boolean getDezinfectie() {
        return this.dezinfectie;
    }

    public Client dezinfectie(Boolean dezinfectie) {
        this.setDezinfectie(dezinfectie);
        return this;
    }

    public void setDezinfectie(Boolean dezinfectie) {
        this.dezinfectie = dezinfectie;
    }

    public Integer getFrecventaDeratizare() {
        return this.frecventaDeratizare;
    }

    public Client frecventaDeratizare(Integer frecventaDeratizare) {
        this.setFrecventaDeratizare(frecventaDeratizare);
        return this;
    }

    public void setFrecventaDeratizare(Integer frecventaDeratizare) {
        this.frecventaDeratizare = frecventaDeratizare;
    }

    public Integer getFrecventaDezinsectie() {
        return this.frecventaDezinsectie;
    }

    public Client frecventaDezinsectie(Integer frecventaDezinsectie) {
        this.setFrecventaDezinsectie(frecventaDezinsectie);
        return this;
    }

    public void setFrecventaDezinsectie(Integer frecventaDezinsectie) {
        this.frecventaDezinsectie = frecventaDezinsectie;
    }

    public Integer getFrecventaDezinfectie() {
        return this.frecventaDezinfectie;
    }

    public Client frecventaDezinfectie(Integer frecventaDezinfectie) {
        this.setFrecventaDezinfectie(frecventaDezinfectie);
        return this;
    }

    public void setFrecventaDezinfectie(Integer frecventaDezinfectie) {
        this.frecventaDezinfectie = frecventaDezinfectie;
    }

    public Set<ProcesVerbal> getProceseVerbales() {
        return this.proceseVerbales;
    }

    public void setProceseVerbales(Set<ProcesVerbal> procesVerbals) {
        if (this.proceseVerbales != null) {
            this.proceseVerbales.forEach(i -> i.setClient(null));
        }
        if (procesVerbals != null) {
            procesVerbals.forEach(i -> i.setClient(this));
        }
        this.proceseVerbales = procesVerbals;
    }

    public Client proceseVerbales(Set<ProcesVerbal> procesVerbals) {
        this.setProceseVerbales(procesVerbals);
        return this;
    }

    public Client addProceseVerbale(ProcesVerbal procesVerbal) {
        this.proceseVerbales.add(procesVerbal);
        procesVerbal.setClient(this);
        return this;
    }

    public Client removeProceseVerbale(ProcesVerbal procesVerbal) {
        this.proceseVerbales.remove(procesVerbal);
        procesVerbal.setClient(null);
        return this;
    }

    public Companie getCompanie() {
        return this.companie;
    }

    public void setCompanie(Companie companie) {
        this.companie = companie;
    }

    public Client companie(Companie companie) {
        this.setCompanie(companie);
        return this;
    }

    public CategorieClient getCategorie() {
        return this.categorie;
    }

    public void setCategorie(CategorieClient categorieClient) {
        this.categorie = categorieClient;
    }

    public Client categorie(CategorieClient categorieClient) {
        this.setCategorie(categorieClient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return getId() != null && getId().equals(((Client) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", denumire='" + getDenumire() + "'" +
            ", codFiscal='" + getCodFiscal() + "'" +
            ", numarRegistruComert='" + getNumarRegistruComert() + "'" +
            ", adresaPunctLucru='" + getAdresaPunctLucru() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", email='" + getEmail() + "'" +
            ", persoanaContact='" + getPersoanaContact() + "'" +
            ", contract='" + getContract() + "'" +
            ", deratizare='" + getDeratizare() + "'" +
            ", dezinsectie='" + getDezinsectie() + "'" +
            ", dezinfectie='" + getDezinfectie() + "'" +
            ", frecventaDeratizare=" + getFrecventaDeratizare() +
            ", frecventaDezinsectie=" + getFrecventaDezinsectie() +
            ", frecventaDezinfectie=" + getFrecventaDezinfectie() +
            "}";
    }
}
