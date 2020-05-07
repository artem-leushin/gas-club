package com.musicgear.gas.data.datasource

import com.musicgear.gas.domain.entity.VkSession
import io.reactivex.Completable
import io.reactivex.Observable

interface VkSessionSource {
  fun getSession(): Observable<VkSession>
  fun saveSession(session: VkSession): Completable
  fun clearSession(): Completable
}