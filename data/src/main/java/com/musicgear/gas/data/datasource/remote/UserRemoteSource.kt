package com.musicgear.gas.data.datasource.remote

import com.musicgear.gas.data.api.retrofit.GasApi
import com.musicgear.gas.data.datasource.UserSource
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.entity.User
import com.musicgear.gas.domain.exception.UnsupportedByRemoteException
import io.reactivex.Completable
import io.reactivex.Observable

internal class UserRemoteSource(
  private val api: GasApi
) : UserSource {

  override fun getUser(): Observable<User> = api.getUser().map {
    it.user?.firstOrNull()?.toDomain() ?: User.EMPTY
  }

  override fun insert(user: User): Completable = throw UnsupportedByRemoteException()

  override fun update(user: User): Completable = throw UnsupportedByRemoteException()

  override fun delete(): Completable = throw UnsupportedByRemoteException()
}