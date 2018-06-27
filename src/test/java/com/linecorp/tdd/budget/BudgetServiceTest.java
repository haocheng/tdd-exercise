package com.linecorp.tdd.budget;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BudgetServiceTest {

    @Mock
    private BudgetDao budgetDao;

    private BudgetService budgetService;

    @Test
    public void queryAmount_sameMonth() {
        when(budgetDao.query("201802", "201802")).thenReturn(
                singletonList(new BudgetModel(YearMonth.of(2018, 2), 28)));

        final int amount = budgetService.queryAmount(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 28));
        assertThat(amount, is(28));
    }

    @Test
    public void queryAmount_2Months() {
        when(budgetDao.query("201802", "201803")).thenReturn(
                Arrays.asList(new BudgetModel(YearMonth.of(2018, 2), 28),
                              new BudgetModel(YearMonth.of(2018, 3), 62)));

        final int amount = budgetService.queryAmount(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 3, 31));
        assertThat(amount, is(90));
    }

    @Test
    public void queryAmount_sameMonthPartial() {
        when(budgetDao.query("201802", "201802")).thenReturn(
                singletonList(new BudgetModel(YearMonth.of(2018, 2), 28)));

        final int amount = budgetService.queryAmount(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 15));
        assertThat(amount, is(15));
    }

    @Test
    public void queryAmount_diffMonthPartial() {
        when(budgetDao.query("201802", "201803")).thenReturn(
                Arrays.asList(new BudgetModel(YearMonth.of(2018, 2), 28),
                              new BudgetModel(YearMonth.of(2018, 3), 62)));

        final int amount = budgetService.queryAmount(LocalDate.of(2018, 2, 15), LocalDate.of(2018, 3, 15));
        assertThat(amount, is(44));
    }

    @Test
    public void queryAmount_diffCrossMonthPartial() {
        when(budgetDao.query("201802", "201804")).thenReturn(
                Arrays.asList(new BudgetModel(YearMonth.of(2018, 2), 28),
                              new BudgetModel(YearMonth.of(2018, 3), 62),
                              new BudgetModel(YearMonth.of(2018, 4), 90)));

        final int amount = budgetService.queryAmount(LocalDate.of(2018, 2, 15), LocalDate.of(2018, 4, 5));
        assertThat(amount, is(91));
    }

    @Test
    public void queryAmount_diffCrossMonthPartial_Empty() {
        when(budgetDao.query("201802", "201804")).thenReturn(
                Arrays.asList(new BudgetModel(YearMonth.of(2018, 2), 28),
                              new BudgetModel(YearMonth.of(2018, 4), 90)));

        final int amount = budgetService.queryAmount(LocalDate.of(2018, 2, 15), LocalDate.of(2018, 4, 5));
        assertThat(amount, is(29));
    }

    @Test(expected = RuntimeException.class)
    public void queryAmount_invalid() {
        budgetService.queryAmount(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 1, 31));
    }

    @Test
    public void queryAmount_noData() {
        when(budgetDao.query("201802", "201802")).thenReturn(Collections.emptyList());

        final int amount = budgetService.queryAmount(LocalDate.of(2018, 2, 1), LocalDate.of(2018, 2, 28));
        assertThat(amount, is(0));
    }

    @Before
    public void setUp() throws Exception {
        budgetService = new BudgetService(budgetDao);
    }
}