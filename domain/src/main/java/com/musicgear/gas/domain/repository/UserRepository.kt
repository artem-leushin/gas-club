package com.musicgear.gas.domain.repository

import com.musicgear.gas.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository {
  fun observeUser(): Observable<User>
  fun saveUser(user: User): Completable
  fun updateUser(user: User): Completable
  fun clear(): Completable
}