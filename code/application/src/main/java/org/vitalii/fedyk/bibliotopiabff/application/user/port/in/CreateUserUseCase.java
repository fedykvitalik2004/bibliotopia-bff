package org.vitalii.fedyk.bibliotopiabff.application.user.port.in;

import org.vitalii.fedyk.bibliotopiabff.application.user.dto.CreateUserCommand;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.UserView;

public interface CreateUserUseCase {
  UserView create(CreateUserCommand command);
}
