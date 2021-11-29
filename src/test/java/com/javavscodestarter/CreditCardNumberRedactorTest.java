package com.javavscodestarter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CreditCardNumberRedactorTest {
  // Fixture source: https://stripe.com/docs/testing
  private static final Stream<String> creditCardNumbers = Stream.of(
      // Visa
      "4242424242424242",
      "4000056655665556",

      // Mastercard
      "5555555555554444",
      "2223003122003222",
      "5200828282828210",
      "5105105105105100",

      // American Express
      "378282246310005",
      "371449635398431",

      // Discover
      "6011111111111117",
      "6011000990139424",

      // Diners Club
      "3056930009020004",
      "36227206271667",

      // JCB
      "3566002020360505",

      // UnionPay
      "6200000000000005");

  static Stream<Arguments> testRedactArgumentProvider() {
    return creditCardNumbers.map((String cc) -> Arguments.of(cc, new RedactResult(1, "PII_CREDIT_CARD")));
  }

  @ParameterizedTest
  @MethodSource("testRedactArgumentProvider")
  void testRedactSingleCcNoWhitespace(String src, RedactResult expected) {
    assertEquals(expected, new CreditCardNumberRedactor(src).redact());
  }
}
