package dam_a51472.agent_tp1

import android.app.Application
import dam_a51472.agent_tp1.data.local.ExpenseDatabase
import dam_a51472.agent_tp1.data.repository.ExpenseRepository

/**
 * Application class to initialize the database and repository globally.
 * This acts as our manual Dependency Injection container.
 */
class FinanceTrackerApplication : Application() {
    
    // Using lazy so the database and repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { ExpenseDatabase.getDatabase(this) }
    val repository by lazy { ExpenseRepository(database.expenseDao()) }
}
