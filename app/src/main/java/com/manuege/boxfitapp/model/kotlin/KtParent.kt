package com.manuege.boxfitapp.model.kotlin

import com.manuege.boxfit.annotations.BoxfitClass
import com.manuege.boxfit.annotations.BoxfitField
import com.manuege.boxfit.annotations.FromJsonIgnoreNull
import com.manuege.boxfitapp.model.java.Parent
import com.manuege.boxfitapp.transformers.ApiStringToDateTransformer
import com.manuege.boxfitapp.transformers.EnumToIntTransformer
import com.manuege.boxfitapp.transformers.ListIntToStringTransformer
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import java.util.*
import kotlin.collections.ArrayList

@BoxfitClass
@Entity
class KtParent() {

    @BoxfitField("id")
    @Id(assignable = true)
    var id: Long = 0

    @BoxfitField("long_class")
    var longOptionalField: Long? = null

    @BoxfitField("integer")
    var integerField: Int = 0

    @BoxfitField("integer_class")
    var integerOptionalField: Int? = null

    @BoxfitField("bool")
    var boolField: Boolean = false

    @BoxfitField("bool_class")
    var boolOptionalField: Boolean? = null

    @BoxfitField("double")
    var doubleField: Double = 0.toDouble()

    @BoxfitField("double_class")
    var doubleOptionalField: Double? = null

    @BoxfitField("string")
    var stringField: String? = null

    @BoxfitField
    var serializerNameInferred: String? = null

    @BoxfitField("first.second.third.key")
    var keyPathField: String? = null

    @BoxfitField("a.b.c.d")
    var fakeKeyPathField: String? = null

    @BoxfitField
    var toOne: ToOne<KtChild>? = null

    @BoxfitField
    var toMany: ToMany<KtChild>? = null

    @BoxfitField
    lateinit var list: List<KtChild>

    @BoxfitField(value = "enum", transformer = EnumToIntTransformer::class)
    @Convert(converter = EnumToIntTransformer::class, dbType = Int::class)
    var enumField: Parent.Enum? = null

    @BoxfitField(value = "date", transformer = ApiStringToDateTransformer::class)
    var dateField: Date? = null

    @BoxfitField
    @FromJsonIgnoreNull
    var fromJsonIgnoreNull: Int = 0

    @Convert(converter = ListIntToStringTransformer::class, dbType = String::class)
    @BoxfitField(transformer = ListIntToStringTransformer::class)
    var listInt: ArrayList<Int> = ArrayList()
}
