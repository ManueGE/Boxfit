package com.manuege.boxfitapp.tojson

import com.manuege.boxfit.BoxfitSerializer
import com.manuege.boxfitapp.AbstractObjectBoxTest
import com.manuege.boxfitapp.model.java.Parent
import com.manuege.boxfitapp.model.kotlin.KtChild
import com.manuege.boxfitapp.model.kotlin.KtParent
import com.manuege.boxfitapp.model.kotlin.KtToJsonTestObject
import com.manuege.boxfitapp.utils.JsonProvider
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import java.util.*

class KtToJsonTest: AbstractObjectBoxTest() {
    @Test
    @Throws(JSONException::class)
    fun parentSerializer_toJson() {
        val parent = KtParent()
        boxStore.boxFor(KtParent::class.java).put(parent)

        parent.id = 1
        parent.longOptionalField = 2L
        parent.integerField = 3
        parent.integerOptionalField = 4
        parent.boolField = true
        parent.boolOptionalField = false
        parent.doubleField = 5.5
        parent.doubleOptionalField = 6.6
        parent.stringField = "hello"
        parent.serializerNameInferred = "inferred"

        val one = KtChild(1, "one")
        val two = KtChild(2, "two")
        val three = KtChild(3, "three")
        val four = KtChild(4, "four")
        val five = KtChild(5, "five")
        boxStore.boxFor(KtChild::class.java).put(one, two, three, four, five)

        parent.keyPathField = "keyPath"
        parent.fakeKeyPathField = "fakeKeyPath"

        parent.toOne!!.target = one

        parent.toMany!!.add(two)
        parent.toMany!!.add(three)

        parent.list.add(four)
        parent.list.add(five)

        parent.enumField = Parent.Enum.ONE

        val calendar = Calendar.getInstance()
        calendar.set(2017, 8, 17)
        parent.dateField = calendar.time

        val boxfitSerializer = BoxfitSerializer(boxStore)
        val actual = boxfitSerializer.toJson(parent)
        val expected = JsonProvider.getJSONObject("parent_for_to_json.json")
        Assert.assertEquals(expected!!.toString(), actual.toString())
    }

    @Test
    @Throws(JSONException::class)
    fun parentSerializer_toJsonNullsIgnored() {
        val parent = KtParent()
        boxStore.boxFor(KtParent::class.java).put(parent)

        parent.id = 1
        parent.integerField = 3
        parent.boolField = true
        parent.doubleField = 5.5

        val expected = JSONObject()
        expected.put("id", 1)
        expected.put("integer", 3)
        expected.put("bool", true)
        expected.put("double", 5.5)
        expected.put("toMany", JSONArray())
        expected.put("list", JSONArray())

        val boxfitSerializer = BoxfitSerializer(boxStore)
        val actual = boxfitSerializer.toJson(parent)
        Assert.assertEquals(expected.toString(), actual.toString())
    }

    @Test
    @Throws(JSONException::class)
    fun parentSerializer_toJsonIgnoreAndIncludeNull() {
        val `object` = KtToJsonTestObject()
        `object`.ignoredField = "hello"
        boxStore.boxFor(KtToJsonTestObject::class.java).put(`object`)

        val expected = JSONObject()
        expected.put("long_class", JSONObject.NULL)
        expected.put("integer_class", JSONObject.NULL)
        expected.put("bool_class", JSONObject.NULL)
        expected.put("double_class", JSONObject.NULL)
        expected.put("string", JSONObject.NULL)
        expected.put("toOne", JSONObject.NULL)

        val boxfitSerializer = BoxfitSerializer(boxStore)
        val actual = boxfitSerializer.toJson(`object`)
        Assert.assertEquals(expected.toString(), actual.toString())
    }

    @Test
    @Throws(JSONException::class)
    fun parentSerializer_toJsonList() {
        val parent1 = KtParent()
        boxStore.boxFor(KtParent::class.java).put(parent1)

        parent1.id = 1
        parent1.stringField = "hello"

        val parent2 = KtParent()
        boxStore.boxFor(KtParent::class.java).put(parent2)

        parent2.id = 2
        parent2.stringField = "world"

        val list = ArrayList<KtParent>()
        list.add(parent1)
        list.add(parent2)

        val boxfitSerializer = BoxfitSerializer(boxStore)
        val actual = boxfitSerializer.toJson(list)

        val object1 = JSONObject()
        object1.put("id", 1)
        object1.put("integer", 0)
        object1.put("bool", false)
        object1.put("double", 0)
        object1.put("string", "hello")
        object1.put("toMany", JSONArray())
        object1.put("list", JSONArray())

        val object2 = JSONObject()
        object2.put("id", 2)
        object2.put("integer", 0)
        object2.put("bool", false)
        object2.put("double", 0)
        object2.put("string", "world")
        object2.put("toMany", JSONArray())
        object2.put("list", JSONArray())

        val expected = JSONArray()
        expected.put(object1)
        expected.put(object2)

        Assert.assertEquals(expected.toString(), actual.toString())
    }
}