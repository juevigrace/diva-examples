package com.diva.models

import com.diva.models.api.PaginationResponse

data class Pagination<T>(
    val items: List<T>,
    val totalItems: Int,
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int
) {
    companion object {
        fun<T> fromResponse(response: PaginationResponse<T>): Pagination<T> {
            return Pagination(
                items = response.items,
                totalItems = response.totalItems,
                totalPages = response.totalPages,
                currentPage = response.currentPage,
                pageSize = response.pageSize
            )
        }
    }
}
