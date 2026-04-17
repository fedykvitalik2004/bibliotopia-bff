package org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.vitalii.fedyk.bibliotopiabff.application.author.port.out.AuthorRepositoryPort;
import org.vitalii.fedyk.bibliotopiabff.domain.author.model.Author;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.entity.AuthorEntity;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.mapper.AuthorEntityMapper;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.author.out.persistence.repository.AuthorJpaRepository;

@Repository
@RequiredArgsConstructor
public class AuthorPersistenceAdapter implements AuthorRepositoryPort {
  private final AuthorEntityMapper authorEntityMapper;

  private final AuthorJpaRepository authorJpaRepository;

  @Override
  public Author save(final Author author) {
    final AuthorEntity authorEntity = this.authorEntityMapper.toAuthorEntity(author);
    authorEntity
        .getTranslations()
        .forEach(authorTranslationEntity -> authorTranslationEntity.setAuthor(authorEntity));
    final AuthorEntity savedEntity = this.authorJpaRepository.save(authorEntity);
    return this.authorEntityMapper.toAuthor(savedEntity);
  }

  @Override
  public Optional<Author> findById(final Long id) {
    return this.authorJpaRepository.findById(id).map(this.authorEntityMapper::toAuthor);
  }

  @Override
  public List<Author> findAll() {
    return this.authorJpaRepository.findAll().stream()
        .map(this.authorEntityMapper::toAuthor)
        .toList();
  }

  @Override
  public List<Author> findAllByIds(List<Long> authorIds) {
    return this.authorJpaRepository.findAllById(authorIds).stream()
        .map(authorEntityMapper::toAuthor)
        .toList();
  }

  @Override
  public int countByIds(List<Long> ids) {
    return this.authorJpaRepository.countByIdIn(ids);
  }
}
