package black.bracken.drainage.dsl.component

import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author BlackBracken
 */

private typealias IconCondiment = Icon.() -> Unit

class Slot {

    private var iconCondiment: IconCondiment = { }
    private var filter: () -> Boolean = { true }
    private var actionOnClick: InventoryClickEvent.() -> Unit = { }

    fun icon(build: Icon.() -> Unit) {
        iconCondiment = build
    }

    fun icon(iconMaterial: Material, build: IconCondiment = {}) = icon {
        material = iconMaterial
        build()
    }

    fun icon(itemStack: ItemStack, build: IconCondiment = {}) = icon {
        basedItemStack = itemStack
        build()
    }

    fun filter(build: () -> Boolean) {
        filter = build
    }

    fun onClick(action: InventoryClickEvent.() -> Unit) {
        actionOnClick = action
    }

    internal fun buildIcon(): Icon = Icon().apply(iconCondiment)

    internal fun isAvailable(): Boolean = filter()

    internal fun fire(event: InventoryClickEvent) {
        event.apply(actionOnClick)
    }

}