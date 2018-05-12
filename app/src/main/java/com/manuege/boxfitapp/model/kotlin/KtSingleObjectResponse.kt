package com.manuege.boxfitapp.model.kotlin

import com.manuege.boxfit.annotations.BoxfitClass
import com.manuege.boxfit.annotations.BoxfitField

open class KtSingleObjectResponse<T> {
    @BoxfitField
    var data: T? = null
}

@BoxfitClass
class KtSingleParentResponse : KtSingleObjectResponse<KtParent>()

