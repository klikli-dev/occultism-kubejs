package com.klikli_dev.occultism_kubejs;

import com.klikli_dev.occultism.crafting.recipe.RitualRecipe;
import com.klikli_dev.occultism.crafting.recipe.result.RecipeResult;
import com.klikli_dev.occultism_kubejs.component.ConditionComponent;
import com.klikli_dev.occultism_kubejs.component.EntityToSacrificeComponent;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.TickDuration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.List;

public interface RitualRecipeSchema {
    EntityToSacrificeComponent ENTITY_TO_SACRIFICE_COMPONENT = new EntityToSacrificeComponent(RitualRecipe.EntityToSacrifice.CODEC.codec());
    ConditionComponent CONDITION_COMPONENT = new ConditionComponent(ICondition.CODEC);

    RecipeKey<ItemStack> RESULT = ItemStackComponent.OPTIONAL_ITEM_STACK.outputKey("result");
    RecipeKey<List<Ingredient>> INGREDIENTS = IngredientComponent.INGREDIENT.asList().inputKey("ingredients");
    RecipeKey<String> RITUAL_TYPE = StringComponent.ID.inputKey("ritual_type").alt("ritualType").optional("occultism:craft").alwaysWrite();
    RecipeKey<String> ENTITY_TO_SUMMON = StringComponent.ID.inputKey("entity_to_summon").alt("entityToSummon").alt("summon").defaultOptional();
    RecipeKey<TagKey<EntityType<?>>> ENTITY_TAG_TO_SUMMON = TagKeyComponent.ENTITY_TYPE.inputKey("entity_tag_to_summon").alt("entityTagToSummon").alt("summonTag").defaultOptional();
    RecipeKey<String> ENTITY_NBT = StringComponent.STRING.inputKey("entity_nbt").alt("entityNbt").defaultOptional();
    RecipeKey<Ingredient> ACTIVATION_ITEM = IngredientComponent.INGREDIENT.inputKey("activation_item").alt("activationItem");
    RecipeKey<String> PENTACLE_ID = StringComponent.ID.inputKey("pentacle_id").alt("pentacleId").alt("pentacle");
    RecipeKey<TickDuration> DURATION = TimeComponent.TICKS.inputKey("duration").optional(new TickDuration(30));
    RecipeKey<Integer> SPIRIT_MAX_AGE = NumberComponent.INT.inputKey("spirit_max_age").alt("spiritMaxAge").alt("maxAge").optional(-1);
    RecipeKey<String> SPIRIT_JOB_TYPE = StringComponent.ID.inputKey("spirit_job_type").alt("spiritJobType").alt("jobType").defaultOptional();
    RecipeKey<ItemStack> RITUAL_DUMMY = ItemStackComponent.ITEM_STACK.outputKey("ritual_dummy").alt("ritualDummy").alt("dummyItem").alt("dummy")
            .optional(new ItemStack(BuiltInRegistries.ITEM.get(Identifier.parse("occultism:ritual_dummy/custom_ritual_misc")).orElseThrow().value()))
            .alwaysWrite();

    RecipeKey<RitualRecipe.EntityToSacrifice> ENTITY_TO_SACRIFICE = ENTITY_TO_SACRIFICE_COMPONENT
            .inputKey("entity_to_sacrifice").alt("entityToSacrifice").alt("sacrifice")
            .defaultOptional();
    RecipeKey<Ingredient> ITEM_TO_USE = IngredientComponent.OPTIONAL_INGREDIENT.inputKey("item_to_use")
            .alt("itemToUse").alt("useItem").optional(Ingredient.of());
    RecipeKey<String> COMMAND = StringComponent.STRING.inputKey("command").defaultOptional();
    RecipeKey<ICondition> CONDITION = CONDITION_COMPONENT.inputKey("condition").alt("startCondition").defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(
            // all the required keys first
            RESULT, INGREDIENTS, ACTIVATION_ITEM, PENTACLE_ID,
            // and now all the optionals...
            DURATION, SPIRIT_MAX_AGE, SPIRIT_JOB_TYPE, RITUAL_DUMMY, RITUAL_TYPE,
            ENTITY_TO_SUMMON, ENTITY_TAG_TO_SUMMON, ENTITY_NBT, ENTITY_TO_SACRIFICE,
            ITEM_TO_USE, COMMAND, CONDITION
    ).uniqueId(RITUAL_DUMMY);

}
