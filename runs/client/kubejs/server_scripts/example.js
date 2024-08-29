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
    ).dummy("kubejs:dummy_ritual_thing").useItem('minecraft:egg')
})

ServerEvents.recipes((event) => {
    const recipes = [];

    event.forEachRecipe({ type: 'occultism:ritual' }, (r) => {
        let recipe = JSON.parse(r.json);
        let recipe_id = r.getId();
        if(recipe_id.includes("warden"))
            return;

        if (recipe.spirit_max_age) {
            if (recipe_id.includes('_trader')) {
                // Remove decay from Traders
                delete recipe.spirit_max_age;
            } else {
                // Reduce delay for all other spirits (weather/daytime rituals)
                recipe.spirit_max_age = 10;
            }
        }

        // Override default craft time for all recipes
        recipe.duration = 20;
        recipe.id = recipe_id;
        recipes.push(recipe);
    });

    recipes.forEach((recipe) => {
        recipe.type = 'occultism:ritual';
        event.custom(recipe).id(recipe.id);
    });
});