package com.musicgear.gas.data.database.room

import com.musicgear.gas.data.database.daos.UserDao
import com.musicgear.gas.data.database.daos.VkSessionDao
import io.reactivex.Completable

internal class GasDatabase(
  private val roomDb: GasRoomDatabase
) {

  val userDao: UserDao = roomDb.userDao()
  val vkSessionDao: VkSessionDao = roomDb.vkSessionDao()

  fun clearAllTables(): Completable = Completable.fromCallable { roomDb.clearAllTables() }
  fun close() = roomDb.close()
}