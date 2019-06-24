package uz.kvikk.yabo.utils;

@FunctionalInterface
public interface TripleFunction<T, R, Q, S> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    S apply(T t, R r, Q q);

}
