package com.musicgear.gas.data.datasource.local

import com.musicgear.gas.data.local.daos.UserDao
import com.musicgear.gas.data.mappers.toDB
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.datasource.UserSource
import com.musicgear.gas.domain.entity.User
import io.reactivex.Completable
import io.reactivex.Observable

internal class UserLocalSource(
  private val dao: UserDao
) : UserSource {

  override fun getUser(): Observable<User> = dao.getUser()
    .map {
      if (it.isEmpty()) User.EMPTY
      else it[0].toDomain()
    }

  override fun saveUser(user: User): Completable = dao.insert(user.toDB())

  override fun updateUser(user: User): Completable = dao.update(user.toDB())

  override fun deleteUser(): Completable = dao.delete()
}