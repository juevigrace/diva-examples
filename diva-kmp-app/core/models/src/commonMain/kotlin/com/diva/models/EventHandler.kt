package com.diva.models

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface Event

abstract class EventHandler<E : Event> {
    protected val resultsInternal: MutableSharedFlow<ResponseType> = MutableSharedFlow()
    val results: SharedFlow<ResponseType> = resultsInternal.asSharedFlow()

    protected fun sendResult(result: ResponseType): Boolean {
        return resultsInternal.tryEmit(result)
    }

    abstract fun handle(event: E)
}
