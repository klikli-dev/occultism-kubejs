package com.github.klikli_dev.occultism_kubejs;

import com.google.gson.JsonElement;
import com.klikli_dev.occultism.common.misc.OutputIngredient;
import com.klikli_dev.occultism.common.misc.WeightedOutputIngredient;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;

/**
 * Subclass of RecipeJS to handle parsing of both OutputIngredient and WeightedOutputIngredient
 */
public class OccultismRecipeJS extends RecipeJS {
	@Override
	public OutputItem readOutputItem(Object from) {
		if (from instanceof OutputIngredient out) {
			// just resolve early lol
			return OutputItem.of(out.getStack());
		} else if (from instanceof WeightedOutputIngredient out) {
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
