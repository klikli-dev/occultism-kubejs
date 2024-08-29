package com.klikli_dev.occultism_kubejs;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.TickDuration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface RitualRecipeSchema {
    RecipeKey<ItemStack> RESULT = ItemStackComponent.ITEM_STACK.outputKey("result").allowEmpty();
    RecipeKey<List<Ingredient>> INGREDIENTS = IngredientComponent.INGREDIENT.asList().inputKey("ingredients");
    RecipeKey<String> RITUAL_TYPE = StringComponent.ID.inputKey("ritual_type").alt("ritualType").optional("occultism:craft").alwaysWrite();
    RecipeKey<String> ENTITY_TO_SUMMON = StringComponent.ID.inputKey("entity_to_summon").alt("entityToSummon").alt("summon").defaultOptional();
    RecipeKey<TagKey<EntityType<?>>> ENTITY_TAG_TO_SUMMON = TagKeyComponent.ENTITY_TYPE.inputKey("entity_tag_to_summon").alt("entityTagToSummon").alt("summonTag").defaultOptional();
    RecipeKey<String> ENTITY_NBT = StringComponent.ANY.inputKey("entity_nbt").alt("entityNbt").defaultOptional();
    RecipeKey<Ingredient> ACTIVATION_ITEM = IngredientComponent.INGREDIENT.inputKey("activation_item").alt("activationItem");
    RecipeKey<String> PENTACLE_ID = StringComponent.ID.inputKey("pentacle_id").alt("pentacleId").alt("pentacle");
    RecipeKey<TickDuration> DURATION = TimeComponent.TICKS.inputKey("duration").optional(TickDuration.wrap(30));
    RecipeKey<Integer> SPIRIT_MAX_AGE = NumberComponent.INT.inputKey("spirit_max_age").alt("spiritMaxAge").alt("maxAge").optional(-1);
    RecipeKey<String> SPIRIT_JOB_TYPE = StringComponent.ID.inputKey("spirit_job_type").alt("spiritJobType").alt("jobType").defaultOptional();
    RecipeKey<ItemStack> RITUAL_DUMMY = ItemStackComponent.ITEM_STACK.outputKey("ritual_dummy").alt("ritualDummy").alt("dummyItem").alt("dummy")
            // apparently there is never any static reference to this item, so let's just hope klikli never changes this lmao
            .optional(new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.parse("occultism:ritual_dummy/custom_ritual"))))
            .alwaysWrite();

    RecipeKey<?> ENTITY_TO_SACRIFICE =
            RecipeComponent.builder(
                            new RecipeComponentBuilder.Key("tag", TagKeyComponent.ENTITY_TYPE),
                            new RecipeComponentBuilder.Key("display_name", StringComponent.ANY)
                    )
                    .inputKey("entity_to_sacrifice").alt("entityToSacrifice").alt("sacrifice")
                    .defaultOptional();
    RecipeKey<Ingredient> ITEM_TO_USE = IngredientComponent.INGREDIENT.inputKey("item_to_use")
            .alt("itemToUse").alt("useItem").optional(Ingredient.EMPTY).allowEmpty();
    RecipeKey<String> COMMAND = StringComponent.ANY.inputKey("command").defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(
            // all the required keys first
            RESULT, INGREDIENTS, ACTIVATION_ITEM, PENTACLE_ID,
            // and now all the optionals...
            DURATION, SPIRIT_MAX_AGE, SPIRIT_JOB_TYPE, RITUAL_DUMMY, RITUAL_TYPE,
            ENTITY_TO_SUMMON, ENTITY_TAG_TO_SUMMON, ENTITY_NBT, ENTITY_TO_SACRIFICE, ITEM_TO_USE, COMMAND
    ).uniqueId(RITUAL_DUMMY);

}
