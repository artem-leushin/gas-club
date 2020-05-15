package com.musicgear.gas.di

import com.musicgear.gas.domain.interactor.LoadAuthorCommentsForInstrument
import com.musicgear.gas.instruments.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
  viewModel { DetailsViewModel(loadAuthorComments = get()) }
  factory { LoadAuthorCommentsForInstrument(commentsRepo = get()) }

}