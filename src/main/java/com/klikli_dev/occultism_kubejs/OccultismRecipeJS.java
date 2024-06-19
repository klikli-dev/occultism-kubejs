package com.klikli_dev.occultism_kubejs;

import com.google.gson.JsonElement;
import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;

/**
 * Subclass of RecipeJS to handle parsing of both OutputIngredient and WeightedOutputIngredient
 */
public class OccultismRecipeJS extends RecipeJS {
	@Override
	public RecipeResult readOutputItem(Object from) {
		if (from instanceof RecipeResult out) {
			// just resolve early lol
			return OutputItem.of(out.getStack());
		} else if (from instanceof WeightedRecipeResult out) {
			// once again resolving early, but with a weight this time
			return OutputItem.of(out.getStack(), out.getWeight().asInt());
		}
		return super.readOutputItem(from);
	}

	@Override
	public JsonElement writeOutputItem(OutputItem value) {
		var json = super.writeOutputItem(value).getAsJsonObject();
		if (json.has("chance")) {
			json.addProperty("weight", json.get("chance").getAsInt());
			json.remove("chance");
		}
		return json;
	}
}
