package com.jeflette.newsapi.util

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val result = query().first()
    val flow = if (shouldFetch(result)) {
        emit(Resource.Loading(result))
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(it, throwable) }
        }
    } else {
        query().map { Resource.Success(it) }
    }
    emitAll(flow)
}
