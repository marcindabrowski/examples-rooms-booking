package com.github.marcindabrowski.example.roomsbooking.config;

import static com.github.marcindabrowski.example.roomsbooking.domain.model.Amount.ZERO;
import static java.util.Optional.ofNullable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.github.marcindabrowski.example.roomsbooking.domain.model.Amount;
import java.io.IOException;
import java.io.Serial;
import java.math.BigDecimal;

class AmountDeserializer extends StdDeserializer<Amount> {
  @Serial private static final long serialVersionUID = -7770059694695607023L;

  AmountDeserializer() {
    super(Amount.class);
  }

  @Override
  public Amount deserialize(JsonParser parser, DeserializationContext context) throws IOException {
    return ofNullable(parser.getText()).map(BigDecimal::new).map(Amount::of).orElse(ZERO);
  }
}
