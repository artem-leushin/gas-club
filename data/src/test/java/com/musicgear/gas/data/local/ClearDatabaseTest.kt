package com.musicgear.gas.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.musicgear.gas.data.database.daos.UserDao
import com.musicgear.gas.data.database.daos.VkSessionDao
import com.musicgear.gas.data.database.room.GasDatabase
import com.musicgear.gas.data.database.room.GasRoomDbProvider
import com.musicgear.gas.data.entity.local.UserDB
import com.musicgear.gas.data.entity.local.VkSessionDB
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ClearDatabaseTest {

  @get:Rule
  open val executorRule = InstantTaskExecutorRule()

  private val context: Context = ApplicationProvider.getApplicationContext<Context>()
  private lateinit var db: GasDatabase
  private lateinit var vkDao: VkSessionDao
  private lateinit var userDao: UserDao
  private lateinit var session: VkSessionDB
  private lateinit var user: UserDB

  @Before
  fun setup() {
    db = GasDatabase(GasRoomDbProvider.createInMemoryRoom(context))
    vkDao = db.vkSessionDao
    userDao = db.userDao
    session = VkSessionDB(userId = 999)
    user = UserDB(id = 999)
  }

  @After
  fun tearDown() {
    db.close()
  }

  @Test
  fun `database tables are cleared`() {
    vkDao.insert(session)
      .andThen(userDao.insert(user))
      .blockingAwait()

    vkDao.getSession()
      .test()
      .assertValue(listOf(session))

    userDao.getUser()
      .test()
      .assertValue(listOf(user))

    db.clearAllTables().blockingAwait()

    vkDao.getSession()
      .test()
      .assertValue(emptyList())

    userDao.getUser()
      .test()
      .assertValue(emptyList())
  }
}