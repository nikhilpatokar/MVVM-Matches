package com.nikhilpatokar.assignment.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.TypeConverters
import androidx.room.RoomDatabase
import androidx.room.Room
import com.nikhilpatokar.assignment.models.Result

@Database(entities = [Result::class], version = 1, exportSchema = false)
@TypeConverters(
    Converters::class
)
abstract class PersonDatabase : RoomDatabase() {
    abstract val resultDao: ResultDao

    companion object {
        const val DATABASE_NAME = "matchers_db"
        @Volatile
        private var instance: PersonDatabase? = null
        @JvmStatic
        fun getInstance(context: Context): PersonDatabase? {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PersonDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return instance
        }
    }
}