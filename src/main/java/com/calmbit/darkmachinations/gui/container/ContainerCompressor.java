package com.calmbit.darkmachinations.gui.container;

import com.calmbit.darkmachinations.tileentity.TileEntityCompressor;
import com.calmbit.darkmachinations.tileentity.TileEntityGenerator;
import com.elytradev.concrete.inventory.StandardMachineSlots;
import com.elytradev.concrete.inventory.gui.widget.WBar;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;
import com.elytradev.concrete.inventory.gui.widget.WLabel;
import com.elytradev.concrete.inventory.gui.widget.WPlainPanel;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class ContainerCompressor extends ContainerBase<TileEntityCompressor> {

    public ContainerCompressor(TileEntityCompressor compressor, IInventory playerInventory, IInventory compressorInventory)
    {
        super(compressor, playerInventory, compressorInventory);

        WPlainPanel panel = new WPlainPanel();
        this.setRootPanel(panel);

        panel.add(WItemSlot.of(compressorInventory, StandardMachineSlots.INPUT), 4*18, (int)(1.75f*18));
        panel.add(WItemSlot.of(compressorInventory, StandardMachineSlots.OUTPUT), 7*18, (int)(1.75f*18));
        panel.add(WItemSlot.ofPlayerStorage(playerInventory), 0, 84);
        panel.add(WItemSlot.of(playerInventory, 0, 9, 1), 0, 144);
        panel.add(new WBar(
                new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_bg.png"),
                new ResourceLocation("darkmachinations","textures/gui/machine/energy_bar_fg.png"),
                compressorInventory,
                TileEntityCompressor.FIELD_ENERGY_COUNT,
                TileEntityCompressor.FIELD_ENERGY_CAPACITY,
                WBar.Direction.UP), 0, 10, 18, 68);
        panel.add(new WBar(
                new ResourceLocation("darkmachinations", "textures/gui/machine/compressor_arrow_down_bg.png"),
                new ResourceLocation("darkmachinations", "textures/gui/machine/compressor_arrow_down_fg.png"),
                compressorInventory,
                TileEntityCompressor.FIELD_ITEM_PROCESSING_TIME,
                TileEntityCompressor.FIELD_ITEM_PROCESSING_MAX,
                WBar.Direction.UP), (int)(3.6*18), 8, 32, 22);
        panel.add(new WBar(
                new ResourceLocation("darkmachinations", "textures/gui/machine/compressor_arrow_up_bg.png"),
                new ResourceLocation("darkmachinations", "textures/gui/machine/compressor_arrow_up_fg.png"),
                compressorInventory,
                TileEntityCompressor.FIELD_ITEM_PROCESSING_TIME,
                TileEntityCompressor.FIELD_ITEM_PROCESSING_MAX,
                WBar.Direction.DOWN), (int)(3.6*18), 48, 32, 22);
        panel.add(new WBar(
                new ResourceLocation("darkmachinations", "textures/gui/machine/arrow_right_bg.png"),
                new ResourceLocation("darkmachinations", "textures/gui/machine/arrow_right_fg.png"),
                compressorInventory,
                TileEntityCompressor.FIELD_ITEM_PROCESSING_TIME,
                TileEntityCompressor.FIELD_ITEM_PROCESSING_MAX,
                WBar.Direction.RIGHT), (int)(5.5*18), (int)(3.75*8), 24, 17);
        panel.add(new WLabel("Compressor"), 0, 0);
    }
}
