package com.box.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.box.domain.entity.room.AccountBoxSettingDbEntity
import com.box.domain.entity.room.AccountDbEntity
import com.box.domain.entity.room.BoxDbEntity
import com.box.domain.entity.view.SettingDbView

@Database(
    version = 1,
    entities = [AccountDbEntity::class, BoxDbEntity::class, AccountBoxSettingDbEntity::class],
    views = [SettingDbView::class],
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getAccountsDao(): AccountsDao

    abstract fun getBoxesDao(): BoxesDao

    companion object {
        private const val databaseName: String = "app_database.db"
        private const val initialDatabaseName: String = "initial_database.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabaseClient(context: Context): AppDatabase {
            if(instance != null) return instance!!
            synchronized(this) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                    .createFromAsset(initialDatabaseName)
                    .build()
                return instance!!
            }
        }
    }
}