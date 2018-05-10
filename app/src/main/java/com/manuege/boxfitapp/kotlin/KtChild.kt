package com.manuege.boxfitapp.kotlin


class KtChild() {
    var id : Long = 0
    var value: String = ""

    constructor(id: Long, value: String) : this() {
        this.id = id
        this.value = value
    }
}