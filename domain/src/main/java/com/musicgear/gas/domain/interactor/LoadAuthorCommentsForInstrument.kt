package com.musicgear.gas.domain.interactor

import com.musicgear.gas.domain.entity.InstrumentDetails
import com.musicgear.gas.domain.repository.CommentsRepository
import io.reactivex.Observable

class LoadAuthorCommentsForInstrument(
  private val commentsRepo: CommentsRepository
) {
  fun execute(instrumentId: Int, userId: Int): Observable<InstrumentDetails> =
    commentsRepo.getCommentsForInstrument(instrumentId)
      .map { comments ->
        comments.takeWhile { it.userId == userId }
      }
      .map { InstrumentDetails(it) }
}
