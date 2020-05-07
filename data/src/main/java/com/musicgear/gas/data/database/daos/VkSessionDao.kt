package com.musicgear.gas.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.musicgear.gas.data.entity.local.VkSessionDB
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
internal abstract class VkSessionDao {

  @Query("SELECT * FROM db_vk_session")
  abstract fun getSession(): Observable<List<VkSessionDB>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insert(session: VkSessionDB): Completable

  @Update(onConflict = OnConflictStrategy.REPLACE)
  abstract fun update(session: VkSessionDB): Completable

  @Query("DELETE FROM db_vk_session")
  abstract fun delete(): Completable
}