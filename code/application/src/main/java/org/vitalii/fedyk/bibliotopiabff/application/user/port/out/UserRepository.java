package org.vitalii.fedyk.bibliotopiabff.application.user.port.out;

import java.util.Optional;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.User;

public interface UserRepository {
  boolean existsByEmail(Email email);

  Optional<User> findByEmail(Email email);

  Optional<User> findById(long id);

  User save(User user);
}
