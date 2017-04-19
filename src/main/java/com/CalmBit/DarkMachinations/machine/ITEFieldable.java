package com.calmbit.darkmachinations.machine;

public interface ITEFieldable {
    void setField(int id, int value);
    int getField(int id);
    int getSlotCount();
}
