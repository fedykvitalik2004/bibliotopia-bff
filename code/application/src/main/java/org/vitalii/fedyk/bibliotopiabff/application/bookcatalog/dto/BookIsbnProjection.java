package org.vitalii.fedyk.bibliotopiabff.application.bookcatalog.dto;

public record BookIsbnProjection(
    String title, int publishDate, int numberOfPages, String thumbnailsUrl) {}
