package com.makkuu.capstoneassignment;

@FunctionalInterface
public interface PropertyListener<T> {

    public void valueChanged(Property<T> property, T oldValue, T newValue);

}
