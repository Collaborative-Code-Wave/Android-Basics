package code.wave.chapter13.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import code.wave.chapter13.databinding.ItemNewsBinding
import code.wave.chapter13.model.NewsModel
import com.bumptech.glide.Glide

class NewsAdapter(
  private val onClick: (NewsModel) -> (Unit)
): ListAdapter<NewsModel, NewsAdapter.ViewHolder>(diffUtil) {

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
    val diffUtil = object: DiffUtil.ItemCallback<NewsModel>(){
      override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem === newItem
      }

      override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
        return oldItem == newItem
      }

    }
  }
  inner class ViewHolder(private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NewsModel){
      binding.titleTextView.text = item.title

      Glide.with(binding.thumbnailImageView)
        .load(item.imageUrl)
        .into(binding.thumbnailImageView)

      binding.root.setOnClickListener {
        onClick(item)
      }
    }
  }
}