package model;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class BaseRepository<T extends Serializable> {
    protected final JPAApi jpaApi;
    protected final DatabaseExecutionContext executionContext;

    @Inject
    public BaseRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    protected T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    protected T insert(EntityManager em, T t) {
        em.persist(t);
        return t;
    }

    protected Stream<T> list(EntityManager em, Class<T> clazz) {
        List<T> list = em.createQuery("select p from "+ clazz.getName() + " p" , clazz).getResultList();
        return list.stream();
    }

}
