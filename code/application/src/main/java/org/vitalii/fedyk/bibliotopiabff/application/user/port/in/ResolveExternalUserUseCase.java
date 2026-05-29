package org.vitalii.fedyk.bibliotopiabff.application.user.port.in;

import lombok.Builder;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.UserView;

public interface ResolveExternalUserUseCase {
  UserView resolve(ResolveExternalUserCommand resolveExternalUserUseCase);

  @Builder
  record ResolveExternalUserCommand(String firstName, String email, String providerId) {}
}
