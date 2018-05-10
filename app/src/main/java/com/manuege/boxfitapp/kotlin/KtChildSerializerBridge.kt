package com.manuege.boxfitapp.kotlin

import com.manuege.boxfit.utils.Json
import io.objectbox.BoxStore
import org.json.JSONException
import org.json.JSONObject

class KtChildSerializerBridge private constructor() {
    companion object {
        val instance: KtChildSerializerBridge by lazy { KtChildSerializerBridge() }
    }

    fun merge(json: Json, item: KtChild, boxStore: BoxStore) {
        if (json.has("id")) {
            item.id = json.getLong("id")
        }
        if (json.has("value")) {
            item.value = json.getString("value")
        }
    }

    fun createFreshObject(id: Long?): KtChild {
        val item = KtChild()
        if (id != null) {
            item.id = id;
        }
        return item
    }

    fun getId(item: KtChild): Long? {
        return item.id
    }

    fun toJson(item: KtChild): JSONObject? {
        val json = JSONObject()
        try {
            json.put("id", item.id)

            if (item.value != null) {
                json.put("value", item.value)
            }

        } catch (ignored: JSONException) {
            return null
        }
        return json
    }

}