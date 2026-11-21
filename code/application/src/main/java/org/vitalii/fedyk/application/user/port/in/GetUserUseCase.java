package org.vitalii.fedyk.application.user.port.in;

import org.vitalii.fedyk.application.user.dto.UserView;

public interface GetUserUseCase {
  UserView findUserById(long userId);
}
