package springAngularApp.system.domain;

import org.hibernate.Session;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import springAngularApp.Application;
import springAngularApp.system.domain.entities.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@WebAppConfiguration
@Transactional(transactionManager = "txManager")
@TestPropertySource(locations = "classpath:test.properties")
@SpringApplicationConfiguration(classes = {Application.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback(value = true)
public abstract class HibernateIntegrationTest<Entity extends Identifiable> {

    @PersistenceContext
    private EntityManager entityManager;

    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    protected SessionAction<Object> save(Entity entity) {
        saveIdentifiable(entity);
        return new SessionAction<>(getSession());
    }

    private void saveIdentifiable(Identifiable entity) {
        List<Identifiable> relations = entity.getRelations();
        relations.forEach(this::saveIdentifiable);
        getSession().save(entity);
    }

    protected SessionAction<Entity> saveAll(Entity... entities) {
        for (Entity entity : entities) {
            save(entity);
        }
        return new SessionAction<>(getSession());
    }

    protected Entity getById(String entityId) {
        return getSession().get(getEntityClass(), entityId);
    }

    protected abstract Class<Entity> getEntityClass();

    public static class SessionAction<Entity>{

        public SessionAction(Session session) {
            this.session = session;
        }

        private Session session;

        public SessionAction flush() {
            session.flush();
            return this;
        }

        public SessionAction clear() {
            session.clear();
            return this;
        }

        public SessionAction flushAndClear() {
            flush();
            clear();
            return this;
        }

    }

}
