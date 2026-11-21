package org.vitalii.fedyk.infrastructure.user.in.messaging;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.vitalii.fedyk.domain.user.event.UserCreatedEvent;

@Component
public class UserCreatedListener {
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleUserCreated(final UserCreatedEvent event) {
    // todo: add a port for sending an email
  }
}
