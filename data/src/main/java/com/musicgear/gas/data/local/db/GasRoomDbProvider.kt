package com.musicgear.gas.data.local.db

import android.content.Context
import androidx.room.Room

internal object GasRoomDbProvider {

  fun createGasRoomDb(context: Context): GasRoomDatabase = createInDiskRoom(context)

  fun createInMemoryRoom(context: Context): GasRoomDatabase =
    Room.inMemoryDatabaseBuilder(
      context.applicationContext,
      GasRoomDatabase::class.java
    )
      .allowMainThreadQueries()
      .build()

  private fun createInDiskRoom(context: Context): GasRoomDatabase = Room.databaseBuilder(
    context.applicationContext,
    GasRoomDatabase::class.java,
    "gas_db"
  ).fallbackToDestructiveMigration()
    .build()
}