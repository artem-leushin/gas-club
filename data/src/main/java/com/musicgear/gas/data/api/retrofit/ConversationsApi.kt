package com.musicgear.gas.data.api.retrofit

import com.musicgear.gas.data.entity.remote.ConversationR
import io.reactivex.Observable
import retrofit2.http.GET

internal interface ConversationsApi {

  @GET("")
  fun getConversations(): Observable<List<ConversationR>>
}