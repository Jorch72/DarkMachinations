package com.calmbit.darkmachinations.gui;

import com.calmbit.darkmachinations.gui.container.ContainerGenerator;
import com.elytradev.concrete.inventory.gui.ConcreteContainer;
import com.elytradev.concrete.inventory.gui.client.ConcreteGui;
import com.elytradev.concrete.inventory.gui.widget.WItemSlot;

public class GuiGenerator extends ConcreteGui{

    private final ContainerGenerator generatorContainer;

    private WItemSlot itemSlots;
    public GuiGenerator(ConcreteContainer inventorySlotsIn) {
        super(inventorySlotsIn);
        this.generatorContainer = (ContainerGenerator) inventorySlotsIn;
    }





}
