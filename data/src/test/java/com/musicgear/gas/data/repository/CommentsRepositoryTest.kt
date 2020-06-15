package com.musicgear.gas.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.musicgear.gas.data.datasource.CommentsSource
import com.musicgear.gas.domain.entity.Comment
import com.musicgear.gas.domain.repository.CommentsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CommentsRepositoryTest {

  private val remote: CommentsSource = mockk(relaxed = true)
  private lateinit var repo: CommentsRepository

  @Before
  fun setup() {
    repo = CommentsRepositoryImpl(remote)
  }

  @Test
  fun `repo returns comments from remote source for proper instrument photo`() {
    val instrumentId = 1
    val expectedComments = listOf(
      Comment(id = 0, text = "го-то там"),
      Comment(id = 1, text = "м продажа"),
      Comment(id = 2, text = "м"),
      Comment(id = 3, text = "а чего-то там"),
      Comment(id = 4, text = "го-то там"),
      Comment(id = 5, text = "го-то там 2"),
      Comment(id = 6, text = "го-то там 3")
    )

    every { remote.getComments(instrumentId) } returns Observable.just(expectedComments)

    repo.getCommentsForInstrument(instrumentId)
      .test()
      .assertValue(expectedComments)

    verify { remote.getComments(instrumentId) }
  }
}