package com.box.domain.entity.room.utils

import android.database.sqlite.SQLiteException
import com.box.domain.entity.room.StorageException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

suspend fun <T> wrapSQLiteException(dispatcher: CoroutineDispatcher, block: suspend CoroutineScope.() -> T) {
    try {
        withContext(dispatcher, block)
    } catch (e: SQLiteException) {
        val appException = StorageException()
        appException.initCause(e)
        throw appException
    }
}