package com.musicgear.gas.data.local.db

import com.musicgear.gas.data.local.daos.UserDao
import com.musicgear.gas.data.local.daos.VkSessionDao
import io.reactivex.Completable

internal class GasDatabase(
  private val roomDb: GasRoomDatabase
) {

  private val userDao: UserDao = roomDb.userDao()
  private val vkSessionDao: VkSessionDao = roomDb.vkSessionDao()

  fun clearAllTables(): Completable = Completable.fromCallable { roomDb.clearAllTables() }
  fun close() = roomDb.close()
}