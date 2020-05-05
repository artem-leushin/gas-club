package com.musicgear.gas.domain.interactor

import com.musicgear.gas.domain.entity.Category
import com.musicgear.gas.domain.entity.Instrument
import io.reactivex.Observable

class LoadCategoriesUseCase {
  fun execute(): Observable<List<Category>> = Observable.just(emptyList())
}

class LoadInstrumentsUseCase {
  fun execute(): Observable<List<Instrument>> = Observable.just(emptyList())
}


