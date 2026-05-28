package org.vitalii.fedyk.bibliotopiabff.infrastructure.security.out.acl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.bibliotopiabff.application.security.dto.UserIdentityView;
import org.vitalii.fedyk.bibliotopiabff.application.security.port.out.LoadUserIdentityPort;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.UserView;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.in.GetUserUseCase;

@Component
@AllArgsConstructor
public class UserIdentityAclAdapter implements LoadUserIdentityPort {
  private final GetUserUseCase getUserUseCase;

  @Override
  public UserIdentityView loadByUserId(long userid) {
    final UserView userView = this.getUserUseCase.findUserById(userid);
    return new UserIdentityView(userView.id(), userView.roles(), userView.permissions());
  }
}
