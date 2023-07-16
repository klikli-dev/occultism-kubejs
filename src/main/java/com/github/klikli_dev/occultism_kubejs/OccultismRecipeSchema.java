package com.github.klikli_dev.occultism_kubejs;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface OccultismRecipeSchema {

	// can also read occultism outputs, but it's a little bit cursed
	RecipeKey<OutputItem> RESULT = new RecipeComponentWithParent<OutputItem>() {
		@Override
		public RecipeComponent<OutputItem> parentComponent() {
			return ItemComponents.OUTPUT;
		}

		@Override
		public void writeToJson(RecipeJS recipe, RecipeComponentValue<OutputItem> value, JsonObject json) {
			RecipeComponentWithParent.super.writeToJson(recipe, value, json);
			if (value.value.hasChance()) {
				json.addProperty("weight", (int) value.value.getChance());
			}
		}

		@Override
		public void readFromJson(RecipeJS recipe, RecipeComponentValue<OutputItem> value, JsonObject json) {
			RecipeComponentWithParent.super.readFromJson(recipe, value, json);
			if (json.has("weight")) {
				value.value = value.value.withChance(json.get("weight").getAsInt());
			}
		}

		@Override
		public String toString() {
			return parentComponent().toString();
		}
	}.key("result");

	RecipeKey<InputItem> INGREDIENT = ItemComponents.INPUT.key("ingredient");

	// additoinal keys for crushing recipes
	RecipeKey<Long> CRUSHING_TIME = TimeComponent.TICKS.key("crushingTime").optional(200L);
	RecipeKey<Integer> MIN_TIER = NumberComponent.ANY_INT.key("minTier").optional(-1);
	RecipeKey<Boolean> IGNORE_MULT = BooleanComponent.BOOLEAN.key("ignoreCrushingMultiplier").optional(false);

	RecipeSchema BASIC = new RecipeSchema(OccultismRecipeJS.class, OccultismRecipeJS::new, RESULT, INGREDIENT);
	RecipeSchema CRUSHING = new RecipeSchema(OccultismRecipeJS.class, OccultismRecipeJS::new, RESULT, INGREDIENT, CRUSHING_TIME, MIN_TIER, IGNORE_MULT);
}
