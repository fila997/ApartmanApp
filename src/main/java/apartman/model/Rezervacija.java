package apartman.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity
@Table(name="rezervacije")
public class Rezervacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "apartman_id")
    private Apartman apartman;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Dodajte format datuma
    private Date pocetak;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Dodajte format datuma
    private Date zavrsetak;




    public Rezervacija(Long id, User createdBy, Apartman apartman, Date pocetak, Date zavrsetak){
        this.id = id;
        this.createdBy = createdBy;
        this.apartman = apartman;
        this.pocetak = pocetak;
        this.zavrsetak = zavrsetak;
    }



    public Date getPocetak() {
        return pocetak;
    }

    public void setPocetak(Date pocetak) {
        this.pocetak = pocetak;
    }

    public Date getZavrsetak() {
        return zavrsetak;
    }

    public void setZavrsetak(Date zavrsetak) {
        this.zavrsetak = zavrsetak;
    }

    public Rezervacija() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }





    public Apartman getApartman() {
        return apartman;
    }

    public void setApartman(Apartman apartman) {
        this.apartman = apartman;
    }


    public User getUser() {
        return createdBy;
    }

    public void setUser(User createdBy) {
        this.createdBy = createdBy;
    }
}


