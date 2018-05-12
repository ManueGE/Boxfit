package com.manuege.boxfitapp.fromJson

import com.manuege.boxfit.BoxfitSerializer
import com.manuege.boxfitapp.AbstractObjectBoxTest
import com.manuege.boxfitapp.model.kotlin.KtNoObjectBoxObject
import com.manuege.boxfitapp.model.kotlin.KtParentResponse
import com.manuege.boxfitapp.model.kotlin.KtSingleParentResponse
import com.manuege.boxfitapp.utils.JsonProvider
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test

class KtFromJsonNoEntitiesSerializationTests : AbstractObjectBoxTest() {
    @Test
    @Throws(JSONException::class)
    fun fromJsonNoEntities_canSerialize() {
        val jsonObject = JSONObject()
        jsonObject.put("integer", 1)
        jsonObject.put("string", "hello")

        val serializer = BoxfitSerializer(boxStore)
        val `object` = serializer.fromJson(KtNoObjectBoxObject::class.java, jsonObject)

        Assert.assertEquals(1, `object`!!.integer.toLong())
        Assert.assertEquals("hello", `object`.string)
    }

    @Test
    @Throws(JSONException::class)
    fun fromJsonNoEntities_canSerializeWithoutBoxStore() {
        val jsonObject = JSONObject()
        jsonObject.put("integer", 1)
        jsonObject.put("string", "hello")

        val serializer = BoxfitSerializer(null)
        val `object` = serializer.fromJson(KtNoObjectBoxObject::class.java, jsonObject)

        Assert.assertEquals(1, `object`!!.integer.toLong())
        Assert.assertEquals("hello", `object`.string)
    }

    @Test
    @Throws(JSONException::class)
    fun fromJsonNoEntities_canSerializeArray() {
        val jsonObject1 = JSONObject()
        jsonObject1.put("integer", 1)
        jsonObject1.put("string", "hello")

        val jsonObject2 = JSONObject()
        jsonObject2.put("integer", 2)
        jsonObject2.put("string", "world")

        val jsonArray = JSONArray()
        jsonArray.put(jsonObject1)
        jsonArray.put(jsonObject2)

        val serializer = BoxfitSerializer(null)
        val array = serializer.fromJson(KtNoObjectBoxObject::class.java, jsonArray)

        Assert.assertEquals(2, array!!.size.toLong())

        Assert.assertEquals(1, array[0].integer.toLong())
        Assert.assertEquals("hello", array[0].string)

        Assert.assertEquals(2, array[1].integer.toLong())
        Assert.assertEquals("world", array[1].string)
    }

    @Test
    @Throws(JSONException::class)
    fun fromJsonNoEntities_canSerializeWthEntityField() {
        val parentJSON = JsonProvider.getJSONObject("parent.json")
        val jsonObject = JSONObject()
        jsonObject.put("parent", parentJSON)
        val serializer = BoxfitSerializer(boxStore)
        val `object` = serializer.fromJson(KtParentResponse::class.java, jsonObject)

        Assert.assertNotNull(`object`!!.parent)
        Assert.assertEquals(1, `object`.parent!!.id)
        Assert.assertEquals("hello", `object`.parent!!.stringField)
    }

    @Test
    @Throws(JSONException::class)
    fun fromJsonNoEntities_canSerializeWthGenericEntityField() {
        val parentJSON = JsonProvider.getJSONObject("parent.json")
        val jsonObject = JSONObject()
        jsonObject.put("data", parentJSON)
        val serializer = BoxfitSerializer(boxStore)
        val `object` = serializer.fromJson(KtSingleParentResponse::class.java, jsonObject)

        Assert.assertNotNull(`object`!!.data)
        Assert.assertEquals(1, `object`.data!!.id)
        Assert.assertEquals("hello", `object`.data!!.stringField)
    }
}