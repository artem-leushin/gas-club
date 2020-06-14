package com.musicgear.gas.data.datasource.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.musicgear.gas.data.database.daos.UserDao
import com.musicgear.gas.data.datasource.UserSource
import com.musicgear.gas.data.entity.local.UserDB
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.entity.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserLocalSourceTest {

  private val userDao: UserDao = mockk(relaxed = true)
  private lateinit var source: UserSource

  private val expectedUser = UserDB(11, "Artem", "Leushin", "artem_leushin", "www.avatar.com")

  @Before
  fun setup() {
    source = UserLocalSource(userDao)
  }

  @Test
  fun `source returns domain user from dao`() {
    every { userDao.getUser() } returns Observable.just(listOf(expectedUser))

    source.getUser()
      .test()
      .assertValue(expectedUser.toDomain())

    every { userDao.getUser() } returns Observable.just(emptyList())

    source.getUser()
      .test()
      .assertValue(User.EMPTY)
  }

  @Test
  fun `source saves domain user into dao`() {
    every { userDao.insert(expectedUser) } returns Completable.complete()

    source.insert(expectedUser.toDomain())
      .test()
      .assertComplete()

    verify { userDao.insert(expectedUser) }
  }

  @Test
  fun `source updates dao with domain user`() {
    every { userDao.update(expectedUser) } returns Completable.complete()

    source.update(expectedUser.toDomain())
      .test()
      .assertComplete()

    verify { userDao.update(expectedUser) }
  }

  @Test
  fun `source deletes user from dao`() {
    every { userDao.delete() } returns Completable.complete()

    source.delete()
      .test()
      .assertComplete()

    verify { userDao.delete() }
  }
}