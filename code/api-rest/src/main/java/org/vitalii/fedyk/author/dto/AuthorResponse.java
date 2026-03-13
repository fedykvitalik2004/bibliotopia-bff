package org.vitalii.fedyk.author.dto;

import java.time.LocalDate;
import java.util.List;

public record AuthorResponse(
    Long id, LocalDate birthDate, LocalDate deathDate, List<TranslationResponse> translations) {}
