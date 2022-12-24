package com.example.mazaady.presentation.first_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.mazaady.core.adapter.AdapterDelegateImpl
import com.example.mazaady.core.adapter.BaseCommand
import com.example.mazaady.core.adapter.CommandListener
import com.example.mazaady.databinding.FragmentFirstScreenBottomSheetBinding
import com.example.mazaady.domain.models.CategoryModel
import com.example.mazaady.domain.models.PropertyOptionModel
import com.example.mazaady.domain.models.SubCategoryModel
import com.example.mazaady.presentation.first_screen.adapters.CategoriesAdapterDelegate
import com.example.mazaady.presentation.first_screen.adapters.PropertyOptionsAdapterDelegate
import com.example.mazaady.presentation.first_screen.adapters.SubCategoryAdapterDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class FirstScreenBottomSheetFragment : BottomSheetDialogFragment(), CommandListener {

    private lateinit var binding: FragmentFirstScreenBottomSheetBinding
    private lateinit var itemsAdapterDelegate: AdapterDelegateImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstScreenBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpItemsRecyclerView()
    }

    private fun setUpCategoriesSearch(list: List<CategoryModel>) {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            itemsAdapterDelegate.submitList(list.filter { it.name.contains(text.toString()) })
        }
    }


    private fun setUpSubCategoriesSearch(list: List<SubCategoryModel>) {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            itemsAdapterDelegate.submitList(list.filter { it.name.contains(text.toString()) })
        }
    }


    private fun setUpOptionsSearch(list: List<PropertyOptionModel>) {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            itemsAdapterDelegate.submitList(list.filter { it.name.contains(text.toString()) })
        }
    }

    private fun setUpItemsRecyclerView() {

        itemsAdapterDelegate = AdapterDelegateImpl(
            this,
            listOf(
                CategoriesAdapterDelegate(),
                SubCategoryAdapterDelegate(),
                PropertyOptionsAdapterDelegate()
            )
        )
        binding.itemsRecycler.adapter = itemsAdapterDelegate


        arguments?.let {
            when {
                it.containsKey("categories") -> {
                    val res = it.getParcelableArrayList<CategoryModel>("categories")
                    res?.let {
                        itemsAdapterDelegate.submitList(it.toList())
                        setUpCategoriesSearch(it)
                    }

                }
                it.containsKey("sub-categories") -> {
                    val res = it.getParcelableArrayList<SubCategoryModel>("sub-categories")
                    res?.let {
                        itemsAdapterDelegate.submitList(it.toList())
                        setUpSubCategoriesSearch(it)
                    }
                }
                else -> {
                    val res = it.getParcelableArrayList<PropertyOptionModel>("options")
                    res?.let {
                        itemsAdapterDelegate.submitList(it.toList())
                        setUpOptionsSearch(it)
                    }
                }
            }


        }

    }

    override fun consumeCommand(action: BaseCommand) {
        when (action) {
            is CategoriesAdapterDelegate.CategoriesAdapterCommand.SelectedCategory -> {

                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "category",
                    action.category
                )
                findNavController().popBackStack()

            }
            is SubCategoryAdapterDelegate.SubCategoriesAdapterCommand.SelectedSubCategory -> {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "sub-category",
                    action.category
                )
                findNavController().popBackStack()

            }
            is PropertyOptionsAdapterDelegate.PropertyOptionsAdapterCommand.SelectedOption -> {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "option",
                    action.option
                )
                findNavController().popBackStack()

            }
        }
    }
}