package train.dao.impl;

import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import train.dao.TrainDao;
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
            Query<Train> findById = session.createQuery(
                    "from Train t "
                            + "left join fetch t.locomotives "
                            + "left join fetch t.wagons "
                            + "where t.id = :id", Train.class);
            findById.setParameter("id", id);
            return findById.uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Can`t get train by id " + id, e);
        }
    }

    @Override
    public List<Train> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Train> allTrains = session.createQuery(
                    "select distinct t "
                            + "from Train t "
                            + "left join fetch t.locomotives "
                            + "left join fetch t.wagons", Train.class);
            return allTrains.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can`t get all trains from BD", e);
        }
    }
}
