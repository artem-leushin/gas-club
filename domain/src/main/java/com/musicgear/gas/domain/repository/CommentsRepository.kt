package com.musicgear.gas.domain.repository

import com.musicgear.gas.domain.entity.Comment
import io.reactivex.Observable

interface CommentsRepository {
  fun getCommentsForInstrument(instrumentId: Int): Observable<List<Comment>>
}