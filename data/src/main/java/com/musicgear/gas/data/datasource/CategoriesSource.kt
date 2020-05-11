package com.musicgear.gas.data.datasource

import com.musicgear.gas.data.api.retrofit.RetrofitApi.Companion.GAS_GROUP_ID
import com.musicgear.gas.domain.entity.Category
import io.reactivex.Observable

interface CategoriesSource {
  fun getCategories(groupId: Int = GAS_GROUP_ID): Observable<List<Category>>
}

