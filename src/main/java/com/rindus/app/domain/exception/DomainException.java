package com.rindus.app.domain.exception;

/**
 * Base class for all domain exceptions.
 */
public class DomainException extends RuntimeException {

  public DomainException(String message) {
    super(message);
  }
}
