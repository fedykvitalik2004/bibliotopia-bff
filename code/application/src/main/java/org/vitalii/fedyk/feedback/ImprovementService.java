package org.vitalii.fedyk.feedback;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vitalii.fedyk.feedback.model.ImprovementItem;
import org.vitalii.fedyk.feedback.repository.ImprovementQueryPort;
import org.vitalii.fedyk.feedback.usecase.GetImprovementsUseCase;

@Service
@AllArgsConstructor
public class ImprovementService implements GetImprovementsUseCase {
  private final ImprovementQueryPort improvementQueryPort;

  @Override
  public List<ImprovementItem> execute() {
    return this.improvementQueryPort.getAllImprovements();
  }
}
