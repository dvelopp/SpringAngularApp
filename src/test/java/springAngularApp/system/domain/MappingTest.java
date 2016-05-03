package springAngularApp.system.domain;

import org.junit.Test;
import springAngularApp.system.domain.entities.Identifiable;

public abstract class MappingTest<Entity extends Identifiable> extends HibernateIntegrationTest<Entity> {

    @Test
    public void testMapping() {
        Entity expected = getExpectedEntity();
        save(expected);
        Entity actual = getActualEntity(expected);

        performChecks(expected, actual);
    }

    private Entity getActualEntity(Entity expected) {
        Entity actual = getById(expected.getId());
        if (actual == null) {
            throw new IllegalStateException("Expected entity was not saved");
        }
        return actual;
    }

    private Entity getExpectedEntity() {
        Entity expected  = getEntityToSave();
        if (expected == null) {
            throw new IllegalArgumentException("Expected entity can't be null");
        }
        return expected;
    }

    protected abstract Entity getEntityToSave();

    protected abstract void performChecks(Entity expected, Entity actual);
}

