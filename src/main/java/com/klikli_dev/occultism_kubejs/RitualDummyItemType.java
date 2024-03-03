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

import com.google.gson.JsonObject;
import com.klikli_dev.occultism.common.item.DummyTooltipItem;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;


public class RitualDummyItemType extends ItemBuilder {
    public RitualDummyItemType(ResourceLocation rl) {
        super(rl);

        //make the item just use the ritual dummy parent mode
        //Note:  we are not using this.parentModel() because it causes textures to be overwritten with a texture location corresponding to the item id unless the correct one is manually specified again
        var modelJson = new JsonObject();
        modelJson.addProperty("parent", "occultism:item/ritual_dummy");
        this.modelJson(modelJson);
    }

    @Override
    public Item createObject() {
        return new DummyTooltipItem(this.createItemProperties());
    }


}
