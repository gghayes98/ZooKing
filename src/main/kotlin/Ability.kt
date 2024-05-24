package org.example

class Ability(
    private val name: String,
    private val type: Type,
    private val statusEffect: StatusEffect? = null,
    private val damage: Int
) {
    fun getName(): String {
        return name
    }
    fun getType(): Type {
        return type
    }
    fun getDamage(): Int {
        return damage
    }
    fun getStatusEffect(): StatusEffect? {
        return statusEffect
    }
    fun hasStatusEffect(): Boolean {
        return statusEffect != null
    }
    fun use(user: Creature, target: Creature): Int {
        println("${user.getName()} used $name on ${target.getName()}.")
        return target.getAttacked(this)
    }
}


enum class Type {
    BASIC,
    TOWER,
    MAJESTIC,
    WANNABE_DRAGON,
    WET_MENACE,
    BEHEMOTH,
    BANANA,
    POLITE,
    GRACEFUL,
    MOUSEKATOOL,
    TINY_TERROR,
    FUZZBALL;

    lateinit var strongAgainst: List<Type>
    lateinit var weakAgainst: List<Type>

    companion object {
        init {
            TOWER.strongAgainst = listOf(WANNABE_DRAGON, MOUSEKATOOL)
            TOWER.weakAgainst = listOf(TINY_TERROR, BANANA)

            WANNABE_DRAGON.strongAgainst = listOf(POLITE, FUZZBALL, TINY_TERROR)
            WANNABE_DRAGON.weakAgainst = listOf(TOWER, BEHEMOTH, WANNABE_DRAGON)

            WET_MENACE.strongAgainst = listOf(FUZZBALL, WANNABE_DRAGON)
            WET_MENACE.weakAgainst = listOf(GRACEFUL, WET_MENACE)

            BEHEMOTH.strongAgainst = listOf(WANNABE_DRAGON, MAJESTIC)
            BEHEMOTH.weakAgainst = listOf(MOUSEKATOOL, BANANA)

            BANANA.strongAgainst = listOf(BEHEMOTH, TOWER)
            BANANA.weakAgainst = listOf(MAJESTIC, GRACEFUL)

            POLITE.strongAgainst = listOf(MAJESTIC, TINY_TERROR)
            POLITE.weakAgainst = listOf(FUZZBALL, WANNABE_DRAGON)

            GRACEFUL.strongAgainst = listOf(WET_MENACE, BANANA)
            GRACEFUL.weakAgainst = listOf(FUZZBALL, MAJESTIC)

            MOUSEKATOOL.strongAgainst = listOf(BEHEMOTH, WET_MENACE)
            MOUSEKATOOL.weakAgainst = listOf(TOWER, TINY_TERROR)

            TINY_TERROR.strongAgainst = listOf(TOWER, MOUSEKATOOL)
            TINY_TERROR.weakAgainst = listOf(POLITE, WANNABE_DRAGON)

            MAJESTIC.strongAgainst = listOf(BANANA, GRACEFUL)
            MAJESTIC.weakAgainst = listOf(BEHEMOTH, POLITE)

            FUZZBALL.strongAgainst = listOf(POLITE, GRACEFUL)
            FUZZBALL.weakAgainst = listOf(WET_MENACE, WANNABE_DRAGON)
        }
    }

    fun isStrongAgainst(otherType: Type): Boolean {
        return otherType in strongAgainst
    }

    fun isWeakAgainst(otherType: Type): Boolean {
        return otherType in weakAgainst
    }
}


enum class StatusEffect(val duration: Int, val damage: Int, val type: Type) {
    CUDDLE(3, -3, Type.FUZZBALL), STURDY(5, -2, Type.TOWER),
    DRENCH(1, 7, Type.WET_MENACE), QUAKE(2, 3, Type.BEHEMOTH),
    MISCHIEF(2, -4, Type.BANANA), PACIFY(2, -3, Type.POLITE),
    VENOM(4, 3, Type.TINY_TERROR), INGENUITY(3, -4, Type.MOUSEKATOOL),
    MAJESTY(1, -8, Type.MAJESTIC), GRACE(2, -4, Type.GRACEFUL),
    HEAT_BATH(6, -1, Type.WET_MENACE)
}