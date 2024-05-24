package org.example

import kotlin.random.Random

abstract class Character {
    val name: String = "ERROR"
    var creatureInventory: MutableList<Creature> = mutableListOf()
    private var numCreatures: Int = 0

    fun getNumCreatures(): Int {
        return numCreatures
    }

    fun prepBattle(battleCreatures: MutableList<Creature>) {
        if (battleCreatures.size <= 6) {
            creatureInventory.clear()
            creatureInventory = battleCreatures
            numCreatures = creatureInventory.size
        } else {
            println("ERROR Too many creatures")
        }
    }

    fun listCreatures() {
        if (creatureInventory.isEmpty()) {
            println("There are no creatures.")
        } else {
            println("Creatures:")
            creatureInventory.forEachIndexed { index, creature ->
                println("${index + 1} - ${creature.getName()}\n")
            }
        }
    }

    fun getCreature(choice: Int): Creature {
        return creatureInventory[choice]
    }

    fun creaturesAlive(): Int {
        return creatureInventory.count { it.getHealth() > 0 }
    }
}

// Unique to the player
class Player : Character() {
    private val itemInventory: MutableList<Item> = mutableListOf()

    fun addItem(item: Item) {
        itemInventory.add(item)
        println("You added ${item.getName()} to their inventory.")
    }

    fun removeItem(item: Item): Boolean {
        return if (itemInventory.contains(item)) {
            itemInventory.remove(item)
            println("You removed ${item.getName()} from their inventory.")
            true
        } else {
            println("${item.getName()} is not in your inventory.")
            false
        }
    }

    fun listItems() {
        if (itemInventory.isEmpty()) {
            println("You have no items in their inventory.")
        } else {
            println("Your items:")
            itemInventory.forEach { item ->
                println("- ${item.getName()}")
            }
        }
    }
}

// The class of all who would challenge you for your zoo
class Challenger : Character() {
    init {
        createListOfCreatures()
    }

    private fun createListOfCreatures() {
        val numCreatures = Random.nextInt(1, 7)
        for (i in 1..numCreatures) {
            val nextType = Random.nextInt(1, 12)
            when (nextType) {
                1 -> DataRepository.createCreature(Random.nextInt(1,5))?.let { creatureInventory.add(it) }
                2 -> DataRepository.createCreature(Random.nextInt(5,9))?.let { creatureInventory.add(it) }
                3 -> DataRepository.createCreature(Random.nextInt(9,13))?.let { creatureInventory.add(it) }
                4 -> DataRepository.createCreature(Random.nextInt(13,17))?.let { creatureInventory.add(it) }
                5 -> DataRepository.createCreature(Random.nextInt(17,21))?.let { creatureInventory.add(it) }
                6 -> DataRepository.createCreature(Random.nextInt(21, 25))?.let { creatureInventory.add(it) }
                7 -> DataRepository.createCreature(Random.nextInt(25, 29))?.let { creatureInventory.add(it) }
                8 -> DataRepository.createCreature(Random.nextInt(29, 33))?.let { creatureInventory.add(it) }
                9 -> DataRepository.createCreature(Random.nextInt(33, 37))?.let { creatureInventory.add(it) }
                10 -> DataRepository.createCreature(Random.nextInt(37, 41))?.let { creatureInventory.add(it) }
                11 -> DataRepository.createCreature(Random.nextInt(41, 45))?.let { creatureInventory.add(it) }
            }
        }
    }
}