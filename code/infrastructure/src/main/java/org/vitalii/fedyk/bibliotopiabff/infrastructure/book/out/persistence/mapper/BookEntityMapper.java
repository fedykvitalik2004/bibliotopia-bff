package org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence.mapper;

import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.Book;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.BookGenre;
import org.vitalii.fedyk.bibliotopiabff.domain.book.model.BookTranslation;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.entity.AuthorEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence.entity.BookEntity;

@Mapper(
    componentModel = "spring",
    uses = {BookTranslationEntityMapper.class, BookGenreEntityMapper.class})
public abstract class BookEntityMapper {
  @Autowired protected BookTranslationEntityMapper bookTranslationEntityMapper;

  @Autowired protected BookGenreEntityMapper bookGenreEntityMapper;

  public abstract BookEntity toBookEntity(Book book, List<AuthorEntity> authors);

  public Book toBook(final BookEntity bookEntity) {
    final List<BookTranslation> translations =
        this.bookTranslationEntityMapper.toBookTranslations(bookEntity.getTranslations());
    final List<BookGenre> genres = this.bookGenreEntityMapper.toBookGenres(bookEntity.getGenres());
    final List<Long> authorIds = toAuthorIds(bookEntity.getAuthors());
    return Book.restore(
        bookEntity.getId(),
        bookEntity.getIsbn(),
        bookEntity.getPageCount(),
        bookEntity.getCoverImageUrl(),
        bookEntity.getPrice(),
        translations,
        genres,
        authorIds);
  }

  public List<Long> toAuthorIds(final List<AuthorEntity> authorEntities) {
    return authorEntities.stream().map(AuthorEntity::getId).toList();
  }

  @AfterMapping
  void setBook(@MappingTarget BookEntity bookEntity) {
    bookEntity.getGenres().forEach(entity -> entity.setBook(bookEntity));
    bookEntity.getTranslations().forEach(entity -> entity.setBook(bookEntity));
  }
}
