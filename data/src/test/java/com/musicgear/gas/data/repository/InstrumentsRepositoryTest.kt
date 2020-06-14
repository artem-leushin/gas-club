package com.musicgear.gas.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.musicgear.gas.data.datasource.InstrumentsSource
import com.musicgear.gas.domain.entity.InstrumentPhoto
import com.musicgear.gas.domain.repository.InstrumentsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentsRepositoryTest {

  private val remote: InstrumentsSource = mockk(relaxed = true)
  private lateinit var repo: InstrumentsRepository

  @Before
  fun setup() {
    repo = InstrumentsRepositoryImpl(remote)
  }

  @Test
  fun `repo returns photos from remote source for proper page and category`() {
    val categoryId = 1
    val page = 1
    val expectedInstruments = listOf(
      InstrumentPhoto(id = 0, name = "го-то там"),
      InstrumentPhoto(id = 1, name = "м продажа"),
      InstrumentPhoto(id = 2, name = "м"),
      InstrumentPhoto(id = 3, name = "а чего-то там"),
      InstrumentPhoto(id = 4, name = "го-то там"),
      InstrumentPhoto(id = 5, name = "го-то там 2"),
      InstrumentPhoto(id = 6, name = "го-то там 3")
    )

    every { remote.getInstruments(categoryId, page) } returns Observable.just(expectedInstruments)

    repo.getInstruments(categoryId, page)
      .test()
      .assertValue(expectedInstruments)

    verify { remote.getInstruments(categoryId, page) }
  }
}