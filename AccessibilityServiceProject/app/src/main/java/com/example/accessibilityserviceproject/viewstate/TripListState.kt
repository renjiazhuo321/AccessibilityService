package com.example.accessibilityserviceproject.viewstate

data class TripListState(
    val isLoading: Boolean,
    val throwable: Throwable?,
    val data:Any?
) {
    companion object {

        fun initial(): TripListState {
            return TripListState(
                isLoading = false,
                throwable = null,
                data = null
            )
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TripListState

        if (isLoading != other.isLoading) return false
        if (throwable != other.throwable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + (throwable?.hashCode() ?: 0)
        return result
    }
}