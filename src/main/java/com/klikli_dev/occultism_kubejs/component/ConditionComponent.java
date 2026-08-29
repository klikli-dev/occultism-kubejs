package com.klikli_dev.occultism_kubejs.component;

import com.google.gson.JsonObject;
import com.klikli_dev.occultism_kubejs.OccultismKubeJS;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.conditions.ICondition;

public record ConditionComponent(Codec<ICondition> codec) implements RecipeComponent<ICondition> {
    public static final ResourceKey<RecipeComponentType<?>> CONDITION = RecipeComponentType.key(OccultismKubeJS.loc("condition"));

    public static final TypeInfo TYPE_INFO = TypeInfo.of(ICondition.class);

    @Override
    public ResourceKey<RecipeComponentType<?>> type() {
        return CONDITION;
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
    public ICondition wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof ICondition k) {
            return k;
        }

        if (from instanceof JsonObject json) {
            return this.codec.decode(JsonOps.INSTANCE, json).result().orElseThrow().getFirst();
        }

        return (ICondition) cx.cx().jsToJava(from, this.typeInfo());
    }
}
