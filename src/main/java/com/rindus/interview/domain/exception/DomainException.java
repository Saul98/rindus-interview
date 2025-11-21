package com.rindus.interview.domain.exception;

/**
 * Base class for all domain exceptions.
 */
public class DomainException extends RuntimeException {

  public DomainException(String message) {
    super(message);
  }
}
