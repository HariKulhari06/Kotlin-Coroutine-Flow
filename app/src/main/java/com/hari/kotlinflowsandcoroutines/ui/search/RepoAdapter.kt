package com.hari.kotlinflowsandcoroutines.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hari.kotlinflowsandcoroutines.R
import com.hari.kotlinflowsandcoroutines.databinding.RepoViewItemBinding
import com.hari.kotlinflowsandcoroutines.vo.Repository

class RepoAdapter : ListAdapter<Repository, RepoAdapter.RepoViewHolder>(DIFF()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.repo_view_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RepoViewHolder(private val viewItem: RepoViewItemBinding) :
        RecyclerView.ViewHolder(viewItem.root) {
        fun bind(repository: Repository) {
            viewItem.repo = repository
            Glide.with(viewItem.imageView.context).load(repository.owner.avatarUrl)
                .into(viewItem.imageView)
        }
    }

    companion object {
        class DIFF : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.owner == newItem.owner
                        && oldItem.name == newItem.name

            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.description == newItem.description
                        && oldItem.stargazersCount == newItem.stargazersCount
            }

        }
    }
}