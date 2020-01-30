package com.rbkmoney.dark.api.util;

import com.rbkmoney.dark.api.exceptions.DeadlineException;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.rbkmoney.dark.api.util.DeadlineUtils.checkDeadline;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeadlineUtilsTest {

    @Test
    public void test() {
        checkDeadline(null, null);

        checkDeadline("12m", null);
        checkDeadline("1.2m", null);
        checkDeadline("12s", null);
        checkDeadline("1.2s", null);
        checkDeadline("12ms", null);
        assertThrows(DeadlineException.class, () -> checkDeadline("1.2ms", null));
        checkDeadline("12m12s12ms", null);
        checkDeadline("1.2m1.2s12ms", null);
        assertThrows(DeadlineException.class, () -> checkDeadline("1.2m1.2s1.2ms", null));
        assertThrows(DeadlineException.class, () -> checkDeadline("12s12s", null));
        assertThrows(DeadlineException.class, () -> checkDeadline("12m12m", null));
        assertThrows(DeadlineException.class, () -> checkDeadline("12ms12ms", null));
        assertThrows(DeadlineException.class, () -> checkDeadline("12s12ms12ms", null));
        assertThrows(DeadlineException.class, () -> checkDeadline("12s12s12ms", null));

        checkDeadline(Instant.now().plus(1, ChronoUnit.DAYS).toString(), null);
        assertThrows(DeadlineException.class, () -> checkDeadline(Instant.now().minus(1, ChronoUnit.DAYS).toString(), null));

        assertThrows(DeadlineException.class, () -> checkDeadline("undefined", null));
    }
}
