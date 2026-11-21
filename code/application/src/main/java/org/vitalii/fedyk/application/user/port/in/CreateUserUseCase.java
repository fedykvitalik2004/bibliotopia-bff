package org.vitalii.fedyk.application.user.port.in;

import org.vitalii.fedyk.application.user.dto.CreateUserCommand;
import org.vitalii.fedyk.application.user.dto.UserView;

public interface CreateUserUseCase {
  UserView create(CreateUserCommand command);
}
