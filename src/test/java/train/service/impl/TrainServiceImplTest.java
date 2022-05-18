package train.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import train.dao.TrainDao;
import train.model.Locomotive;
import train.model.Train;
import train.model.Wagon;
import train.service.TrainService;

class TrainServiceImplTest {
    private static final int PASSENGER_WEIGHT = 75;
    private static final int PASSENGERS_NUMBER_NEEDS_C0NDUCTOR = 50;
    private TrainService trainService;
    private TrainDao trainDao;
    private Locomotive firstLocomotive;
    private Wagon firstWagon;
    private Train train;
    private Set<Locomotive> locomotives;
    private Set<Wagon> wagons;

    @BeforeEach
    void setUp() {
        trainDao = Mockito.mock(TrainDao.class);
        trainService = new TrainServiceImpl(trainDao);
        firstLocomotive = new Locomotive();
        firstWagon = new Wagon();
        train = new Train();
        firstLocomotive.setEmptyWeight(130000);
        firstWagon.setEmptyWeight(60000);
        firstLocomotive.setGoodsLoadingWeight(200);
        firstWagon.setGoodsLoadingWeight(5800);
        firstLocomotive.setNumberOfPassengers(2);
        firstWagon.setNumberOfPassengers(58);
        locomotives = Set.of(firstLocomotive);
        wagons = Set.of(firstWagon);
        train.setId(1L);
        train.setLocomotives(locomotives);
        train.setWagons(wagons);
    }

    @Test
    void add_ok() {
        Mockito.when(trainDao.getAll()).thenReturn(List.of());
        Mockito.when(trainDao.add(train)).thenReturn(train);
        Train actual = trainService.add(train);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(train.getId(), actual.getId());
    }

    @Test
    void get_ok() {
        Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
        Train actual = trainService.get(train.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(train.getId(), actual.getId());
    }

    @Test
    void get_notOk() {
        Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
        try {
            trainService.get(2L);
        } catch (RuntimeException e) {
            Assertions.assertEquals("Can`t get train by id " + 2L, e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive RuntimeException");
    }

    @Test
    void getAll_ok() {
        Mockito.when(trainDao.getAll()).thenReturn(List.of(train));
        List<Train> actual = trainService.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void getEmptyWeight_ok() {
        Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
        int actual = trainService.getEmptyWeight(train.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(firstLocomotive.getEmptyWeight()
                + firstWagon.getEmptyWeight(), actual);
    }

    @Test
    void getMaxNumberOfPassengers_ok() {
        Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
        int actual = trainService.getMaxNumberOfPassengers(train.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(firstLocomotive.getNumberOfPassengers()
                + firstWagon.getNumberOfPassengers(), actual);
    }

    @Test
    void getMaxLoadingWeight_ok() {
        Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
        int actual = trainService.getMaxLoadingWeight(train.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(firstLocomotive.getGoodsLoadingWeight()
                + firstWagon.getGoodsLoadingWeight(), actual);
    }

    @Test
    void getMaxTrainLoading_ok() {
        Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
        int actual = trainService.getMaxTrainLoading(train.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(((firstLocomotive.getNumberOfPassengers()
                        + firstWagon.getNumberOfPassengers()) * PASSENGER_WEIGHT)
                        + (firstLocomotive.getGoodsLoadingWeight()
                        + firstWagon.getGoodsLoadingWeight()), actual);
        }

    @Test
    void getMaxTotalTrainWeight_ok() {
        Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
        int actual = trainService.getMaxTotalTrainWeight(train.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(((firstLocomotive.getEmptyWeight()
                + firstWagon.getEmptyWeight())
                + ((firstLocomotive.getNumberOfPassengers()
                + firstWagon.getNumberOfPassengers()) * PASSENGER_WEIGHT)
                + (firstLocomotive.getGoodsLoadingWeight()
                + firstWagon.getGoodsLoadingWeight())), actual);
    }

    @Test
    void getTrainLength_ok() {
        firstLocomotive.setLength(15);
        firstWagon.setLength(20);
        Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
        int actual = trainService.getTrainLength(train.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(firstLocomotive.getLength()
                + firstWagon.getLength(), actual);
    }

    @Test
    void isCapableToDrive_ok_true() {
       firstLocomotive.setTractiveEffort(500000);
       Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
       boolean actual = trainService.isCapableToDrive(train.getId());
       int maxTrainLoading = ((firstLocomotive.getNumberOfPassengers()
               + firstWagon.getNumberOfPassengers()) * PASSENGER_WEIGHT)
               + (firstLocomotive.getGoodsLoadingWeight()
               + firstWagon.getGoodsLoadingWeight());
       Assertions.assertEquals((firstLocomotive.getTractiveEffort()
               >= maxTrainLoading), actual);
    }

    @Test
    void isCapableToDrive_ok_false() {
        firstLocomotive.setTractiveEffort(1000);
        Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
        boolean actual = trainService.isCapableToDrive(train.getId());
        int maxTrainLoading = ((firstLocomotive.getNumberOfPassengers()
                + firstWagon.getNumberOfPassengers()) * PASSENGER_WEIGHT)
                + (firstLocomotive.getGoodsLoadingWeight()
                + firstWagon.getGoodsLoadingWeight());
        Assertions.assertEquals((firstLocomotive.getTractiveEffort()
                >= maxTrainLoading), actual);
    }

    @Test
    void getNecessaryConductorsNumber_ok_notEmptyTrain() {
        Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
        int actual = trainService.getNecessaryConductorsNumber(train.getId());
        int maxNumberOfPassengers = (firstLocomotive.getNumberOfPassengers()
                + firstWagon.getNumberOfPassengers());
        int conductorsInvolved = maxNumberOfPassengers / PASSENGERS_NUMBER_NEEDS_C0NDUCTOR;
        int neededNumberOfConductors = (maxNumberOfPassengers % PASSENGERS_NUMBER_NEEDS_C0NDUCTOR == 0)
                ? conductorsInvolved : conductorsInvolved + 1;
        Assertions.assertEquals(neededNumberOfConductors, actual);
    }

    @Test
    void getNecessaryConductorsNumber_ok_emptyTrain() {
        firstLocomotive.setNumberOfPassengers(0);
        firstWagon.setNumberOfPassengers(0);
        Mockito.when(trainDao.get(train.getId())).thenReturn(Optional.of(train));
        int actual = trainService.getNecessaryConductorsNumber(train.getId());
        int maxNumberOfPassengers = (firstLocomotive.getNumberOfPassengers()
                + firstWagon.getNumberOfPassengers());
        int conductorsInvolved = maxNumberOfPassengers / PASSENGERS_NUMBER_NEEDS_C0NDUCTOR;
        int neededNumberOfConductors = (maxNumberOfPassengers % PASSENGERS_NUMBER_NEEDS_C0NDUCTOR == 0)
                ? conductorsInvolved : conductorsInvolved + 1;
        Assertions.assertEquals(neededNumberOfConductors, actual);
    }

    @Test
    void isExisted_notOk_locomotives() {
        Train secondTrain = new Train();
        secondTrain.setId(2L);
        secondTrain.setLocomotives(Set.of(firstLocomotive));
        secondTrain.setWagons(Set.of(new Wagon()));
        Mockito.when(trainDao.getAll()).thenReturn(List.of(train));
        Mockito.when(trainDao.add(secondTrain)).thenReturn(secondTrain);
        try {
            trainService.add(secondTrain);
        } catch (RuntimeException e) {
            Assertions.assertEquals("Locomotives can only be assigned to one train at a time",
                    e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive RuntimeException");
    }

    @Test
    void isExisted_notOk_wagons() {
        Train secondTrain = new Train();
        secondTrain.setId(2L);
        secondTrain.setLocomotives(Set.of(new Locomotive()));
        secondTrain.setWagons(Set.of(firstWagon));
        Mockito.when(trainDao.getAll()).thenReturn(List.of(train));
        Mockito.when(trainDao.add(secondTrain)).thenReturn(secondTrain);
        try {
            trainService.add(secondTrain);
        } catch (RuntimeException e) {
            Assertions.assertEquals("Wagons can only be assigned to one train at a time",
                    e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive RuntimeException");
    }
}