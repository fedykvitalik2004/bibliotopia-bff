package org.vitalii.fedyk.bibliotopiabff.application.book.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.vitalii.fedyk.bibliotopiabff.application.author.port.out.AuthorRepositoryPort;
import org.vitalii.fedyk.bibliotopiabff.application.book.dto.BookPricingResult;
import org.vitalii.fedyk.bibliotopiabff.application.book.dto.CreateBookCommand;
import org.vitalii.fedyk.bibliotopiabff.application.book.port.in.CreateBookUseCase;
import org.vitalii.fedyk.bibliotopiabff.application.book.port.out.BookRepositoryPort;
import org.vitalii.fedyk.bibliotopiabff.application.bookcatalog.dto.BookIsbnProjection;
import org.vitalii.fedyk.bibliotopiabff.application.bookcatalog.port.out.BookCatalogRepository;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.Book;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.BookGenre;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.BookTranslation;
import org.vitalii.fedyk.bibliotopiabff.domain.book.service.PricingService;

@Service
@AllArgsConstructor
public class CreateBookUseCaseImpl implements CreateBookUseCase {
  private final AuthorRepositoryPort authorRepositoryPort;

  private final BookRepositoryPort bookRepositoryPort;

  private final BookCatalogRepository bookCatalogRepository;

  private final PricingService pricingService;

  @Override
  public BookPricingResult create(final CreateBookCommand command) {
    this.validateAuthorsExist(command.authorIds());

    final List<BookTranslation> translations = mapToTranslations(command.translations());
    final List<BookGenre> genres = mapToGenres(command.genres());

    final BookIsbnProjection bookIsbnProjection =
        this.bookCatalogRepository.getBookDetail(command.isbn());

    final Book book =
        Book.create(
            command.isbn(),
            bookIsbnProjection.numberOfPages(),
            bookIsbnProjection.thumbnailsUrl(),
            command.price(),
            translations,
            genres,
            command.authorIds());

    final BigDecimal newPrice = this.pricingService.calculatePrice(book, command.zoneId());
    final Book savedBook = this.bookRepositoryPort.save(book);
    // todo: remove model from this class
    return new BookPricingResult(savedBook, newPrice);
  }

  private void validateAuthorsExist(final List<Long> authorIds) {
    if (this.authorRepositoryPort.countByIds(authorIds) != authorIds.size()) {
      throw new IllegalArgumentException("One or more authors were not found");
    }
  }

  private List<BookTranslation> mapToTranslations(
      final List<CreateBookCommand.TranslationCommand> commands) {
    return commands.stream()
        .map(t -> new BookTranslation(t.title(), t.description(), t.language()))
        .toList();
  }

  private List<BookGenre> mapToGenres(final List<String> genres) {
    return genres.stream().map(BookGenre::new).toList();
  }
}
