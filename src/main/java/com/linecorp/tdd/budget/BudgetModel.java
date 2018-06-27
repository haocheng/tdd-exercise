package com.linecorp.tdd.budget;

import java.time.YearMonth;

public class BudgetModel {

    private YearMonth month;

    private int amount;

    public BudgetModel(YearMonth month, int amount) {
        this.month = month;
        this.amount = amount;
    }

    public YearMonth getMonth() {
        return month;
    }

    public int getAmount() {
        return amount;
    }

    public int getPartialAmount(int days) {
        final int totalDaysInMonth = getMonth().getMonth().length(false);
        return getAmount() * days / totalDaysInMonth;
    }
}
