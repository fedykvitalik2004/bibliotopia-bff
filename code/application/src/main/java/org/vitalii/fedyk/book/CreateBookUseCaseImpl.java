package org.vitalii.fedyk.book;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vitalii.fedyk.author.repository.AuthorRepositoryPort;
import org.vitalii.fedyk.book.model.Book;
import org.vitalii.fedyk.book.model.BookGenre;
import org.vitalii.fedyk.book.model.BookTranslation;
import org.vitalii.fedyk.book.model.CreateBookCommand;
import org.vitalii.fedyk.book.repository.BookRepositoryPort;
import org.vitalii.fedyk.book.usecase.CreateBookUseCase;
import org.vitalii.fedyk.bookcatalog.model.BookIsbnProjection;
import org.vitalii.fedyk.bookcatalog.repository.BookCatalogRepository;

@Service
@AllArgsConstructor
public class CreateBookUseCaseImpl implements CreateBookUseCase {
  private final AuthorRepositoryPort authorRepositoryPort;

  private final BookRepositoryPort bookRepositoryPort;

  private final BookCatalogRepository bookCatalogRepository;

  @Override
  public Book create(final CreateBookCommand command) {
    this.validateAuthorsExist(command.authorIds());

    final List<BookTranslation> translations = mapToTranslations(command.translations());
    final List<BookGenre> genres = mapToGenres(command.genres());

    final BookIsbnProjection bookIsbnProjection =
        this.bookCatalogRepository.getBookDetail(command.isbn()).orElseThrow();

    final Book book =
        Book.create(
            command.isbn(),
            bookIsbnProjection.numberOfPages(),
            bookIsbnProjection.thumbnailsUrl(),
            command.price(),
            translations,
            genres,
            command.authorIds());

    return this.bookRepositoryPort.save(book);
  }

  private void validateAuthorsExist(final List<Long> authorIds) {
    if (this.authorRepositoryPort.countByIds(authorIds) != authorIds.size()) {
      throw new IllegalArgumentException("One or more authors were not found");
    }
  }

  private List<BookTranslation> mapToTranslations(
      final List<CreateBookCommand.TranslationCommand> commands) {
    return commands.stream().map(t -> new BookTranslation(t.title(), t.description())).toList();
  }

  private List<BookGenre> mapToGenres(final List<String> genres) {
    return genres.stream().map(BookGenre::new).toList();
  }
}
