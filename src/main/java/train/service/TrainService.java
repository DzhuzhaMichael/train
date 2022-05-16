package train.service;

import train.model.Train;

public interface TrainService {
    Train add(Train train);

    Train get(Long id);
}
