package com.link.worldwidenews.domain.usecase.base

interface SingleUseCaseCallback<T> {
    fun onSuccess(response: T)
    fun onError(throwable: Throwable)
}