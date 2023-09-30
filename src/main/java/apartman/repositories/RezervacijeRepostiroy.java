package apartman.repositories;

import apartman.model.Apartman;
import apartman.model.User;
import apartman.model.Rezervacija;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RezervacijeRepostiroy extends JpaRepository <Rezervacija, Long> {
    Rezervacija findByCreatedByAndApartman(User createdBy, Apartman apartman);
    List<Rezervacija> findByCreatedBy(User createdBy);
}
