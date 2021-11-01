package model;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class UserRepository extends GenericRepository<User> implements BaseRepository<User>{

    @Inject
    public UserRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        super(jpaApi,executionContext);
    }

    @Override
    public CompletionStage<User> save(User entity) {
        return CompletableFuture.supplyAsync(()->this.persist(entity),this.executionContext);
    }

    @Override
    public CompletionStage<User> update(User entity) {
        return CompletableFuture.supplyAsync(()->this.merge(entity),this.executionContext);
    }

    @Override
    public CompletionStage<Void> delete(User entity) {
        return null;
    }

    @Override
    public CompletionStage<Stream<User>> list() {
        return supplyAsync(() ->this.list(User.class), this.executionContext);
    }


}
