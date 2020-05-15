package com.musicgear.gas.domain.repository

import com.musicgear.gas.domain.entity.InstrumentPhoto
import io.reactivex.Observable

interface InstrumentsRepository {
  fun getInstruments(categoryId: Int, page: Int): Observable<List<InstrumentPhoto>>
}