package org.vitalii.fedyk.infrastructure.user.out.messaging;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.application.user.port.out.UserEventPublisher;
import org.vitalii.fedyk.domain.user.event.UserCreatedEvent;

@Component
@AllArgsConstructor
public class SpringUserEventPublisher implements UserEventPublisher {
  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publish(final UserCreatedEvent event) {
    this.applicationEventPublisher.publishEvent(event);
  }
}
