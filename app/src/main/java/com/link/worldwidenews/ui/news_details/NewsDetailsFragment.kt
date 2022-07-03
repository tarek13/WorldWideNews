package com.link.worldwidenews.ui.news_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.link.worldwidenews.R
import com.link.worldwidenews.databinding.FragmentNewsDetailsBinding
import com.link.worldwidenews.model.news.Article
import com.link.worldwidenews.utils.extensions.loadUrl
import com.link.worldwidenews.utils.extensions.openUrl
import com.link.worldwidenews.utils.helper.DateHelper
import com.link.worldwidenews.utils.helper.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {

    private val binding by viewBinding(FragmentNewsDetailsBinding::bind)

    private val newsDetailsViewModel by viewModels<NewsDetailsViewModel>()

    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgumentsData()
        setupView()
    }

    private fun getArgumentsData() {
        val newsDetailsFragmentArgs: NewsDetailsFragmentArgs =
            NewsDetailsFragmentArgs.fromBundle(
                requireArguments()
            )
        article = newsDetailsFragmentArgs.article
    }

    private fun setupView() {
        binding.articleDescriptionTextView.text = article?.description
        binding.articleTitleTextView.text = article?.title
        binding.articleAuthorTextView.text =
            activity?.getString(R.string.by) + " ${article?.author}"
        binding.articlePhotoImageView.loadUrl(article?.urlToImage)
        article?.publishedAt?.let { date ->
            binding.articleDateTextView.text = DateHelper.convertDateFormatToAnother(
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                date,
                "MMMM dd,yyyy"
            )

        }
        binding.openWebsiteButton.setOnClickListener {
            activity?.openUrl(article?.url)
        }
    }

}