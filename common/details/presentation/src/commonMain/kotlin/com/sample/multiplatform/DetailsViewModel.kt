package com.sample.multiplatform

import com.adeo.kviewmodel.BaseSharedViewModel
import com.sample.multiplatform.di.Inject
import com.sample.multiplatform.models.DetailsAction
import com.sample.multiplatform.models.DetailsEvent
import com.sample.multiplatform.models.DetailsModel
import com.sample.multiplatform.models.DetailsViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel : BaseSharedViewModel<DetailsViewState, DetailsAction, DetailsEvent>(
    initialState = DetailsViewState(isCenterProgress = true)
) {

    private val detailsRepository: DetailsRepository = Inject.instance()

    private var detailsJob: Job? = null
    private var details: DetailsModel? = null

    fun getDetails(id: Long, url: String) {
        detailsJob = viewModelScope.launch {
            try {
                val result = detailsRepository.getUserDetails(id, url)
                this@DetailsViewModel.details = result
                viewState = viewState.copy(details = result, isCenterProgress = false)
            } catch (e: Exception) {
                viewState = viewState.copy(isCenterProgress = false)
                viewAction = DetailsAction.ShowError(e.message ?: "Unknown error")
            }
        }
    }

    override fun obtainEvent(viewEvent: DetailsEvent) {
        when (viewEvent) {
            else -> {}
        }
    }

}