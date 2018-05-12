package com.manuege.boxfitapp.retrofit

import com.manuege.boxfitapp.AbstractRetrofitTest
import org.junit.Assert
import org.junit.Test
import java.io.IOException

class KtRetrofitConverterFactoryTest: AbstractRetrofitTest() {

    @Test
    @Throws(IOException::class)
    fun canSerializeModel() {
        mockResponse(200, "parent.json")

        val response = service.ktModelDetail.execute()

        Assert.assertTrue(response.isSuccessful)
        val parent = response.body()

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
    }

    @Test
    @Throws(IOException::class)
    fun canSerializeListOfModel() {
        mockResponse(200, "parents_list.json")

        val response = service.ktModelList.execute()

        Assert.assertTrue(response.isSuccessful)
        val list = response.body()

        Assert.assertEquals(list!!.size.toLong(), 2)

        val parent = list[0]

        Assert.assertNotNull(parent)
        Assert.assertEquals(1, parent.id)
        Assert.assertEquals(2, parent.longOptionalField as Long)

        Assert.assertEquals(3, parent.integerField.toLong())
        Assert.assertEquals(4, (parent.integerOptionalField as Int).toLong())

        Assert.assertEquals(true, parent.boolField)
        Assert.assertEquals(false, parent.boolOptionalField)

        Assert.assertEquals(5.5, parent.doubleField, 0.0)
        Assert.assertEquals(6.6, parent.doubleOptionalField!!, 0.0)

        Assert.assertEquals("hello", parent.stringField)
        Assert.assertEquals("inferred", parent.serializerNameInferred)

        Assert.assertEquals(1, parent.toOne!!.target.id)
        Assert.assertEquals("one", parent.toOne!!.target.value)

        Assert.assertEquals(2, parent.toMany!!.size.toLong())
        Assert.assertEquals(2, parent.toMany!![0].id)
        Assert.assertEquals("two", parent.toMany!![0].value)

        Assert.assertEquals(2, parent.list.size.toLong())
        Assert.assertEquals(4, parent.list[0].id)
        Assert.assertEquals("four", parent.list[0].value)

        Assert.assertEquals(2, list[1].id)
    }
}