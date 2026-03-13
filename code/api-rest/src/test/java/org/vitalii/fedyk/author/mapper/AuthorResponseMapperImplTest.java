package org.vitalii.fedyk.author.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.vitalii.fedyk.author.model.Author;

class AuthorResponseMapperImplTest {
  private final AuthorResponseMapper mapper = new AuthorResponseMapperImpl();

  @Test
  void shouldMapAllAuthorFieldsCorrectly() {
    // Given
    final var author =
        Instancio.of(Author.class)
            .generate(field(Author::getTranslations), gen -> gen.collection().size(2))
            .create();

    // When
    final var response = this.mapper.toResponse(author);

    // Then
    assertThat(response.id()).isEqualTo(author.getId());
    assertThat(response.birthDate()).isEqualTo(author.getBirthDate());
    assertThat(response.deathDate()).isEqualTo(author.getDeathDate());
    assertThat(response.translations()).hasSameSizeAs(author.getTranslations());

    for (int i = 0; i < author.getTranslations().size(); i++) {
      var domainTrans = author.getTranslations().get(i);
      var responseTrans = response.translations().get(i);

      assertThat(responseTrans.firstName()).isEqualTo(domainTrans.getFirstName());
      assertThat(responseTrans.lastName()).isEqualTo(domainTrans.getLastName());
      assertThat(responseTrans.description()).isEqualTo(domainTrans.getDescription());
    }
  }
}
