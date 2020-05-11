package com.musicgear.gas.data.datasource.remote

import com.musicgear.gas.data.api.retrofit.RetrofitApi
import com.musicgear.gas.data.datasource.CategoriesSource
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.entity.Category
import io.reactivex.Observable

internal class CategoriesRemoteSource(
  private val api: RetrofitApi
) : CategoriesSource {

  override fun getCategories(groupId: Int): Observable<List<Category>> = api.getAlbums()
    .map { it.response!!.items!!.map { category -> category.toDomain() } }
}