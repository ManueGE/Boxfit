package com.manuege.boxfitapp.model.kotlin

object KtPaginatedParentResponseKtBridgess {
    fun getCount(obj: KtPaginatedParentResponse): Int = obj.count

    fun setCount(obj: KtPaginatedParentResponse, value: Int) {
        obj.count = value
    }

    fun getNext(obj: KtPaginatedParentResponse): Int = obj.next

    fun setNext(obj: KtPaginatedParentResponse, value: Int) {
        obj.next = value
    }

    fun getPrevious(obj: KtPaginatedParentResponse): Int = obj.previous

    fun setPrevious(obj: KtPaginatedParentResponse, value: Int) {
        obj.previous = value
    }

    fun getResults(obj: KtPaginatedParentResponse): Any = obj.results
}
