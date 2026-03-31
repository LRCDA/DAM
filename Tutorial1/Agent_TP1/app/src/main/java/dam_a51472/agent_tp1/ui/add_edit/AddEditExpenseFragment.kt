package dam_a51472.agent_tp1.ui.add_edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dam_a51472.agent_tp1.FinanceTrackerApplication
import dam_a51472.agent_tp1.data.local.Expense
import dam_a51472.agent_tp1.databinding.FragmentAddEditExpenseBinding
import dam_a51472.agent_tp1.ui.viewmodel.ExpenseViewModel
import dam_a51472.agent_tp1.ui.viewmodel.ExpenseViewModelFactory
import kotlinx.coroutines.launch

/**
 * Fragment to add a new expense or edit an existing one.
 */
class AddEditExpenseFragment : Fragment() {

    private var _binding: FragmentAddEditExpenseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory((requireActivity().application as FinanceTrackerApplication).repository)
    }

    private var currentExpenseId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditExpenseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentExpenseId = arguments?.getInt("expenseId", -1) ?: -1

        if (currentExpenseId != -1) {
            // Editing existing expense
            viewLifecycleOwner.lifecycleScope.launch {
                val expense = viewModel.getExpenseById(currentExpenseId)
                expense?.let {
                    binding.editTextTitle.setText(it.title)
                    binding.editTextAmount.setText(it.amount.toString())
                    binding.editTextCategory.setText(it.category)
                }
            }
        }

        binding.buttonSave.setOnClickListener {
            saveExpense()
        }
    }

    private fun saveExpense() {
        val title = binding.editTextTitle.text?.toString()?.trim() ?: ""
        val amountStr = binding.editTextAmount.text?.toString()?.trim() ?: ""
        val category = binding.editTextCategory.text?.toString()?.trim() ?: ""

        if (title.isEmpty()) {
            Toast.makeText(requireContext(), "Title is empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (amountStr.isEmpty()) {
            Toast.makeText(requireContext(), "Amount is empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (category.isEmpty()) {
            Toast.makeText(requireContext(), "Category is empty", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.replace(',', '.').toDoubleOrNull()
        if (amount == null) {
            Toast.makeText(requireContext(), "Invalid amount", Toast.LENGTH_SHORT).show()
            return
        }

        val expense = Expense(
            id = if (currentExpenseId == -1) 0 else currentExpenseId, // 0 for auto-generate
            title = title,
            amount = amount,
            category = category,
            date = System.currentTimeMillis()
        )

        if (currentExpenseId == -1) {
            viewModel.insert(expense)
        } else {
            viewModel.update(expense)
        }

        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
