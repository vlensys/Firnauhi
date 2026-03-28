package moe.nea.firnauhi.api.v1;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface FirnauhiItemWidget {
	/**
	 * non-exhaustive enum for the placement position of an item widget
	 */
	@ApiStatus.NonExtendable
	interface Placement {
		String name();

		Placement ITEM_LIST = () -> "ITEM_LIST";
		Placement RECIPE_SCREEN = () -> "RECIPE_SCREEN";
	}

	/**
	 * @return where in the UI the item widget is placed
	 */
	Placement getPlacement();

	/**
	 * get the currently displayed minecraft item stack object.
	 * this stack might be empty. care should be taken not to mutate the instance without copying it first.
	 *
	 * @return the currently displayed item stack object, may be empty.
	 */
	Object getItemStack();

	/**
	 * @return a SkyBlock id, potentially processed to reflect more details about the item stack, such as which specific
	 * {@code PET} it is. this is sometimes referred to as the "neu id".
	 */
	String getSkyBlockId();
}
