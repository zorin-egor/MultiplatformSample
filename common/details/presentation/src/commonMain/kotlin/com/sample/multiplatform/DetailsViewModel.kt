package com.sample.multiplatform

import com.adeo.kviewmodel.BaseSharedViewModel
import com.sample.multiplatform.di.Inject
import com.sample.multiplatform.mappers.mapTo
import com.sample.multiplatform.models.Details
import com.sample.multiplatform.models.DetailsAction
import com.sample.multiplatform.models.DetailsEvent
import com.sample.multiplatform.models.DetailsViewState
import com.sample.multiplatform.models.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel(val user: User) : BaseSharedViewModel<DetailsViewState, DetailsAction, DetailsEvent>(
    initialState = DetailsViewState(isCenterProgress = true)
) {

    private val detailsRepository: DetailsRepository = Inject.instance()
    private var detailsJob: Job? = null
    private var details: Details = user.mapTo()

    init {
        viewState = viewState.copy(details = details)
        getDetails(details)
    }

    private fun getDetails(details: Details) {
        detailsJob = viewModelScope.launch {

            val url = details.url
            if (url == null) {
                viewAction = DetailsAction.ShowError("Forbidden url")
                viewState = viewState.copy(isCenterProgress = false)
                return@launch
            }

            val result = detailsRepository.getUserDetails(url)
            this@DetailsViewModel.details = result
            viewState = viewState.copy(details = result, isCenterProgress = false)
        }
    }

    override fun obtainEvent(viewEvent: DetailsEvent) {
        when (viewEvent) {
            else -> {}
        }
    }

}