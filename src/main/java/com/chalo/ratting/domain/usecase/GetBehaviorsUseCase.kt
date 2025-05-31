package com.chalo.ratting.domain.usecase

import com.chalo.core.model.base.ApiResult
import com.chalo.ratting.data.toBehavior
import com.chalo.ratting.domain.model.Behaviour
import com.chalo.ratting.domain.repo.RatingRepository

class GetBehaviorsUseCase(
    private val repository: RatingRepository
) {
    suspend operator fun invoke(): ApiResult<Map<Int, List<Behaviour>>> {
        return when (val result = repository.getDriverBehaviors()) {
            is ApiResult.Success -> {
                val mapped = result.data
                    ?.mapKeys { it.key.toInt() }
                    ?.mapValues { entry ->
                        entry.value.map { it.toBehavior() }
                    }.orEmpty()

                ApiResult.Success(mapped)
            }

            is ApiResult.Error -> {
                ApiResult.Error(result.message)
            }
        }
    }
}
