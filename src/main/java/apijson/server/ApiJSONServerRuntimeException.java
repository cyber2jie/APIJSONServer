package apijson.server;

public class ApiJSONServerRuntimeException extends RuntimeException{
    public ApiJSONServerRuntimeException() {
    }

    public ApiJSONServerRuntimeException(String message) {
        super(message);
    }

    public ApiJSONServerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiJSONServerRuntimeException(Throwable cause) {
        super(cause);
    }
}
