package com.musicgear.gas.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.musicgear.gas.data.database.LocalDateConverter
import com.musicgear.gas.data.database.daos.UserDao
import com.musicgear.gas.data.database.daos.VkSessionDao
import com.musicgear.gas.data.entity.local.UserDB
import com.musicgear.gas.data.entity.local.VkSessionDB

@Database(
  entities = [UserDB::class, VkSessionDB::class],
  version = 1
)
@TypeConverters(LocalDateConverter::class)
internal abstract class GasRoomDatabase : RoomDatabase() {
  abstract fun userDao(): UserDao
  abstract fun vkSessionDao(): VkSessionDao
}
