package moe.nea.firnauhi.api.v1;

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Methods you can call to get information about firnauhis current state.
 */
@ApiStatus.NonExtendable
public abstract class FirnauhiAPI {
	private static @Nullable FirnauhiAPI INSTANCE;

	/**
	 * @return the canonical instance of the {@link FirnauhiAPI}.
	 */
	public static FirnauhiAPI getInstance() {
		if (INSTANCE != null)
			return INSTANCE;
		try {
			return INSTANCE = (FirnauhiAPI) Class.forName("moe.nea.firnauhi.impl.v1.FirnauhiAPIImpl")
				.getField("INSTANCE")
				.get(null);
		} catch (IllegalAccessException | NoSuchFieldException | ClassCastException e) {
			throw new RuntimeException("Firnauhi API implementation class found, but could not load api instance.", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could not find Firnauhi API, check FabricLoader.getInstance().isModLoaded(\"firnauhi\") first.");
		}
	}

	/**
	 * @return list-view of registered extensions
	 */
	public abstract List<? extends FirnauhiExtension> getExtensions();

	/**
	 * Obtain a reference to the currently hovered item widget, which may be either in the item list or placed in a UI.
	 * This widget may or may not also be present in the Widgets on the current screen.
	 *
	 * @return the currently hovered firnauhi item widget.
	 */
	public abstract Optional<FirnauhiItemWidget> getHoveredItemWidget();
}
