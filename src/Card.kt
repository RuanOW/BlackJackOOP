data class Card (val rank: Int, val suit: Int){
    override fun toString(): String {
        val rankName = when(rank){
            1 -> "Ace"
            11 -> "Jack"
            12 -> "Queen"
            13 -> "King"
            else -> rank.toString()
        }
        val suitName = when(suit){
            1 -> "Hearts"
            2 -> "Diamonds"
            3 -> "Spades"
            4 -> "Clubs"
            else -> ""
        }
        return "$rankName of $suitName"
    }
}