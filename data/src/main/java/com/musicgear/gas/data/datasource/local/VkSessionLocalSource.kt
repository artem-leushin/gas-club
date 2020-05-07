package com.musicgear.gas.data.datasource.local

import com.musicgear.gas.data.database.daos.VkSessionDao
import com.musicgear.gas.data.datasource.VkSessionSource
import com.musicgear.gas.data.mappers.toDB
import com.musicgear.gas.data.mappers.toDomain
import com.musicgear.gas.domain.entity.VkSession
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import io.reactivex.Completable
import io.reactivex.Observable

internal class VkSessionLocalSource(private val dao: VkSessionDao) : VkSessionSource,
  VKTokenExpiredHandler {

  init {
    VK.addTokenExpiredHandler(this)
  }

  override fun getSession(): Observable<VkSession> = dao.getSession()
    .map { it[0].toDomain() }

  override fun saveSession(session: VkSession): Completable = dao.insert(session.toDB())

  override fun clearSession(): Completable = dao.delete()

  override fun onTokenExpired() {
    dao.delete()
  }
}