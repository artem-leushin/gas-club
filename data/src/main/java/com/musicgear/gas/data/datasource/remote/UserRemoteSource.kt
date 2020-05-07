package com.musicgear.gas.data.datasource.remote

import com.musicgear.gas.data.api.retrofit.RetrofitApi
import com.musicgear.gas.data.datasource.UserSource
import com.musicgear.gas.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Observable

internal class UserRemoteSource(
  private val api: RetrofitApi
) : UserSource {

  override fun getUser(): Observable<User> = TODO()

  override fun saveUser(user: User): Completable = TODO()

  override fun updateUser(user: User): Completable = TODO()

  override fun deleteUser(): Completable = TODO()
}