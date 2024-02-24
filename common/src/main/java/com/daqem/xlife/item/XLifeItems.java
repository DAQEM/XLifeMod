package com.daqem.xlife.item;

import com.daqem.xlife.XLife;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class XLifeItems {

    // Registering a new creative tab
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(XLife.MOD_ID, Registries.CREATIVE_MODE_TAB);
    public static final RegistrySupplier<CreativeModeTab> XLIFE_TAB = TABS.register("xlife_tab", () ->
            CreativeTabRegistry.create(Component.translatable("itemGroup." + XLife.MOD_ID + ".xlife_tab"),
                    () -> new ItemStack(XLifeItems.EXTRA_LIFE.get())));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(XLife.MOD_ID, Registries.ITEM);
    public static final RegistrySupplier<Item> EXTRA_LIFE = ITEMS.register("extra_life", ExtraLifeItem::new);

    public static void init() {
        TABS.register();
        ITEMS.register();
    }
}