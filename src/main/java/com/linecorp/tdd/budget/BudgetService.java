package com.linecorp.tdd.budget;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BudgetService {

    private BudgetDao budgetDao;

    public BudgetService(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }

    public int queryAmount(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("invalid query terms");
        }

        final String startMonth = startDate.format(DateTimeFormatter.ofPattern("yyyyMM"));
        final String endMonth = endDate.format(DateTimeFormatter.ofPattern("yyyyMM"));
        final List<BudgetModel> budgets = budgetDao.query(startMonth, endMonth);

        final BudgetPeriod budgetPeriod = new BudgetPeriod(startDate, endDate);
        return budgets.stream().mapToInt(
                budgetModel -> budgetModel.calculateAmount(budgetPeriod)).sum();
    }

}
