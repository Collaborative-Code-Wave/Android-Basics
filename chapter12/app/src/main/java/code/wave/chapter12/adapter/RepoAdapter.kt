package code.wave.chapter12.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import code.wave.chapter12.databinding.ItemRepoBinding
import code.wave.chapter12.model.Repo

class RepoAdapter(
  private val onClick: (Repo) -> Unit,
): ListAdapter<Repo, RepoAdapter.RepoViewHolder>(diffUtil) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
    return RepoViewHolder(
      ItemRepoBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
    holder.bind(currentList[position])
  }

  inner class RepoViewHolder(private val binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(item: Repo){
      binding.repoNameTextView.text = item.name
      binding.descriptionTextView.text = item.description
      binding.forkCountTextView.text = item.forkCount.toString()
      binding.startCountTextView.text = item.starCount.toString()
      binding.root.setOnClickListener {
        onClick(item)
      }
    }
  }

  companion object {
    val diffUtil = object: DiffUtil.ItemCallback<Repo>() {
      override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
      }
    }
  }
}