package com.manuege.boxfitapp.model.kotlin

import com.manuege.boxfit.annotations.BoxfitField
import com.manuege.boxfitapp.model.java.Parent
import java.util.*

open class KtPaginatedResponse<T> {
    @BoxfitField
    var count: Int = 0
        internal set

    @BoxfitField
    var next: Int = 0
        internal set

    @BoxfitField
    var previous: Int = 0
        internal set

    @BoxfitField
    var results: List<T> = ArrayList()
        internal set
}

//@BoxfitClass
open class KtPaginatedParentResponse : KtPaginatedResponse<Parent>()

//@BoxfitClass
class KtPaginatedParentResponseSubclass : KtPaginatedParentResponse()

