package com.musicgear.gas.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.musicgear.gas.data.local.entity.UserDB
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
internal abstract class UserDao {

  @Query("SELECT * FROM db_user")
  abstract fun getUser(): Observable<List<UserDB>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract fun insert(userDB: UserDB): Completable

  @Update(onConflict = OnConflictStrategy.REPLACE)
  abstract fun update(userDB: UserDB): Completable

  @Query("DELETE FROM db_user")
  abstract fun delete(): Completable
}