package com.tekton.challenge.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "pagination information")
public record PaginationDto(
        @Schema(description = "total number of elements", example = "100")
        Long totalElements,

        @Schema(description = "total number of pages available", example = "10")
        Integer totalPages) {

}
