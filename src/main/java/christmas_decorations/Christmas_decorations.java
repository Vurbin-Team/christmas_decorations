package christmas_decorations;

import christmas_decorations.blocks.ModBlocks;
import christmas_decorations.entity.ModEntities;
import christmas_decorations.group.ModGroup;
import christmas_decorations.items.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Christmas_decorations implements ModInitializer {
	public static final String MOD_ID = "christmas_decorations";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Christmas decorations Mod");
		ModBlocks.register();
		ModGroup.register();
		ModItems.register();
		ModEntities.register();
	}
}