package travel.converter;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Converter<T, U, V> {

    T convertToEntity(U u);

    V convertToResponse(T t);

    default List<V> convertToList(Function<T, V> convert, Collection<T> collection) {
        return collection.stream().map(convert::apply).collect(Collectors.toList());
    }
}
