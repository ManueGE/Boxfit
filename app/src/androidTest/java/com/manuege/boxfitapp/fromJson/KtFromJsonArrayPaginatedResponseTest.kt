package com.manuege.boxfitapp.fromJson

import com.manuege.boxfit.BoxfitSerializer
import com.manuege.boxfitapp.AbstractObjectBoxTest
import com.manuege.boxfitapp.model.kotlin.KtChild
import com.manuege.boxfitapp.model.kotlin.KtPaginatedParentResponse
import com.manuege.boxfitapp.model.kotlin.KtPaginatedParentResponseSubclass
import com.manuege.boxfitapp.model.kotlin.KtParent
import com.manuege.boxfitapp.utils.JsonProvider
import org.junit.Assert
import org.junit.Test

class KtFromJsonArrayPaginatedResponseTest : AbstractObjectBoxTest() {
    @Test
    fun fromJsonPaginatedResponse_canSerialize() {
        val jsonObject = JsonProvider.getJSONObject("paginated_parent.json")
        val serializer = BoxfitSerializer(boxStore)
        val `object` = serializer.fromJson(KtPaginatedParentResponse::class.java, jsonObject)

        Assert.assertEquals(10, `object`!!.count.toLong())
        Assert.assertEquals(0, `object`.previous.toLong())
        Assert.assertEquals(2, `object`.next.toLong())

        Assert.assertEquals(2, boxStore.boxFor(KtParent::class.java).count())
        Assert.assertEquals(6, boxStore.boxFor(KtChild::class.java).count())

        val parent = `object`.results[0]
        Assert.assertEquals("hello", parent.stringField)
    }

    @Test
    fun fromJsonPaginatedResponse_canSerializeSubclassOfGeneric() {
        val jsonObject = JsonProvider.getJSONObject("paginated_parent.json")
        val serializer = BoxfitSerializer(boxStore)
        val obj = serializer.fromJson(KtPaginatedParentResponseSubclass::class.java, jsonObject)

        Assert.assertEquals(10, obj!!.count.toLong())
        Assert.assertEquals(0, obj.previous.toLong())
        Assert.assertEquals(2, obj.next.toLong())

        Assert.assertEquals(2, boxStore.boxFor(KtParent::class.java).count())
        Assert.assertEquals(6, boxStore.boxFor(KtChild::class.java).count())

        val parent = obj.results[0]
        Assert.assertEquals("hello", parent.stringField)
    }
}