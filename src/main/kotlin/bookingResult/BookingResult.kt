package bookingResult

enum class BookingStatus {
    SUCCESS,
    ERROR
}

sealed class BookingResult(val status: BookingStatus, val message: String) {
    class BookingSuccess: BookingResult(BookingStatus.SUCCESS,"Booking successful")
    class BookingError: BookingResult(BookingStatus.ERROR,"Error while Booking")
}