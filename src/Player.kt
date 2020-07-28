class Player(val name: String) {
    // Could make the hand a two dimensional array for split hands.
    var hand = BlackJackHand()
    var isSecondHand = false
    var cash = 1000000f
    var betPlayed = 0f
    var active = true
    val dealer = name == "DEALER"

    fun addCardToHand(card: Card) {
        hand.addCard(card)
    }

    fun placeBet(bet: Float) {
        if(hand.empty()) {
            cash =- bet
            betPlayed = bet
        }
    }

    fun hasBlackJack(): Boolean {
        return hand.isBlackJack
    }

    fun hit(card: Card){
        addCardToHand(card)
        if (hand.bust){
            println("${name} HAND BUSTED")
            active = false
        }
    }

    fun stand(){
        active = false
    }

    fun playerOptions() {
        println(
                """
                    1 -> Hit
                    2 -> Stand
                    ${if(hand.hasPair())  "3 -> Split" else ""}
                """.trimIndent()
        )
    }


}