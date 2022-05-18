package train.service;

import train.model.Locomotive;

public interface LocomotiveService {
    Locomotive add(Locomotive locomotive);

    Locomotive get(Long id);
}
