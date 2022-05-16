package train.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import train.dao.TrainDao;
import train.model.Train;

@Service
public class TrainServiceImpl implements TrainService {
    private final TrainDao trainDao;

    @Autowired
    public TrainServiceImpl(TrainDao trainDao) {
        this.trainDao = trainDao;
    }

    @Override
    public Train add(Train train) {
        return trainDao.add(train);
    }

    @Override
    public Train get(Long id) {
        return trainDao.get(id).orElseThrow(
                () -> new RuntimeException("Can`t get train by id " + id));
    }
}
