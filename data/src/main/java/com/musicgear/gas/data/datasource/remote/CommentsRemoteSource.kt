package com.musicgear.gas.data.datasource.remote

import com.musicgear.gas.data.api.retrofit.RetrofitApi
import com.musicgear.gas.data.datasource.CommentsSource
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.entity.Comment
import io.reactivex.Observable

class CommentsRemoteSource(
  private val api: RetrofitApi
) : CommentsSource {
  override fun getComments(instrumentId: Int): Observable<List<Comment>> =
    api.getComments(albumId = instrumentId)
      .map { it.map { comment -> comment.toDomain() } }
}