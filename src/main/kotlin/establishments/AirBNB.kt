package establishments

import Establishment

class AirBNB(override val name: String, override val address: String, override val livableArea: Int, val owner : String,
             override val price: Int, override val amenities: Set<String>
)
    : Establishment() {

    private var booked : Boolean = false


    override fun book(){
        booked = true
    }

    fun isBooked() : Boolean{
        return this.booked
    }

}