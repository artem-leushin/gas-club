package com.musicgear.gas.data.datasource

import com.musicgear.gas.domain.entity.VkSession
import io.reactivex.Completable
import io.reactivex.Observable

interface VkSessionSource {
  fun getSession(): Observable<VkSession>
  fun insert(session: VkSession): Completable
  fun clear(): Completable
}