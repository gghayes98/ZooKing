package org.example

class Creature(
    private val name: String = "ERROR",
    private val rarity: Rarity = Rarity.DEBUG,
    private var maxHealth : Int = 0,
    private val mainType: Type = Type.BASIC,
    private val secondType: Type = Type.BASIC
) {
    private val statusEffects: MutableMap<StatusEffect, Int> = mutableMapOf()
    private var currentHealth : Int = maxHealth
    private var hunger : Int = 100
    private var thirst : Int = 100
    private var cleanliness : Int = 100
    private var social : Int = 100
    private var level : Int = 1
    private var nextLevelXP : Int = 100
    private var currXP : Int = 0
    private var abilities: List<Ability> = mutableListOf()

    init {
        if (name == "Empty") {
            currentHealth = 0
            maxHealth = 0
            hunger = 0
            thirst = 0
            cleanliness = 0
            social = 0
            level = 0
            nextLevelXP = 0
            currXP = 0
        } else {
            val availableAbilities = DataRepository.getAbilitiesByType(mainType, secondType, Type.BASIC)
            abilities = availableAbilities.shuffled().take(3)
        }
    }

    fun getCondition() : Int {
        return hunger + thirst + cleanliness + social + calculateHealthPercentage()
    }
    fun getName() : String {
        return name
    }
    fun getHealth(): Int {
        return currentHealth
    }

    private fun calculateHealthPercentage() : Int {
        return (currentHealth/maxHealth) * 100
    }

    fun addAbility(newAbility: Ability) : Boolean {
        if (abilities.size <= 3) {
            abilities += newAbility
            println("$name has learned: $newAbility!")
            return true
        } else {
            println("$name already knows enough abilities!")
            return false
        }
    }

    fun getAbilities() {
        abilities.forEachIndexed { index, ability ->
            println("${index + 1}. ${ability.getName()} - ${ability.getType()} - ${if (ability.getDamage() > 0) "Deals ${ability.getDamage()} damage" else "Heals ${ability.getDamage()} health"}.")
        }
    }
    fun useAbility(index: Int): Ability {
        return abilities[index]
    }

    fun getStats() {
        println("\nName: $name")
        println("Type: $mainType - $secondType")
        println("XP: $currXP/$nextLevelXP - Level $level - $rarity")
        println("Health: $currentHealth/$maxHealth - (${calculateHealthPercentage()}%)")
        println("Hunger: $hunger")
        println("Thirst: $thirst")
        println("Cleanliness: $cleanliness")
        println("Social: $social")
    }

    fun simDay(susUp: Boolean, cleanUp: Boolean, spaceUp: Boolean) {
        if (!susUp) {
            thirst -= 10
            hunger -= 10
        }
        if (!cleanUp) {
            cleanliness -= 10
        }
        if (!spaceUp) {
            social -= 10
        }
        if (currentHealth < maxHealth) {
            currentHealth += (maxHealth * 0.2 * (getCondition() / 500)).toInt()
            if (currentHealth > maxHealth) {
                currentHealth = maxHealth
            }
            println("$name has healed some due to it's habitat. It is now at ${calculateHealthPercentage()}% health.")
        }
    }

    fun calculateVisitors(): Int {
        val baseVisitors = 10
        val levelBonus = level * 2
        val rarityBonus = rarity.value * 5
        val conditionBonus = getCondition() / 100

        return baseVisitors + levelBonus + rarityBonus + conditionBonus
    }

    fun sustain() {
        hunger += 10
        thirst += 10
        println("You made sure $name had enough food and water. Their condition has improved to ${getCondition()}!")
    }
    fun clean() {
        cleanliness += 10
        println("You thoroughly cleaned $name's habitat. Their condition has improved to ${getCondition()}!")
    }
    fun play() {
        social += 10
        println("You played with $name. Their condition has improved to ${getCondition()}!")
    }

    private fun applyStatusEffect(effect: StatusEffect) {
        if (effect in statusEffects) {
            println("$name is already being effected by ${effect}.")
        } else {
            statusEffects[effect] = effect.duration
            println("$name is now being effected by $effect for ${effect.duration} turns!")
        }
    }
    fun endTurnStatusEffects() {
        val effectsToRemove = mutableListOf<StatusEffect>()
        for ((effect, duration) in statusEffects) {
            println("$name is under the effect of ${effect.name}. Duration left: $duration")
            if (duration - 1 <= 0) {
                effectsToRemove.add(effect)
            } else {
                statusEffects[effect] = duration - 1
            }
        }
        effectsToRemove.forEach { statusEffects.remove(it) }
    }

    fun getAttacked(ability: Ability): Int {
        var damageToTake = ability.getDamage()
        if (mainType.isWeakAgainst(ability.getType())) {
            println("$name is weak against ${ability.getType()} attacks!")
            damageToTake = ability.getDamage() * 2
        }
        else if (mainType.isStrongAgainst(ability.getType())) {
            println("$name is strong against ${ability.getType()} attacks!")
            damageToTake = ability.getDamage() / 2
        }
        if (damageToTake < 0) {
            currentHealth += damageToTake
            if (currentHealth > maxHealth) {
                currentHealth = maxHealth
            }
            println("It healed for $damageToTake health!")
            println("$name now has $currentHealth/$maxHealth health.")
        } else {
            currentHealth -= damageToTake
            println("It did $damageToTake damage!")
            if (currentHealth <= 0) {
                println("$name is temporarily dead.")
                return level * 5
            } else {
                println("$name now has $currentHealth/$maxHealth health remaining.")
            }
        }
        if (ability.hasStatusEffect()) {
            val effect: StatusEffect = ability.getStatusEffect()!!
            applyStatusEffect(effect)
        }
        return 0
    }
    fun setHealth(health: Int) {
        currentHealth = health
    }

    fun addXP(xp: Int) {
        currXP += xp
        if(currXP >= nextLevelXP) {
            levelUp()
        }
    }
    private fun levelUp() {
        level++
        println("$name has leveled up to level $level!")
        currXP -= nextLevelXP
        nextLevelXP += 100
        println("In the future this will also increase stats! I can't wait for the future!")
    }
}

enum class Rarity(val value: Int) {
    MYTHIC(4), RARE(3), UNCOMMON(2), COMMON(1), DEBUG(0)
}

data class CreaturePrototype(
    private val name: String,
    private val rarity: Rarity,
    private val maxHealth: Int,
    private val primaryType: Type,
    private val secondaryType: Type? = null
) {
    fun getName(): String {
        return name
    }
    fun getRarity(): Rarity {
        return rarity
    }
    fun getMaxHealth(): Int {
        return maxHealth
    }
    fun getPrimaryType(): Type {
        return primaryType
    }
    fun getSecondaryType(): Type {
        return secondaryType!!
    }
}