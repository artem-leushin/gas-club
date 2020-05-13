package com.musicgear.gas.details

import com.musicgear.gas.domain.interactor.LoadFirstCommentUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsModule = module {
  viewModel { DetailsViewModel(loadFirstComment = get()) }
  factory { LoadFirstCommentUseCase(repo = get()) }
}