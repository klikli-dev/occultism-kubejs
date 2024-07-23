/*
 * MIT License
 *
 * Copyright 2021 klikli-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.klikli_dev.occultism_kubejs;

import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import com.klikli_dev.occultism.crafting.recipe.result.WeightedRecipeResult;
import com.klikli_dev.occultism.registry.OccultismRecipes;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistry;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import net.minecraft.core.registries.Registries;

public class KubeJSOccultismPlugin implements KubeJSPlugin {
    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.ITEM, (reg) -> {
            reg.add("occultism:ritual_dummy", RitualDummyItemType.class, RitualDummyItemType::new);
        });
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {

        registry.register(OccultismRecipes.SPIRIT_FIRE.getId(), OccultismRecipeSchema.SPIRIT_FIRE);
        registry.register(OccultismRecipes.SPIRIT_TRADE.getId(), OccultismRecipeSchema.SPIRIT_TRADE);
        registry.register(OccultismRecipes.CRUSHING.getId(), OccultismRecipeSchema.CRUSHING);
        registry.register(OccultismRecipes.MINER.getId(), OccultismRecipeSchema.MINER);
        registry.register(OccultismRecipes.RITUAL.getId(), RitualRecipeSchema.SCHEMA);
    }

    @Override
    public void registerRecipeComponents(RecipeComponentFactoryRegistry registry) {
        registry.register(RecipeResultComponent.RECIPE_RESULT);
        registry.register(WeightedRecipeResultComponent.WEIGHTED_RECIPE_RESULT);
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("RecipeResult", RecipeResultWrapper.class);
        bindings.add("WeightedRecipeResult", WeightedRecipeResultWrapper.class);
    }

    @Override
    public void registerTypeWrappers(TypeWrapperRegistry registry) {
        registry.register(RecipeResult.class, RecipeResultWrapper::wrap);
        registry.register(WeightedRecipeResult.class, WeightedRecipeResultWrapper::wrap);
    }
}
