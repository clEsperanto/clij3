/*-
 * #%L
 * clij3
 * %%
 * Copyright (C) 2023 - 2025 Robert Haase, Stéphane Rigaud, Institut Pasteur Paris, DFG Cluster of Excellence "Physice of Life" TU Dresden
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the clesperanto nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package net.clesperanto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ij.ImagePlus;

import net.clesperanto.core.DeviceJ;
import net.clesperanto.core.MemoryType;
import net.clesperanto.core.BackendJ;
import net.clesperanto.core.DataType;
import net.clesperanto.core.ArrayJ;

import net.clesperanto.kernels.*;
import net.clesperanto.imglib2.ImgLib2Converters;
import net.clesperanto.imagej.ImageJConverters;

import net.imglib2.RandomAccessibleInterval;

public class CLIJ3 implements CLIJ3Ops{

    DeviceJ device;

    @Deprecated
    public CLIJ3() {
        BackendJ.setBackend("opencl");
        device = DeviceJ.getDefaultDevice();
    }

    private static CLIJ3 instance = null;

    public static CLIJ3 getInstance() {
        if (instance == null) {
            instance = new CLIJ3();
        }
        return instance;
    }

    @Deprecated
    public DeviceJ getDevice() {
        return device;
    }

    public ArrayJ push(Object image) {
        return convert(image, ArrayJ.class);
    }

    public ImagePlus pull(Object image) {
        return convert(image, ImagePlus.class);
    }

    public RandomAccessibleInterval<?> pullRAI(Object image) {
        return convert(image, RandomAccessibleInterval.class);
    }

    public <S, T> T convert(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        if (targetClass.isAssignableFrom(source.getClass())) {
            return (T) source;
        }
        synchronized (this) {
            // if source is an ImagePlus and targetClass is ArrayJ
            if (source instanceof ImagePlus && targetClass == ArrayJ.class) {
                return (T) ImageJConverters.copyImagePlus2ToArrayJ((ImagePlus) source, device, MemoryType.BUFFER);
            }
            // if source is an ImgLib2 Img<> and targetClass is ArrayJ
            if (source instanceof RandomAccessibleInterval && targetClass == ArrayJ.class) {
                return (T) ImgLib2Converters.copyImgLib2ToArrayJ((RandomAccessibleInterval) source, device, MemoryType.BUFFER);
            }
            // if source is an ArrayJ and targetClass is ImagePlus
            if (source instanceof ArrayJ && targetClass == ImagePlus.class) {
                return (T) ImageJConverters.copyArrayJToImagePlus((ArrayJ) source);
            }
            if (source instanceof ArrayJ && targetClass == RandomAccessibleInterval.class) {
                return (T) ImgLib2Converters.copyArrayJToImgLib2((ArrayJ) source);
            }
            // throw an exception if conversion is not possible
            throw new IllegalArgumentException(
                    "Conversion from " + source.getClass() + " to " + targetClass + " is not supported.");
        }
    }

    public void imshow(Object gpu_image) {
        ImagePlus image = this.pull(gpu_image);
        image.resetDisplayRange();
        image.show();
    }

    public ArrayJ create(long width, long height, long depth) {
        return device.createArray(DataType.FLOAT32, MemoryType.BUFFER, new long[] { width, height, depth });
    }

    public ArrayJ create(long width, long height, long depth, String data_type) {
        switch (data_type) {
            case "float":
                return device.createArray(DataType.FLOAT32, MemoryType.BUFFER, new long[] { width, height, depth });
            case "int":
                return device.createArray(DataType.INT32, MemoryType.BUFFER, new long[] { width, height, depth });
            case "short":
                return device.createArray(DataType.INT16, MemoryType.BUFFER, new long[] { width, height, depth });
            case "char":
                return device.createArray(DataType.INT8, MemoryType.BUFFER, new long[] { width, height, depth });
            case "uint":
                return device.createArray(DataType.UINT32, MemoryType.BUFFER, new long[] { width, height, depth });
            case "ushort":
                return device.createArray(DataType.UINT16, MemoryType.BUFFER, new long[] { width, height, depth });
            case "uchar":
                return device.createArray(DataType.UINT8, MemoryType.BUFFER, new long[] { width, height, depth });
            default:
                throw new IllegalArgumentException("Data type " + data_type + " not supported.");
        }
    }

    public ArrayJ create_like(ArrayJ source) {
        DataType data_type = source.dataType();
        switch (data_type) {
            case FLOAT32:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(), source.depth() });
            case INT32:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(), source.depth() });
            case INT16:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(), source.depth() });
            case INT8:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(), source.depth() });
            case UINT32: 
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(), source.depth() });
            case UINT16:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(), source.depth() });
            case UINT8:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(), source.depth() });
            default:
                throw new IllegalArgumentException("Data type " + data_type + " not supported.");
        }
    }

    public ArrayJ create_like(ImagePlus source) {
        Map<Integer, String> bit_depth_map = new HashMap<>();
        bit_depth_map.put(8, "uchar");
        bit_depth_map.put(16, "ushort");
        bit_depth_map.put(32, "float");
        String data_type = bit_depth_map.get(source.getBitDepth());
        return this.create(source.getWidth(), source.getHeight(), source.getNSlices(), data_type);
    }

}
