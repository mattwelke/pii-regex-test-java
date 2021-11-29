package com.javavscodestarter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CreditCardNumberRedactorTest {
  // Fixture source: https://stripe.com/docs/testing
  private static final List<String> creditCardNumbers = List.of(
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

  private static final List<String> creditCardNumbersStylized = List.of(
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

  // Test fixtures must be a list because streams can't be re-used in multiple
  // tests.
  private static final List<String> allCreditCardNumbers = Stream
      .concat(creditCardNumbers.stream(), creditCardNumbersStylized.stream())
      .collect(Collectors.toList());

  static Stream<Arguments> testRedactSingleCcNoWhitespaceArgumentProvider() {
    return allCreditCardNumbers.stream().map(number -> Arguments.of(number,
        new RedactResult(1, "PII_CREDIT_CARD")));
  }

  @DisplayName("Can sanitize a string that is a credit card number, reporting 1 redaction.")
  @ParameterizedTest
  @MethodSource("testRedactSingleCcNoWhitespaceArgumentProvider")
  void testRedactSingleCcNoWhitespace(String src, RedactResult expected) {
    assertEquals(expected, new CreditCardNumberRedactor(src).redact());
  }

  static Stream<Arguments> testRedactSingleCcInStringArgumentProvider() {
    return allCreditCardNumbers.stream()
        .map(number -> Arguments.of(" " + number + " ", new RedactResult(1, " PII_CREDIT_CARD ")));
  }

  @DisplayName("Can sanitize a string that contains a credit card number, reporting 1 redaction.")
  @ParameterizedTest
  @MethodSource("testRedactSingleCcInStringArgumentProvider")
  void testRedactSingleCcInString(String src, RedactResult expected) {
    assertEquals(expected, new CreditCardNumberRedactor(src).redact());
  }

  static Stream<Arguments> testRedactTwoCcWithSpaceArgumentProvider() {
    return allCreditCardNumbers.stream().map(number -> Arguments.of(number + " " + number,
        new RedactResult(2, "PII_CREDIT_CARD PII_CREDIT_CARD")));
  }

  @DisplayName("Can sanitize a string that contains multiple credit card numbers, reporting 2 redactions.")
  @ParameterizedTest
  @MethodSource("testRedactTwoCcWithSpaceArgumentProvider")
  void testRedactTwoCcWithSpace(String src, RedactResult expected) {
    assertEquals(expected, new CreditCardNumberRedactor(src).redact());
  }

  
  static Stream<Arguments> testRedactTwoCcWithNewlineArgumentProvider() {
    return allCreditCardNumbers.stream().map(number -> Arguments.of(number + "\n" + number,
        new RedactResult(2, "PII_CREDIT_CARD\nPII_CREDIT_CARD")));
  }

  @DisplayName("Can sanitize a string that contains multiple credit card numbers, separated by a newline, reporting 2 redactions.")
  @ParameterizedTest
  @MethodSource("testRedactTwoCcWithNewlineArgumentProvider")
  void testRedactTwoCcWithNewline(String src, RedactResult expected) {
    assertEquals(expected, new CreditCardNumberRedactor(src).redact());
  }
}
