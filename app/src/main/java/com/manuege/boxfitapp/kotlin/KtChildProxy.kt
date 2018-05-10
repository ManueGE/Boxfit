package com.manuege.boxfitapp.kotlin

object KtChildProxy {
    fun setId(obj: KtChild, value: Long) {
        obj.id = value
    }

    fun getId(obj: KtChild) : Long  {
        return obj.id
    }

    fun setValue(obj: KtChild, value: String) {
        obj.value = value
    }

    fun getValue(obj: KtChild): String {
        return obj.value
    }
}