package org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.dto.response;

public record TranslationResponse(
    String firstName, String lastName, String description, String language) {}
