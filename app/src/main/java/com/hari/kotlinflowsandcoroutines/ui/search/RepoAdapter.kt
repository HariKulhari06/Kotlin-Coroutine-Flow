/*
 *  Copyright 2019  Hari Singh Kulhari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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