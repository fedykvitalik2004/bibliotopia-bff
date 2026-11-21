package org.vitalii.fedyk.application.user.port.out;

import org.vitalii.fedyk.domain.user.event.UserCreatedEvent;

public interface UserEventPublisher {
  void publish(UserCreatedEvent event);
}
