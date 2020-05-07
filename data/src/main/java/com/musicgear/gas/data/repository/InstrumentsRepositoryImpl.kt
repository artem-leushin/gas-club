package com.musicgear.gas.data.repository

import com.musicgear.gas.data.datasource.InstrumentsSource
import com.musicgear.gas.domain.entity.Instrument
import com.musicgear.gas.domain.repository.InstrumentsRepository
import io.reactivex.Observable

class InstrumentsRepositoryImpl(
  private val remote: InstrumentsSource
) : InstrumentsRepository {

  override fun getInstruments(categoryId: Int): Observable<List<Instrument>> =
    remote.getInstruments(categoryId)

}

