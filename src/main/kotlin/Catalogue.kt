import bookingResult.BookingResult
import kotlinx.coroutines.delay

import kotlin.random.Random
import java.time.LocalDate
import java.time.format.DateTimeFormatter
class Catalogue(private val establishments : List<Establishment>, private val user : User) {
    private val bookings: MutableMap<Int, Booking> = mutableMapOf()


    // As a reusable property
    private val currentDateString: String
        get() = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))


    fun showCatalogue(){
        println("Available Establishments")
        for (i in establishments.indices){
            print(i+1)
            print(" "+ establishments[i].name)
            println()
        }
    }
    suspend fun makeBooking(establishment: Establishment?) : BookingResult{
        if (establishment == null){
            return BookingResult.BookingError()
        }
        val result : BookingResult
        if (user.getBalance() < establishment.price){
            result = BookingResult.BookingError()
        }
        else{
            delay(1000)
            user.withdrawBalance(establishment.price)
            establishment.book()
            val booking = Booking( Random.nextInt(1,100),user, establishment, currentDateString)
            bookings[booking.id] = booking
            result = BookingResult.BookingSuccess()
        }
        return result
    }
    fun getBookings():Map<Int, Booking>{
        return this.bookings
    }
    fun printBookingsForUser(searchUser: User){
        val bookingObjects = bookings.map {it.value}
        for(booking in bookingObjects ){
            if (searchUser.phone == booking.user.phone){
                println("${booking.user.username} booked ${booking.establishment.name} for for the date ${booking.date}")
            }
        }
    }

    fun showEstablishmentsCheaperThan(price: Int){
        val filteredEstablishments = this.establishments.filter { it.price<= price}
        for (i in filteredEstablishments.indices){
            println("${i+1} ${filteredEstablishments[i].name}")
        }
    }

    fun getTotalCatalougePrices(): Int{
        return this.establishments.map { it.price }.reduce{totalPrice, price -> totalPrice + price}
    }

    fun search(
        condition: (Establishment) -> Boolean
    ): List<Establishment> {
        return establishments.filter(condition)
    }

    fun getEstablishmentByName(estName: String) : Establishment?{
        for (est in this.establishments){
            if (est.name == estName){
                return est
            }
        }
        return null
    }

}