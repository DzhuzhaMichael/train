package train.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import train.dao.LocomotiveDao;
import train.model.Locomotive;

class LocomotiveDaoImplTest extends AbstractTest {
    private LocomotiveDao locomotiveDao;
    private Locomotive locomotive;

    @Override
    protected Class<?>[] entities() {
        return new Class[] {Locomotive.class};
    }

    @BeforeEach
    void setUp() {
        locomotiveDao = new LocomotiveDaoImpl(getSessionFactory());
        locomotive = new Locomotive();
    }

    @Test
    void add_ok() {
        Locomotive actual = locomotiveDao.add(locomotive);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1L, actual.getId());
    }

    @Test
    void add_notOk() {
        try {
            locomotiveDao.add(null);
        } catch (RuntimeException e) {
            Assertions.assertEquals("Can`t insert locomotive to DB. "
                            + "Locomotive: null", e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive RuntimeException");
    }

    @Test
    void get_ok() {
        Locomotive addedLocomotive = locomotiveDao.add(locomotive);
        Locomotive actual = locomotiveDao.get(locomotive.getId()).get();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(addedLocomotive.getId(), actual.getId());
    }
}