package org.vitalii.fedyk.infrastructure.book.adapter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.book.model.Book;
import org.vitalii.fedyk.book.repository.BookRepositoryPort;
import org.vitalii.fedyk.infrastructure.author.jpa.AuthorEntity;
import org.vitalii.fedyk.infrastructure.author.jpa.AuthorJpaRepository;
import org.vitalii.fedyk.infrastructure.book.jpa.BookEntity;
import org.vitalii.fedyk.infrastructure.book.jpa.BookJpaRepository;
import org.vitalii.fedyk.infrastructure.book.mapper.BookEntityMapper;

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
