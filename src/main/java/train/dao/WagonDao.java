package train.dao;

import java.util.Optional;
import train.model.Wagon;

public interface WagonDao {
    Wagon add (Wagon wagon);

    Optional<Wagon> get (Long id);
}
