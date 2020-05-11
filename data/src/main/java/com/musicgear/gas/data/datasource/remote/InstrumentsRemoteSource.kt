package com.musicgear.gas.data.datasource.remote

import com.musicgear.gas.data.api.retrofit.RetrofitApi
import com.musicgear.gas.data.datasource.InstrumentsSource
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.entity.Instrument
import io.reactivex.Observable

internal class InstrumentsRemoteSource(
  private val api: RetrofitApi
) : InstrumentsSource {
  override fun getInstruments(categoryId: Int, page: Int): Observable<List<Instrument>> =
    api.getPhotos(albumId = categoryId, offset = page)
      .map { it.response?.items?.map { instrument -> instrument.toDomain() } }

}