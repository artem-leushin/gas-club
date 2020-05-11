package com.musicgear.gas.data.datasource

import com.musicgear.gas.domain.entity.Instrument
import io.reactivex.Observable

interface InstrumentsSource {
  fun getInstruments(categoryId: Int, page: Int): Observable<List<Instrument>>
}