package train.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import train.dao.TrainDao;
import train.model.Locomotive;
import train.model.Train;
import train.model.Wagon;
import train.service.TrainService;

@Service
public class TrainServiceImpl implements TrainService {
    private static final int PASSENGER_WEIGHT = 75;
    private static final int PASSENGERS_NUMBER_NEEDS_C0NDUCTOR = 50;
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

    @Override
    public int getEmptyWeight(Long id) {
        Train trainFromDb = trainDao.get(id).get();
        int emptyWeight = trainFromDb.getLocomotives().stream()
                .mapToInt(Locomotive::getEmptyWeight)
                .sum();
        emptyWeight += trainFromDb.getWagons().stream()
                .mapToInt(Wagon::getEmptyWeight)
                .sum();
        return emptyWeight;
    }

    @Override
    public int getMaxNumberOfPassengers(Long id) {
        Train trainFromDb = trainDao.get(id).get();
        int maxPassengersNumber = trainFromDb.getLocomotives().stream()
                .mapToInt(Locomotive::getNumberOfPassengers)
                .sum();
        maxPassengersNumber += trainFromDb.getWagons().stream()
                .mapToInt(Wagon::getNumberOfPassengers)
                .sum();
        return maxPassengersNumber;
    }

    @Override
    public int getMaxLoadingWeight(Long id) {
        Train trainFromDb = trainDao.get(id).get();
        int maxLoadingWeight = trainFromDb.getLocomotives().stream()
                .mapToInt(Locomotive::getGoodsLoadingWeight)
                .sum();
        maxLoadingWeight += trainFromDb.getWagons().stream()
                .mapToInt(Wagon::getGoodsLoadingWeight)
                .sum();
        return maxLoadingWeight;
    }

    @Override
    public int getMaxTrainLoading(Long id) {
        Train trainFromDb = trainDao.get(id).get();
        return (getMaxNumberOfPassengers(trainFromDb.getId()) * PASSENGER_WEIGHT)
                + getMaxLoadingWeight(trainFromDb.getId());
    }

    @Override
    public int getMaxTotalTrainWeight(Long id) {
        Train trainFromDb = trainDao.get(id).get();
        return getEmptyWeight(trainFromDb.getId()) + getMaxTrainLoading(trainFromDb.getId());
    }

    @Override
    public int getTrainLength(Long id) {
        Train trainFromDb = trainDao.get(id).get();
        int trainLength = trainFromDb.getLocomotives().stream()
                .mapToInt(Locomotive::getLength)
                .sum();
        trainLength += trainFromDb.getWagons().stream()
                .mapToInt(Wagon::getLength)
                .sum();
        return trainLength;
    }

    @Override
    public boolean isCapableToDrive(Long id) {
        Train trainFromDb = trainDao.get(id).get();
        int totalTractiveEffort = trainFromDb.getLocomotives().stream()
                .mapToInt(Locomotive::getTractiveEffort)
                .sum();
        return totalTractiveEffort >= getMaxTrainLoading(trainFromDb.getId());
    }

    @Override
    public int getNecessaryConductorsNumber(Long id) {
        Train trainFromDb = trainDao.get(id).get();
        int maxNumberOfPassengers = getMaxNumberOfPassengers(trainFromDb.getId());
        if (maxNumberOfPassengers == 0) {
            return 0;
        }
        return (maxNumberOfPassengers <= PASSENGERS_NUMBER_NEEDS_C0NDUCTOR)
                ? 1 : maxNumberOfPassengers / PASSENGERS_NUMBER_NEEDS_C0NDUCTOR;
    }
}
