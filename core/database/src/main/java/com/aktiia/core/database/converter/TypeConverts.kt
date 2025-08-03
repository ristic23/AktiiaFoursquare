package com.aktiia.core.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.aktiia.core.database.entity.PhotoRoom
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@ProvidedTypeConverter
class TypeConverts {
    private val json: Json = Json { ignoreUnknownKeys = true }

    private val photoListSerializer = ListSerializer<PhotoRoom>(serializer<PhotoRoom>())

    @TypeConverter
    fun photoRoomListToJson(list: List<PhotoRoom>?): String? {
        return list?.let {
            json.encodeToString<List<PhotoRoom>>(photoListSerializer, it)
        }
    }

    @TypeConverter
    fun jsonToPhotoRoomList(jsonString: String?): List<PhotoRoom>? {
        return jsonString?.let { json.decodeFromString(it) }
    }
}
