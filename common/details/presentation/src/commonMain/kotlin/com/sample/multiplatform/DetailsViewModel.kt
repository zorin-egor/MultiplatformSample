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

class DetailsViewModel : BaseSharedViewModel<DetailsViewState, DetailsAction, DetailsEvent>(
    initialState = DetailsViewState(isCenterProgress = true)
) {

    private val detailsRepository: DetailsRepository = Inject.instance()
    private val usersRepository: UsersRepository = Inject.instance()

    private var detailsJob: Job? = null
    private var details: Details? = null

    private fun getDetails(url: String) {
        detailsJob = viewModelScope.launch {
            try {
                val result = detailsRepository.getUserDetails(url)
                this@DetailsViewModel.details = result
                viewState = viewState.copy(details = result, isCenterProgress = false)
            } catch (e: Exception) {
                viewState = viewState.copy(isCenterProgress = false)
                viewAction = DetailsAction.ShowError(e.message ?: "Unknown error")
            }
        }
    }

    fun setUser(item: User) {
        val temp = item.mapTo()
        details = temp

        val url = temp.url
        if (url == null) {
            viewAction = DetailsAction.ShowError("Error state")
        } else {
            getDetails(url)
        }

        viewState = viewState.copy(details = temp, isCenterProgress = false)
    }

    override fun obtainEvent(viewEvent: DetailsEvent) {
        when (viewEvent) {
            else -> {}
        }
    }

}