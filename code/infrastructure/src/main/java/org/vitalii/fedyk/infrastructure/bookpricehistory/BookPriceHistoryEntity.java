package org.vitalii.fedyk.infrastructure.bookpricehistory;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.vitalii.fedyk.infrastructure.BaseEntity;
import org.vitalii.fedyk.infrastructure.book.Book;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "book_price_history")
@Setter
@Getter
public class BookPriceHistory extends BaseEntity {
  private BigDecimal price;

  private OffsetDateTime changeDate;

  @ManyToOne
  private Book book;
}
