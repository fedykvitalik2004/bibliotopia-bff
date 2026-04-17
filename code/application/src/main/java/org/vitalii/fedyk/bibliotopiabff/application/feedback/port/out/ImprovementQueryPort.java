package org.vitalii.fedyk.bibliotopiabff.application.feedback.port.out;

import java.util.List;
import org.vitalii.fedyk.bibliotopiabff.domain.feedback.model.ImprovementItem;

/**
 * Port for retrieving a consolidated view of improvements. This interface is strictly read-only.
 */
public interface ImprovementQueryPort {
  /**
   * Retrieves a unified list of all active bugs and feature requests.
   *
   * @return A list of ImprovementItem DTOs.
   */
  List<ImprovementItem> getAllImprovements();
}
