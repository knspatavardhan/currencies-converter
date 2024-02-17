package com.knspatavardhan.currenciesconverterwrapper.error;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class RuntimeWebException extends RuntimeException {

    private final String message;

    private final HttpStatus httpStatus;

    private final Throwable throwable;

    private final Map<String, Object> params;

    RuntimeWebException(final String message,
                        final HttpStatus httpStatus,
                        final Throwable throwable,
                        final Map<String, Object> params) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.throwable = throwable;
        this.params = params;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public static class Builder {
        private String message;

        private HttpStatus httpStatus;

        private Throwable throwable;

        private Map<String, Object> params;

        public static Builder builder() {
            return new Builder();
        }

        public Builder message(final String message, Object... params) {
            this.message = String.format(message, params);
            return this;
        }

        public Builder httpStatus(final HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public Builder throwable(final Throwable throwable) {
            this.throwable = throwable;
            return this;
        }

        public Builder params(final Map<String, Object> params) {
            this.params = params;
            return this;
        }

        public RuntimeWebException build() {
            return new RuntimeWebException(this.message, this.httpStatus, this.throwable, this.params);
        }
    }
}
