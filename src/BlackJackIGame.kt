import java.lang.Exception

class BlackJackGame: Game() {
    val name = "BlackJack"
    val deck: Deck = Deck()
    val players = mutableListOf<Player>()
//    override var gameOver = false
    var amountOfPlayers: Int = 0

    init {
        while (!gameOver){
            // Set amount of players that will be playing
            checkPlayerCount(takeGameInput(this,"How many players will be playing?"))
            if (amountOfPlayers == 0) continue

            // Set game up with players
            setupGame()

            // Start the game
            play()
        }
    }

    fun checkPlayerCount(count: String) {
        val playerCount = count.toIntOrNull() ?: return

        if (playerCount > 6){
            takeGameInput(this,"Players can't be more than 6")
        } else if (playerCount < 0) {
            takeGameInput(this,"Players can't less than 0")
        } else {
            println("$count players will be playing")
            amountOfPlayers = playerCount
        }
    }

    private fun addPlayer(player: Player) {
        players.add(player)
    }

    private fun setupGame(){
        var addedPlayer = 0
        while(addedPlayer < amountOfPlayers) {
            val playerName = takeGameInput(this,"Please add a Player Name:")
            val newPlayer = Player(playerName)
            val playerInitialBet = takeGameInput(this,"How much will you be betting")
            if (playerInitialBet.toFloatOrNull() == null){
                println("Please place a valid bet")
                continue
            }

            addedPlayer++
            newPlayer.placeBet(playerInitialBet.toFloat())
            this.addPlayer(newPlayer)
        }
        println("Game setup complete")

        shuffleLoading()

        deck.shuffleCards()
        // Insert DEALER
        players.add(0, Player("DEALER"))
        println("Dealing cards")
        for (player in players){
            for (i in 1..2){
                val card = deck.draw()
                player.addCardToHand(card)
            }
        }
    }

    fun play(){
        println("Game Started!!")
        var seat = 1
        val dealer: Player = players.find { it.name == "DEALER" } ?: throw Exception("There's no DEALER")
        dealer.hand.showCards(true, dealer.name)

        move@while (!gameOver){
            if(seat >= players.size){
                evaluateGame()
                gameOver = true
                break@move
            }
            val currentPlayer = players[seat]
            if (currentPlayer.dealer){
                println("DEALER TURN")
                // Dealer comes last -> evaluate and end game
                evaluateGame()
                gameOver = true
                break
            }
            // Go to next player if this player is out of the game
            if (!currentPlayer.active) {
                currentPlayer.hand.showCards(false, currentPlayer.name)
                seat++
                continue
            }
            // Move to the next player if current player has BlackJack
            // Game will be evaluated at the end
            if(currentPlayer.hasBlackJack()) {
                currentPlayer.active = false
                seat++
                continue
            }

            currentPlayer.hand.showCards(false, currentPlayer.name)
            currentPlayer.playerOptions()
            when(takeGameInput(this, "Your move...")){
                "1" -> {
                    println("HIT WAS PLAYED")
                    currentPlayer.hit(deck.draw())
                    continue@move
                }
                "2" -> {
                    seat++
                    currentPlayer.stand()
                    continue@move
                }
                "3" -> {
                    // Create a second player as second hand and play firsthand
                    // A Player ID can be used to join winnings at the end into one user for a second game
                    val secondHandOfPlayer = Player("${currentPlayer.name} second")
                            .apply {
                                isSecondHand = true
                                betPlayed = currentPlayer.betPlayed
                                addCardToHand(currentPlayer.hand.removeCardForSplit())
                            }

                    players.add(seat.plus(1), secondHandOfPlayer)
                    continue@move
                }
                else -> {
                    continue@move
                }
            }

        }
    }

    private fun evaluateGame(){
        val dealer = players[0]
        // Dealer must draw cards until 16 and stand on 17
        while (dealer.hand.getHandValue() < 16){
            dealer.hit(deck.draw())
        }
        // Game Over
        println(gameOverSign)
        dealer.hand.showCards(false, dealer.name)


        // Get results for each player in the game.
        for (player in players){
            if (player.dealer) continue
            when {
                player.hand.bust -> {
                    println("${player.name} BUSTED!!")
                    println("${player.name} Lost a bet of R${player.betPlayed}")
                }
                player.hand.getHandValue() > dealer.hand.getHandValue() -> {
                    println("${player.name} WINS!!")
                    println("${player.name} wins R${player.betPlayed}")
                }
                !player.hand.bust && dealer.hand.bust -> {
                    println("${player.name} WINS!!")
                    println("${player.name} wins R${player.betPlayed}")
                }
                player.hand.getHandValue() == dealer.hand.getHandValue() -> {
                    print("It was a DRAW!! or a PUSH!!")
                    println("${player.name} gets back R${player.betPlayed}")
                }
                else -> {
                    // If player did not bust but the hand value was less that the dealer

                    println("${player.name} Lost a bet of R${player.betPlayed}")
                }
            }
            player.hand.showCards(false, player.name)
        }
    }




}