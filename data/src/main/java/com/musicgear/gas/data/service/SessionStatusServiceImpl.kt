package com.musicgear.gas.data.service

import com.musicgear.gas.data.datasource.VkFacade
import com.musicgear.gas.domain.entity.SessionStatus
import com.musicgear.gas.domain.entity.SessionStatus.LOGGED_IN
import com.musicgear.gas.domain.entity.SessionStatus.LOGGED_OUT
import com.musicgear.gas.domain.service.SessionStatusService
import io.reactivex.Observable

class SessionStatusServiceImpl(
  private val vkFacade: VkFacade
) : SessionStatusService {

  override fun observeSessionStatus(): Observable<SessionStatus> = Observable.fromCallable {
    if (vkFacade.isLoggedIn()) LOGGED_IN
    else LOGGED_OUT
  }
}