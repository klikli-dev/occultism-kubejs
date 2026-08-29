package com.klikli_dev.occultism_kubejs.component;

import com.klikli_dev.occultism.crafting.recipe.conditionextension.condition.IsInBiomeCondition;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.biome.Biome;

@Info("Various IsInBiomeCondition related helper methods")
public interface IsInBiomeConditionWrapper {

    @Info("Returns an IsInBiomeCondition of the input")
    static IsInBiomeCondition of(Holder<Biome> biome) {
        return new IsInBiomeCondition(biome);
    }

    @Info("Returns an IsInBiomeCondition for the given biome resource location, e.g. 'minecraft:plains'")
    static IsInBiomeCondition of(Identifier biomeLocation) {
        var access = RegistryAccessContainer.current != null ? RegistryAccessContainer.current : RegistryAccessContainer.BUILTIN;
        var holder = access.lookup(Registries.BIOME)
                .flatMap(reg -> reg.get(biomeLocation))
                .orElseThrow(() -> new IllegalArgumentException("Unknown biome: " + biomeLocation));
        return new IsInBiomeCondition(holder);
    }
}
