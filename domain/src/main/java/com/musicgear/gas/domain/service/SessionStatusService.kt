package com.musicgear.gas.domain.service

import com.musicgear.gas.domain.entity.SessionStatus
import io.reactivex.Observable

interface SessionStatusService {
  fun observeSessionStatus(): Observable<SessionStatus>
}