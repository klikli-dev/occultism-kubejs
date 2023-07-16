package com.github.klikli_dev.occultism_kubejs;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilder;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public interface RitualRecipeSchema {
	RecipeKey<String> GROUP = StringComponent.ANY.key("group").optional("").exclude();
	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result").allowEmpty();
	RecipeKey<InputItem[]> INGREDIENTS = ItemComponents.INPUT_ARRAY.key("ingredients");
	RecipeKey<String> RITUAL_TYPE = StringComponent.ID.key("ritual_type").preferred("ritualType").optional("occultism:craft").alwaysWrite();
	RecipeKey<String> ENTITY_TO_SUMMON = StringComponent.ID.key("entity_to_summon").preferred("entityToSummon").alt("summon").defaultOptional();
	RecipeKey<String> ENTITY_NBT = StringComponent.ANY.key("entity_nbt").preferred("entityNbt").defaultOptional();
	RecipeKey<InputItem> ACTIVATION_ITEM = ItemComponents.INPUT.key("activation_item").preferred("activationItem");
	RecipeKey<String> PENTACLE_ID = StringComponent.ID.key("pentacle_id").preferred("pentacleId").alt("pentacle");
	RecipeKey<Integer> DURATION = NumberComponent.INT.key("duration").optional(30);
	RecipeKey<Integer> SPIRIT_MAX_AGE = NumberComponent.ANY_INT.key("spirit_max_age").alt("spiritMaxAge").preferred("maxAge").optional(-1);
	RecipeKey<String> SPIRIT_JOB_TYPE = StringComponent.ID.key("spirit_job_type").alt("spiritJobType").preferred("jobType").defaultOptional();
	RecipeKey<OutputItem> RITUAL_DUMMY = ItemComponents.OUTPUT.key("ritual_dummy").alt("ritualDummy").preferred("dummyItem").alt("dummy")
			// apparently there is never any static reference to this item, so let's just hope klikli never changes this lmao
			.optional(OutputItem.of(Registry.ITEM.get(new ResourceLocation("occultism:ritual_dummy/custom_ritual"))))
			.alwaysWrite();
	RecipeKey<?> ENTITY_TO_SACRIFICE = new RecipeComponentBuilder(2)
			.add(StringComponent.ID.key("tag"))
			.add(StringComponent.ANY.key("display_name").preferred("displayName").alt("name"))
			.key("entity_to_sacrifice").alt("entityToSacrifice").preferred("sacrifice")
			.defaultOptional();
	RecipeKey<InputItem> ITEM_TO_USE = ItemComponents.INPUT.key("item_to_use")
			.alt("itemToUse").preferred("useItem").optional(InputItem.EMPTY).allowEmpty();
	RecipeKey<String> COMMAND = StringComponent.ANY.key("command").defaultOptional();

	RecipeSchema SCHEMA = new RecipeSchema(OccultismRecipeJS.class, OccultismRecipeJS::new,
			// all the required keys first
			RESULT, INGREDIENTS, ACTIVATION_ITEM, PENTACLE_ID,
			// and now all the optionals...
			DURATION, SPIRIT_MAX_AGE, SPIRIT_JOB_TYPE, RITUAL_DUMMY, RITUAL_TYPE,
			// excluded keys last
			ENTITY_TO_SUMMON, ENTITY_NBT, ENTITY_TO_SACRIFICE, ITEM_TO_USE, COMMAND, GROUP
	);

}
