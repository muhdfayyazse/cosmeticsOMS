package model;

import java.io.Serializable;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface BaseRepository<T extends Serializable> {
    CompletionStage<T> save(T entity);
    CompletionStage<T> update(T entity);
    CompletionStage<Void> delete(T entity);
    CompletionStage<Stream<T>> list();
}
