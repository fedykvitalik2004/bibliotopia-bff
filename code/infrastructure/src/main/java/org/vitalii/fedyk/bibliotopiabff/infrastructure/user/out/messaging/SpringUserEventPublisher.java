package org.vitalii.fedyk.bibliotopiabff.infrastructure.user.out.messaging;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.vitalii.fedyk.bibliotopiabff.application.user.port.out.UserEventPublisher;
import org.vitalii.fedyk.bibliotopiabff.domain.user.event.UserCreatedEvent;

@Component
@AllArgsConstructor
public class SpringUserEventPublisher implements UserEventPublisher {
  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publish(final UserCreatedEvent event) {
    this.applicationEventPublisher.publishEvent(event);
  }
}
