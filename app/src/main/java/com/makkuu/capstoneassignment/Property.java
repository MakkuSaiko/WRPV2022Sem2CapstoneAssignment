package com.makkuu.capstoneassignment;
import java.util.ArrayList;

public class Property<T> {
    private T value;
    private ArrayList<PropertyListener<T>> listeners;

    public Property()
    {
        listeners= new ArrayList<>();
    }

    public Property(T value) {
        this.value = value;
        listeners = new ArrayList<>();
    }

    public T get()
    {
        return value;
    }

    /**
     * Update the value to result in changes being notified.
     * @param newValue
     */
    public void set(T newValue)
    {
        T oldValue = value;
        value = newValue;
        notifyListeners(oldValue);
    }

    /**
     * Set without updating
     * @param newValue
     */
    public void reset(T newValue)
    {
        value = newValue;
    }

    public void addListener(PropertyListener<T> listener)
    {
        listeners.add(listener);
    }

    public void addListeners(PropertyListener<T>... listeners)
    {
        for (PropertyListener<T> listener:listeners) {
            addListener(listener);
        }
    }

    public void removeListener(PropertyListener<T> listener)
    {
        listeners.remove(listener);
    }   //Remove listeners

    protected void notifyListeners(T oldValue)      //Calls valueChanged for each propertyListener
    {
        for (PropertyListener<T> listener:listeners)
        {
            listener.valueChanged(this, oldValue, value);

        }
    }
}
