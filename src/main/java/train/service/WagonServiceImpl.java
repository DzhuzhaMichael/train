package train.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import train.dao.WagonDao;
import train.model.Wagon;

@Service
public class WagonServiceImpl implements WagonService {
    private final WagonDao wagonDao;

    @Autowired
    public WagonServiceImpl(WagonDao wagonDao) {
        this.wagonDao = wagonDao;
    }

    @Override
    public Wagon add(Wagon wagon) {
        return wagonDao.add(wagon);
    }

    @Override
    public Wagon get(Long id) {
        return wagonDao.get(id).orElseThrow(
                () -> new RuntimeException("Can`t get wagon by id " + id));
    }
}
