package ma.hiddenfounders.codingchallenge.common.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class InstantAttributeConverter implements AttributeConverter<Instant, Timestamp> {
   @Override
   public Timestamp convertToDatabaseColumn(Instant instant) {
      return (instant == null ? null : Timestamp.valueOf(LocalDateTime.ofInstant(instant, ZoneOffset.UTC)));
   }

   @Override
   public Instant convertToEntityAttribute(Timestamp sqlTimestamp) {
      return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime().toInstant(ZoneOffset.UTC));
   }
}