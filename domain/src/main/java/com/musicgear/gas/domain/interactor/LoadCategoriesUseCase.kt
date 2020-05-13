package com.musicgear.gas.domain.interactor

import com.musicgear.gas.domain.entity.Category
import com.musicgear.gas.domain.entity.Comment
import com.musicgear.gas.domain.entity.Instrument
import com.musicgear.gas.domain.repository.CategoriesRepository
import com.musicgear.gas.domain.repository.CommentsRepository
import com.musicgear.gas.domain.repository.InstrumentsRepository
import io.reactivex.Observable

class LoadCategoriesUseCase(
  private val repo: CategoriesRepository
) {
  fun execute(): Observable<List<Category>> = repo.getShoppingCategories()
}

class LoadInstrumentsUseCase(
  private val repo: InstrumentsRepository
) {
  fun execute(categoryId: Int, page: Int): Observable<List<Instrument>> =
    repo.getInstruments(categoryId, page)
}

class LoadFirstCommentUseCase(
  private val repo: CommentsRepository
) {
  fun execute(instrumentId: Int): Observable<Comment> =
    repo.getSellerDescriptionComment(instrumentId)
}
