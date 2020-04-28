package travel.validation;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Validator<T> {
    private T object;

    private Validator(T object){
        this.object = object;
    }

    public static <T> Validator<T> of(T object){
        return new Validator<>(object);
    }

    public Validator<T> validate(Predicate<T> predicate, Supplier<RuntimeException> exception){
        if (predicate.test(object))
            throw exception.get();

        return this;
    }

    public <U> Validator<T> validate(Function<T, U> function, Predicate<U> predicate, Supplier<RuntimeException> exception){
        return validate(function.andThen(predicate::test)::apply, exception);
    }

    public T get(){
        return object;
    }

}
