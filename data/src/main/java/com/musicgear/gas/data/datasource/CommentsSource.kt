package com.musicgear.gas.data.datasource

import com.musicgear.gas.domain.entity.Comment
import io.reactivex.Observable

interface CommentsSource {
  fun getComments(instrumentId: Int): Observable<List<Comment>>
}