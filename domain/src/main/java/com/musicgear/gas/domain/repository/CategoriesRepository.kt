package com.musicgear.gas.domain.repository

import com.musicgear.gas.domain.entity.Category
import io.reactivex.Observable

interface CategoriesRepository {
  fun getShoppingCategories(): Observable<List<Category>>
}