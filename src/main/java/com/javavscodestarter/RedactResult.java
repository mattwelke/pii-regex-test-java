package com.javavscodestarter;

/**
 * Represents the result of a redact operation. Not using records because we
 * have to target no greater than Java 11 for Apache Beam.
 */
public class RedactResult {
  private long numRedacted;
  private String redacted;

  public RedactResult(long numRedacted, String redacted) {
    this.numRedacted = numRedacted;
    this.redacted = redacted;
  }

  public long getNumRedacted() {
    return numRedacted;
  }

  public String getRedacted() {
    return redacted;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (numRedacted ^ (numRedacted >>> 32));
    result = prime * result + ((redacted == null) ? 0 : redacted.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    RedactResult other = (RedactResult) obj;
    if (numRedacted != other.numRedacted)
      return false;
    if (redacted == null) {
      if (other.redacted != null)
        return false;
    } else if (!redacted.equals(other.redacted))
      return false;
    return true;
  }
}
