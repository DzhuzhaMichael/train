package train.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import train.dao.WagonDao;
import train.model.Wagon;

class WagonDaoImplTest extends AbstractTest {
    private WagonDao wagonDao;
    private Wagon wagon;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {Wagon.class};
    }

    @BeforeEach
    void setUp() {
        wagonDao = new WagonDaoImpl(getSessionFactory());
        wagon = new Wagon();
    }

    @Test
    void add_ok() {
        Wagon actual = wagonDao.add(wagon);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1L, actual.getId());
    }

    @Test
    void add_notOk() {
        try {
            wagonDao.add(null);
        } catch (RuntimeException e) {
            Assertions.assertEquals("Can`t insert wagon to DB. "
                    + "Wagon: null", e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive RuntimeException");
    }

    @Test
    void get_ok() {
        Wagon addedWagon = wagonDao.add(wagon);
        Wagon actual = wagonDao.get(wagon.getId()).get();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(addedWagon.getId(), actual.getId());
    }
}