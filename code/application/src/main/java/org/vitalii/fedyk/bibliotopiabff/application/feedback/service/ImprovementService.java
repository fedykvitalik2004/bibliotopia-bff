package org.vitalii.fedyk.bibliotopiabff.application.feedback.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vitalii.fedyk.bibliotopiabff.application.feedback.port.in.GetImprovementsUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.feedback.port.out.ImprovementQueryPort;
import org.vitalii.fedyk.bibliotopiabff.domain.feedback.model.ImprovementItem;

@Service
@AllArgsConstructor
public class ImprovementService implements GetImprovementsUseCase {
  private final ImprovementQueryPort improvementQueryPort;

  @Override
  public List<ImprovementItem> execute() {
    return this.improvementQueryPort.getAllImprovements();
  }
}
