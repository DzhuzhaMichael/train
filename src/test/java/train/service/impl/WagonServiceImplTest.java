package train.service.impl;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import train.dao.WagonDao;
import train.model.Wagon;
import train.service.WagonService;

class WagonServiceImplTest {
    private WagonService wagonService;
    private WagonDao wagonDao;
    private Wagon wagon;

    @BeforeEach
    void setUp() {
        wagonDao = Mockito.mock(WagonDao.class);
        wagonService = new WagonServiceImpl(wagonDao);
        wagon = new Wagon();
        wagon.setId(1L);
    }

    @Test
    void add_ok() {
        Mockito.when(wagonDao.add(wagon)).thenReturn(wagon);
        Wagon actual = wagonService.add(wagon);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(wagon.getId(), actual.getId());
    }

    @Test
    void get_ok() {
        Mockito.when(wagonDao.get(wagon.getId())).thenReturn(Optional.of(wagon));
        Wagon actual = wagonService.get(this.wagon.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(wagon.getId(), actual.getId());
    }

    @Test
    void get_notOk() {
        Mockito.when(wagonDao.get(wagon.getId())).thenReturn(Optional.of(wagon));
        try {
            wagonService.get(2L);
        } catch (RuntimeException e) {
            Assertions.assertEquals("Can`t get wagon by id " + 2L, e.getMessage());
            return;
        }
        Assertions.fail("Expected to receive RuntimeException");
    }
}