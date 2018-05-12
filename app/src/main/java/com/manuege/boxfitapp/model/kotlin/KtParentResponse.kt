package com.manuege.boxfitapp.model.kotlin

import com.manuege.boxfit.annotations.BoxfitClass
import com.manuege.boxfit.annotations.BoxfitField
import com.manuege.boxfitapp.model.java.Parent

@BoxfitClass
class KtParentResponse {
    @BoxfitField
    var parent: Parent? = null
}