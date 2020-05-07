package com.musicgear.gas.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.musicgear.gas.categories.CategoriesView.DisplayableCategory
import com.musicgear.gas.categories.CategoriesView.Event.GoToInstruments
import com.musicgear.gas.categories.R
import com.musicgear.gas.categories.databinding.ItemCategoryBinding
import com.musicgear.gas.utils.rx.safeClicks

class CategoriesAdapter(
  private val intentsPublisher: (Any) -> Unit
) : ListAdapter<DisplayableCategory, CategoryViewHolder>(
  ItemDiffCallback
) {

  override fun getItemViewType(position: Int): Int =
    R.layout.item_category

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
    val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return CategoryViewHolder(
      binding,
      intentsPublisher
    )
  }

  override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
    val item = getItem(position) as DisplayableCategory
    holder.bind(item)
  }
}

class CategoryViewHolder(
  private val binding: ItemCategoryBinding,
  publishIntent: (Any) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

  init {
    binding.run { root.safeClicks { publishIntent(GoToInstruments(category!!.id)) } }
  }

  fun bind(category: DisplayableCategory) {
    binding.category = category
    binding.executePendingBindings()
  }
}

private object ItemDiffCallback : DiffUtil.ItemCallback<DisplayableCategory>() {

  override fun areItemsTheSame(
    oldItem: DisplayableCategory,
    newItem: DisplayableCategory
  ): Boolean = oldItem.id == newItem.id

  override fun areContentsTheSame(
    oldItem: DisplayableCategory,
    newItem: DisplayableCategory
  ): Boolean = oldItem == newItem
}