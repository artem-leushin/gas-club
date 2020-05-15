package com.musicgear.gas.domain.interactor

import com.musicgear.gas.domain.entity.Category
import com.musicgear.gas.domain.entity.InstrumentPhoto
import com.musicgear.gas.domain.repository.CategoriesRepository
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
  fun execute(categoryId: Int, page: Int): Observable<List<InstrumentPhoto>> =
    repo.getInstruments(categoryId, page)
}

