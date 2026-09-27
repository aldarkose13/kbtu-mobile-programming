class User(val username: String, val phone: String) {
    private var balance = 0
    fun setBalance(amount : Int){
        this.balance = amount
    }
    fun getBalance(): Int{
        return this.balance
    }
    fun addBalance(amount : Int){
        this.balance += amount
    }
    fun withdrawBalance(amount : Int){
        this.balance -= amount
    }


}