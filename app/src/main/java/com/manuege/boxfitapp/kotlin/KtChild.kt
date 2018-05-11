package com.manuege.boxfitapp.kotlin

import com.manuege.boxfit.annotations.BoxfitClass
import com.manuege.boxfit.annotations.BoxfitField
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@BoxfitClass
@Entity
class KtChild {

    @BoxfitField
    @Id(assignable = true)
    var id : Long = 0

    @BoxfitField
    var value: String = ""

    constructor()
}