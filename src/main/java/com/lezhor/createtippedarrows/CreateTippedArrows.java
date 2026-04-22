package com.lezhor.createtippedarrows;

import com.lezhor.createtippedarrows.data.ModRecipeProvider;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(CreateTippedArrows.MODID)
public class CreateTippedArrows {
    public static final String MODID = "createtippedarrows";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateTippedArrows(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::gatherData);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("Use {}mB of Potion per Arrow to craft a tipped arrow by using a Spout", Config.TIPPED_ARROW_REQUIRED_FILL_AMOUNT.get());
    }

    private void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();

        // This runs during ./gradlew runData right?
        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, lookupProvider));
    }
}
