package model;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@ImplementedBy(UserRepositoryImpl.class)
public interface UserRepository {
    CompletionStage<User> add(User user);

    CompletionStage<Stream<User>> list();
}
