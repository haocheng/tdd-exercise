package com.linecorp.tdd.budget;

import java.time.YearMonth;

public class BudgetModel {

    private final YearMonth yearMonth;

    private final int amount;

    public BudgetModel(YearMonth yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public int calculateAmount(BudgetPeriod period) {
        final int days = period.calculateDays(getBudgetPeriod());
        final int totalDaysInMonth = yearMonth.getMonth().length(false);
        return amount * days / totalDaysInMonth;
    }

    private BudgetPeriod getBudgetPeriod() {
        return new BudgetPeriod(yearMonth.atDay(1), yearMonth.atEndOfMonth());
    }
}
