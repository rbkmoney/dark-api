package com.rbkmoney.dark.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileNotFoundException extends RuntimeException {

    private String fileId;
}
