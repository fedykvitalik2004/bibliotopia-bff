package org.vitalii.fedyk.bibliotopiabff.application.user.service;

import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.CreateUserCommand;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.UserView;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.in.CreateUserUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.DefaultRoleProvider;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.PasswordEncoder;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.RoleData;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.UserEventPublisher;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.UserRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Language;
import org.vitalii.fedyk.bibliotopiabff.domain.user.event.UserCreatedEvent;
import org.vitalii.fedyk.bibliotopiabff.domain.user.exception.UserAlreadyExistsException;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.EncodedPassword;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.FullName;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.User;

@Service
@AllArgsConstructor
public class UserService implements CreateUserUseCase {
  private final UserEventPublisher eventPublisher;

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final DefaultRoleProvider defaultRoleProvider;

  @Override
  @Transactional
  public UserView create(final CreateUserCommand command) {
    final Email email = new Email(command.email());
    if (this.userRepository.existsByEmail(email)) {
      throw new UserAlreadyExistsException(email.value());
    }
    final EncodedPassword encodedPassword = this.passwordEncoder.encode(command.rawPassword());

    final Language language = Language.fromCode(command.language());
    final User user =
        User.create(
            new FullName(command.firstName(), command.lastName()),
            email,
            encodedPassword,
            command.birthDate(),
            language);

    // ACL
    final RoleData defaultRole = this.defaultRoleProvider.getDefault();
    user.addRoleId(defaultRole.roleId());

    final User savedUser = this.userRepository.save(user);

    this.publishUserCreatedEvent(savedUser);

    return this.buildUserView(savedUser, defaultRole);
  }

  private UserView buildUserView(final User user, final RoleData defaultRole) {
    final FullName fullName = user.getFullName();

    return UserView.builder()
        .id(user.getId())
        .firstName(fullName.firstName())
        .lastName(fullName.lastName())
        .email(user.getEmail().value())
        .birthDate(user.getBirthDate())
        .createdAt(user.getCreatedAt())
        .roles(Set.of(defaultRole.roleName()))
        .permissions(defaultRole.permissionNames())
        .build();
  }

  private void publishUserCreatedEvent(final User user) {
    this.eventPublisher.publish(
        UserCreatedEvent.builder()
            .userId(user.getId())
            .firstName(user.getFullName().firstName())
            .lastName(user.getFullName().lastName())
            .email(user.getEmail().value())
            .language(user.getLanguage().getValue())
            .build());
  }
}
