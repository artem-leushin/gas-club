package com.musicgear.gas.data.repository

import com.musicgear.gas.data.datasource.InstrumentsSource
import com.musicgear.gas.domain.entity.InstrumentPhoto
import com.musicgear.gas.domain.repository.InstrumentsRepository
import io.reactivex.Observable

internal class InstrumentsRepositoryImpl(
  private val remote: InstrumentsSource
) : InstrumentsRepository {

  override fun getInstruments(categoryId: Int, page: Int): Observable<List<InstrumentPhoto>> =
    remote.getInstruments(categoryId, page)

}

