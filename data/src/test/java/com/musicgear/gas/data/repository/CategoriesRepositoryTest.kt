package com.musicgear.gas.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.musicgear.gas.data.api.retrofit.GasApi.Companion.GAS_GROUP_ID
import com.musicgear.gas.data.datasource.CategoriesSource
import com.musicgear.gas.domain.entity.Category
import com.musicgear.gas.domain.repository.CategoriesRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoriesRepositoryTest {

  private val remote: CategoriesSource = mockk(relaxed = true)
  private lateinit var repo: CategoriesRepository

  @Before
  fun setup() {
    repo = CategoriesRepositoryImpl(remote)
  }

  @Test
  fun `repo returns albums for selling stuff only`() {
    val expectedCategories = listOf(
      Category(id = 0, name = "Продажа чего-то там"),
      Category(id = 1, name = "чего-то там продажа"),
      Category(id = 2, name = "чего-то там"),
      Category(id = 3, name = "Перепродажа чего-то там"),
      Category(id = 4, name = "Закупка чего-то там"),
      Category(id = 5, name = "Продажа чего-то там 2"),
      Category(id = 6, name = "Продажа чего-то там 3")
    )

    every { remote.getCategories(GAS_GROUP_ID) } returns Observable.just(expectedCategories)

    repo.getShoppingCategories()
      .test()
      .assertValue { categories ->
        categories.all { it.name.startsWith("Продажа") }
      }
  }

  @Test
  fun `repo returns empty list if there are no albums for selling stuff`() {
    val expectedCategories = listOf(
      Category(id = 1, name = "чего-то там продажа"),
      Category(id = 2, name = "чего-то там"),
      Category(id = 4, name = "Закупка чего-то там")
    )

    every { remote.getCategories(GAS_GROUP_ID) } returns Observable.just(expectedCategories)

    repo.getShoppingCategories()
      .test()
      .assertValue { categories -> categories.isEmpty() }
  }
}