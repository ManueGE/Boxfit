package com.manuege.boxfitapp.fromJson

import com.manuege.boxfit.BoxfitSerializer
import com.manuege.boxfitapp.AbstractObjectBoxTest
import com.manuege.boxfitapp.model.java.ManualId
import com.manuege.boxfitapp.utils.JsonProvider
import org.json.JSONArray
import org.junit.Assert
import org.junit.Test

class KtFromJsonManualIdTest : AbstractObjectBoxTest() {
    
    @Test
    @Throws(Exception::class)
    fun fromJson_importNewObject() {
        val json = JsonProvider.getJSONObject("manual_id.json")
        val boxfitSerializer = BoxfitSerializer(boxStore)
        val `object` = boxfitSerializer.fromJson(ManualId::class.java, json)

        Assert.assertEquals(1, boxStore.boxFor(ManualId::class.java).count())

        Assert.assertNotNull(`object`)
        Assert.assertEquals("a", `object`!!.manualId)
        Assert.assertEquals("one", `object`.value)

        // second object
        json!!.put("id", "b")
        json.put("value", "two")

        val object2 = boxfitSerializer.fromJson(ManualId::class.java, json)

        Assert.assertEquals(2, boxStore.boxFor(ManualId::class.java).count())
        Assert.assertNotNull(`object`)
        Assert.assertEquals("b", object2!!.manualId)
        Assert.assertEquals("two", object2.value)
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_updateObject() {
        val json = JsonProvider.getJSONObject("manual_id.json")
        val boxfitSerializer = BoxfitSerializer(boxStore)
        boxfitSerializer.fromJson(ManualId::class.java, json)

        Assert.assertEquals(1, boxStore.boxFor(ManualId::class.java).count())

        json!!.put("value", "uno")
        val `object` = boxfitSerializer.fromJson(ManualId::class.java, json)

        Assert.assertEquals(1, boxStore.boxFor(ManualId::class.java).count())
        Assert.assertNotNull(`object`)
        Assert.assertEquals("a", `object`!!.manualId)
        Assert.assertEquals("uno", `object`.value)
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_newObjectsFromList() {
        val jsonArray = JsonProvider.getJSONArray("manual_id_array.json")
        val boxfitSerializer = BoxfitSerializer(boxStore)
        val manualIds = boxfitSerializer.fromJson(ManualId::class.java, jsonArray)

        Assert.assertEquals(3, boxStore.boxFor(ManualId::class.java).count())

        val `object` = manualIds!![0]
        Assert.assertEquals("a", `object`.manualId)
        Assert.assertEquals("one", `object`.value)

        val object2 = manualIds[1]
        Assert.assertEquals("b", object2.manualId)
        Assert.assertEquals("two", object2.value)

        val object3 = manualIds[2]
        Assert.assertEquals("c", object3.manualId)
        Assert.assertEquals("three", object3.value)
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_newSingleObjectFromList() {
        val jsonArray = JsonProvider.getJSONArray("manual_id_array.json")
        val boxfitSerializer = BoxfitSerializer(boxStore)
        val manualIds = boxfitSerializer.fromJson(ManualId::class.java, jsonArray)

        Assert.assertEquals(3, boxStore.boxFor(ManualId::class.java).count())

        val `object` = manualIds!![0]
        Assert.assertEquals("a", `object`.manualId)
        Assert.assertEquals("one", `object`.value)

        val object2 = manualIds[1]
        Assert.assertEquals("b", object2.manualId)
        Assert.assertEquals("two", object2.value)

        val object3 = manualIds[2]
        Assert.assertEquals("c", object3.manualId)
        Assert.assertEquals("three", object3.value)
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_noObjectFromList() {
        val jsonArray = JSONArray()

        val boxfitSerializer = BoxfitSerializer(boxStore)
        val manualIds = boxfitSerializer.fromJson(ManualId::class.java, jsonArray)

        Assert.assertEquals(0, boxStore.boxFor(ManualId::class.java).count())
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_updateObjectsFromList() {
        val boxfitSerializer = BoxfitSerializer(boxStore)

        val json = JsonProvider.getJSONObject("manual_id.json")
        boxfitSerializer.fromJson(ManualId::class.java, json)

        val jsonArray = JsonProvider.getJSONArray("manual_id_array.json")
        jsonArray!!.getJSONObject(0).put("value", "uno")
        val manualIds = boxfitSerializer.fromJson(ManualId::class.java, jsonArray)

        Assert.assertEquals(3, boxStore.boxFor(ManualId::class.java).count())

        val `object` = manualIds!![0]
        Assert.assertEquals("a", `object`.manualId)
        Assert.assertEquals("uno", `object`.value)

        val object2 = manualIds[1]
        Assert.assertEquals("b", object2.manualId)
        Assert.assertEquals("two", object2.value)

        val object3 = manualIds[2]
        Assert.assertEquals("c", object3.manualId)
        Assert.assertEquals("three", object3.value)
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_updateSingleObjectFromList() {
        val boxfitSerializer = BoxfitSerializer(boxStore)

        val json = JsonProvider.getJSONObject("manual_id.json")
        boxfitSerializer.fromJson(ManualId::class.java, json)

        json!!.put("value", "uno")
        val jsonArray = JSONArray()
        jsonArray.put(json)

        val manualIds = boxfitSerializer.fromJson(ManualId::class.java, jsonArray)

        Assert.assertEquals(1, boxStore.boxFor(ManualId::class.java).count())

        val `object` = manualIds!![0]
        Assert.assertEquals("a", `object`.manualId)
        Assert.assertEquals("uno", `object`.value)
    }
}