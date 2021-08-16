package com.emil.github.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.emil.github.data.UserListData
import com.emil.github.databinding.ItemUserDataBinding

class UsersItemAdapter(private val onClickListener: OnClickListener):
    ListAdapter<UserListData, UsersItemAdapter.UserViewHolder>(DiffCallback) {

    class UserViewHolder(private var binding: ItemUserDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserListData) {
            binding.userData = userData
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserDataBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userData = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(userData)
        }
        holder.bind(userData)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<UserListData>() {
        override fun areItemsTheSame(oldItem: UserListData, newItem: UserListData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserListData, newItem: UserListData): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val myClickListener: (userData: UserListData) -> Unit) {
        fun onClick(userData: UserListData) = myClickListener(userData)
    }

}


