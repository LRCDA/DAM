package dam_a51472.agent_tp1.ui.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dam_a51472.agent_tp1.FinanceTrackerApplication
import dam_a51472.agent_tp1.databinding.FragmentExpenseSummaryBinding
import dam_a51472.agent_tp1.ui.viewmodel.ExpenseViewModel
import dam_a51472.agent_tp1.ui.viewmodel.ExpenseViewModelFactory
import kotlinx.coroutines.launch

/**
 * Fragment to display a summary of total expenses.
 */
class ExpenseSummaryFragment : Fragment() {

    private var _binding: FragmentExpenseSummaryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory((requireActivity().application as FinanceTrackerApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpenseSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.totalExpenses.collect { total ->
                    binding.textViewTotal.text = "$${String.format("%.2f", total)}"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
