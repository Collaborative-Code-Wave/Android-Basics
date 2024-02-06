package code.wave.chapter13.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import code.wave.chapter13.databinding.ItemNewsBinding
import code.wave.chapter13.model.NewsItem

class NewsAdapter(
  private val onClick: (NewsItem) -> (Unit)
): ListAdapter<NewsItem, NewsAdapter.ViewHolder>(diffUtil) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      ItemNewsBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      ),
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(currentList[position])
  }

  companion object {
    val diffUtil = object: DiffUtil.ItemCallback<NewsItem>(){
      override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem === newItem
      }

      override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
        return oldItem == newItem
      }

    }
  }
  inner class ViewHolder(private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NewsItem){
      binding.titleTextView.text = item.title
      binding.root.setOnClickListener {
        onClick(item)
      }
    }
  }
}