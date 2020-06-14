package com.musicgear.gas.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.musicgear.gas.data.database.daos.UserDao
import com.musicgear.gas.data.database.room.GasDatabase
import com.musicgear.gas.data.database.room.GasRoomDbProvider
import com.musicgear.gas.data.entity.local.UserDB
import com.musicgear.gas.data.mappers.toDB
import com.musicgear.gas.domain.entity.User
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

  @get:Rule
  open val executorRule = InstantTaskExecutorRule()

  private lateinit var db: GasDatabase
  private lateinit var userDao: UserDao
  private val expectedUser = User(11, "Artem", "Leushin", "artem_leushin", "www.avatar.com").toDB()
  private val modifiedUser = expectedUser.copy(firstName = "Sheppard", avatarUrl = "www.new_avatar.com")

  private val getUserObserver = TestObserver<List<UserDB>>()
  private val insertUserObserver = TestObserver<Void>()

  @Before
  fun setup() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = GasDatabase(GasRoomDbProvider.createInMemoryRoom(context))
    userDao = db.userDao
  }

  @After
  fun tearDown() {
    db.close()
  }

  @Test
  fun `empty dao returns empty user list`() {
    userDao.getUser()
      .subscribe(getUserObserver)

    getUserObserver
      .awaitCount(1)
      .assertValue(emptyList())
  }

  @Test
  fun `user subscription is not completed after receiving an item`() {
    userDao.getUser()
      .subscribe(getUserObserver)

    getUserObserver
      .assertSubscribed()
      .assertNoErrors()
      .assertNotComplete()
  }

  @Test
  fun `empty dao returns valid user after insertion`() {
    userDao.getUser()
      .subscribe(getUserObserver)

    getUserObserver
      .awaitCount(1)
      .assertValue(listOf())

    userDao.insert(expectedUser)
      .subscribe(insertUserObserver)

    insertUserObserver
      .assertSubscribed()
      .assertNoErrors()
      .assertComplete()
      .awaitTerminalEvent()

    getUserObserver
      .awaitCount(2)
      .assertValueCount(2)
      .assertValues(emptyList(), listOf(expectedUser))
  }

  @Test
  fun `fields are updated for existing user`() {
    userDao.insert(expectedUser)
      .andThen(userDao.update(modifiedUser))
      .blockingAwait()

    userDao.getUser()
      .test()
      .assertValue(listOf(modifiedUser))
  }

  @Test
  fun `user is deleted from the database`() {
    userDao.insert(expectedUser)
      .blockingAwait()

    userDao.getUser()
      .test()
      .assertValue(listOf(expectedUser))

    userDao.delete().blockingAwait()

    userDao.getUser()
      .test()
      .assertValue(emptyList())
  }
}

