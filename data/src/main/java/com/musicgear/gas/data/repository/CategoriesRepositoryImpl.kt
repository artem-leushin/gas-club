package com.musicgear.gas.data.repository

import com.musicgear.gas.data.datasource.CategoriesSource
import com.musicgear.gas.domain.entity.Category
import com.musicgear.gas.domain.repository.CategoriesRepository
import io.reactivex.Observable

internal class CategoriesRepositoryImpl(
  private val remote: CategoriesSource
) : CategoriesRepository {
  override fun getShoppingCategories(): Observable<List<Category>> = remote.getCategories()
    .map { it.filter { category -> category.name.startsWith("Продажа") } }
}

