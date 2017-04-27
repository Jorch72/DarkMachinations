package com.calmbit.darkmachinations.gui.container;

import com.calmbit.darkmachinations.tileentity.TileEntityGenerator;
import com.elytradev.concrete.inventory.gui.widget.WBar;
import com.elytradev.concrete.inventory.gui.widget.WBar.Direction;
import com.elytradev.concrete.inventory.gui.widget.WGridPanel;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ContainerGenerator extends ContainerBase<TileEntityGenerator> {

    public static final int GENERATOR_SUPPLY_SLOT= 0;

    public ContainerGenerator(TileEntityGenerator generator, IInventory playerInventory)
    {
        super(generator, playerInventory);

        IInventory generatorInventory = generator.getContainerInventory();

        WGridPanel panel = new WGridPanel();
        this.setRootPanel(panel);

        panel.add(WItemSlot.of(generatorInventory, 0), 4, 1);
        panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 4);
        panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 8);
        panel.add(new WBar(
                new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_bg.png"),
                new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_fg.png"),
                generatorInventory,
                TileEntityGenerator.FIELD_ENERGY_COUNT,
                TileEntityGenerator.FIELD_ENERGY_CAPACITY,
                Direction.UP), 0, 0, 1, 3);
    }

}
