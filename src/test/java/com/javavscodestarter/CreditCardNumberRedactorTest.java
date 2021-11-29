package com.javavscodestarter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CreditCardNumberRedactorTest {
  /**
   * JUnit 5 arg provider.
   * Fixture source: https://stripe.com/docs/testing
   * @return
   */
  static Stream<Arguments> testRedactArgumentProvider() {
    Stream<Arguments> creditCardNumbers = Stream.of(
      // Visa
      Arguments.of("4242424242424242", new RedactResult(1, "PII_CREDIT_CARD")),
      Arguments.of("4000056655665556", new RedactResult(1, "PII_CREDIT_CARD")),

      // Mastercard
      Arguments.of("5555555555554444", new RedactResult(1, "PII_CREDIT_CARD")),
      Arguments.of("2223003122003222", new RedactResult(1, "PII_CREDIT_CARD")),
      Arguments.of("5200828282828210", new RedactResult(1, "PII_CREDIT_CARD")),
      Arguments.of("5105105105105100", new RedactResult(1, "PII_CREDIT_CARD")),

      // American Express
      Arguments.of("378282246310005", new RedactResult(1, "PII_CREDIT_CARD")),
      Arguments.of("371449635398431", new RedactResult(1, "PII_CREDIT_CARD")),

      // Discover
      Arguments.of("6011111111111117", new RedactResult(1, "PII_CREDIT_CARD")),
      Arguments.of("6011000990139424", new RedactResult(1, "PII_CREDIT_CARD")),

      // Diners Club
      Arguments.of("3056930009020004", new RedactResult(1, "PII_CREDIT_CARD")),
      Arguments.of("36227206271667", new RedactResult(1, "PII_CREDIT_CARD")),

      // JCB
      Arguments.of("3566002020360505", new RedactResult(1, "PII_CREDIT_CARD")),

      // UnionPay
      Arguments.of("6200000000000005", new RedactResult(1, "PII_CREDIT_CARD"))
    );

    return creditCardNumbers;    
  }

  @ParameterizedTest
  @MethodSource("testRedactArgumentProvider")
  void testRedact(String src, RedactResult expected) {
    assertEquals(expected, new CreditCardNumberRedactor(src).redact());
  }
}
