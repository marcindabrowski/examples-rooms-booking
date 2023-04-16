package com.github.marcindabrowski.example.roomsbooking.domain.model;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

import java.math.BigDecimal;

public record Amount(BigDecimal value) implements Comparable<Amount> {
  public static final Amount ZERO = new Amount(BigDecimal.ZERO);

  public Amount {
    requireNonNull(value, "Amount's value must not be null");
    notNegative(value);
  }

  private static void notNegative(BigDecimal value) {
    if (value.signum() < 0) {
      throw new IllegalArgumentException("Amount's value must not be negative, but got " + value);
    }
  }

  public static Amount of(BigDecimal value) {
    return ofNullable(value).map(Amount::new).orElse(ZERO);
  }

  public Amount add(Amount addend) {
    return ofNullable(addend).map(Amount::value).map(value::add).map(Amount::new).orElse(this);
  }

  /**
   * @apiNote Note: this class depends on BigDecimal and has a natural ordering that is inconsistent
   *     with equals.
   */
  @Override
  public int compareTo(Amount that) {
    return this.value.compareTo(that.value);
  }
}
