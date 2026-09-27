abstract class Establishment() : Bookable {
    abstract val name : String
    abstract val address : String
    abstract val livableArea: Int
    abstract val price : Int
    abstract val amenities : Set<String>
}
