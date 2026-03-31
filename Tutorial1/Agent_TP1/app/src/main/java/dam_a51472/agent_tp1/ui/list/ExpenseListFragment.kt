package dam_a51472.agent_tp1.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dam_a51472.agent_tp1.FinanceTrackerApplication
import dam_a51472.agent_tp1.R
import dam_a51472.agent_tp1.databinding.FragmentExpenseListBinding
import dam_a51472.agent_tp1.ui.viewmodel.ExpenseViewModel
import dam_a51472.agent_tp1.ui.viewmodel.ExpenseViewModelFactory
import kotlinx.coroutines.launch

/**
 * Fragment that displays the list of all expenses.
 */
class ExpenseListFragment : Fragment() {

    private var _binding: FragmentExpenseListBinding? = null
    private val binding get() = _binding!!

    // Retrieve the ViewModel using the factory and the repository from Application
    private val viewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory((requireActivity().application as FinanceTrackerApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ExpenseAdapter { selectedExpense -> 
            // Handle row click by navigating to AddEdit screen with the ID
            val bundle = Bundle().apply { 
                putInt("expenseId", selectedExpense.id)
            }
            findNavController().navigate(R.id.action_expenseListFragment_to_addEditExpenseFragment, bundle)
        }

        binding.recyclerViewExpenses.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewExpenses.adapter = adapter

        binding.fabAddExpense.setOnClickListener {
            findNavController().navigate(R.id.action_expenseListFragment_to_addEditExpenseFragment)
        }

        // Add options menu for Summary
        val menuProvider = object : androidx.core.view.MenuProvider {
            override fun onCreateMenu(menu: android.view.Menu, menuInflater: android.view.MenuInflater) {
                menuInflater.inflate(R.menu.menu_list, menu)
            }
            override fun onMenuItemSelected(menuItem: android.view.MenuItem): Boolean {
                return if (menuItem.itemId == R.id.action_summary) {
                    findNavController().navigate(R.id.action_expenseListFragment_to_expenseSummaryFragment)
                    true
                } else false
            }
        }
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Observe the StateFlow
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allExpenses.collect { expenses ->
                    adapter.submitList(expenses)
                    binding.textViewEmpty.visibility = if (expenses.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
