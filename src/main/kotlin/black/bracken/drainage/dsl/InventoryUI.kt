package black.bracken.drainage.dsl

import black.bracken.drainage.dsl.component.InventoryLayout
import black.bracken.drainage.util.InventoryInformation
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

/**
 * @author BlackBracken
 */

private typealias LayoutCondiment = InventoryLayout.() -> Unit

interface InventoryUI : InventoryHolder {

    val layout: (Player) -> InventoryLayout

    @Deprecated(
            message = "Use InventoryUI#layout(Player).toInventory() instead.",
            level = DeprecationLevel.ERROR,
            replaceWith = ReplaceWith("layout(Player).toInventory()"))
    override fun getInventory(): Inventory = throw UnsupportedOperationException()

    fun InventoryUI.build(inventoryInformation: InventoryInformation, build: LayoutCondiment): LayoutBuilder {
        return LayoutBuilder(inventoryInformation, this@build, build)
    }

    fun InventoryUI.build(inventoryType: InventoryType, build: LayoutCondiment): LayoutBuilder {
        return build(InventoryInformation(inventoryType), build)
    }

    fun InventoryUI.build(inventorySize: Int, build: LayoutCondiment): LayoutBuilder {
        return build(InventoryInformation(InventoryType.CHEST, inventorySize), build)
    }

}