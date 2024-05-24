package org.example

object DataRepository {
    private val abilities: MutableMap<Int, Ability> = mutableMapOf()
    private val creaturePrototypes: MutableMap<Int, CreaturePrototype> = mutableMapOf()
    val items: MutableList<Item> = mutableListOf()

    init {
        initializeAbilities()
        initializeCreaturePrototypes()
        initializeItems()
    }

    private fun initializeAbilities() {
        // TOWER Abilities
        abilities[1] = Ability("Stomp", Type.TOWER, null, 10)
        abilities[2] = Ability("Sturdy Defense", Type.TOWER, StatusEffect.STURDY, 5)
        abilities[3] = Ability("High Ground", Type.TOWER, null, 8)
        // MAJESTIC Abilities
        abilities[4] = Ability("Royal Command", Type.MAJESTIC, StatusEffect.MAJESTY, 12)
        abilities[5] = Ability("Elegant Strike", Type.MAJESTIC, null, 10)
        abilities[6] = Ability("Prowl", Type.MAJESTIC, StatusEffect.GRACE, 8)
        // WANNABE_DRAGON Abilities
        abilities[7] = Ability("Warm Blooding", Type.WANNABE_DRAGON, StatusEffect.HEAT_BATH, 15)
        abilities[8] = Ability("Tail Whip", Type.WANNABE_DRAGON, null, 7)
        abilities[9] = Ability("Scale Assault", Type.WANNABE_DRAGON, null, 9)
        // WET_MENACE Abilities
        abilities[10] = Ability("Drench", Type.WET_MENACE, StatusEffect.DRENCH, 7)
        abilities[11] = Ability("Tidal Wave", Type.WET_MENACE, null, 10)
        abilities[12] = Ability("Aqua Jet", Type.WET_MENACE, null, 12)
        // BEHEMOTH Abilities
        abilities[13] = Ability("Quake", Type.BEHEMOTH, StatusEffect.QUAKE, 15)
        abilities[14] = Ability("Heavy Slam", Type.BEHEMOTH, null, 13)
        abilities[15] = Ability("Charge", Type.BEHEMOTH, null, 10)
        // BANANA Abilities
        abilities[16] = Ability("Monkey Business", Type.BANANA, StatusEffect.MISCHIEF, 8)
        abilities[17] = Ability("Banana Peel", Type.BANANA, null, 5)
        abilities[18] = Ability("Toss", Type.BANANA, null, 7)
        // POLITE Abilities
        abilities[19] = Ability("Pacify", Type.POLITE, StatusEffect.PACIFY, 3)
        abilities[20] = Ability("Gentle Touch", Type.POLITE, null, 6)
        abilities[21] = Ability("Pose", Type.POLITE, null, 4)
        // GRACEFUL Abilities
        abilities[22] = Ability("Graceful Dance", Type.GRACEFUL, StatusEffect.GRACE, 4)
        abilities[23] = Ability("Swan Song", Type.GRACEFUL, null, 7)
        abilities[24] = Ability("Elegant Strike", Type.GRACEFUL, null, 5)
        // MOUSEKATOOL Abilities
        abilities[25] = Ability("Ingenious Trap", Type.MOUSEKATOOL, StatusEffect.INGENUITY, 10)
        abilities[26] = Ability("Surprise", Type.MOUSEKATOOL, null, 7)
        abilities[27] = Ability("Secret Weapon", Type.MOUSEKATOOL, null, 5)
        // TINY_TERROR Abilities
        abilities[28] = Ability("Venom Bite", Type.TINY_TERROR, StatusEffect.VENOM, 8)
        abilities[29] = Ability("Scary Stare", Type.TINY_TERROR, null, 5)
        abilities[30] = Ability("Tiny Fury", Type.TINY_TERROR, null, 6)
        // FUZZBALL Abilities
        abilities[31] = Ability("Cuddle", Type.FUZZBALL, StatusEffect.CUDDLE, 3)
        abilities[32] = Ability("Soft Hug", Type.FUZZBALL, null, 2)
        abilities[33] = Ability("Fluffy Strike", Type.FUZZBALL, null, 4)
    }

    private fun initializeCreaturePrototypes() {
        // Tower Types
        creaturePrototypes[1] = CreaturePrototype("Giraffe", Rarity.RARE, 100, Type.TOWER, Type.POLITE)
        creaturePrototypes[2] = CreaturePrototype("Ostrich", Rarity.UNCOMMON, 100, Type.TOWER, Type.GRACEFUL)
        creaturePrototypes[3] = CreaturePrototype("Llama", Rarity.COMMON, 100, Type.TOWER, Type.MOUSEKATOOL)
        creaturePrototypes[4] = CreaturePrototype("Alpaca", Rarity.MYTHIC, 100, Type.TOWER, Type.FUZZBALL)
        // Majestic Types
        creaturePrototypes[5] = CreaturePrototype("Flamingo", Rarity.RARE, 100, Type.MAJESTIC, Type.TOWER)
        creaturePrototypes[6] = CreaturePrototype("Lion", Rarity.MYTHIC, 100, Type.MAJESTIC, Type.BEHEMOTH)
        creaturePrototypes[7] = CreaturePrototype("Emperor Penguin", Rarity.UNCOMMON, 100, Type.MAJESTIC, Type.POLITE)
        creaturePrototypes[8] = CreaturePrototype("Eagle", Rarity.COMMON, 100, Type.MAJESTIC, Type.GRACEFUL)
        // Wannabe Dragon Types
        creaturePrototypes[9] = CreaturePrototype("Gila Monster", Rarity.MYTHIC, 100, Type.WANNABE_DRAGON, Type.TINY_TERROR)
        creaturePrototypes[10] = CreaturePrototype("Horned Lizard", Rarity.RARE, 100, Type.WANNABE_DRAGON, Type.MOUSEKATOOL)
        creaturePrototypes[11] = CreaturePrototype("Gecko", Rarity.COMMON, 100, Type.WANNABE_DRAGON, Type.POLITE)
        creaturePrototypes[12] = CreaturePrototype("Viper", Rarity.UNCOMMON, 100, Type.WANNABE_DRAGON, Type.MAJESTIC)
        // Wet Menace Types
        creaturePrototypes[13] = CreaturePrototype("Otter", Rarity.UNCOMMON, 100, Type.WET_MENACE, Type.POLITE)
        creaturePrototypes[14] = CreaturePrototype("Dolphin", Rarity.COMMON, 100, Type.WET_MENACE, Type.GRACEFUL)
        creaturePrototypes[15] = CreaturePrototype("Oarfish", Rarity.MYTHIC, 100, Type.WET_MENACE, Type.BEHEMOTH)
        creaturePrototypes[16] = CreaturePrototype("Barracuda", Rarity.RARE, 100, Type.WET_MENACE, Type.TOWER)
        // Behemoth Types
        creaturePrototypes[17] = CreaturePrototype("Sun Bear", Rarity.RARE, 100, Type.BEHEMOTH, Type.POLITE)
        creaturePrototypes[18] = CreaturePrototype("Gorilla", Rarity.COMMON, 100, Type.BEHEMOTH, Type.BANANA)
        creaturePrototypes[19] = CreaturePrototype("Seychelles Giant Tortoise", Rarity.UNCOMMON, 100, Type.BEHEMOTH, Type.WET_MENACE)
        creaturePrototypes[20] = CreaturePrototype("Mammoth", Rarity.MYTHIC, 100, Type.BEHEMOTH, Type.TOWER)
        // Banana Types
        creaturePrototypes[21] = CreaturePrototype("Gibbon", Rarity.RARE, 100, Type.BANANA, Type.GRACEFUL)
        creaturePrototypes[22] = CreaturePrototype("Chimpanzee", Rarity.COMMON, 100, Type.BANANA, Type.MOUSEKATOOL)
        creaturePrototypes[23] = CreaturePrototype("Slow Loris", Rarity.UNCOMMON, 100, Type.BANANA, Type.TINY_TERROR)
        creaturePrototypes[24] = CreaturePrototype("Emperor Tamarin", Rarity.MYTHIC, 100, Type.BANANA, Type.MAJESTIC)
        // Polite Types
        creaturePrototypes[25] = CreaturePrototype("Quokkas", Rarity.RARE, 100, Type.POLITE, Type.FUZZBALL)
        creaturePrototypes[26] = CreaturePrototype("Pangolin", Rarity.MYTHIC, 100, Type.POLITE, Type.WANNABE_DRAGON)
        creaturePrototypes[27] = CreaturePrototype("Capybara", Rarity.UNCOMMON, 100, Type.POLITE, Type.WET_MENACE)
        creaturePrototypes[28] = CreaturePrototype("Jumping Spider", Rarity.COMMON, 100, Type.POLITE, Type.TINY_TERROR)
        // Graceful Types
        creaturePrototypes[29] = CreaturePrototype("Impala", Rarity.RARE, 100, Type.GRACEFUL, Type.BASIC)
        creaturePrototypes[30] = CreaturePrototype("Peacock", Rarity.UNCOMMON, 100, Type.GRACEFUL, Type.MAJESTIC)
        creaturePrototypes[31] = CreaturePrototype("Swan", Rarity.COMMON, 100, Type.GRACEFUL, Type.WET_MENACE)
        creaturePrototypes[32] = CreaturePrototype("Cheetah", Rarity.MYTHIC, 100, Type.GRACEFUL, Type.MAJESTIC)
        // Mousekatool Types
        creaturePrototypes[33] = CreaturePrototype("Mantis Shrimp", Rarity.MYTHIC, 100 ,Type.MOUSEKATOOL, Type.WET_MENACE)
        creaturePrototypes[34] = CreaturePrototype("Stonefish", Rarity.UNCOMMON, 100, Type.MOUSEKATOOL, Type.TINY_TERROR)
        creaturePrototypes[35] = CreaturePrototype("Goblin Shark", Rarity.RARE, 100, Type.MOUSEKATOOL, Type.GRACEFUL)
        creaturePrototypes[36] = CreaturePrototype("Tarantula", Rarity.COMMON, 100, Type.MOUSEKATOOL, Type.TINY_TERROR)
        // Tiny Terror Types
        creaturePrototypes[37] = CreaturePrototype("Slavemaker Ant", Rarity.RARE, 100, Type.TINY_TERROR, Type.BASIC)
        creaturePrototypes[38] = CreaturePrototype("Praying Mantis", Rarity.COMMON, 100, Type.TINY_TERROR, Type.MAJESTIC)
        creaturePrototypes[39] = CreaturePrototype("Velvet Worm", Rarity.UNCOMMON, 100, Type.TINY_TERROR, Type.MOUSEKATOOL)
        creaturePrototypes[40] = CreaturePrototype("Antlion", Rarity.MYTHIC, 100, Type.TINY_TERROR, Type.BASIC)
        // Fuzzball Types
        creaturePrototypes[41] = CreaturePrototype("Chinchilla", Rarity.MYTHIC, 100, Type.FUZZBALL, Type.BASIC)
        creaturePrototypes[42] = CreaturePrototype("Panda", Rarity.RARE, 100, Type.FUZZBALL, Type.BEHEMOTH)
        creaturePrototypes[43] = CreaturePrototype("Tiger", Rarity.UNCOMMON, 100, Type.FUZZBALL, Type.GRACEFUL)
        creaturePrototypes[44] = CreaturePrototype("Dog", Rarity.COMMON, 100, Type.FUZZBALL, Type.POLITE)
    }

    private fun initializeItems() {

    }
    fun createCreature(id: Int): Creature? {
        val prototype = creaturePrototypes[id] ?: return null
        return Creature(prototype.getName(), prototype.getRarity(), prototype.getMaxHealth(), prototype.getPrimaryType(), prototype.getSecondaryType())
    }
    fun getAbilitiesByType(vararg types: Type): List<Ability> {
        return abilities.values.filter { it.getType() in types }.map { it }
    }
}