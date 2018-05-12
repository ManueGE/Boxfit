package com.manuege.boxfitapp.model.kotlin

import com.manuege.boxfit.annotations.BoxfitField
import com.manuege.boxfitapp.model.java.Parent

open class KtSingleObjectResponse<T> {
    @BoxfitField
    var data: T? = null
}

//@BoxfitClass
class KtSingleParentResponse : KtSingleObjectResponse<Parent>()

