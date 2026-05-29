package org.vitalii.fedyk.bibliotopiabff.application.user.service;

import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.AccessNames;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.CreateUserCommand;
import org.vitalii.fedyk.bibliotopiabff.application.user.dto.UserView;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.in.CreateUserUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.in.GetUserUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.in.ResolveExternalUserUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.AccessMetadataProvider;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.DefaultRoleProvider;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.PasswordEncoder;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.RoleData;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.UserEventPublisher;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.UserRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Email;
import org.vitalii.fedyk.bibliotopiabff.domain.common.model.Language;
import org.vitalii.fedyk.bibliotopiabff.domain.user.event.UserCreatedEvent;
import org.vitalii.fedyk.bibliotopiabff.domain.user.exception.AccountProviderConflictException;
import org.vitalii.fedyk.bibliotopiabff.domain.user.exception.UserAlreadyExistsException;
import org.vitalii.fedyk.bibliotopiabff.domain.user.exception.UserNotFoundException;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.EncodedPassword;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.FullName;
import org.vitalii.fedyk.bibliotopiabff.domain.user.model.User;

@Service
@AllArgsConstructor
public class UserService implements CreateUserUseCase, ResolveExternalUserUseCase, GetUserUseCase {
  private final UserEventPublisher eventPublisher;

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final DefaultRoleProvider defaultRoleProvider;

  private final AccessMetadataProvider accessMetadataProvider;

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

  @Override
  @Transactional
  public UserView resolve(final ResolveExternalUserCommand command) {
    final Email email = new Email(command.email());
    final Optional<User> userMaybe = this.userRepository.findByEmail(email);

    final FullName commandFullName = new FullName(command.firstName(), null);
    final User.AuthProvider provider = User.AuthProvider.fromString(command.providerId());

    final User user =
        userMaybe
            .map(existingUser -> this.handleExistingUser(existingUser, commandFullName, provider))
            .orElseGet(() -> this.handleNotExistingUser(commandFullName, email, provider));

    final AccessNames accessNames =
        this.accessMetadataProvider.getAccessNames(user.getRoleIds(), user.getPermissionIds());
    return UserView.builder()
        .id(user.getId())
        .firstName(user.getFullName().firstName())
        .lastName(user.getFullName().lastName())
        .email(user.getEmail().value())
        .birthDate(user.getBirthDate())
        .createdAt(user.getCreatedAt())
        .roles(accessNames.roles())
        .permissions(accessNames.permissions())
        .build();
  }

  @Override
  @Transactional(readOnly = true)
  public UserView findUserById(final long userId) {
    final User user =
        this.userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

    final AccessNames accessNames =
        this.accessMetadataProvider.getAccessNames(user.getRoleIds(), user.getPermissionIds());

    return this.buildUserView(user, accessNames);
  }

  private UserView buildUserView(final User user, final AccessNames accessNames) {
    final FullName fullName = user.getFullName();

    return UserView.builder()
        .id(user.getId())
        .firstName(fullName.firstName())
        .lastName(fullName.lastName())
        .email(user.getEmail().value())
        .birthDate(user.getBirthDate())
        .createdAt(user.getCreatedAt())
        .roles(accessNames.roles())
        .permissions(accessNames.permissions())
        .build();
  }

  private User handleNotExistingUser(
      final FullName fullName, final Email email, User.AuthProvider provider) {
    final User user = User.createPartial(fullName, email, provider);
    return this.userRepository.save(user);
  }

  private User handleExistingUser(
      final User existingUser, final FullName commandFullName, final User.AuthProvider provider) {
    if (existingUser.getAuthProvider() != provider) {
      throw new AccountProviderConflictException(
          "User exists with different provider", existingUser.getAuthProvider());
    }

    final FullName mergedFullName = existingUser.getFullName().merge(commandFullName);
    final RoleData defaultRole = this.defaultRoleProvider.getDefault();

    existingUser.addRoleId(defaultRole.roleId());
    existingUser.setFullName(mergedFullName);
    return userRepository.save(existingUser);
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
