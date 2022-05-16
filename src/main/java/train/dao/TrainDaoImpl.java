package train.dao;

import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import train.model.Train;

@Repository
public class TrainDaoImpl implements TrainDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public TrainDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Train add(Train train) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(train);
            transaction.commit();
            return train;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t insert train to DB. Train: "
            + train, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Train> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Train.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Can`t get locomotive by id " + id, e);
        }
    }
}
