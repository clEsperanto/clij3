package net.clesperanto.converters;

import net.clesperanto.CLIJ3;
import org.scijava.plugin.SciJavaPlugin;

public interface ConverterPlugin<S, T> extends SciJavaPlugin {
    void setCLE(CLIJ3 cle);
    T convert(S source);
    Class<S> getSourceType();
    Class<T> getTargetType();
}
