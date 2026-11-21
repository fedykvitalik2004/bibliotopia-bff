package org.vitalii.fedyk.application.feedback.port.in;

import java.util.List;
import org.vitalii.fedyk.domain.feedback.model.ImprovementItem;

public interface GetImprovementsUseCase {
  List<ImprovementItem> execute();
}
