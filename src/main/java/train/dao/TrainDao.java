package train.dao;

import java.util.List;
import java.util.Optional;
import train.model.Train;

public interface TrainDao {
    Train add(Train train);

    Optional<Train> get(Long id);

    List<Train> getAll();
}
