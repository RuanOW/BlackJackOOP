val blackjackSign =
        """
    ______  _               _       ___               _    
    | ___ \| |             | |     |_  |             | |   
    | |_/ /| |  __ _   ___ | | __    | |  __ _   ___ | | __
    | ___ \| | / _` | / __|| |/ /    | | / _` | / __|| |/ /
    | |_/ /| || (_| || (__ |   < /\__/ /| (_| || (__ |   < 
    \____/ |_| \__,_| \___||_|\_\\____/  \__,_| \___||_|\_\

""".trimIndent()

val gameOverSign =
        """
       _____            __  __  ______    ____ __      __ ______  _____  
      / ____|    /\    |  \/  ||  ____|  / __ \\ \    / /|  ____||  __ \ 
     | |  __    /  \   | \  / || |__    | |  | |\ \  / / | |__   | |__) |
     | | |_ |  / /\ \  | |\/| ||  __|   | |  | | \ \/ /  |  __|  |  _  / 
     | |__| | / ____ \ | |  | || |____  | |__| |  \  /   | |____ | | \ \ 
      \_____|/_/    \_\|_|  |_||______|  \____/    \/    |______||_|  \_\
""".trimIndent()

fun shuffleLoading(){
    for (shuff in 1..3){
        val shuffleTime = 200L
        for (show in 0..3){
            print("Shuffling $shuff " + ".".repeat(show))
            Thread.sleep(shuffleTime)
            print("\r")
        }
    }
}

// A function that helps with taking correct inputs from user
fun takeGameInput(context: Game, message:String): String{
    println(message)
    val userInput = readLine().toString()
    var value: String = ""
    when{
        userInput in listOf("q", "quite", "exit") -> {
            println("quiting game!")
            context.gameOver = true
        }
        userInput.isEmpty() -> {
            takeGameInput(context, message)
        }
        else -> {
            value = userInput
        }
    }
    return value
}