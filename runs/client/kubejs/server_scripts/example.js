// priority: 0

// Visit the wiki for more info - https://kubejs.com/

console.info('Hello, World! (Loaded server scripts)')

ServerEvents.recipes((event) => {
    //Some examples of how to add recipes:

    // event.recipes.occultism.spirit_trade('minecraft:rotten_flesh', 'minecraft:bone') //Note that a trade recipe alone is not enough, it needs a corresponding spirit job that is set to perform this trade.
    event.recipes.occultism.spirit_fire('minecraft:emerald_ore', '#c:gems/emerald')
    event.recipes.occultism.crushing(
        RecipeResult.of("#c:ores/iron", 2),
        '#minecraft:swords'
    )
    event.recipes.occultism.miner(
        //item, count, weight
        WeightedRecipeResult.of('minecraft:wooden_pickaxe', 1, 100),
        '#occultism:miners/master'
    )

    event.recipes.occultism.ritual(
        'occultism:spirit_lantern',
        [
            "lapis_lazuli",
            "#c:raw_materials",
            "minecraft:coal"
        ],
        '#c:stones',
        'occultism:craft_afrit'
    )
        // kubejs:dummy_ritual_thing is created in ../startup_scripts/example.js
        // if omitted defaults to occultism:ritual_dummy/custom_ritual_misc
        .dummy("kubejs:dummy_ritual_thing")
        .useItem('minecraft:egg')
        .entityToSacrifice(EntityToSacrifice.of("minecraft:cows", "Cows"))//it would be better to use a translation key instead of "Cows" to allow translating to other languages. E.g. "tags.entities.cows" -> but that also needs a corresponding translation in en_us.json and other lang files.
        .condition(IsInBiomeCondition.of("minecraft:plains"))// Optional start condition. Unlike the neo conditions array this does not prevent loading the recipe, but rather prevents the start of the ritual (with a reasonable error message to the player). Wrappers provided by OccultismKubeJS: IsInBiomeCondition, IsInBiomeWithTagCondition, IsInDimensionCondition, IsInDimensionTypeCondition. Also supports neoforge default conditions, but there is no kubejs wrapper for them yet.
})
