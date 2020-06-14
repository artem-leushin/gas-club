package com.musicgear.gas.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.musicgear.gas.data.datasource.UserSource
import com.musicgear.gas.domain.entity.User
import com.musicgear.gas.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserRepositoryTest {

  private val local: UserSource = mockk(relaxed = true)
  private val remote: UserSource = mockk(relaxed = true)
  private lateinit var repo: UserRepository

  private val emptyUser = User.EMPTY
  private val newUser = User(id = 123, firstName = "User-Abuser")

  @Before
  fun setup() {
    repo = UserRepositoryImpl(local, remote)
  }

  @Test
  fun `repo returns user from local`() {
    every { local.getUser() } returns Observable.just(emptyUser)

    repo.observeUser()
      .test()
      .assertValue(emptyUser)
  }

  @Test
  fun `new subscribers receive last item`() {
    val userSource = BehaviorSubject.create<User>()
    every { local.getUser() } returns userSource

    userSource.onNext(emptyUser)

    val firstSubscriber = repo.observeUser().test()
    firstSubscriber.assertValue(emptyUser)

    userSource.onNext(newUser)

    val secondSubscriber = repo.observeUser().test()
    secondSubscriber.assertValue(newUser)

    val latestUser = newUser.copy(id = 888)

    userSource.onNext(latestUser)

    firstSubscriber.assertValue(latestUser)
    secondSubscriber.assertValue(latestUser)
  }

  @Test
  fun `repo gives fresh user after refreshing`() {
    every { local.getUser() } returns Observable.just(emptyUser)
    every { remote.getUser() } returns Observable.just(newUser)
    every { local.insert(newUser) } returns Completable.complete()

    val userSubscription = repo.observeUser().test()
    userSubscription.assertValue(emptyUser)

    repo.refresh().blockingAwait()

    verify { local.insert(newUser) }

    userSubscription.assertValue(newUser)
  }

  @Test
  fun `repo saves specified user to the datasource`() {
    every { local.insert(any()) } returns Completable.complete()
    every { local.update(any()) } returns Completable.complete()

    repo.saveUser(newUser).blockingAwait()
    verify { local.insert(newUser) }

    repo.updateUser(newUser).blockingAwait()
    verify { local.update(newUser) }
  }

  @Test
  fun `clearing repo deletes user from the datasource`() {
    every { local.delete() } returns Completable.complete()

    repo.clear().blockingAwait()
    verify { local.delete() }
  }
}