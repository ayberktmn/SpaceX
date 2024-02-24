package com.ayberk.spacex.usecase

import com.ayberk.spacex.data.retrofit.RetrofitRepository
import javax.inject.Inject


class ClearRoom @Inject constructor(
    private val retrofitRepository: RetrofitRepository
) {
    suspend operator fun invoke() {
        retrofitRepository.clearRoom()
    }
}