package com.javavscodestarter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CreditCardNumberRedactorTest {
    // Fixture source: https://stripe.com/docs/testing

    @Test
    void testRedact() {
        final CreditCardNumberRedactor redactor = new CreditCardNumberRedactor("4242424242424242");

        assertEquals(new RedactResult(1, "PII_CREDIT_CARD"), redactor.redact());
    }
}
