package com.sample.multiplatform

import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<State : Any, Action, Event, Navigation>(initialState: State) :
    BaseSharedViewModel<State, Action, Event>(initialState = initialState) {

    protected val consumableViewActions = Channel<Navigation>(capacity = Channel.CONFLATED)

    fun consumableViewActions() = consumableViewActions.receiveAsFlow()

}