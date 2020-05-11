package com.musicgear.gas.instruments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.musicgear.gas.instruments.InstrumentsView.Displayable
import com.musicgear.gas.instruments.InstrumentsView.Intent
import com.musicgear.gas.instruments.R
import com.musicgear.gas.instruments.databinding.ItemInstrumentBinding
import com.musicgear.gas.utils.imageloading.ImageLoader
import com.musicgear.gas.utils.rx.safeClicks

class InstrumentsAdapter(
  private val intentsPublisher: (Any) -> Unit,
  private val imageLoader: ImageLoader
) : ListAdapter<Displayable, RecyclerView.ViewHolder>(ItemDiffCallback) {

  override fun getItemViewType(position: Int): Int = when (getItem(position)) {
    is Displayable.DisplayableInstrument -> R.layout.item_instrument
    is Displayable.ProgressItem -> R.layout.item_progress
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
    when (viewType) {
      R.layout.item_instrument -> InstrumentViewHolder(
        ItemInstrumentBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        intentsPublisher, imageLoader
      )
      else -> ProgressViewHolder(
        LayoutInflater.from(parent.context).inflate(viewType, parent, false)
      )
    }

  override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
    (holder as? InstrumentViewHolder)?.let { imageLoader.clear(it.binding.ivPhoto) }
  }

  override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
    (holder as? InstrumentViewHolder)?.let { imageLoader.clear(it.binding.ivPhoto) }
    return true
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val item = getItem(position) as? Displayable.DisplayableInstrument
    item?.let { (holder as InstrumentViewHolder).bind(item) }
  }
}

class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class InstrumentViewHolder(
  val binding: ItemInstrumentBinding,
  publishIntent: (Any) -> Unit,
  private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(binding.root) {

  init {
    binding.run { root.safeClicks { publishIntent(Intent.GoToDetails(instrument!!)) } }
      .subscribe()
  }

  fun bind(instrument: Displayable.DisplayableInstrument) {
    binding.instrument = instrument
    imageLoader.loadImg(
      binding.ivPhoto,
      instrument.photoUrl,
      ImageLoader.Args(transformCenterCrop = true)
    )
  }
}

private object ItemDiffCallback : DiffUtil.ItemCallback<Displayable>() {
  override fun areItemsTheSame(
    oldItem: Displayable,
    newItem: Displayable
  ): Boolean = when {
    oldItem is Displayable.DisplayableInstrument && newItem is Displayable.DisplayableInstrument ->
      oldItem.id == newItem.id
    else -> oldItem is Displayable.ProgressItem && newItem is Displayable.ProgressItem
  }

  override fun areContentsTheSame(
    oldItem: Displayable,
    newItem: Displayable
  ): Boolean = oldItem == newItem
}