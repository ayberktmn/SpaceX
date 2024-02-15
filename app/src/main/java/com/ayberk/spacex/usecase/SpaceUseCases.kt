package com.ayberk.spacex.usecase

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
data class SpaceUseCases @Inject constructor(
    val upsertRocket: UpsertRocket
)