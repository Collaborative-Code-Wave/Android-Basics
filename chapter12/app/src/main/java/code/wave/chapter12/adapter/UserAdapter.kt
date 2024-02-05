package code.wave.chapter12.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import code.wave.chapter12.databinding.ItemUserBinding
import code.wave.chapter12.model.User

class UserAdapter: ListAdapter<User, UserAdapter.UserViewHolder>(diffUtil) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
    return UserViewHolder(
      ItemUserBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
    holder.bind(currentList[position])
  }

  inner class UserViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: User){
      binding.usernameTextView.text = item.username
    }
  }

  companion object {
    val diffUtil = object: DiffUtil.ItemCallback<User>() {
      override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
      }
    }
  }
}