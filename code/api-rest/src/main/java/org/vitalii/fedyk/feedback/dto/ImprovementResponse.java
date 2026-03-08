package org.vitalii.fedyk.feedback.dto;

import lombok.Builder;

@Builder
public record ImprovementResponse(
    Long id, String title, String description, String type, Integer importance) {}
