package org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.bibliotopiabff.application.book.port.out.BookRepositoryPort;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.Book;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.entity.AuthorEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.repository.AuthorJpaRepository;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence.entity.BookEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence.mapper.BookEntityMapper;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence.repository.BookJpaRepository;

@Repository
@RequiredArgsConstructor
public class BookPersistenceAdapter implements BookRepositoryPort {
  private final BookJpaRepository bookJpaRepository;

  private final AuthorJpaRepository authorJpaRepository;

  private final BookEntityMapper bookEntityMapper;

  @Override
  public Book save(final Book book) {
    final List<Long> authorIds = book.getAuthorIds();

    final List<AuthorEntity> managedAuthors = this.authorJpaRepository.findAllById(authorIds);

    final BookEntity bookEntity = this.bookEntityMapper.toBookEntity(book, managedAuthors);

    final BookEntity savedBookEntity = this.bookJpaRepository.save(bookEntity);

    return this.bookEntityMapper.toBook(savedBookEntity);
  }
}
