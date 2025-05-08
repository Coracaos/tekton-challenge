package com.tekton.challenge.model.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "response containing the calculated value")
public record CalculatedValueResp(
        @Schema(
                description = "final calculated result including the percentage addition",
                example = "115.50"
        )
        BigDecimal value
) { }

