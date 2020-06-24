package com.musicgear.gas.di

import com.musicgear.gas.data.database.room.GasRoomDbProvider
import org.koin.dsl.module

val localDbModule = module {
  single { GasRoomDbProvider.createGasRoomDb(get()).userDao() }
  single { GasRoomDbProvider.createGasRoomDb(get()).vkSessionDao() }
}