package com.musicgear.gas.data.datasource

import android.app.Activity
import com.musicgear.gas.domain.entity.AuthBundle
import com.vk.api.sdk.auth.VKAuthCallback

interface VkFacade {
  fun login(activity: Activity)
  fun logout()
  fun handleAuthResultFromActivity(authBundle: AuthBundle, authCallback: VKAuthCallback)
  fun isLoggedIn(): Boolean
}