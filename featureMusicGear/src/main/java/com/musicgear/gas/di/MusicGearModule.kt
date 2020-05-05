package com.musicgear.gas.di

import com.musicgear.gas.gear.MusicGearViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module

val musicGearModule = module {
  viewModel<MusicGearViewModel>()
}