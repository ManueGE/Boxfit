package com.manuege.boxfitapp.model.kotlin

import com.manuege.boxfit.annotations.BoxfitClass
import com.manuege.boxfit.annotations.BoxfitField
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

@BoxfitClass
open class KtPaginatedParentResponse : KtPaginatedResponse<KtParent>()

@BoxfitClass
class KtPaginatedParentResponseSubclass : KtPaginatedParentResponse()

