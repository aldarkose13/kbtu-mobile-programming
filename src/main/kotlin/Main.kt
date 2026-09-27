import establishments.AirBNB
import establishments.Hotel
import kotlinx.coroutines.runBlocking


fun main (){
    val user = User(
        username = "Admin",
        phone = "707"
    )
    user.setBalance(200)
    val hotel = Hotel(
        name = "Grand Hotel",
        address = "123 Main Street",
        livableArea = 150,
        price = 100,
        amenities = setOf("Wifi", "Pool", "Gym")
    )
    val hotel2 = Hotel(
        name = "Mini Hotel",
        address = "122 Auxiliary Street",
        livableArea = 20,
        price = 50,
        amenities = setOf("Wifi", "Parking")
    )

    val airbnb = AirBNB(
        name = "Cozy Apartment",
        address = "456 Central Street",
        livableArea = 70,
        owner = "John Doe",
        price = 75,
        amenities = setOf("Wifi", "Parking", "Kitchen")
    )
    val establishments = listOf(hotel, hotel2, airbnb)
    val catalogue = Catalogue(establishments = establishments, user = user)
    println("Total price of all establishments:${catalogue.getTotalCatalougePrices()}")
    catalogue.showEstablishmentsCheaperThan(80)
    runBlocking {
        val res1 = catalogue.makeBooking(catalogue.getEstablishmentByName("Mini Hotel"))
        println(res1.message)
        val res2 = catalogue.makeBooking(catalogue.getEstablishmentByName("Cozy Apartment"))
        println(res2.message)
    }
    catalogue.printBookingsForUser(user)
    val largeEstablishments = catalogue.search { it.livableArea> 70 }
    for (es in largeEstablishments.map { it.name }){
        println("Larger establishments: $es with area greater than 70")
    }

}