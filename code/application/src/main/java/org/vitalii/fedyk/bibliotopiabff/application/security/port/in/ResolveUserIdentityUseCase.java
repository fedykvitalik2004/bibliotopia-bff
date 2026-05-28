package org.vitalii.fedyk.bibliotopiabff.application.security.port.in;

import org.vitalii.fedyk.bibliotopiabff.application.security.dto.UserIdentityView;

public interface ResolveUserIdentityUseCase {
  UserIdentityView getIdentity(Long userId);
}
