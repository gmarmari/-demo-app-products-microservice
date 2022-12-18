package gmarmari.demo.microservices.products.api;

import java.util.function.Supplier;

public enum Response {

    OK,
    ERROR,
    ;

    boolean isError() {
        return this == ERROR;
    }

    public <X extends Throwable> void throwIfError(Supplier<? extends X> exceptionSupplier) throws X {
        if (isError()) {
            throw exceptionSupplier.get();
        }
    }

}
