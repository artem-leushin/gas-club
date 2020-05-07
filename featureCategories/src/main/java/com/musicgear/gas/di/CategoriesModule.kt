package com.musicgear.gas.di

import com.musicgear.gas.categories.CategoriesViewModel
import com.musicgear.gas.domain.interactor.LoadCategoriesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoriesModule = module {
  viewModel { CategoriesViewModel(loadCategories = get(), coordinator = get()) }
  factory { LoadCategoriesUseCase(repo = get()) }
}