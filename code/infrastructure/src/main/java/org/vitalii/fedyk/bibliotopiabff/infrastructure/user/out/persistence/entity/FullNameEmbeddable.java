package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FullNameEmbeddable {
  private String firstName;
  private String lastName;
}
