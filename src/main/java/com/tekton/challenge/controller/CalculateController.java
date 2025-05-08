package com.tekton.challenge.controller;

import com.tekton.challenge.model.response.ApiErrorResp;
import com.tekton.challenge.model.response.CalculatedValueResp;
import com.tekton.challenge.service.CalculateService;
import com.tekton.challenge.validation.DecimalScale;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalculateController {

    private final CalculateService calculateService;

    @Operation(summary = "calculate new value: sum inputs and add percentage")
    @GetMapping(value = "/calculate")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "calculation completed successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "invalid input parameters",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResp.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResp.class)
                    )
            )
    })
    public ResponseEntity<CalculatedValueResp> getCalculatedValue(
            @Parameter(
                    description = "amount with two decimal places, between 1.00 and 1000000.00",
                    example = "10.20",
                    required = true,
                    schema = @Schema(type = "number", format = "decimal", minimum = "0.00", maximum = "100000.00")
            )
            @NotNull @DecimalMin(value = "0.0") @DecimalMax("1000000.00") @DecimalScale(maxScale = 2) @RequestParam("num1") BigDecimal num1,
            @Parameter(
                    description = "amount with two decimal places, between 1.00 and 1000000.00",
                    example = "11.20",
                    required = true,
                    schema = @Schema(type = "number", format = "decimal", minimum = "0.00", maximum = "1000000.00")
            )
            @NotNull @DecimalMin(value = "0.0") @DecimalMax("1000000.00") @DecimalScale(maxScale = 2) @RequestParam("num2") BigDecimal num2
    ) {
        return ResponseEntity.ok(calculateService.calculateValue(num1, num2));
    }

}
