package com.github.marcindabrowski.example.roomsbooking.config;

import static com.github.marcindabrowski.example.roomsbooking.domain.model.Amount.ZERO;

import com.github.marcindabrowski.example.roomsbooking.domain.model.Amount;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.core.convert.converter.Converter;

class AmountConverter implements Converter<String, Amount> {

  @Override
  public Amount convert(String source) {
    return Optional.of(source).map(BigDecimal::new).map(Amount::of).orElse(ZERO);
  }
}
