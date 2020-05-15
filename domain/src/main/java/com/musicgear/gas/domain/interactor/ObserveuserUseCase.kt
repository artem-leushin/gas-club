package com.musicgear.gas.domain.interactor

import com.musicgear.gas.domain.entity.User
import com.musicgear.gas.domain.repository.UserRepository
import io.reactivex.Observable

class ObserveUserUseCase(
  private val repo: UserRepository
) {
  fun execute(): Observable<User> = repo.observeUser()
}

class LoadUserUseCase(
  private val repo: UserRepository
) {
  fun execute(): Observable<User> = repo.refresh()
    .andThen(repo.observeUser())
}
