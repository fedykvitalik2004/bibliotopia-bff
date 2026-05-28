package org.vitalii.fedyk.bibliotopiabff.application.security.port.out;

import org.vitalii.fedyk.bibliotopiabff.application.security.dto.UserIdentityView;

public interface LoadUserIdentityPort {
  UserIdentityView loadByUserId(long userId);
}
