package com.musicgear.gas.data.repository

import com.jakewharton.rx.ReplayingShare
import com.musicgear.gas.domain.datasource.UserSource
import com.musicgear.gas.domain.entity.User
import com.musicgear.gas.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Observable

class UserRepositoryImpl(private val local: UserSource, remote: UserSource) :
  UserRepository {

  private val source = local.getUser().compose(ReplayingShare.instance<User>())

  override fun observeUser(): Observable<User> = source

  override fun saveUser(user: User): Completable = local.saveUser(user)

  override fun updateUser(user: User): Completable = local.updateUser(user)

  override fun clear(): Completable = local.deleteUser()
}