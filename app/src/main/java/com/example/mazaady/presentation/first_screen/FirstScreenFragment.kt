package com.example.mazaady.presentation.first_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mazaady.R
import com.example.mazaady.core.adapter.AdapterDelegateImpl
import com.example.mazaady.core.adapter.BaseCommand
import com.example.mazaady.core.adapter.CommandListener
import com.example.mazaady.databinding.FragmentFirstScreenBinding
import com.example.mazaady.domain.models.CategoryModel
import com.example.mazaady.domain.models.PropertyModel
import com.example.mazaady.domain.models.PropertyOptionModel
import com.example.mazaady.domain.models.SubCategoryModel
import com.example.mazaady.presentation.first_screen.adapters.PropertiesAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstScreenFragment : Fragment(), CommandListener {

    private val viewModel: FirstScreenViewModel by viewModels()

    private lateinit var binding: FragmentFirstScreenBinding
    private lateinit var propertiesAdapterDelegate: AdapterDelegateImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handelUiState()
        handelClickListeners()
        observeForLiveDataEvents()
    }

    private fun handelUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect {
                    when (it) {
                        is FirstScreenUiState.Loading -> {
                            binding.progressCircular.isVisible = it.value
                        }
                        is FirstScreenUiState.Error -> {
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        }
                        is FirstScreenUiState.ShowCategoriesBottomSheet -> {
                            findNavController().navigate(
                                R.id.firstScreenBottomSheetFragment, bundleOf(
                                    "categories" to it.categories
                                )
                            )
                        }
                        is FirstScreenUiState.ShowSubCategoriesBottomSheet -> {
                            findNavController().navigate(
                                R.id.firstScreenBottomSheetFragment, bundleOf(
                                    "sub-categories" to it.categories
                                )
                            )
                        }
                        is FirstScreenUiState.ShowOptionsBottomSheet -> {
                            findNavController().navigate(
                                R.id.firstScreenBottomSheetFragment, bundleOf(
                                    "options" to it.options
                                )
                            )
                        }
                        is FirstScreenUiState.ShowSubCategoryField -> {
                            binding.tiSubCategories.isVisible = true
                        }
                        is FirstScreenUiState.ShowPropertiesList -> {
                            showPropertiesRecycler(it.properties)
                        }
                        is FirstScreenUiState.ShowResult -> {
                            with(binding){
                                tiCategories.isVisible = false
                                tiSubCategories.isVisible = false
                                rvProperties.isVisible = false
                                tvResult.isVisible = true
                                tvResult.text = it.result
                            }

                        }
                    }
                }
            }
        }
    }

    private fun showPropertiesRecycler(properties: List<PropertyModel?>?) {

        propertiesAdapterDelegate = AdapterDelegateImpl(
            this,
            listOf(PropertiesAdapterDelegate())
        )
        binding.rvProperties.adapter = propertiesAdapterDelegate

        propertiesAdapterDelegate.submitList(properties)

    }

    private fun observeForLiveDataEvents() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<CategoryModel>(
            "category"
        )
            ?.observe(viewLifecycleOwner) {
                binding.edCategories.setText(it.name)
                viewModel.handelEvents(FirstScreenUIEvent.OnCategorySelected(it.children))
            }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<SubCategoryModel>(
            "sub-category"
        )
            ?.observe(viewLifecycleOwner) {
                binding.edSubCategories.setText(it.name)
                viewModel.handelEvents(FirstScreenUIEvent.OnSubCategorySelected(it.id))
            }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<PropertyOptionModel>(
            "option"
        )
            ?.observe(viewLifecycleOwner) {

                viewModel.handelEvents(FirstScreenUIEvent.OnPropertyOptionSelected(it))
            }
    }

    private fun handelClickListeners() {
        binding.edCategories.setOnClickListener {
            viewModel.handelEvents(FirstScreenUIEvent.OnCategoriesFieldClicked)
        }

        binding.edSubCategories.setOnClickListener {
            viewModel.handelEvents(FirstScreenUIEvent.OnSubCategoriesFieldClicked)
        }
        binding.btnShowResult.setOnClickListener {
            viewModel.handelEvents(FirstScreenUIEvent.OnShowResultButtonClicked)
        }
    }

    override fun consumeCommand(action: BaseCommand) {
        when (action) {
            is PropertiesAdapterDelegate.PropertiesAdapterCommand.ShowPropertyOptions -> {
                viewModel.handelEvents(FirstScreenUIEvent.OnPropertyFieldClicked(action.property))
            }
            is PropertiesAdapterDelegate.PropertiesAdapterCommand.UpdatePropertyOptionValue->{
                viewModel.handelEvents(FirstScreenUIEvent.OnPropertyFieldTextChanged(action.property,action.value))
            }
        }

    }
}