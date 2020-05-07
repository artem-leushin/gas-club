package com.musicgear.gas.data.datasource

import com.musicgear.gas.domain.entity.Instrument
import io.reactivex.Observable

interface InstrumentsSource {
  fun getInstruments(categoryId: Int): Observable<List<Instrument>>
}