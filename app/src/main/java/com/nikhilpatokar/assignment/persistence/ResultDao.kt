package com.nikhilpatokar.assignment.persistence

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import com.nikhilpatokar.assignment.models.Result

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertResults(list: List<Result>?): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResult(result: Result)

    @Query("SELECT * FROM RESULTS ORDER BY _id ASC LIMIT :noOfResults ")
    fun searchResults(noOfResults: Int): LiveData<List<Result>>

    @Query("UPDATE RESULTS SET status = :status WHERE _id = :id")
    fun updateStatus(status: String, id: Int)
}