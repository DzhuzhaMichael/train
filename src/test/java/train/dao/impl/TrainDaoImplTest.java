package train.dao.impl;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import train.dao.TrainDao;
import train.model.Locomotive;
import train.model.Train;
import train.model.Wagon;

class TrainDaoImplTest extends AbstractTest {
    private TrainDao trainDao;
    private Train train;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {Train.class, Locomotive.class, Wagon.class};
    }

    @BeforeEach
    void setUp() {
        trainDao = new TrainDaoImpl(getSessionFactory());
        train = new Train();
        train.setLocomotives(Set.of());
        train.setWagons(Set.of());
    }

    @Test
    void add_ok() {
        Train actual = trainDao.add(train);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1L, actual.getId());
    }

    @Test
    void add_notOk() {
        train.setLocomotives(Set.of(new Locomotive()));
        try {
            trainDao.add(train);
        } catch (RuntimeException e) {
            Assertions.assertEquals("Can`t insert train to DB. Train: "
                            + train, e.getMessage());
            return;
        }
         Assertions.fail("Expected to receive RuntimeException");
    }

    @Test
    void get_ok() {
        Train addedTrain = trainDao.add(train);
        Train actual = trainDao.get(train.getId()).get();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(addedTrain.getId(), actual.getId());
    }

    @Test
    void getAll_ok() {
        trainDao.add(train);
        List<Train> actual = trainDao.getAll();
        Assertions.assertEquals(1, actual.size());
    }
}