package com.musicgear.gas.domain.datasource

import com.musicgear.gas.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserSource {
  fun getUser(): Observable<User>
  fun saveUser(user: User): Completable
  fun updateUser(user: User): Completable
  fun deleteUser(): Completable
}