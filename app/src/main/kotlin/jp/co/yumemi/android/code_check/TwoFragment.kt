/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding

class TwoFragment : Fragment(R.layout.fragment_two) {
    private val args: TwoFragmentArgs by navArgs()
    private val viewModel: OneViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", viewModel.getDate())

        val _binding = FragmentTwoBinding.bind(view)

        val item = args.item

        _binding.ownerIconView.load(item.ownerIconUrl)
        _binding.nameView.text = item.name
        _binding.languageView.text = getString(R.string.written_language, item.language)
        _binding.starsView.text = "${item.stargazersCount} stars"
        _binding.watchersView.text = "${item.watchersCount} watchers"
        _binding.forksView.text = "${item.forksCount} forks"
        _binding.openIssuesView.text = "${item.openIssuesCount} open issues"
    }
}
