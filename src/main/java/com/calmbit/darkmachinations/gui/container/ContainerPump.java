package com.calmbit.darkmachinations.gui.container;

import com.calmbit.darkmachinations.tileentity.TileEntityPump;
import com.elytradev.concrete.inventory.StandardMachineSlots;
import com.elytradev.concrete.inventory.gui.widget.*;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ContainerPump extends ContainerBase<TileEntityPump> {

    public ContainerPump(TileEntityPump pump, IInventory playerInventory, IInventory pumpInventory)
    {
        super(pump, playerInventory, pumpInventory);

        WPlainPanel panel = new WPlainPanel();
        this.setRootPanel(panel);

        panel.add(WItemSlot.of(pumpInventory, StandardMachineSlots.INPUT), 144, 60);
        panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 84);
        panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 144);
        panel.add(new WBar(
                new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_bg.png"),
                new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_fg.png"),
                pumpInventory,
                TileEntityPump.FIELD_ENERGY_COUNT,
                TileEntityPump.FIELD_ENERGY_CAPACITY,
                WBar.Direction.UP), 0, 10, 18, 68);
        panel.add(new WFluidBar(
                new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_bg.png"),
                new ResourceLocation("darkmachinations","textures/gui/machine/fluid_bar_fg.png"),
                pump.fluidTank,
                WFluidBar.Direction.RIGHT),20, 10, 18*2, 68);
        panel.add(new WLabel("Pump"), 0, 0);
    }

}
