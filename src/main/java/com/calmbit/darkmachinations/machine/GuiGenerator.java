package com.calmbit.darkmachinations.machine;

import com.elytradev.concrete.inventory.ConcreteItemStorage;
import com.elytradev.concrete.inventory.ValidatedInventoryView;
import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import com.elytradev.concrete.inventory.gui.client.ConcreteGui;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiGenerator extends ConcreteGui{

    private final ContainerGenerator generatorContainer;

    private WItemSlot itemSlots;
    public GuiGenerator(ConcreteContainer inventorySlotsIn) {
        super(inventorySlotsIn);
        this.generatorContainer = (ContainerGenerator) inventorySlotsIn;
    }





}
