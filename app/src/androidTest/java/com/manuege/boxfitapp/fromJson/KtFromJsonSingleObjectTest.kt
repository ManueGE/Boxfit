package com.manuege.boxfitapp.fromJson

import com.manuege.boxfit.BoxfitSerializer
import com.manuege.boxfitapp.AbstractObjectBoxTest
import com.manuege.boxfitapp.kotlin.KtParent
import com.manuege.boxfitapp.model.Parent
import com.manuege.boxfitapp.utils.JsonProvider
import org.junit.Assert
import org.junit.Test

class KtFromJsonSingleObjectTest : AbstractObjectBoxTest() {
    @Test
    @Throws(Exception::class)
    fun fromJson_importNewObject() {
        val obj = JsonProvider.getJSONObject("parent.json")
        val boxfitSerializer = BoxfitSerializer(boxStore)
        val parent = boxfitSerializer.fromJson(KtParent::class.java, obj)

        Assert.assertEquals(1, boxStore.boxFor(Parent::class.java).count())
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
        Assert.assertEquals(2, parent.toMany!![0].value)
        Assert.assertEquals("two", parent.toMany!![0].value)

        Assert.assertEquals(2, parent.list.size.toLong())
        Assert.assertEquals(4, parent.list[0].id)
        Assert.assertEquals("four", parent.list[0].value)
    }

        /*
        Assert.assertEquals(Parent.Enum.ONE, parent.enumField)

        Assert.assertEquals(117, parent.dateField.year.toLong())
        Assert.assertEquals(8, parent.dateField.month.toLong())
        Assert.assertEquals(17, parent.dateField.date.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_updateExistingObject() {
        val serializer = BoxfitSerializer(boxStore)
        val first_json = JsonProvider.getJSONObject("parent_2.json")
        serializer.fromJson(Parent::class.java, first_json)

        val second_json = JsonProvider.getJSONObject("parent.json")
        serializer.fromJson(Parent::class.java, second_json)

        val parent = serializer.fromJson(Parent::class.java, second_json)

        Assert.assertEquals(1, boxStore.boxFor(Parent::class.java).count())
        Assert.assertNotNull(parent)

        Assert.assertEquals(1, parent!!.id)
        Assert.assertEquals(2, parent.longClassField as Long)

        Assert.assertEquals(3, parent.integerField.toLong())
        Assert.assertEquals(4, (parent.integerClassField as Int).toLong())

        Assert.assertEquals(true, parent.boolField)
        Assert.assertEquals(false, parent.boolClassField)

        Assert.assertEquals(5.5, parent.doubleField, 0.0)
        Assert.assertEquals(6.6, parent.doubleClassField, 0.0)

        Assert.assertEquals("hello", parent.stringField)
        Assert.assertEquals("inferred", parent.serializerNameInferred)
        Assert.assertEquals("keyPath", parent.keyPathField)
        Assert.assertEquals("fakeKeyPath", parent.fakeKeyPathField)

        Assert.assertEquals(1, parent.toOne.target.id)
        Assert.assertEquals("one", parent.toOne.target.value)

        Assert.assertEquals(2, parent.toMany.size.toLong())
        Assert.assertEquals(2, parent.toMany[0].id)
        Assert.assertEquals("two", parent.toMany[0].value)

        Assert.assertEquals(2, parent.list.size.toLong())
        Assert.assertEquals(4, parent.list[0].id)
        Assert.assertEquals("four", parent.list[0].value)

        Assert.assertEquals(Parent.Enum.ONE, parent.enumField)

        Assert.assertEquals(117, parent.dateField.year.toLong())
        Assert.assertEquals(8, parent.dateField.month.toLong())
        Assert.assertEquals(17, parent.dateField.date.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_partialUpdateExistingObject() {
        val serializer = BoxfitSerializer(boxStore)
        val first_json = JsonProvider.getJSONObject("parent.json")
        serializer.fromJson(Parent::class.java, first_json)

        val second_json = JsonProvider.getJSONObject("partial_parent.json")
        val parent = serializer.fromJson(Parent::class.java, second_json)

        Assert.assertEquals(1, boxStore.boxFor(Parent::class.java).count())
        Assert.assertNotNull(parent)

        Assert.assertEquals(1, parent!!.id)
        Assert.assertEquals(20, parent.longClassField as Long)

        Assert.assertEquals(30, parent.integerField.toLong())
        Assert.assertEquals(4, (parent.integerClassField as Int).toLong())

        Assert.assertEquals(false, parent.boolField)
        Assert.assertEquals(false, parent.boolClassField)

        Assert.assertEquals(5.5, parent.doubleField, 0.0)
        Assert.assertEquals(60.6, parent.doubleClassField, 0.0)

        Assert.assertEquals("bye", parent.stringField)
        Assert.assertEquals("inferred", parent.serializerNameInferred)
        Assert.assertEquals("keyPath", parent.keyPathField)
        Assert.assertEquals("fakeKeyPath", parent.fakeKeyPathField)

        Assert.assertEquals(1, parent.toOne.target.id)
        Assert.assertEquals("one", parent.toOne.target.value)

        Assert.assertEquals(2, parent.toMany.size.toLong())
        Assert.assertEquals(2, parent.toMany[0].id)
        Assert.assertEquals("two", parent.toMany[0].value)

        Assert.assertEquals(2, parent.list.size.toLong())
        Assert.assertEquals(4, parent.list[0].id)
        Assert.assertEquals("four", parent.list[0].value)

        Assert.assertEquals(Parent.Enum.ONE, parent.enumField)

        Assert.assertEquals(117, parent.dateField.year.toLong())
        Assert.assertEquals(8, parent.dateField.month.toLong())
        Assert.assertEquals(17, parent.dateField.date.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_relationshipWithIds() {
        val serializer = BoxfitSerializer(boxStore)
        val json = JsonProvider.getJSONObject("parent_with_properties_with_id.json")
        val parent = serializer.fromJson(Parent::class.java, json)

        Assert.assertEquals(1, parent!!.toOne.target.id)
        Assert.assertNull(parent.toOne.target.value)

        Assert.assertEquals(2, parent.toMany.size.toLong())
        Assert.assertEquals(2, parent.toMany[0].id)
        Assert.assertNull(parent.toMany[0].value)
        Assert.assertEquals(3, parent.toMany[1].id)
        Assert.assertNull(parent.toMany[1].value)

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
        boxStore.boxFor(Child::class.java).put(
                Child(1, "one"),
                Child(2, "two"),
                Child(3, "three"),
                Child(4, "four"),
                Child(5, "five"),
                Child(6, "six")
        )

        val serializer = BoxfitSerializer(boxStore)
        val json = JsonProvider.getJSONObject("parent_with_properties_with_id.json")
        val parent = serializer.fromJson(Parent::class.java, json)

        Assert.assertEquals(1, parent!!.toOne.target.id)
        Assert.assertEquals("one", parent.toOne.target.value)

        Assert.assertEquals(2, parent.toMany.size.toLong())
        Assert.assertEquals(2, parent.toMany[0].id)
        Assert.assertEquals("two", parent.toMany[0].value)
        Assert.assertEquals(3, parent.toMany[1].id)
        Assert.assertEquals("three", parent.toMany[1].value)

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
        val parent = serializer.fromJson(Parent::class.java, json)

        Assert.assertEquals(1, parent!!.integerField.toLong())
        Assert.assertEquals("2", parent.stringField)
    }

    @Test
    @Throws(Exception::class)
    fun albumSerializer_overridesNullProperties() {

        val existingParent = Parent()
        boxStore.boxFor(Parent::class.java).put(existingParent)

        val one = Child(1, "one")
        val two = Child(2, "two")
        val three = Child(3, "three")
        val four = Child(4, "four")
        val five = Child(5, "five")
        boxStore.boxFor(Child::class.java).put(one, two, three, four, five)

        existingParent.id = 1
        existingParent.integerClassField = 1
        existingParent.stringField = "hello"
        existingParent.toOne.target = one
        existingParent.toMany.add(one)
        existingParent.toMany.add(two)
        existingParent.toMany.add(three)
        existingParent.list.add(four)
        existingParent.list.add(five)

        val serializer = BoxfitSerializer(boxStore)
        val json = JSONObject()
        json.put("id", 1)
        json.put("integerClass", null)
        json.put("string", null)
        json.put("toOne", null)
        json.put("toMany", null)
        json.put("list", null)

        serializer.fromJson(Parent::class.java, json)

        val parent = serializer.fromJson(Parent::class.java, json)
        Assert.assertNull(parent!!.integerClassField)
        Assert.assertNull(parent.stringField)
        Assert.assertNull(parent.toOne.target)
        Assert.assertEquals(0, parent.toMany.size.toLong())
        Assert.assertEquals(0, parent.list.size.toLong())
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_serializeObjectThatNeedsConversion() {
        val serializer = BoxfitSerializer(boxStore)
        val json = JsonProvider.getJSONObject("parent.json")
        json!!.put("_id", 1)
        json.remove("id")
        val parent = serializer.fromJson(Parent::class.java, json)
        Assert.assertEquals(1, parent!!.id)
        Assert.assertEquals("hello", parent.stringField)
    }

    @Test
    @Throws(Exception::class)
    fun fromJson_serializeObjectFromJsonWithoutId() {
        val serializer = BoxfitSerializer(boxStore)
        val json = JSONObject()
        json.put("value", "sample")
        val child = serializer.fromJson(Child::class.java, json)
        Assert.assertEquals("sample", child!!.value)
    }*/
}