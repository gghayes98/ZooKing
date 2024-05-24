package org.example

import javax.xml.crypto.Data
import kotlin.random.Random

class Zoo(firstHabitat: Habitat) {
    private val habitats = mutableListOf(firstHabitat)
    private var currentMoney = 5000.00
    private var currentGuests = 0
    private var spaceForHabs = 6 - habitats.size
    private var emptyHabs = 0

    fun getResidents(): MutableList<Creature> {
        val residents = mutableListOf<Creature>()
        habitats.forEach { habitat ->
            if (habitat.getResident().getName() != "ERROR" && habitat.getResident().getName() != "Empty")
                residents.add(habitat.getResident())
        }
        return residents
    }

    // Called every game day.
    fun manage(actionPoints: Int) {
        var actionCompleted = false
        do {
            println("\nYou have enough time today to do $actionPoints more things! You can VISIT a habitat to care for a creature, SPEND your hard earned cash on habitat upgrades or more habitats, or just do NOTHING. ")
            val chosenAction = readlnOrNull()?.lowercase()
            when (chosenAction?.lowercase()) {
                "nothing" -> actionCompleted = true
                "visit" -> {
                    if(visit()) {
                        actionCompleted = true
                    }
                }
                "spend" -> {
                    if (spend()) {
                        actionCompleted = true
                    }
                }
                else -> {
                    println("Invalid choice! Try again")
                }
            }
        } while (!actionCompleted)
    }

    // Visit an animal to feed/water, clean, or socialize
    // First choose an animal, then choose an action to take with them. Automated actions should not appear.
    private fun visit(): Boolean {
        if (habitats.isNotEmpty()) {
            println("Which habitat would you like to visit?")
            val visitHab = selectHabitat()
            val habRes = visitHab.getResident()
            if(visitHab.getResident().getName() != "ERROR" && visitHab.getResident().getName() != "Empty") {
                println("This ${habRes.getName()} is looking ${if (habRes.getCondition() > 450) "great!" else if (habRes.getCondition() > 250) "not great." else "bad!"}")
                habRes.getStats()
                do {
                    println("What would you like to do? (1. feed/water,2. clean,3. play,4. exit)")
                    val chosenAction = readlnOrNull()?.toIntOrNull()
                    when (chosenAction) {
                        1 -> {
                            habRes.sustain()
                            return true
                        }
                        2 -> {
                            habRes.clean()
                            return true
                        }
                        3 -> {
                            habRes.play()
                            return true
                        }
                        4 -> return false
                        else -> println("Invalid choice! Try again")
                    }
                } while (true)
            }
            else {
                return false
            }
        }
        else {
            println("You do not have any animals in your Zoo")
        }
        return false
    }

    // Spend your money on upgrades (or maybe to purchase more animals?)
    // First choose a habitat, then choose an upgrade. Check if player has enough money when attempting to purchase
    private fun spend(): Boolean {
        println("You have $$currentMoney to spend. Would you like to UPGRADE a habitat or BUY a new one? ")
        val choice = readlnOrNull()?.lowercase()
        if (choice == "upgrade") {
            val upgradeHab = selectHabitat()
            var numAvailableUpgrades = 0
            if (upgradeHab.getResident().getName() != "ERROR" && upgradeHab.getResident().getName() != "Empty") {
                Upgrades.entries.forEachIndexed { index, upgrade ->
                    if (upgrade !in upgradeHab.upgrades) {
                        println("${index + 1}. $upgrade: $${"%.2f".format(upgrade.price)}"); numAvailableUpgrades++
                    }
                }
                println("${numAvailableUpgrades + 1}. Exit")
                do {
                    when (val desiredUpgrade = readlnOrNull()?.toIntOrNull()) {
                        in 1..numAvailableUpgrades -> {
                            val upgrade = Upgrades.entries[desiredUpgrade!! - 1]
                            if (currentMoney >= upgrade.price) {
                                currentMoney -= upgrade.price
                                upgradeHab.upgrade(upgrade)
                                println("Upgraded habitat with ${upgrade.name.lowercase().replace('_', ' ')}")
                                return true
                            } else {
                                println("Not enough money to upgrade.")
                                return false
                            }
                        }
                        numAvailableUpgrades + 1 -> {
                            println("Nevermind then.")
                            return false
                        }
                        else -> println("Invalid choice! Try again")
                    }
                } while (true)
            }
        }
        else if (choice == "buy") {
            if (spaceForHabs > 0 && currentMoney >= 5000) {
                println("Buying a new habitat for $5000...")
                val newHabitat = Habitat(Creature("Empty"))
                habitats.add(newHabitat)
                spaceForHabs--
                emptyHabs++
                println("You now have a new empty habitat! Total habitats: ${habitats.size}")
                return true
            } else {
                println("Maximum number of habitats reached.")
                return false
            }
        }
        else {
            println("Invalid choice!")
            return false
        }
        return false
    }

    // Function for selecting a habitat to use. Returns a habitat of the users choice.
    private fun selectHabitat() : Habitat {
        habitats.forEachIndexed { index, habitat -> println("${index + 1}. ${habitat.getResident().getName()}") }
        println("${habitats.size + 1}. Exit")
        do {
            val desiredHabitat = readlnOrNull()?.toIntOrNull()
            when (desiredHabitat) {
                in 1..habitats.size -> return habitats.elementAt(desiredHabitat!! - 1)
                habitats.size + 1 -> println("Nevermind then.")
                else -> println("Invalid choice! Try again")
            }
        } while (desiredHabitat !in 1..habitats.size + 1)
        return Habitat(Creature())
    }

    fun finishDay() {
        currentGuests = 0
        var income = 0
        for (habitat in habitats) {
            val resident = habitat.getResident()
            if (resident.getName() != "ERROR" && resident.getName() != "Empty") {
                habitats.forEach { habitatVis ->
                    if (habitatVis.getResident().getName() != "ERROR" && habitatVis.getResident().getName() != "Empty") {
                        val visitors = resident.calculateVisitors()
                        currentGuests += visitors
                        println("${habitatVis.getResident().getName()} attracted $visitors visitors.")
                    }
                }
                // Random event modifier
                when (Random.nextInt(1, 5)) {
                    1 -> {
                        println("A celebrity visited the zoo today! More visitors came to see the animals.")
                        currentGuests += 30
                    }

                    2 -> {
                        println("It rained heavily today. Fewer visitors came to the zoo.")
                        currentGuests -= 20
                    }

                    3 -> {
                        println("A special event was held at the zoo today. More visitors!")
                        currentGuests += 40
                    }

                    4 -> {
                        println("There was a minor animal escape today, causing some panic. Visitors were scared away.")
                        currentGuests -= 15
                    }

                    5 -> {
                        continue
                    }
                }

                if (currentGuests < 0) {
                    currentGuests = 0
                }
                income = currentGuests * (habitats.size * 10)
                currentMoney += income
                habitat.simDay()
            }
        }
            println("End of the day report:")
            if (emptyHabs > 0) {
                println("You've received a new animal! Do you want to keep it? (y/n)")
                val newAnimal = DataRepository.createCreature(Random.nextInt(1, 45))
                newAnimal?.getStats()
                val choice = readlnOrNull()?.lowercase()
                if (choice == "y") { println("You now have a new ${newAnimal?.getName()}!"); habitats.firstOrNull { it.getResident().getName() == "Empty" }?.setResident(newAnimal!!)}
                else { println("Well, it's gone now. I'm sure it's fine to roam wherever...") }
            }
            println("Visitors: $currentGuests")
            println("Income: $$income")
            println("Current Money: $$currentMoney")

            habitats.forEach { habitat -> if(habitat.getResident().getName() != "ERROR" && habitat.getResident().getName() != "Empty") habitat.getResident().getStats() }
    }
}


// HABITAT CLASS
class Habitat (private var resident: Creature) {
    val upgrades = mutableListOf<Upgrades>()

    fun getResident() : Creature {
        return resident
    }
    fun setResident(newResident: Creature) {
        resident = newResident
    }

    fun simDay() {
        resident.simDay(Upgrades.FOOD_AND_WATER in upgrades, Upgrades.CLEANING in upgrades, Upgrades.SIZE_UP in upgrades)
    }
    fun upgrade(upgrade: Upgrades) {
        upgrades.add(upgrade)
    }
}

// POSSIBLE UPGRADES
enum class Upgrades(val price: Double) {
    FOOD_AND_WATER(100.00), CLEANING(100.00), SIZE_UP(100.00);

    override fun toString(): String {
        return name.split('_').joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }
    }
}