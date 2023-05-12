package com.example.bgproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bgproject.model.Tgl
import com.example.bgproject.model.User


@Database(
    entities = [
        User::class,
        Tgl::class
    ],
    version = 4, exportSchema = false
)
@TypeConverters(EditableConverter::class)

abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private val migration1to2 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // perform database migration
            }
        }

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    ).addMigrations(migration1to2).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
