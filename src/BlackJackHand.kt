class BlackJackHand {
    private val hand = mutableListOf<Card>()
    private var handValue = 0
    var bust = false
    var isBlackJack = false

    fun addCard(card: Card): List<Card> {
        hand.add(card)
        calculateHandValue()
        return hand
    }
    fun removeCardForSplit(): Card{
        val cardForNewHand = hand.removeAt(hand.size -1)
        calculateHandValue()
        return cardForNewHand
    }
    fun getHandValue(): Int{
        return handValue
    }

    private fun calculateHandValue(){
        handValue = 0
        hand.forEach { card ->
            when {
                // Ace == 11
                card.rank == 1 -> handValue += 11
                // Face cards are == 10
                card.rank >= 10 -> {
                    handValue += 10
                }
                // all other cards is its pip value
                else -> handValue += card.rank

            }
        }
        // Hand Value of 21 == Blackjack
        if (handValue ==  21) isBlackJack = true
        // Hand Value over 21 and does not have an ACE, Hand == Bust
        if (handValue > 21 && !hasAce()) bust = true
        // Hand Value more that 21 but has ACE should assume player is using ACE as 1
        if(hasAce() && handValue > 21){
            // remove 10 to allow user to use Ace as 1
            handValue -= 10
        }
//        println("HandValue: $handValue")
        if(isBlackJack) println("This hand has BlackJack")
    }

    fun hasAce(): Boolean {
        return hand.find { it.rank == 1 } is Card
    }

    fun hasPair(): Boolean {
        if(hand.size == 1){
            println("Hit to get a second card in this hand")
            return false
        } else {
            // This only checks if the dealt hand has pairs by hard coding position of cards
            return hand[0].rank == hand[1].rank
        }
    }

    fun empty(): Boolean {
        return hand.isEmpty()
    }

    fun showCards(initDealerHand: Boolean, name: String){
        if (initDealerHand){
            println("=============")
            println("${name}' Hand")
            println("|*| ${hand.last()}")
            println("=============")
        } else {
            println("=============")
            println("${name} Hand")
            if(isBlackJack) println("BLACKJACK")
            println(hand.joinToString("\n"))
            println("=============")
        }
    }


}