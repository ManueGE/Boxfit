package com.manuege.boxfitapp.model.kotlin

import com.manuege.boxfit.annotations.*
import com.manuege.boxfitapp.model.java.Child
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
@BoxfitClass
class KtToJsonTestObject {

    @Id
    var id: Long = 0

    @BoxfitField("long_class")
    @ToJsonIncludeNull
    var longClassField: Long? = null

    @BoxfitField("integer_class")
    @ToJsonIncludeNull
    var integerClassField: Int? = null

    @BoxfitField("bool_class")
    @ToJsonIncludeNull
    var boolClassField: Boolean? = null

    @BoxfitField("double_class")
    @ToJsonIncludeNull
    var doubleClassField: Double? = null

    @BoxfitField("string")
    @ToJsonIncludeNull
    var stringField: String? = null

    @ToJsonIncludeNull
    @BoxfitField
    var toOne: ToOne<KtChild>? = null

    @BoxfitField("ignored")
    @ToJsonIgnore
    var ignoredField: String? = null

    @BoxfitField
    @ToJsonAsId
    var toOneAsId: ToOne<Child>? = null

    @ToJsonAsId
    @BoxfitField
    var toManyAsId: ToMany<KtChild>? = null
}