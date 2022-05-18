package train.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
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
        isExisted(train);
        return trainDao.add(train);
    }

    @Override
    public Train get(Long id) {
        return trainDao.get(id).orElseThrow(
                () -> new RuntimeException("Can`t get train by id " + id));
    }

    @Override
    public List<Train> getAll() {
        return trainDao.getAll();
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
        int conductorsInvolved = maxNumberOfPassengers / PASSENGERS_NUMBER_NEEDS_C0NDUCTOR;
        return (maxNumberOfPassengers % PASSENGERS_NUMBER_NEEDS_C0NDUCTOR == 0)
                ? conductorsInvolved : conductorsInvolved + 1;
    }

    private void isExisted(Train train) {
        List<Train> allTrainsFromBd = trainDao.getAll();
        List<Locomotive> locomotivesOfExistingTrains = allTrainsFromBd.stream()
                .map(Train::getLocomotives)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        List<Wagon> wagonsOfExistingTrains = allTrainsFromBd.stream()
                .map(Train::getWagons)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        List<Locomotive> currentTrainLocomotives = new ArrayList<>(train.getLocomotives());
        List<Wagon> currentTrainWagons = new ArrayList<>(train.getWagons());
        for (Locomotive currentTrainLocomotive : currentTrainLocomotives) {
            if (locomotivesOfExistingTrains.contains(currentTrainLocomotive)) {
                throw new RuntimeException("Locomotives can only be assigned to one "
                        + "train at a time");
            }
        }
        for (Wagon currentTrainWagon : currentTrainWagons) {
            if (wagonsOfExistingTrains.contains(currentTrainWagon)) {
                throw new RuntimeException("Wagons can only be assigned to one train at a time");
            }
        }
    }
}
