package org.vitalii.fedyk.bibliotopiabff.application.security.port.out;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.vitalii.fedyk.bibliotopiabff.domain.security.model.Role;

public interface RoleRepository {
  Role getDefault();

  Optional<Role> findById(Long id);

  List<Role> findAll();

  List<Role> findAllById(Set<Long> roleId);
}
