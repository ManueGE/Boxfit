package com.manuege.boxfitapp.model.kotlin

import com.manuege.boxfit.annotations.BoxfitClass
import com.manuege.boxfit.annotations.BoxfitField
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@BoxfitClass
@Entity
class KtChild() {

    @BoxfitField
    @Id(assignable = true)
    var id : Long = 0

    @BoxfitField
    var value: String? = null

    constructor(id: Long, value: String?) : this() {
        this.id = id
        this.value = value
    }
}