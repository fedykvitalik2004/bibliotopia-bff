package org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.mapper.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.WithSettings;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.vitalii.fedyk.bibliotopiabff.domain.author.model.Author;
import org.vitalii.fedyk.bibliotopiabff.domain.author.model.AuthorTranslation;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.dto.request.AuthorRequest;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.mapper.AuthorRequestMapper;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.in.rest.mapper.AuthorRequestMapperImpl;

@ExtendWith(InstancioExtension.class)
class AuthorRequestMapperTest {
  private final AuthorRequestMapper mapper = new AuthorRequestMapperImpl();

  @WithSettings
  private final Settings settings = Settings.create().set(Keys.COLLECTION_MAX_SIZE, 1);

  @Test
  void shouldMapAuthorRequestToAuthor() {
    // Given
    final var authorRequest = Instancio.create(AuthorRequest.class);

    final var firstTranslationRequest = authorRequest.translations().get(0);

    final var expected =
        Author.builder()
            .birthDate(authorRequest.birthDate())
            .deathDate(authorRequest.deathDate())
            .translations(
                List.of(
                    AuthorTranslation.builder()
                        .firstName(firstTranslationRequest.firstName())
                        .lastName(firstTranslationRequest.lastName())
                        .description(firstTranslationRequest.description())
                        .language(firstTranslationRequest.language())
                        .build()))
            .build();
    // When
    final var actual = this.mapper.toAuthor(authorRequest);

    // Then
    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }
}
