package train.dao.impl;

import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import train.dao.LocomotiveDao;
import train.model.Locomotive;

@Repository
public class LocomotiveDaoImpl implements LocomotiveDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public LocomotiveDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Locomotive add(Locomotive locomotive) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(locomotive);
            transaction.commit();
            return locomotive;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t insert locomotive to DB. Locomotive: "
            + locomotive, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Locomotive> get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Locomotive.class, id));
        } catch (Exception e) {
            throw new RuntimeException("Can`t get locomotive by id " + id, e);
        }
    }
}
