package com.klikli_dev.occultism_kubejs.component;

import com.google.gson.JsonObject;
import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism_kubejs.OccultismKubeJS;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.resources.ResourceKey;

public record EntityToSacrificeComponent(
        Codec<RitualRecipe.EntityToSacrifice> codec) implements RecipeComponent<RitualRecipe.EntityToSacrifice> {
    public static final ResourceKey<RecipeComponentType<?>> ENTITY_TO_SACRIFICE = RecipeComponentType.key(OccultismKubeJS.loc("entity_to_sacrifice"));

    public static final TypeInfo TYPE_INFO = TypeInfo.of(RitualRecipe.EntityToSacrifice.class);

    @Override
    public ResourceKey<RecipeComponentType<?>> type() {
        return ENTITY_TO_SACRIFICE;
    }

    @Override
    public TypeInfo typeInfo() {
        return TYPE_INFO;
    }

    @Override
    public String toString() {
        return type().toString();
    }

    @Override
    public RitualRecipe.EntityToSacrifice wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof RitualRecipe.EntityToSacrifice k) {
            return k;
        }

        if (from instanceof JsonObject json) {
            return this.codec.decode(JsonOps.INSTANCE, json).result().orElseThrow().getFirst();
        }

        return (RitualRecipe.EntityToSacrifice) cx.cx().jsToJava(from, this.typeInfo());

    }
}
