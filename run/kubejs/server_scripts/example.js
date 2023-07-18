// priority: 0

// Visit the wiki for more info - https://kubejs.com/

console.info('Hello, World! (Loaded server scripts)')

ServerEvents.recipes((event) => {
	event.recipes.occultism.spirit_trade('minecraft:rotten_flesh', 'minecraft:bone')
	event.recipes.occultism.spirit_fire('minecraft:emerald_ore', '#forge:gems/emerald')
	event.recipes.occultism.crushing(
		'2x #forge:ores/iron',
		'#forge:tools/swords'
	)
	event.recipes.occultism.miner(
		Item.of('minecraft:wooden_pickaxe').withChance(100),
		'#occultism:miners/master'
	)
	event.recipes.occultism.ritual(
		'occultism:spirit_lantern',
		[
			"lapis_lazuli",
			"#forge:raw_materials",
			["minecraft:coal", 'minecraft:charcoal'],
		],
		'#forge:stone',
		'occultism:craft_afrit'
	).dummy("kubejs:dummy_ritual_thing").useItem('minecraft:egg')
})
