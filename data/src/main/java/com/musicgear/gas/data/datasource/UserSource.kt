package com.musicgear.gas.data.datasource

import com.musicgear.gas.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserSource {
  fun getUser(): Observable<User>
  fun insert(user: User): Completable
  fun update(user: User): Completable
  fun delete(): Completable
}