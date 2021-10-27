package model;

import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository{

    @Inject
    public UserRepositoryImpl(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        super(jpaApi,executionContext);
    }

    @Override
    public CompletionStage<User> add(User user) {
        return supplyAsync(() -> this.insert(user), this.executionContext);
    }

    @Override
    public CompletionStage<Stream<User>> list() {
        return supplyAsync(() ->this.list(User.class), this.executionContext);
    }


}
