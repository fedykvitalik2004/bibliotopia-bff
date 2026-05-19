package org.vitalii.fedyk.bibliotopiabff.application.user.port.out;

import org.vitalii.fedyk.bibliotopiabff.domain.user.event.UserCreatedEvent;

public interface UserEventPublisher {
  void publish(UserCreatedEvent event);
}
