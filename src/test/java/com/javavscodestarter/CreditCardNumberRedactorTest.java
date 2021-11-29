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

  private static final Stream<String> creditCardNumbersStylized = Stream.of(
      // Visa
      "4242 4242 4242 4242",
      "4000 0566 5566 5556",

      // Mastercard
      "5555 5555 5555 4444",
      "2223 0031 2200 3222",
      "5200 8282 8282 8210",
      "5105 1051 0510 5100",

      // American Express
      "3782 822463 10005",
      "3782 822463 10005",

      // Discover
      "6011 1111 1111 1117",
      "6011 0009 9013 9424",

      // Diners Club
      "3056 9300 0902 0004",
      "3622 720627 1667",

      // JCB
      "3566 0020 2036 0505",

      // UnionPay
      "6200 0000 0000 0005");

  private static final Stream<String> allCreditCardNumbers = Stream.concat(creditCardNumbers,
      creditCardNumbersStylized);

  static Stream<Arguments> testRedactArgumentProvider() {
    return allCreditCardNumbers.map((String cc) -> Arguments.of(cc, new RedactResult(1, "PII_CREDIT_CARD")));
  }

  @ParameterizedTest
  @MethodSource("testRedactArgumentProvider")
  void testRedactSingleCcNoWhitespace(String src, RedactResult expected) {
    assertEquals(expected, new CreditCardNumberRedactor(src).redact());
  }
}
