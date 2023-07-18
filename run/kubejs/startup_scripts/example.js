// priority: 0

// Visit the wiki for more info - https://kubejs.com/

console.info('Hello, World! (Loaded startup scripts)')

StartupEvents.registry('item', (event) => {
	event.create('dummy_ritual_thing', 'occultism:ritual_dummy').displayName('Hey Lois Look At Me I\'m A Minecraft Item Hehehehehe')
})
