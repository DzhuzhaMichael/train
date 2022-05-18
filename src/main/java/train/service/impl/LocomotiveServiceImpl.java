package train.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import train.dao.LocomotiveDao;
import train.model.Locomotive;
import train.service.LocomotiveService;

@Service
public class LocomotiveServiceImpl implements LocomotiveService {
    private final LocomotiveDao locomotiveDao;

    @Autowired
    public LocomotiveServiceImpl(LocomotiveDao locomotiveDao) {
        this.locomotiveDao = locomotiveDao;
    }

    @Override
    public Locomotive add(Locomotive locomotive) {
        return locomotiveDao.add(locomotive);
    }

    @Override
    public Locomotive get(Long id) {
        return locomotiveDao.get(id).orElseThrow(
                () -> new RuntimeException("Can`t get locomotive by id " + id));
    }
}
