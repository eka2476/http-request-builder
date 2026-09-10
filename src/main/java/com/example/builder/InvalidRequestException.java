package com.example.builder;

/**
 * Thrown by a builder's getResult()/build() step when the request
 * being assembled is not in a valid, submittable state
 * (e.g. missing URL, missing method).
 *
 * A dedicated exception type gives callers a clear, specific signal
 * instead of a generic IllegalStateException.
 */
public class InvalidRequestException extends IllegalStateException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
