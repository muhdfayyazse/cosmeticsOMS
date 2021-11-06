package model;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.function.Function;
import java.util.stream.Stream;

public class GenericRepository<T extends Serializable> {
    protected final JPAApi jpaApi;
    protected final DatabaseExecutionContext executionContext;


    @Inject
    public GenericRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    public <E> E wrap(Function<EntityManager, E> function) {
        return jpaApi.withTransaction(function);
    }

    public T persist(T t) {
        return this.wrap(em -> this.persist(em, t));
    }
    public T persist(EntityManager em , T t){
        em.persist(t);
        return t;
    }

    public T merge(T t) {
        return this.wrap(em -> this.merge(em, t));
    }
    public T merge(EntityManager em , T t){
        em.merge(t);
        return t;
    }

    public T remove(T t) {
        return this.wrap(em -> this.remove(em, t));
    }
    public T remove(EntityManager em , T t){
        em.remove(t);
        return t;
    }





    public Stream<T> list(Class<T> clazz) {
        return this.wrap(em->this.list(em,clazz));
    }
    public Stream<T> list(EntityManager em ,Class<T> clazz) {
        return em.createQuery("select p from "+ clazz.getName() + " p" , clazz).getResultList().stream();
    }
    public Stream<T> listByProductId(String pId, Class<T> clazz) {
        return this.wrap(em->this.listByProductId(em,pId,clazz));
    }
    public Stream<T> listByProductId(EntityManager em, String pId ,Class<T> clazz) {
        return em.createQuery("select p from "+ clazz.getName() + " p where p.productId='"+ pId +"'" , clazz).getResultList().stream();
    }

}
