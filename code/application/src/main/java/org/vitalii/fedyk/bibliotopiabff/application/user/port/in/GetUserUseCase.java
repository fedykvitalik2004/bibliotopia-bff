package org.vitalii.fedyk.bibliotopiabff.application.user.port.in;

import org.vitalii.fedyk.bibliotopiabff.application.user.dto.UserView;

public interface GetUserUseCase {
  UserView findUserById(long userId);
}
