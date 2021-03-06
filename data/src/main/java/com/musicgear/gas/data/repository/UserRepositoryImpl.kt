package com.musicgear.gas.data.repository

import com.jakewharton.rx.ReplayingShare
import com.musicgear.gas.data.datasource.UserSource
import com.musicgear.gas.domain.entity.User
import com.musicgear.gas.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Observable

internal class UserRepositoryImpl(
  private val local: UserSource,
  private val remote: UserSource
) : UserRepository {

  private val source = local.getUser().compose(ReplayingShare.instance<User>())

  override fun observeUser(): Observable<User> = source

  override fun saveUser(user: User): Completable = local.insert(user)

  override fun updateUser(user: User): Completable = local.update(user)

  override fun clear(): Completable = local.delete()

  override fun refresh(): Completable = remote.getUser()
    .flatMapCompletable { local.insert(it) }
}