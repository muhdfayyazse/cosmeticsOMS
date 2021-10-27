package model;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
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

    protected <E> E wrap(Function<EntityManager, E> function) {
        return jpaApi.withTransaction(function);
    }

    protected T insert(T t) {
        return this.wrap(em -> this.insert(em, t));
    }

    public T insert(EntityManager em , T t){
        em.persist(t);
        return t;
    }


    protected Stream<T> list(Class<T> clazz) {
        return this.wrap(em->this.list(em,clazz));
    }
    public Stream<T> list(EntityManager em ,Class<T> clazz) {
        return em.createQuery("select p from "+ clazz.getName() + " p" , clazz).getResultList().stream();
    }

}
