package com.tekton.challenge.model.response;

import com.tekton.challenge.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "response structure for API errors")
public class ApiErrorResp {

    @Schema(
            description = "error code representing the type of error",
            example = "VALIDATION_FAILED"
    )
    private ErrorCode code;

    @Schema(
            description = "error message",
            example = "invalid input parameters"
    )
    private String message;

    @Schema(
            description = "list of additional specific errors related to the main error",
            example = "[\"num2=22.312 : the number must not exceed 2 decimal places.\", \"num1=-10.11 : must be greater than or equal to 0.0\"]"
    )
    private List<String> subErrors;
}
