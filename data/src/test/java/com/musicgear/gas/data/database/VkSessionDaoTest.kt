package com.musicgear.gas.data.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.musicgear.gas.data.database.daos.VkSessionDao
import com.musicgear.gas.data.database.room.GasDatabase
import com.musicgear.gas.data.database.room.GasRoomDbProvider
import com.musicgear.gas.data.entity.local.VkSessionDB
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VkSessionDaoTest {

  @get:Rule
  open val executorRule = InstantTaskExecutorRule()

  private lateinit var db: GasDatabase
  private lateinit var dao: VkSessionDao
  private val expectedSession = VkSessionDB(11, "email", "phone", "11_22_33_44")
  private val modifiedSession = expectedSession.copy(email = "email 2", accessToken = "55_66_77")

  private val getUserObserver = TestObserver<List<VkSessionDB>>()
  private val insertUserObserver = TestObserver<Void>()

  @Before
  fun setup() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = GasDatabase(GasRoomDbProvider.createInMemoryRoom(context))
    dao = db.vkSessionDao
  }

  @After
  fun tearDown() {
    db.close()
  }

  @Test
  fun `empty dao returns empty session list`() {
    dao.getSession()
      .subscribe(getUserObserver)

    getUserObserver
      .assertValue(emptyList())
  }

  @Test
  fun `session subscription is not completed after receiving an item`() {
    dao.getSession()
      .subscribe(getUserObserver)

    getUserObserver
      .assertSubscribed()
      .assertNoErrors()
      .assertNotComplete()
  }

  @Test
  fun `empty dao returns valid session after insertion`() {
    dao.getSession()
      .subscribe(getUserObserver)

    getUserObserver
      .awaitCount(1)
      .assertValue(listOf())

    dao.insert(expectedSession)
      .subscribe(insertUserObserver)

    insertUserObserver
      .assertSubscribed()
      .assertNoErrors()
      .assertComplete()
      .awaitTerminalEvent()

    getUserObserver
      .awaitCount(2)
      .assertValueCount(2)
      .assertValues(emptyList(), listOf(expectedSession))
  }

  @Test
  fun `fields are updated for existing session`() {
    dao.insert(expectedSession)
      .andThen(dao.update(modifiedSession))
      .blockingAwait()

    dao.getSession()
      .test()
      .assertValue(listOf(modifiedSession))
  }

  @Test
  fun `session is deleted from the database`() {
    dao.insert(expectedSession)
      .blockingAwait()

    dao.getSession()
      .test()
      .assertValue(listOf(expectedSession))

    dao.delete().blockingAwait()

    dao.getSession()
      .test()
      .assertValue(emptyList())
  }
}

