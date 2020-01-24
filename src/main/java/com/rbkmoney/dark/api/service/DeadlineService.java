package com.rbkmoney.dark.api.service;

import com.rbkmoney.dark.api.exceptions.DeadlineException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeadlineService {

    public void checkDeadline(String xRequestDeadline, String xRequestId) {
        try {
            Instant instant = Instant.parse(xRequestDeadline);

            if (Instant.now().isAfter(instant)) {
                throw new DeadlineException(String.format("Deadline is expired, xRequestId=%s ", xRequestId));
            }
        } catch (Exception ex) {
            throw new DeadlineException(String.format("Deadline has invalid 'Instant' format, xRequestId=%s ", xRequestId));
        }
    }
}
