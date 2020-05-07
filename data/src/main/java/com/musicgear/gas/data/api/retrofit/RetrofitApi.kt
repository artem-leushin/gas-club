package com.musicgear.gas.data.api.retrofit

import com.musicgear.gas.data.entity.remote.AlbumsResponse
import com.musicgear.gas.data.entity.remote.CommentR
import com.musicgear.gas.data.entity.remote.PhotoR
import com.musicgear.gas.data.entity.remote.Response
import com.musicgear.gas.domain.GAS_GROUP_ID
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
  @GET("photos.getAlbums")
  fun getAlbums(
    @Query("owner_id") groupId: Int? = GAS_GROUP_ID,
    @Query("need_covers") needCovers: Int? = 1
  ): Observable<Response<AlbumsResponse>>

  @GET("photos.get")
  fun getPhotos(
    @Query("owner_id") groupId: Int? = GAS_GROUP_ID,
    @Query("album_id") albumId: Int
  ): Observable<List<PhotoR>>

  @GET("photos.getComments")
  fun getComments(
    @Query("owner_id") groupId: Int? = GAS_GROUP_ID,
    @Query("album_id") albumId: Int,
    @Query("sort") sort: String? = "asc"
  ): Observable<List<CommentR>>
}
