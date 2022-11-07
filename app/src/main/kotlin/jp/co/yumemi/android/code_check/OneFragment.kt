/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding

class OneFragment: Fragment(R.layout.fragment_one){
    private val viewModel: OneViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val _binding= FragmentOneBinding.bind(view)

        val _layoutManager= LinearLayoutManager(requireContext())
        val _dividerItemDecoration=
            DividerItemDecoration(requireContext(), _layoutManager.orientation)
        val _adapter= CustomAdapter(object : CustomAdapter.OnItemClickListener{
            override fun itemClick(item: item){
                _binding.searchInputText.editableText.clear()
                gotoRepositoryFragment(item)
            }
        })

        _binding.searchInputText
            .setOnEditorActionListener{ editText, action, _ ->
                if (action== EditorInfo.IME_ACTION_SEARCH){
                    editText.text.toString().let {
                        if(it.isNotEmpty()){
                            viewModel.searchResults(it).apply{
                                _adapter.submitList(this)
                            }
                            val keyboard = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            keyboard.hideSoftInputFromWindow(view.windowToken, 0)
                        }
                    }
                    return@setOnEditorActionListener true
                } else {
                    return@setOnEditorActionListener false
                }
            }

        _binding.recyclerView.also{
            it.layoutManager= _layoutManager
            it.addItemDecoration(_dividerItemDecoration)
            it.adapter= _adapter
        }
    }

    fun gotoRepositoryFragment(item: item)
    {
        val _action= OneFragmentDirections
            .actionRepositoriesFragmentToRepositoryFragment(item= item)
        findNavController().navigate(_action)
    }
}
