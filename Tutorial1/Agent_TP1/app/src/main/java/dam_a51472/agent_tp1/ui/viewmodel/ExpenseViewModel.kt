package dam_a51472.agent_tp1.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam_a51472.agent_tp1.data.local.Expense
import dam_a51472.agent_tp1.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing UI-related data and handling interaction with the Repository.
 */
class ExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {

    /**
     * StateFlow exposing all expenses.
     * Uses stateIn to convert the Flow from Room into a StateFlow that survives configuration changes.
     */
    val allExpenses: StateFlow<List<Expense>> = repository.allExpenses
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Keeps flow active for 5s after last subscriber un-subscribes
            initialValue = emptyList()
        )

    /**
     * StateFlow exposing the sum of all expenses.
     */
    val totalExpenses: StateFlow<Double> = repository.totalExpenses
        .map { it ?: 0.0 }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    /**
     * Fetches a single expense synchronously inside a coroutine.
     */
    suspend fun getExpenseById(id: Int): Expense? {
        return repository.getExpenseById(id)
    }

    /**
     * Inserts a new expense.
     */
    fun insert(expense: Expense) = viewModelScope.launch {
        repository.insert(expense)
    }

    /**
     * Updates an existing expense.
     */
    fun update(expense: Expense) = viewModelScope.launch {
        repository.update(expense)
    }

    /**
     * Deletes an expense.
     */
    fun delete(expense: Expense) = viewModelScope.launch {
        repository.delete(expense)
    }
}
