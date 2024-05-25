package org.example

import kotlin.random.Random

class GameManager {
    private val player = Player()
    private val firstCreature = DataRepository.createCreature(Random.nextInt(1, 45))
    private val firstHabitat = Habitat(firstCreature!!)
    private val zoo = Zoo(firstHabitat)
    private var daysRunning = 0
    private var isRunning = true

    fun startGame() {
        println("Welcome to ZooKing! You are the king of a brand new Zoo. Here's a starter creature just for you!")
        zoo.getResidents().forEach { habitat -> habitat.getStats() }
        while (isRunning) {
            var actionsLeft = 8 + (-1..1).random()
            while (daysRunning < 10) {
                actionsLeft = 8 + (-1..1).random()
                while (actionsLeft > 0) {
                    zoo.manage(actionsLeft)
                    println("Skip ahead?")
                    val choice = readlnOrNull()
                    if (choice == "yes") {actionsLeft = 1; daysRunning = 9}
                    actionsLeft--
                }
                zoo.finishDay()
                daysRunning++
            }
            if (daysRunning == 10) {
                println("\nWhat's this? A boy in a red cap approaches you. He wants to fight his animals against yours to become King of the Zoo! I hope you're prepared!")
                if (battle(player, Challenger())) {
                    println("You've successfully beaten a child! I hope you're happy with yourself.")
                } else {
                    println("You just lost to a child. Now a child is in charge of the zoo. Way to go.")
                    isRunning = false
                    break
                }
            }
            else {
                val combatOdds = Random.nextInt(0, daysRunning)
                if (combatOdds > 7) {
                    if (battle(player, Challenger())) {
                        println("You've successfully defended your Zoo!")
                    } else {
                        println("You've lost your crown as the ZooKing.")
                        isRunning = false
                    }
                }
                while (actionsLeft > 0) {
                    zoo.manage(actionsLeft)
                    actionsLeft--
                }
            }
            zoo.finishDay()
            daysRunning++
        }
        println("\n\nGAME OVER")
    }

    private fun battle(player: Player, challenger: Challenger) : Boolean {
        println("\nA challenger approaches to try and become the ZooKing!")
        player.prepBattle(zoo.getResidents())
        var combatCreature = Creature()
        var challengerCreature = Creature()
        println("Choose your combatant!")
        while (player.creaturesAlive() > 0 && challenger.creaturesAlive() > 0) {
            if (combatCreature.getHealth() == 0)
                player.listCreatures()
            if (challengerCreature.getHealth() == 0)
                challengerCreature = challenger.getCreature(Random.nextInt(0, challenger.creaturesAlive()))
            val choice = readlnOrNull()?.toIntOrNull()
            if (choice != null) {
                if (choice <= player.getNumCreatures()) {
                    combatCreature = player.getCreature(choice - 1)
                    println("You've chosen ${combatCreature.getName()}! Your opponent sends out ${challengerCreature.getName()}")
                    // Fight!
                    while (combatCreature.getHealth() > 0 && challengerCreature.getHealth() > 0) {
                        combat(combatCreature, challengerCreature)
                        combatCreature.endTurnStatusEffects()
                        challengerCreature.endTurnStatusEffects()
                    }
                }
            }
        }
        return player.creaturesAlive() > 0
    }


    private fun combat(playerCreature: Creature, challengerCreature: Creature) {
        println("What would you like to do?")
        println("1. Attack")
        println("2. Use Item")
        println("3. Surrender")
        var choice = readlnOrNull()?.toIntOrNull()
        when (choice) {
            1 -> {
                println("Your ${playerCreature.getName()} can use these abilities:")
                playerCreature.getAbilities()
                choice = readlnOrNull()?.toIntOrNull()
                if (choice != null) {
                    if (choice in 1..3){
                        // Player Attack
                        if (playerCreature.useAbility(choice - 1).getDamage() > 0)
                            // Positive Damage
                            playerCreature.addXP(playerCreature.useAbility(choice - 1).use(playerCreature, challengerCreature))
                        else if (playerCreature.useAbility(choice - 1).getDamage() < 0)
                            // Healing
                            playerCreature.useAbility(choice - 1).use(playerCreature, playerCreature)
                    }
                    // Challenger Attack
                    if (challengerCreature.getHealth() > 0) {
                        val challengerAttack: Int = Random.nextInt(0, 2)
                        if (challengerCreature.useAbility(challengerAttack).getDamage() > 0)
                        // Positive Damage
                            challengerCreature.addXP(
                                challengerCreature.useAbility(choice).use(challengerCreature, playerCreature)
                            )
                        else if (challengerCreature.useAbility(challengerAttack).getDamage() < 0)
                        // Healing
                            challengerCreature.useAbility(choice).use(challengerCreature, challengerCreature)
                    }
                }
            }
            2 -> {
                println("Your inventory contains:")
                player.listItems()
                println("Yeah I haven't really implemented this, so you've lost your turn. Sucks.")
            }
            3 -> {
                println("You give up. You lose. Why did you do that?\n")
                playerCreature.setHealth(0)
            }
        }
    }
}