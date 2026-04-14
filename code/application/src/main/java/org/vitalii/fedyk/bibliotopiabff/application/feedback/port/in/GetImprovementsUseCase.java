package org.vitalii.fedyk.bibliotopiabff.application.feedback.port.in;

import java.util.List;
import org.vitalii.fedyk.bibliotopiabff.domain.feedback.model.ImprovementItem;

public interface GetImprovementsUseCase {
  List<ImprovementItem> execute();
}
