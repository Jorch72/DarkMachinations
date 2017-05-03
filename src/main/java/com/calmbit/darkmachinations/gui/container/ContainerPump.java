package com.calmbit.darkmachinations.gui.container;

import com.calmbit.darkmachinations.tileentity.TileEntityPump;
import com.elytradev.concrete.inventory.gui.widget.WBar;
import com.elytradev.concrete.inventory.gui.widget.WFluidBar;
import com.elytradev.concrete.inventory.gui.widget.WGridPanel;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ContainerPump extends ContainerBase<TileEntityPump> {

    public static final int PUMP_BLOCK_SLOT= 0;

    public ContainerPump(TileEntityPump pump, IInventory playerInventory, IInventory pumpInventory)
    {
        super(pump, playerInventory, pumpInventory);

        WGridPanel panel = new WGridPanel();
        this.setRootPanel(panel);

        panel.add(WItemSlot.of(pumpInventory, 0), 4, 1);
        panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 4);
        panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 8);
        panel.add(new WBar(
                new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_bg.png"),
                new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_fg.png"),
                pumpInventory,
                TileEntityPump.FIELD_ENERGY_COUNT,
                TileEntityPump.FIELD_ENERGY_CAPACITY,
                WBar.Direction.UP), 0, 0, 1, 3);
        panel.add(new WFluidBar(
                new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_bg.png"),
                pump.fluidTank,
                WFluidBar.Direction.UP), 1,0,2,3);
    }

}
