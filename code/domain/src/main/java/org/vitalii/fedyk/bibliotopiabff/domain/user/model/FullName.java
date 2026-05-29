package org.vitalii.fedyk.bibliotopiabff.domain.user.model;

public record FullName(String firstName, String lastName) {
  public String getFull() {
    return this.firstName + " " + this.lastName;
  }

  public FullName merge(final FullName other) {
    final String newFirst = (other.firstName() != null) ? other.firstName() : this.firstName();
    final String newLast = (other.lastName() != null) ? other.lastName() : this.lastName();
    return new FullName(newFirst, newLast);
  }
}
