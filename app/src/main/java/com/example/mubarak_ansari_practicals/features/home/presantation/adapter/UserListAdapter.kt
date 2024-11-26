package com.example.mubarak_ansari_practicals.features.home.presantation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mubarak_ansari_practicals.databinding.ItemUserBinding
import com.example.mubarak_ansari_practicals.features.home.data.model.Item
import com.example.mubarak_ansari_practicals.utils.Constant.convertNumberToReadableFormat
import java.util.Locale

class UserListAdapter(
    private val itemClick: ItemClick
) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    private var list: List<Item> = listOf()
    private lateinit var mContext: Context

    fun setData(list: List<Item>) {
        this.list = list
    }

    class UserListViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: Item) {
            binding.apply {
                tvUserName.text = data.display_name.orEmpty()
                tvReputation.text = convertNumberToReadableFormat(data.reputation ?: 0)
                tvBadgeGold.text = data.badge_counts?.gold.toString()
                tvBadgeSilver.text = data.badge_counts?.silver.toString()
                tvBadgeBronze.text = data.badge_counts?.bronze.toString()
                Glide.with(binding.root.context)
                    .load(data.profile_image)
                    .into(binding.ivUser)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        mContext = parent.context
        return UserListViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val data = list[position]
        holder.setData(data)
        holder.itemView.setOnClickListener {
            itemClick.getUserDetails(data)
        }
    }
}

interface ItemClick {
    fun getUserDetails(model: Item)
}