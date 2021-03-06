package com.musicgear.gas.data.datasource.local

import com.musicgear.gas.data.database.daos.UserDao
import com.musicgear.gas.data.datasource.UserSource
import com.musicgear.gas.data.mappers.toDB
import com.musicgear.gas.data.mappers.toDomain
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

  override fun insert(user: User): Completable = dao.insert(user.toDB())

  override fun update(user: User): Completable = dao.update(user.toDB())

  override fun delete(): Completable = dao.delete()
}