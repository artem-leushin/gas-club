package com.musicgear.gas.instruments.di

import com.musicgear.gas.domain.interactor.LoadInstrumentsUseCase
import com.musicgear.gas.instruments.InstrumentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val instrumentsModule = module {
  viewModel { InstrumentsViewModel() }
  factory { LoadInstrumentsUseCase() }
}