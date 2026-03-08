package org.vitalii.fedyk.feedback.usecase;

import java.util.List;
import org.vitalii.fedyk.feedback.model.ImprovementItem;

public interface GetImprovementsUseCase {
  List<ImprovementItem> execute();
}
