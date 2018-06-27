package com.linecorp.tdd.budget;

import java.time.LocalDate;
import java.time.Period;
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
        final List<BudgetModel> budgetModels = budgetDao.query(startMonth, endMonth);

        return budgetModels.stream().mapToInt(budgetModel -> {
            if (isInSameMonth(startDate, budgetModel) && isInDifferentMonth(endDate, budgetModel)) {
                final int days = Period.between(startDate, budgetModel.getMonth().atEndOfMonth()).getDays() + 1;
                return budgetModel.getPartialAmount(days);
            }

            if (isInSameMonth(endDate, budgetModel) && isInDifferentMonth(startDate, budgetModel)) {
                final int days = Period.between(budgetModel.getMonth().atDay(1), endDate).getDays() + 1;
                return budgetModel.getPartialAmount(days) ;
            }

            if (isInSameMonth(startDate, budgetModel) && isInSameMonth(endDate, budgetModel)) {
                final int days = Period.between(startDate, endDate).getDays() + 1;
                return budgetModel.getPartialAmount(days) ;
            }
            return budgetModel.getAmount();
        }).sum();
    }

    private boolean isInDifferentMonth(LocalDate endDate, BudgetModel budgetModel) {
        return endDate.getMonthValue() != budgetModel.getMonth().getMonthValue();
    }

    private boolean isInSameMonth(LocalDate startDate, BudgetModel budgetModel) {
        return startDate.getMonthValue() == budgetModel.getMonth().getMonthValue();
    }
}
