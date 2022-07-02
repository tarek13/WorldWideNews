package com.link.worldwidenews.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.link.worldwidenews.R
import com.link.worldwidenews.databinding.ViewHolderNewsItemBinding
import com.link.worldwidenews.model.news.Article
import com.link.worldwidenews.utils.extensions.loadUrl
import com.link.worldwidenews.utils.helper.DateHelper

class NewsListAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<NewsListAdapter.ArticleViewHolder>() {
    private var mData: List<Article?>? = null
    private val mInflater: LayoutInflater
    private var mClickListener: ItemClickListener? = null
    private val context: Context


    fun setData(data: List<Article?>) {
        this.mData = data
        notifyDataSetChanged()
    }

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ViewHolderNewsItemBinding.inflate(mInflater, parent, false)
        return ArticleViewHolder(binding, mClickListener)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = mData?.get(position)
        holder.onBind(item)

    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

    // stores and recycles views as they are scrolled off screen
    inner class ArticleViewHolder internal constructor(
        private val binding: ViewHolderNewsItemBinding,
        private val itemClickListener: ItemClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(article: Article?) {
            binding.articleTitleTextView.text = article?.title
            binding.articleAuthorTextView.text =
                context.getString(R.string.by) + " ${article?.author}"
            article?.publishedAt?.let { date ->
                binding.articleDateTextView.text = DateHelper.convertDateFormatToAnother(
                    "yyyy-MM-dd'T'HH:mm:ss'Z'",
                    date,
                    "MMMM dd,yyyy"
                )

            }
            binding.articlePhotoImageView.loadUrl(article?.urlToImage)
            binding.root.setOnClickListener {
                itemClickListener?.onItemClick(article, adapterPosition)
            }
        }

    }

    // convenience method for getting data at click position
    fun getItem(id: Int): Article? {
        return mData?.get(id)
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(article: Article?, position: Int)
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.context = context
    }
}