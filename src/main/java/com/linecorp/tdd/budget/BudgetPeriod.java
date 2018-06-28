package com.linecorp.tdd.budget;

import java.time.LocalDate;
import java.time.Period;

public class BudgetPeriod {

    private final LocalDate start;

    private final LocalDate end;

    public BudgetPeriod(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public int calculateDays(BudgetPeriod that) {
        final LocalDate overlappingStart = start.isAfter(that.start) ? start : that.start;
        final LocalDate overlappingEnd = end.isBefore(that.end) ? end : that.end;

        if (overlappingStart.isAfter(overlappingEnd)) {
            return 0;
        }
        return Period.between(overlappingStart, overlappingEnd).getDays() + 1;
    }
}
