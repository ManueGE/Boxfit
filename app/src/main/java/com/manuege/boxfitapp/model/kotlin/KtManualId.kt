package com.manuege.boxfitapp.model.kotlin

import com.manuege.boxfit.annotations.BoxfitClass
import com.manuege.boxfit.annotations.BoxfitField
import com.manuege.boxfit.annotations.BoxfitId
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
@BoxfitClass
class KtManualId() {

    @Id
    var id: Long = 0

    @BoxfitId
    @BoxfitField("id")
    var manualId: String = ""

    @BoxfitField
    var value: String = ""
}