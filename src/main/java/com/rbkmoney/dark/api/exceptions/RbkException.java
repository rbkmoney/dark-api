package com.rbkmoney.dark.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RbkException extends RuntimeException {

    private String code;
    private String message;

}
