package com.musicgear.gas.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.musicgear.gas.data.local.daos.UserDao
import com.musicgear.gas.data.local.daos.VkSessionDao
import com.musicgear.gas.data.local.entity.UserDB
import com.musicgear.gas.data.local.entity.VkSessionDB

@Database(
  entities = [UserDB::class, VkSessionDB::class],
  version = 1
)
@TypeConverters(LocalDateConverter::class)
internal abstract class GasRoomDatabase : RoomDatabase() {
  abstract fun userDao(): UserDao
  abstract fun vkSessionDao(): VkSessionDao
}
