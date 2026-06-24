package org.yearup.errors;

public class EmptyCart extends RuntimeException {
  public EmptyCart(String message) {
    super(message);
  }
}
