package com.manuege.boxfitapp.fromJson

import com.manuege.boxfit.BoxfitSerializer
import com.manuege.boxfitapp.AbstractObjectBoxTest
import com.manuege.boxfitapp.model.java.Parent
import com.manuege.boxfitapp.model.kotlin.KtChild
import com.manuege.boxfitapp.model.kotlin.KtParent
import com.manuege.boxfitapp.utils.JsonProvider
import org.json.JSONObject
import org.junit.Assert
import org.junit.Test

class KtFromJsonSingleObjectTest : AbstractObjectBoxTest() {
    @Test
    @Throws(Exception::class)
    fun fromJson_importNewObject() {
        val obj = JsonProvider.getJSONObject("parent.json")
        val boxfitSerializer = BoxfitSerializer(boxStore)
        val parent = boxfitSerializer.fromJson(KtParent::class.java, obj)

        Assert.assertEquals(1, boxStore.boxFor(KtParent::class.java).count())
        Assert.assertNotNull(parent)

        Assert.assertEquals(1, parent!!.id)
        Assert.assertEquals(2, parent.longOptionalField as Long)

        Assert.assertEquals(3, parent.integerField.toLong())
        Assert.assertEquals(4, (parent.integerOptionalField as Int).toLong())

        Assert.assertEquals(true, parent.boolField)
        Assert.assertEquals(false, parent.boolOptionalField)

        Assert.assertEquals(5.5, parent.doubleField, 0.0)
        Assert.assertEquals(6.6, parent.doubleOptionalField!!, 0.0)

        Assert.assertEquals("hello", parent.stringField)
        Assert.assertEquals("inferred", parent.serializerNameInferred)
        Assert.assertEquals("keyPath", parent.keyPathField)
        Assert.assertEquals("fakeKeyPath", parent.fakeKeyPathField)

        Assert.assertEquals(1, parent.toOne!!.target.id)
        Assert.assertEquals("one", parent.toOne!!.target.value)

        Assert.assertEquals(2, parent.toMany!!.size.toLong())
        Assert.assertEquals(2, parent.toMany!![0].id)
        Assert.assertEquals("two", parent.toMany!![0].value)

        Assert.assertEquals(2, parent.list.size.toLong())
        Assert.assertEquals(4, parent.list[0].id)
        Assert.assertEquals("four", parent.list[0].value)

        Assert.assertEquals(Parent.Enum.ONE, parent.enumField)

        Assert.assertEquals(117, parent.dateField!!.year.toLong())
        Assert.assertEquals(8, parent.dateField!!.month.toLong())
        Assert.assertEquals(17, parent.dateField!!.date.toLong())

        Assert.assertEquals(1, parent.ignoreNull)
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_updateExistingObject() {
        val serializer = BoxfitSerializer(boxStore)
        val first_json = JsonProvider.getJSONObject("parent_2.json")
        serializer.fromJson(KtParent::class.java, first_json)

        val second_json = JsonProvider.getJSONObject("parent.json")
        serializer.fromJson(KtParent::class.java, second_json)

        val parent = serializer.fromJson(KtParent::class.java, second_json)

        Assert.assertEquals(1, boxStore.boxFor(KtParent::class.java).count())
        Assert.assertNotNull(parent)

        Assert.assertEquals(1, parent!!.id)
        Assert.assertEquals(2, parent.longOptionalField as Long)

        Assert.assertEquals(3, parent.integerField.toLong())
        Assert.assertEquals(4, (parent.integerOptionalField as Int).toLong())

        Assert.assertEquals(true, parent.boolField)
        Assert.assertEquals(false, parent.boolOptionalField)

        Assert.assertEquals(5.5, parent.doubleField, 0.0)
        Assert.assertEquals(6.6, parent.doubleOptionalField!!, 0.0)

        Assert.assertEquals("hello", parent.stringField)
        Assert.assertEquals("inferred", parent.serializerNameInferred)
        Assert.assertEquals("keyPath", parent.keyPathField)
        Assert.assertEquals("fakeKeyPath", parent.fakeKeyPathField)

        Assert.assertEquals(1, parent.toOne!!.target.id)
        Assert.assertEquals("one", parent.toOne!!.target.value)

        Assert.assertEquals(2, parent.toMany!!.size.toLong())
        Assert.assertEquals(2, parent.toMany!![0].id)
        Assert.assertEquals("two", parent.toMany!![0].value)

        Assert.assertEquals(2, parent.list.size.toLong())
        Assert.assertEquals(4, parent.list[0].id)
        Assert.assertEquals("four", parent.list[0].value)

        Assert.assertEquals(Parent.Enum.ONE, parent.enumField)

        Assert.assertEquals(117, parent.dateField!!.year.toLong())
        Assert.assertEquals(8, parent.dateField!!.month.toLong())
        Assert.assertEquals(17, parent.dateField!!.date.toLong())

        // Shouldn't update value, is null
        Assert.assertEquals(1, parent.ignoreNull)
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_partialUpdateExistingObject() {
        val serializer = BoxfitSerializer(boxStore)
        val first_json = JsonProvider.getJSONObject("parent.json")
        serializer.fromJson(KtParent::class.java, first_json)

        val second_json = JsonProvider.getJSONObject("partial_parent.json")
        val parent = serializer.fromJson(KtParent::class.java, second_json)

        Assert.assertEquals(1, boxStore.boxFor(KtParent::class.java).count())
        Assert.assertNotNull(parent)

        Assert.assertEquals(1, parent!!.id)
        Assert.assertEquals(20, parent.longOptionalField as Long)

        Assert.assertEquals(30, parent.integerField.toLong())
        Assert.assertEquals(4, (parent.integerOptionalField as Int).toLong())

        Assert.assertEquals(false, parent.boolField)
        Assert.assertEquals(false, parent.boolOptionalField)

        Assert.assertEquals(5.5, parent.doubleField, 0.0)
        Assert.assertEquals(60.6, parent.doubleOptionalField!!, 0.0)

        Assert.assertEquals("bye", parent.stringField)
        Assert.assertEquals("inferred", parent.serializerNameInferred)
        Assert.assertEquals("keyPath", parent.keyPathField)
        Assert.assertEquals("fakeKeyPath", parent.fakeKeyPathField)

        Assert.assertEquals(1, parent.toOne!!.target.id)
        Assert.assertEquals("one", parent.toOne!!.target.value)

        Assert.assertEquals(2, parent.toMany!!.size.toLong())
        Assert.assertEquals(2, parent.toMany!![0].id)
        Assert.assertEquals("two", parent.toMany!![0].value)

        Assert.assertEquals(2, parent.list.size.toLong())
        Assert.assertEquals(4, parent.list[0].id)
        Assert.assertEquals("four", parent.list[0].value)

        Assert.assertEquals(Parent.Enum.ONE, parent.enumField)

        Assert.assertEquals(117, parent.dateField!!.year.toLong())
        Assert.assertEquals(8, parent.dateField!!.month.toLong())
        Assert.assertEquals(17, parent.dateField!!.date.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_relationshipWithIds() {
        val serializer = BoxfitSerializer(boxStore)
        val json = JsonProvider.getJSONObject("parent_with_properties_with_id.json")
        val parent = serializer.fromJson(KtParent::class.java, json)

        Assert.assertEquals(1, parent!!.toOne!!.target.id)
        Assert.assertNull(parent.toOne?.target?.value)

        Assert.assertEquals(2, parent.toMany!!.size.toLong())
        Assert.assertEquals(2, parent.toMany!![0].id)
        Assert.assertNull(parent.toMany!![0].value)
        Assert.assertEquals(3, parent.toMany!![1].id)
        Assert.assertNull(parent.toMany!![1].value)

        Assert.assertEquals(3, parent.list.size.toLong())
        Assert.assertEquals(4, parent.list[0].id)
        Assert.assertNull(parent.list[0].value)
        Assert.assertEquals(5, parent.list[1].id)
        Assert.assertNull(parent.list[1].value)
        Assert.assertEquals(6, parent.list[2].id)
        Assert.assertNull(parent.list[2].value)
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_relationshipWithIdsAndExistingObjects() {
        boxStore.boxFor(KtChild::class.java).put(
                KtChild(1, "one"),
                KtChild(2, "two"),
                KtChild(3, "three"),
                KtChild(4, "four"),
                KtChild(5, "five"),
                KtChild(6, "six")
        )

        val serializer = BoxfitSerializer(boxStore)
        val json = JsonProvider.getJSONObject("parent_with_properties_with_id.json")
        val parent = serializer.fromJson(KtParent::class.java, json)

        Assert.assertEquals(1, parent!!.toOne!!.target.id)
        Assert.assertEquals("one", parent.toOne!!.target.value)

        Assert.assertEquals(2, parent.toMany!!.size.toLong())
        Assert.assertEquals(2, parent.toMany!![0].id)
        Assert.assertEquals("two", parent.toMany!![0].value)
        Assert.assertEquals(3, parent.toMany!![1].id)
        Assert.assertEquals("three", parent.toMany!![1].value)

        Assert.assertEquals(3, parent.list.size.toLong())
        Assert.assertEquals(4, parent.list[0].id)
        Assert.assertEquals("four", parent.list[0].value)
        Assert.assertEquals(5, parent.list[1].id)
        Assert.assertEquals("five", parent.list[1].value)
        Assert.assertEquals(6, parent.list[2].id)
        Assert.assertEquals("six", parent.list[2].value)
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_canAutoConvertStringInNumbersAndViceVersa() {
        val serializer = BoxfitSerializer(boxStore)
        val json = JsonProvider.getJSONObject("parent.json")
        json!!.put("integer", "1")
        json.put("string", 2)
        val parent = serializer.fromJson(KtParent::class.java, json)

        Assert.assertEquals(1, parent!!.integerField.toLong())
        Assert.assertEquals("2", parent.stringField)
    }

    @Test
    @Throws(Exception::class)
    fun albumSerializer_overridesNullProperties() {

        val existingParent = KtParent()
        boxStore.boxFor(KtParent::class.java).put(existingParent)

        val one = KtChild(1, "one")
        val two = KtChild(2, "two")
        val three = KtChild(3, "three")
        val four = KtChild(4, "four")
        val five = KtChild(5, "five")
        boxStore.boxFor(KtChild::class.java).put(one, two, three, four, five)

        existingParent.id = 1
        existingParent.integerOptionalField = 1
        existingParent.stringField = "hello"
        existingParent.toOne!!.target = one
        existingParent.toMany!!.add(one)
        existingParent.toMany!!.add(two)
        existingParent.toMany!!.add(three)
        var list = ArrayList<KtChild>()
        list.add(four)
        list.add(five)
        existingParent.list = list

        val serializer = BoxfitSerializer(boxStore)
        val json = JSONObject()
        json.put("id", 1)
        json.put("integerClass", (null as String?))
        json.put("string", (null as String?))
        json.put("toOne", (null as String?))
        json.put("toMany", (null as String?))
        json.put("list", (null as String?))

        serializer.fromJson(KtParent::class.java, json)

        val parent = serializer.fromJson(KtParent::class.java, json)
        Assert.assertNull(parent!!.integerOptionalField)
        Assert.assertNull(parent.stringField)
        Assert.assertNull(parent.toOne!!.target)
        Assert.assertEquals(0, parent.toMany!!.size.toLong())
        Assert.assertEquals(0, parent.list.size.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_serializeObjectThatNeedsConversion() {
        val serializer = BoxfitSerializer(boxStore)
        val json = JsonProvider.getJSONObject("parent.json")
        json!!.put("_id", 1)
        json.remove("id")
        val parent = serializer.fromJson(KtParent::class.java, json)
        Assert.assertEquals(1, parent!!.id)
        Assert.assertEquals("hello", parent.stringField)
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_serializeObjectFromJsonWithoutId() {
        val serializer = BoxfitSerializer(boxStore)
        val json = JSONObject()
        json.put("value", "sample")
        val child = serializer.fromJson(KtChild::class.java, json)
        Assert.assertEquals("sample", child!!.value)
    }
}