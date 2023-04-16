package com.github.marcindabrowski.example.roomsbooking.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.github.marcindabrowski.example.roomsbooking.domain.model.Amount;
import java.io.IOException;
import java.io.Serial;

class AmountSerializer extends StdSerializer<Amount> {
  @Serial private static final long serialVersionUID = -7437440075754725878L;

  AmountSerializer() {
    super(Amount.class);
  }

  @Override
  public void serialize(Amount value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    gen.writeString(value.value().toPlainString());
  }
}
