package com.musicgear.gas.data.repository

import com.musicgear.gas.data.datasource.CommentsSource
import com.musicgear.gas.domain.entity.Comment
import com.musicgear.gas.domain.repository.CommentsRepository
import io.reactivex.Observable

class CommentsRepositoryImpl(
  private val remote: CommentsSource
) : CommentsRepository {
  override fun getCommentsForInstrument(instrumentId: Int): Observable<List<Comment>> =
    remote.getComments(instrumentId)
}