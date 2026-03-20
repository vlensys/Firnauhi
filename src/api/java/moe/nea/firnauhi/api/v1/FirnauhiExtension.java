package moe.nea.firnauhi.api.v1;

import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;

import java.util.Collection;
import java.util.List;

/**
 * An extension to firnauhi, allowing you to hook into firnauhis functions.
 *
 * <p>To register, set the entrypoint {@code firnauhi:v1} to an implementation of this class.</p>
 * @see #ENTRYPOINT_NAME
 */
public interface FirnauhiExtension {

	/**
	 * Name of the entry point that should be used registering firnauhi extensions.
	 */
	String ENTRYPOINT_NAME = "firnauhi:v1";

	/**
	 * This method gets called during client initialization, if firnauhi is installed. Can be used as an alternative to
	 * checking {@code FabricLoader.getInstance().isModLoaded("firnauhi")}.
	 */
	default void onLoad() {}

	/**
	 * @param screen the current active screen
	 * @return whether inventory buttons should be hidden on the current screen.
	 */
	default boolean shouldHideInventoryButtons(Screen screen) {
		return false;
	}

	/**
	 * @param screen the current active screen
	 * @return a list of zones which contain content rendered by other mods, which should therefore hide the items in those areas
	 */
	default Collection<? extends ScreenRectangle> getExclusionZones(Screen screen) {
		return List.of();
	}
}
