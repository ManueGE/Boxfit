package com.manuege.boxfitapp.fromJson

import com.manuege.boxfit.BoxfitSerializer
import com.manuege.boxfit.utils.Json
import com.manuege.boxfitapp.AbstractObjectBoxTest
import com.manuege.boxfitapp.model.kotlin.KtChild
import com.manuege.boxfitapp.model.kotlin.KtParent
import com.manuege.boxfitapp.utils.JsonProvider
import junit.framework.Assert
import org.junit.Test

class KtFromJsonArraySerializerTest : AbstractObjectBoxTest(){
    @Test
    fun fromJsonArray_canSerialize() {
        val `object` = JsonProvider.getJSONObject("paginated_parent.json")
        val array = Json(`object`).getJSONArray("results")
        val serializer = BoxfitSerializer(boxStore)
        val objects = serializer.fromJson(KtParent::class.java, array)

        Assert.assertEquals(2, boxStore.boxFor(KtParent::class.java).count())
        Assert.assertEquals(6, boxStore.boxFor(KtChild::class.java).count())

        val parent = objects!![0]
        Assert.assertEquals("hello", parent.stringField)
    }
}