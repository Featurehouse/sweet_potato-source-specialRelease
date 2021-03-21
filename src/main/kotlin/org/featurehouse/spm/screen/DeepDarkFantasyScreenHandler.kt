package org.featurehouse.spm.screen

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import org.featurehouse.spm.SPMFools
import org.featurehouse.spm.blocks.entities.DeepDarkFantasyBlockEntity
import org.featurehouse.spm.util.inventory.UniversalResultSlot
import org.featurehouse.spm.util.properties.fantasy.IntDeepDarkFantasyProperties
import org.featurehouse.spm.util.properties.fantasy.NullDeepDarkFantasyProperties

class DeepDarkFantasyScreenHandler(syncId: Int, playerInventory: PlayerInventory, val inventory: Inventory, val properties: IntDeepDarkFantasyProperties)
    : ScreenHandler(SPMFools.DEEP_DARK_FANTASY_SCREEN_HANDLER_TYPE, syncId) {

    constructor(syncId: Int, playerInventory: PlayerInventory)
            : this(syncId, playerInventory, SimpleInventory(3), NullDeepDarkFantasyProperties())

    init {
        this.addProperties(properties)
        this.addSlot(Slot(inventory, 0, 40, 43))
        this.addSlot(UniversalResultSlot(playerInventory.player, inventory, 1, 112, 43))
        this.addSlot(Slot(inventory, 2, 73, 14))

        for (k in 0..2) for (j in 0..8)
            this.addSlot(Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18))
        for (k in 0..8)
            this.addSlot(Slot(playerInventory, k, 8 + k * 18, 142))
    }

    override fun canUse(player: PlayerEntity?)
        = this.inventory.canPlayerUse(player)

    override fun transferSlot(player: PlayerEntity?, index: Int): ItemStack {
        var itemStack = ItemStack.EMPTY
        val slot: Slot? = this.slots[index]
        if (slot != null && slot.hasStack()) {
            val itemStack2 : ItemStack = slot.stack
            itemStack = itemStack2.copy()
            if (index < 0)
                throw IndexOutOfBoundsException(index)
            else if (index == 1) {
                if (!this.insertItem(itemStack2, 3, 39, true))
                    return ItemStack.EMPTY
                slot.onStackChanged(itemStack2, itemStack)
            } else if (index > 2) {
                if (DeepDarkFantasyBlockEntity.isValidIngredient(itemStack2)) {
                    if (!this.insertItem(itemStack2, 0, 1, false))
                        return ItemStack.EMPTY
                } else if (itemStack2.item == SPMFools.MICROSTONE_ITEM) {
                    if (!this.insertItem(itemStack2, 2, 3, false))
                        return ItemStack.EMPTY
                } else if (index < 30) {
                    if (!this.insertItem(itemStack2, 30, 39, false))
                        return ItemStack.EMPTY
                } else if (index < 39 && !this.insertItem(itemStack2, 3, 30, false))
                    return ItemStack.EMPTY
            } else if (!this.insertItem(itemStack2, 3, 39, false))
                return ItemStack.EMPTY

            if (itemStack2.isEmpty)
                slot.stack = ItemStack.EMPTY
            else slot.markDirty()

            if (itemStack2.count == itemStack.count)
                return ItemStack.EMPTY
            slot.onTakeItem(player, itemStack2)
        }
        return itemStack
    }

    val ingredientData: Int
        @Environment(EnvType.CLIENT)
        get() = properties.ingredientData
    val sublimateProcess: Int
        @Environment(EnvType.CLIENT)
        get() {
            val sublimateTime = properties.sublimateTime
            return if (sublimateTime > 0) sublimateTime * 22 / 200
                else 0
        }
    val isWorking: Boolean
        @Environment(EnvType.CLIENT)
        get() = this.properties.isWorking
}