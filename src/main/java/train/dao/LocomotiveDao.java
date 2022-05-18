package train.dao;

import java.util.Optional;
import train.model.Locomotive;

public interface LocomotiveDao {
    Locomotive add(Locomotive locomotive);

    Optional<Locomotive> get(Long id);
}
