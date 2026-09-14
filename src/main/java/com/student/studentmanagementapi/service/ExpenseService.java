package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.entity.Expense;
import com.student.studentmanagementapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Expense not found: " + id
                        )
                );
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
