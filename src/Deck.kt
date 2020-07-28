class Deck {
    val deck =  mutableListOf<Card>()
    init{
        for(rank in 1..13){
            for (suit in 1..4){
                deck.add(Card(rank, suit))
            }
        }
    }

    fun show(){
        for (card in deck){
            println(card.toString())
        }
    }

    fun shuffleCards(){
        deck.shuffle()
    }

    fun draw() = deck.removeAt(deck.size-1)

}