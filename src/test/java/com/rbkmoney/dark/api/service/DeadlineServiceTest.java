package com.rbkmoney.dark.api.service;

import com.rbkmoney.dark.api.exceptions.DeadlineException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeadlineServiceTest {

    @Autowired
    private DeadlineService deadlineService;

    @Test
    public void deadlineServiceTest() {
        deadlineService.checkDeadline(Instant.now().plus(1, ChronoUnit.DAYS).toString(), null);
        assertThrows(DeadlineException.class, () -> deadlineService.checkDeadline(Instant.now().minus(1, ChronoUnit.DAYS).toString(), null));
        assertThrows(DeadlineException.class, () -> deadlineService.checkDeadline("sda", null));
    }
}
