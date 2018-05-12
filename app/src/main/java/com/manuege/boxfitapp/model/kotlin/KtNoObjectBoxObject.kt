package com.manuege.boxfitapp.model.kotlin

import com.manuege.boxfit.annotations.BoxfitClass
import com.manuege.boxfit.annotations.BoxfitField

@BoxfitClass
class KtNoObjectBoxObject {
    @BoxfitField
    var integer: Int = 0

    @BoxfitField
    var string: String? = null
}