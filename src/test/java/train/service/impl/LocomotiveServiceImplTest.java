package train.service.impl;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import train.dao.LocomotiveDao;
import train.model.Locomotive;
import train.service.LocomotiveService;

class LocomotiveServiceImplTest {
    private LocomotiveService locomotiveService;
    private LocomotiveDao locomotiveDao;
    private Locomotive locomotive;

    @BeforeEach
    void setUp() {
        locomotiveDao = Mockito.mock(LocomotiveDao.class);
        locomotiveService = new LocomotiveServiceImpl(locomotiveDao);
        locomotive = new Locomotive();
        locomotive.setId(1L);
    }

    @Test
    void add_ok() {
        Mockito.when(locomotiveDao.add(locomotive)).thenReturn(locomotive);
        Locomotive actual = locomotiveService.add(locomotive);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(locomotive.getId(), actual.getId());
    }

    @Test
    void get_ok() {
        Mockito.when(locomotiveDao.get(locomotive.getId())).thenReturn(Optional.of(locomotive));
        Locomotive actual = locomotiveService.get(locomotive.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(locomotive.getId(), actual.getId());
    }

    @Test
    void get_notOk() {
        Mockito.when(locomotiveDao.get(locomotive.getId())).thenReturn(Optional.of(locomotive));
    try {
        locomotiveService.get(2L);
    } catch (RuntimeException e) {
        Assertions.assertEquals("Can`t get locomotive by id " + 2L, e.getMessage());
        return;
    }
        Assertions.fail("Expected to receive RuntimeException");
    }
}