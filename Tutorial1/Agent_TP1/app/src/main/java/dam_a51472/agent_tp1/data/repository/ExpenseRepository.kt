package dam_a51472.agent_tp1.data.repository

import dam_a51472.agent_tp1.data.local.Expense
import dam_a51472.agent_tp1.data.local.ExpenseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Repository that abstracts access to the expense data source (Room DAO).
 */
class ExpenseRepository(private val expenseDao: ExpenseDao) {

    /**
     * Flow emitting the list of all expenses ordered by date descending.
     */
    val allExpenses: Flow<List<Expense>> = expenseDao.getAllExpenses()

    /**
     * Flow emitting the sum of all expenses.
     */
    val totalExpenses: Flow<Double?> = expenseDao.getTotalExpenses()

    /**
     * Retrieve a specific expense by ID.
     */
    suspend fun getExpenseById(id: Int): Expense? {
        return withContext(Dispatchers.IO) {
            expenseDao.getExpenseById(id)
        }
    }

    /**
     * Insert a new expense into the database.
     */
    suspend fun insert(expense: Expense) {
        withContext(Dispatchers.IO) {
            expenseDao.insertExpense(expense)
        }
    }

    /**
     * Update an existing expense in the database.
     */
    suspend fun update(expense: Expense) {
        withContext(Dispatchers.IO) {
            expenseDao.updateExpense(expense)
        }
    }

    /**
     * Delete an expense from the database.
     */
    suspend fun delete(expense: Expense) {
        withContext(Dispatchers.IO) {
            expenseDao.deleteExpense(expense)
        }
    }
}
