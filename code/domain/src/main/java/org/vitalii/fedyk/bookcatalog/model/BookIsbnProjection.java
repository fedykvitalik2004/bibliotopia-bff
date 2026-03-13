package org.vitalii.fedyk.bookcatalog.model;

public record BookIsbnProjection(
    String title, int publishDate, int numberOfPages, String thumbnailsUrl) {}
