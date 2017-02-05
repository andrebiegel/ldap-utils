package de.abiegel.ldap.processing.api;

@FunctionalInterface
public interface CheckedBiConsumer<T, U>{

	   /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    void accept(T t, U u) throws Exception;
}
