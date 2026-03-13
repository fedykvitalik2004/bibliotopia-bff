package org.vitalii.fedyk.infrastructure.book.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {}
