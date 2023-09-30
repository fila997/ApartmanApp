package apartman.repositories;

import apartman.model.Apartman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmanRepository extends JpaRepository <Apartman, Long> {}