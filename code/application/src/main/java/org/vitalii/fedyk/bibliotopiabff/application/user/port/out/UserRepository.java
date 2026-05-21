package org.vitalii.fedyk.bibliotopiabff.application.user.port.out;

import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.User;

public interface UserRepository {
  boolean existsByEmail(Email email);

  User save(User user);
}
