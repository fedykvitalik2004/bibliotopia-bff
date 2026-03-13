package org.vitalii.fedyk.annotation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

@Parameter(
    name = "Accept-Language",
    description = "Locale",
    in = ParameterIn.HEADER,
    required = true,
    schema =
        @Schema(
            type = "string",
            allowableValues = {"en", "he", "uk"}))
public @interface OpenApiLocale {}
