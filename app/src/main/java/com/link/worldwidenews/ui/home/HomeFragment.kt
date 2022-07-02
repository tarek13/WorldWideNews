package com.link.worldwidenews.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.link.worldwidenews.R
import com.link.worldwidenews.databinding.FragmentHomeBinding
import com.link.worldwidenews.domain.util.AppConstants
import com.link.worldwidenews.model.news.Article
import com.link.worldwidenews.utils.extensions.displayAlertDialog
import com.link.worldwidenews.utils.extensions.gone
import com.link.worldwidenews.utils.extensions.showToastMessage
import com.link.worldwidenews.utils.extensions.visible
import com.link.worldwidenews.utils.helper.ErrorMessageHelper
import com.link.worldwidenews.utils.helper.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val homeViewModel by viewModels<HomeViewModel>()

    private var newsListAdapter: NewsListAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        homeViewModel.loadNews()
        observeLiveData()


    }

    private fun setupView() {
        newsListAdapter = NewsListAdapter(requireActivity())
        binding.newsListRecyclerView.adapter = newsListAdapter

        binding.newsListSwipeRefreshLayout.setColorSchemeResources(
            R.color.black,
            R.color.black,
            R.color.black,
            R.color.black
        )
        binding.newsListSwipeRefreshLayout.setOnRefreshListener {
            binding.newsListSwipeRefreshLayout.isRefreshing = true
            homeViewModel.loadNews()
            binding.newsListSwipeRefreshLayout.isRefreshing = false
        }
    }


    private fun observeLiveData() {
        homeViewModel.stateListener.successResponseLiveData.observe(viewLifecycleOwner) { response: Any? ->
            val news = response as List<Article?>?
            news?.let {
                binding.newsListRecyclerView.visible()
                binding.errorTextView.gone()
                newsListAdapter?.setData(it)
                newsListAdapter?.setClickListener(object : NewsListAdapter.ItemClickListener {
                    override fun onItemClick(article: Article?, position: Int) {
                        val bundle = Bundle()
                        bundle.putParcelable(AppConstants.ARTICLE_ITEM_KEY, article)
                        findNavController().navigate(R.id.newsDetailsFragment, bundle)
                    }
                })
            }
        }

        homeViewModel.stateListener.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage: Any? ->
            when (errorMessage) {
                is Int -> activity?.showToastMessage(
                    ErrorMessageHelper.showGeneralErrorMessage(
                        errorMessage
                    )
                )
                is String -> activity?.showToastMessage(errorMessage)
            }
            binding.errorTextView.visible()
            binding.newsListRecyclerView.gone()
        }

        homeViewModel.stateListener.loadingProgressLiveData.observe(viewLifecycleOwner) { status ->
            if (status == true) {
                binding.newsListRecyclerView.gone()
                binding.errorTextView.gone()
                binding.loadingProgressBar.visible()
            } else {
                binding.loadingProgressBar.gone()
            }

        }

        homeViewModel.stateListener.unAuthorizedErrorLiveData.observe(viewLifecycleOwner) { status ->
            if (status == true) {
                activity?.displayAlertDialog(
                    message = resources.getString(R.string.invalid_api_key),
                    positiveButtonTitle = getString(android.R.string.ok),
                    positiveOnClickListener = { _, _ ->
                        activity?.finish()
                    }

                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        newsListAdapter = null
    }
}