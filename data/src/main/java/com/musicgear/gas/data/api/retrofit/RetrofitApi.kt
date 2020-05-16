package com.musicgear.gas.data.api.retrofit

import com.musicgear.gas.data.entity.remote.AlbumsResponse
import com.musicgear.gas.data.entity.remote.CommentsResponse
import com.musicgear.gas.data.entity.remote.PhotosResponse
import com.musicgear.gas.data.entity.remote.Response
import com.musicgear.gas.data.entity.remote.UserResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RetrofitApi {

  @GET("users.get")
  fun getUser(
    @Query("fields") fields: List<String>? = listOf("screen_name, photo_200_orig")
  ): Observable<UserResponse>

  @GET("photos.getAlbumsasd")
  fun getAlbums(
    @Query("owner_id") groupId: Int? = GAS_GROUP_ID,
    @Query("need_covers") needCovers: Int? = 1,
    @Query("photo_sizes") photoSizes: Int? = 1
  ): Observable<Response<AlbumsResponse>>

  @GET("photos.get")
  fun getPhotos(
    @Query("owner_id") groupId: Int? = GAS_GROUP_ID,
    @Query("album_id") albumId: Int,
    @Query("count") count: Int? = 20,
    @Query("offset") offset: Int
  ): Observable<Response<PhotosResponse>>

  @GET("photos.getComments")
  fun getComments(
    @Query("owner_id") groupId: Int? = GAS_GROUP_ID,
    @Query("photo_id") photoId: Int,
    @Query("sort") sort: String? = "asc"
  ): Observable<Response<CommentsResponse>>

  companion object {
    const val GAS_GROUP_ID = -6923031
  }
}

