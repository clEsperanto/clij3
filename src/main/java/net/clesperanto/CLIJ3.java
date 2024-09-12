package net.clesperanto;

import java.util.HashMap;
import java.util.Map;

import ij.ImagePlus;

import net.clesperanto.core.DeviceJ;
import net.clesperanto.core.MemoryJ;
import net.clesperanto.core.BackendJ;
import net.clesperanto.core.ArrayJ;

import net.clesperanto.kernels.*;

import net.clesperanto.imglib2.ImgLib2Converters;
import net.clesperanto.imagej.ImageJConverters;

import net.imglib2.RandomAccessibleInterval;


public class CLIJ3 {

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

    public RandomAccessibleInterval pullRAI(Object image) {
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
                return (T) ImageJConverters.copyImagePlus2ToArrayJ((ImagePlus) source, device, "buffer");
            }
            // if source is an ImgLib2 Img<> and targetClass is ArrayJ
            if (source instanceof RandomAccessibleInterval && targetClass == ArrayJ.class) {
                return (T) ImgLib2Converters.copyImgLib2ToArrayJ((RandomAccessibleInterval) source, device, "buffer");
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
        ImagePlus image = pull(gpu_image);
        image.resetDisplayRange();
        image.show();
    }

    public ArrayJ create(long width, long height, long depth) {
        return MemoryJ.makeFloatBuffer(this.device, new long[] { width, height, depth }, "buffer");
    }

    public ArrayJ create(long width, long height, long depth, String data_type) {
        switch (data_type) {
            case "float":
                return MemoryJ.makeFloatBuffer(this.device, new long[] { width, height, depth }, "buffer");
            case "int":
                return MemoryJ.makeIntBuffer(this.device, new long[] { width, height, depth }, "buffer");
            case "short":
                return MemoryJ.makeShortBuffer(this.device, new long[] { width, height, depth }, "buffer");
            case "char":
                return MemoryJ.makeByteBuffer(this.device, new long[] { width, height, depth }, "buffer");
            case "uint":
                return MemoryJ.makeUIntBuffer(this.device, new long[] { width, height, depth }, "buffer");
            case "ushort":
                return MemoryJ.makeUShortBuffer(this.device, new long[] { width, height, depth }, "buffer");
            case "uchar":
                return MemoryJ.makeUByteBuffer(this.device, new long[] { width, height, depth }, "buffer");    
            default:
                throw new IllegalArgumentException("Data type " + data_type + " not supported.");
        }
    }

    public ArrayJ create_like(ArrayJ source) {
        String data_type = source.getDataType();
        switch (data_type) {
            case "float":
                return MemoryJ.makeFloatBuffer(this.device, source.getDimensions(), "buffer");
            case "int":
                return MemoryJ.makeIntBuffer(this.device, source.getDimensions(), "buffer");
            case "short":
                return MemoryJ.makeShortBuffer(this.device, source.getDimensions(), "buffer");
            case "char":
                return MemoryJ.makeByteBuffer(this.device, source.getDimensions(), "buffer");
            case "uint":
                return MemoryJ.makeUIntBuffer(this.device, source.getDimensions(), "buffer");
            case "ushort":
                return MemoryJ.makeUShortBuffer(this.device, source.getDimensions(), "buffer");
            case "uchar":
                return MemoryJ.makeUByteBuffer(this.device, source.getDimensions(), "buffer");    
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

    /* FUNCTIONS */

    public ArrayJ add_image_and_scalar(Object source, Object target, float scalar) {
        return Tier1.addImageAndScalar(device, push(source), (ArrayJ) target, scalar);
    }

    public ArrayJ gaussian_blur(Object source, Object target, float sigma_x, float sigma_y, float sigma_z) {
        return Tier1.gaussianBlur(device, push(source), (ArrayJ) target, sigma_x, sigma_y, sigma_z);
    }

    public ArrayJ absolute(Object source, Object target) {
        return Tier1.absolute(device, push(source), (ArrayJ) target);
    }

    public ArrayJ threshold_otsu(Object source, Object target) {
        return Tier4.thresholdOtsu(device, push(source), (ArrayJ) target);
    }

    public ArrayJ connected_components_labeling(Object source, Object target) {
        return Tier5.connectedComponentsLabeling(device, push(source), (ArrayJ) target, "box");
    }
}
