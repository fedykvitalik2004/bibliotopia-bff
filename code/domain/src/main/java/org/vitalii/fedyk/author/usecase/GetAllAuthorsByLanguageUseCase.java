package org.vitalii.fedyk.author.usecase;

import org.springframework.core.SpringVersion;
import org.vitalii.fedyk.author.Author;

import java.util.Optional;
import java.util.UUID;

public interface GetAuthorUseCase {
  Author getAuthor(String language, UUID id);
}
