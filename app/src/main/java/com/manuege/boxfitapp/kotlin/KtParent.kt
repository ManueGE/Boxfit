/*package com.manuege.boxfitapp.kotlin

import com.manuege.boxfit.annotations.BoxfitField
import com.manuege.boxfitapp.model.Child
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

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
    var toOne: ToOne<Child>? = null

    @BoxfitField
    var toMany: ToMany<Child>? = null

    @BoxfitField
    var list: MutableList<Child>? = null
}
*/