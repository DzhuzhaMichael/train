package train.service;

import train.model.Wagon;

public interface WagonService {
    Wagon add(Wagon wagon);

    Wagon get(Long id);
}
