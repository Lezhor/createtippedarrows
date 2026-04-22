package com.lezhor.createtippedarrows;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(CreateTippedArrows.MODID)
public class CreateTippedArrows {
    public static final String MODID = "createtippedarrows";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateTippedArrows(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Use {}mB of Potion per Arrow to craft a tipped arrow by using a Spout", Config.TIPPED_ARROW_REQUIRED_FILL_AMOUNT.get());
    }

}
