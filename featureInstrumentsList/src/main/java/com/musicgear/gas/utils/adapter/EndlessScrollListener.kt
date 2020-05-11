package com.musicgear.gas.utils.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EndlessScrollListener(
  private val loadNextPage: () -> Unit
) : RecyclerView.OnScrollListener() {

  private var pastVisiblesItems: Int = 0
  private var visibleItemCount: Int = 0
  private var totalItemCount: Int = 0

  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    if (dy > 0) {
      visibleItemCount = recyclerView.layoutManager!!.childCount
      totalItemCount = recyclerView.layoutManager!!.itemCount
      pastVisiblesItems = (recyclerView.layoutManager as LinearLayoutManager)
        .findFirstVisibleItemPosition()

      if ((visibleItemCount + pastVisiblesItems) == totalItemCount)
        loadNextPage()
    }
  }
}