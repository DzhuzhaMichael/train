package train.dao.impl;

import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import train.dao.WagonDao;
import train.model.Wagon;

@Repository
public class WagonDaoImpl implements WagonDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public WagonDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Wagon add(Wagon wagon) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(wagon);
            transaction.commit();
            return wagon;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t insert wagon to DB. Wagon: "
            + wagon, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Wagon> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Wagon.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Can`t get wagon by id " + id, e);
        }
    }
}
