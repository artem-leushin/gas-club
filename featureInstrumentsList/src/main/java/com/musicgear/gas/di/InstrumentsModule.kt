package com.musicgear.gas.di

import com.musicgear.gas.domain.interactor.LoadInstrumentsUseCase
import com.musicgear.gas.instruments.master.InstrumentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val instrumentsModule = module {
  viewModel {
    InstrumentsViewModel(
      loadInstruments = get(),
      coordinator = get()
    )
  }
  factory { LoadInstrumentsUseCase(repo = get()) }
}