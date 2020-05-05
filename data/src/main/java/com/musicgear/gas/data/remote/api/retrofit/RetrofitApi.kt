package com.musicgear.gas.data.remote.api.retrofit

import com.musicgear.gas.data.remote.api.entity.CategoryR
import com.musicgear.gas.data.remote.api.entity.InstrumentR
import io.reactivex.Observable

interface RetrofitApi {
  fun getCategories(groupId: Int): Observable<List<CategoryR>>
  fun getInstruments(albumId: Int): Observable<List<InstrumentR>>
  fun getInstrumentDetails(photoId: Int): Observable<InstrumentR>
}
