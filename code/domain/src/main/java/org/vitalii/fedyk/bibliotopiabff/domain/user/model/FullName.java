package org.vitalii.fedyk.bibliotopiabff.domain.user.model;

public record FullName(String firstName, String lastName) {
  public String getFull() {
    return this.firstName + " " + this.lastName;
  }
}
