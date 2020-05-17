package com.musicgear.gas.data.datasource.remote

import com.musicgear.gas.data.api.retrofit.GasApi
import com.musicgear.gas.data.datasource.CommentsSource
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.entity.Comment
import io.reactivex.Observable

internal class CommentsRemoteSource(
  private val api: GasApi
) : CommentsSource {
  override fun getComments(instrumentId: Int): Observable<List<Comment>> =
    api.getComments(photoId = instrumentId)
      .map { it.response?.items?.map { comment -> comment.toDomain() } }
}