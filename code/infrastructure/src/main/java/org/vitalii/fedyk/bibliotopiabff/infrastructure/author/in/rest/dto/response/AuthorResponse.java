package org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.dto.response;

import java.time.LocalDate;
import java.util.List;

public record AuthorResponse(
    Long id, LocalDate birthDate, LocalDate deathDate, List<TranslationResponse> translations) {}
