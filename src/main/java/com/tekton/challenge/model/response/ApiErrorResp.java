package com.tekton.challenge.model.response;

import com.tekton.challenge.exception.ErrorCode;
import lombok.Data;

import java.util.List;

@Data
public class ApiErrorResp {

    private ErrorCode code;
    private String message;
    private List<String> subErrors;
}
