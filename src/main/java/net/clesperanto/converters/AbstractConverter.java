package net.clesperanto.converters;

import net.clesperanto.CLIJ3;

public abstract class AbstractConverter<S, T> implements ConverterPlugin<S, T> {
    protected CLIJ3 cle;

    @Override
    public void setCLE(CLIJ3 cle) {
        this.cle = cle;
    }
}
