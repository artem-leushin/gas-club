package com.musicgear.gas.domain.repository

import com.musicgear.gas.domain.entity.Comment
import io.reactivex.Observable

interface CommentsRepository {
  fun getSellerDescriptionComment(instrumentId: Int): Observable<Comment>
}