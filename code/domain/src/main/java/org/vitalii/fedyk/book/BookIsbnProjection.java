package org.vitalii.fedyk.infrastructure.book;

public record BookIsbnProjection(String title, String description, int publishDate, String thumbnailsUrl) {
}
