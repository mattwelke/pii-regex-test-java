package com.javavscodestarter;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreditCardNumberRedactor {
  private static final String regexCreditCard = "\\d{4}[ -]?\\d{4}[ -]?\\d{4}[ -]?\\d{4}|\\d{4}[ -]?\\d{6}[ -]?\\d{4}\\d?";
  private static final Pattern patternCreditCard = Pattern.compile(regexCreditCard);
  private static final String redactStubCreditCard = "PII_CREDIT_CARD";

  private Matcher m;
  private long numRedacted;

  public CreditCardNumberRedactor(String src) {
    this.m = patternCreditCard.matcher(src);
  }

  public RedactResult redact() {
    String redacted = m.replaceAll((MatchResult result) -> {
      numRedacted++;
      return redactStubCreditCard;
    });

    return new RedactResult(numRedacted, redacted);
  }
}
