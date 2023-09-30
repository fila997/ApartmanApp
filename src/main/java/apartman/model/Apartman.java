package apartman.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name="courses")
public class Apartman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @NotBlank(message = "Unesite naziv apratmana.")
    String name;

    @Column(nullable = false)
    @NotBlank(message = "Unesite opis apartmana")
    String opis;

    @Column(nullable = false)
    @NotBlank(message = "Unesite adresu.")
    String adresa;
    @Column(nullable = false)
    @NotBlank(message = "Unesite cijenu.")
    String cijena;




    @ManyToOne
    @JoinColumn(name = "tečaj_id", nullable = true)
    Apartman parent;

    @OneToMany(mappedBy = "parent")
    List<Apartman> apartmani;



    @ManyToOne
    @JoinColumn(name = "user_id")  // Naziv stupca koji će povezivati Course s Category
    private User createdBy;

    @OneToMany(mappedBy = "apartman")
    List<Rezervacija> rezervacije;


    public Apartman(Long id, String name, String opis,String adresa, String cijena) {
        this.id = id;
        this.name = name;
        this.opis = opis;
        this.adresa = adresa;
        this.cijena = cijena;
    }

    public Apartman() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
    public String getCijena() {
        return cijena;
    }

    public void setCijena(String cijena) {
        this.cijena = cijena;
    }


    public Apartman getParent() {
        return parent;
    }

    public void setParent(Apartman parent) {
        this.parent = parent;
    }



    public Apartman(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<Rezervacija> getRezervacije() {
        return rezervacije;
    }

    public void setRezervacije(List<Rezervacija> rezervacije) {
        this.rezervacije = rezervacije;
    }

    public User getUser() {
        return createdBy;
    }

    public void setUser(User createdBy) {
        this.createdBy = createdBy;
    }
}
