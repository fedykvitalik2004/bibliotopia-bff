package org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vitalii.fedyk.bibliotopiabff.infrastructure.book.out.persistence.entity.BookEntity;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {}
