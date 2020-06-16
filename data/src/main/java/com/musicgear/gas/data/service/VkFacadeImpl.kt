package com.musicgear.gas.data.service

import android.app.Activity
import com.musicgear.gas.data.datasource.VkFacade
import com.musicgear.gas.domain.entity.AuthBundle
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope

class VkFacadeImpl(private val vk: VK) : VkFacade {
  override fun login(activity: Activity) = vk.login(activity, listOf(VKScope.GROUPS, VKScope.PHOTOS))

  override fun logout() = vk.logout()

  override fun handleAuthResultFromActivity(authBundle: AuthBundle, authCallback: VKAuthCallback) {
    authBundle.run { vk.onActivityResult(requestCode, resultCode, data, authCallback) }
  }

  override fun isLoggedIn(): Boolean = VK.isLoggedIn()
}