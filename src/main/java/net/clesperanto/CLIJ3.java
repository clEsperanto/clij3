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

                return (T) ImageJConverters.copyImagePlus2ToArrayJ((ImagePlus) source, device, MemoryType.BUFFER);
            }
            // if source is an ImgLib2 Img<> and targetClass is ArrayJ
            if (source instanceof RandomAccessibleInterval && targetClass == ArrayJ.class) {
                return (T) ImgLib2Converters.copyImgLib2ToArrayJ((RandomAccessibleInterval) source, device,
                        MemoryType.BUFFER);
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
        return device.createArray(DataType.FLOAT32, MemoryType.BUFFER,
                new long[] { width, height, depth });
        // return MemoryJ.makeFloatBuffer(this.device, new long[] { width, height, depth
        // }, "buffer");
    }

    public ArrayJ create(long width, long height, long depth, String data_type) {
        switch (data_type) {
            case "float":
                return device.createArray(DataType.FLOAT32, MemoryType.BUFFER, new long[] { width, height, depth });
            // return MemoryJ.makeFloatBuffer(this.device, new long[] { width, height, depth
            // }, "buffer");
            case "int":
                return device.createArray(DataType.INT32, MemoryType.BUFFER, new long[] { width, height, depth });
            // return MemoryJ.makeIntBuffer(this.device, new long[] { width, height, depth
            // }, "buffer");
            case "short":
                return device.createArray(DataType.INT16, MemoryType.BUFFER, new long[] { width, height, depth });
            // return MemoryJ.makeShortBuffer(this.device, new long[] { width, height, depth
            // }, "buffer");
            case "char":
                return device.createArray(DataType.INT8, MemoryType.BUFFER, new long[] { width, height, depth });
            // return MemoryJ.makeByteBuffer(this.device, new long[] { width, height, depth
            // }, "buffer");
            case "uint":
                return device.createArray(DataType.UINT32, MemoryType.BUFFER, new long[] { width, height, depth });
            // return MemoryJ.makeUIntBuffer(this.device, new long[] { width, height, depth
            // }, "buffer");
            case "ushort":
                return device.createArray(DataType.UINT16, MemoryType.BUFFER, new long[] { width, height, depth });
            // return MemoryJ.makeUShortBuffer(this.device, new long[] { width, height,
            // depth }, "buffer");
            case "uchar":
                return device.createArray(DataType.UINT8, MemoryType.BUFFER, new long[] { width, height, depth });
            // return MemoryJ.makeUByteBuffer(this.device, new long[] { width, height, depth
            // }, "buffer");
            default:
                throw new IllegalArgumentException("Data type " + data_type + " not supported.");
        }
    }

    public ArrayJ create_like(ArrayJ source) {
        // source.dataType()
        DataType data_type = source.dataType();
        switch (data_type) {
            case FLOAT32:
                return device.createArray(data_type, source.memoryType(),
                        new long[] { source.width(), source.height(), source.depth() });
            // return MemoryJ.makeFloatBuffer(this.device, source.getDimensions(),
            // "buffer");
            case INT32:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(),
                        source.depth() });
            // return MemoryJ.makeIntBuffer(this.device, source.getDimensions(), "buffer");
            case INT16:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(),
                        source.depth() });
            // return MemoryJ.makeShortBuffer(this.device, source.getDimensions(),
            // "buffer");
            case INT8:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(),
                        source.depth() });
            // return MemoryJ.makeByteBuffer(this.device, source.getDimensions(), "buffer");
            case UINT32:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(),
                        source.depth() });
            // return MemoryJ.makeUIntBuffer(this.device, source.getDimensions(), "buffer");
            case UINT16:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(),
                        source.depth() });
            // return MemoryJ.makeUShortBuffer(this.device, source.getDimensions(),
            // "buffer");
            case UINT8:
                return device.createArray(data_type, source.memoryType(), new long[] { source.width(), source.height(),
                        source.depth() });
            // return MemoryJ.makeUByteBuffer(this.device, source.getDimensions(),
            // "buffer");
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

    /* BEGIN AUTO-GENERATED FUNCTIONS */

    public ArrayJ absolute(Object input, Object output) {
        return Tier1.absolute(device, push(input), push(output));
    }

    public ArrayJ add_images_weighted(Object input0, Object input1, Object output, float factor0, float factor1) {
        return Tier1.addImagesWeighted(device, push(input0), push(input1), push(output), factor0, factor1);
    }

    public ArrayJ add_image_and_scalar(Object input, Object output, float scalar) {
        return Tier1.addImageAndScalar(device, push(input), push(output), scalar);
    }

    public ArrayJ binary_and(Object input0, Object input1, Object output) {
        return Tier1.binaryAnd(device, push(input0), push(input1), push(output));
    }

    public ArrayJ binary_edge_detection(Object input, Object output) {
        return Tier1.binaryEdgeDetection(device, push(input), push(output));
    }

    public ArrayJ binary_not(Object input, Object output) {
        return Tier1.binaryNot(device, push(input), push(output));
    }

    public ArrayJ binary_or(Object input0, Object input1, Object output) {
        return Tier1.binaryOr(device, push(input0), push(input1), push(output));
    }

    public ArrayJ binary_subtract(Object input0, Object input1, Object output) {
        return Tier1.binarySubtract(device, push(input0), push(input1), push(output));
    }

    public ArrayJ binary_xor(Object input0, Object input1, Object output) {
        return Tier1.binaryXor(device, push(input0), push(input1), push(output));
    }

    public ArrayJ binary_supinf(Object input, Object output) {
        return Tier1.binarySupinf(device, push(input), push(output));
    }

    public ArrayJ binary_infsup(Object input, Object output) {
        return Tier1.binaryInfsup(device, push(input), push(output));
    }

    public ArrayJ block_enumerate(Object input0, Object input1, Object output, int blocksize) {
        return Tier1.blockEnumerate(device, push(input0), push(input1), push(output), blocksize);
    }

    public ArrayJ convolve(Object input0, Object input1, Object output) {
        return Tier1.convolve(device, push(input0), push(input1), push(output));
    }

    public ArrayJ copy(Object input, Object output) {
        return Tier1.copy(device, push(input), push(output));
    }

    public ArrayJ copy_slice(Object input, Object output, int slice) {
        return Tier1.copySlice(device, push(input), push(output), slice);
    }

    public ArrayJ copy_horizontal_slice(Object input, Object output, int slice) {
        return Tier1.copyHorizontalSlice(device, push(input), push(output), slice);
    }

    public ArrayJ copy_vertical_slice(Object input, Object output, int slice) {
        return Tier1.copyVerticalSlice(device, push(input), push(output), slice);
    }

    public ArrayJ crop(Object input, Object output, int start_x, int start_y, int start_z, int width, int height,
            int depth) {
        return Tier1.crop(device, push(input), push(output), start_x, start_y, start_z, width, height, depth);
    }

    public ArrayJ cubic_root(Object input, Object output) {
        return Tier1.cubicRoot(device, push(input), push(output));
    }

    public ArrayJ detect_label_edges(Object input, Object output) {
        return Tier1.detectLabelEdges(device, push(input), push(output));
    }

    public ArrayJ dilate_box(Object input, Object output) {
        return Tier1.dilateBox(device, push(input), push(output));
    }

    public ArrayJ dilate_sphere(Object input, Object output) {
        return Tier1.dilateSphere(device, push(input), push(output));
    }

    public ArrayJ dilate(Object input, Object output, String connectivity) {
        return Tier1.dilate(device, push(input), push(output), connectivity);
    }

    public ArrayJ divide_images(Object input0, Object input1, Object output) {
        return Tier1.divideImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ divide_scalar_by_image(Object input, Object output, float scalar) {
        return Tier1.divideScalarByImage(device, push(input), push(output), scalar);
    }

    public ArrayJ equal(Object input0, Object input1, Object output) {
        return Tier1.equal(device, push(input0), push(input1), push(output));
    }

    public ArrayJ equal_constant(Object input, Object output, float scalar) {
        return Tier1.equalConstant(device, push(input), push(output), scalar);
    }

    public ArrayJ erode_box(Object input, Object output) {
        return Tier1.erodeBox(device, push(input), push(output));
    }

    public ArrayJ erode_sphere(Object input, Object output) {
        return Tier1.erodeSphere(device, push(input), push(output));
    }

    public ArrayJ erode(Object input, Object output, String connectivity) {
        return Tier1.erode(device, push(input), push(output), connectivity);
    }

    public ArrayJ exponential(Object input, Object output) {
        return Tier1.exponential(device, push(input), push(output));
    }

    public ArrayJ flip(Object input, Object output, boolean flip_x, boolean flip_y, boolean flip_z) {
        return Tier1.flip(device, push(input), push(output), flip_x, flip_y, flip_z);
    }

    public ArrayJ gaussian_blur(Object input, Object output, float sigma_x, float sigma_y, float sigma_z) {
        return Tier1.gaussianBlur(device, push(input), push(output), sigma_x, sigma_y, sigma_z);
    }

    public ArrayJ generate_distance_matrix(Object input0, Object input1, Object output) {
        return Tier1.generateDistanceMatrix(device, push(input0), push(input1), push(output));
    }

    public ArrayJ gradient_x(Object input, Object output) {
        return Tier1.gradientX(device, push(input), push(output));
    }

    public ArrayJ gradient_y(Object input, Object output) {
        return Tier1.gradientY(device, push(input), push(output));
    }

    public ArrayJ gradient_z(Object input, Object output) {
        return Tier1.gradientZ(device, push(input), push(output));
    }

    public ArrayJ greater(Object input0, Object input1, Object output) {
        return Tier1.greater(device, push(input0), push(input1), push(output));
    }

    public ArrayJ greater_constant(Object input, Object output, float scalar) {
        return Tier1.greaterConstant(device, push(input), push(output), scalar);
    }

    public ArrayJ greater_or_equal(Object input0, Object input1, Object output) {
        return Tier1.greaterOrEqual(device, push(input0), push(input1), push(output));
    }

    public ArrayJ greater_or_equal_constant(Object input, Object output, float scalar) {
        return Tier1.greaterOrEqualConstant(device, push(input), push(output), scalar);
    }

    public ArrayList<ArrayJ> hessian_eigenvalues(Object input, Object small_eigenvalue, Object middle_eigenvalue,
            Object large_eigenvalue) {
        return Tier1.hessianEigenvalues(device, push(input), push(small_eigenvalue), push(middle_eigenvalue),
                push(large_eigenvalue));
    }

    public ArrayJ laplace_box(Object input, Object output) {
        return Tier1.laplaceBox(device, push(input), push(output));
    }

    public ArrayJ laplace_diamond(Object input, Object output) {
        return Tier1.laplaceDiamond(device, push(input), push(output));
    }

    public ArrayJ laplace(Object input, Object output, String connectivity) {
        return Tier1.laplace(device, push(input), push(output), connectivity);
    }

    public ArrayJ local_cross_correlation(Object input0, Object input1, Object output) {
        return Tier1.localCrossCorrelation(device, push(input0), push(input1), push(output));
    }

    public ArrayJ logarithm(Object input, Object output) {
        return Tier1.logarithm(device, push(input), push(output));
    }

    public ArrayJ mask(Object input, Object mask, Object output) {
        return Tier1.mask(device, push(input), push(mask), push(output));
    }

    public ArrayJ mask_label(Object input0, Object input1, Object output, float label) {
        return Tier1.maskLabel(device, push(input0), push(input1), push(output), label);
    }

    public ArrayJ maximum_image_and_scalar(Object input, Object output, float scalar) {
        return Tier1.maximumImageAndScalar(device, push(input), push(output), scalar);
    }

    public ArrayJ maximum_images(Object input0, Object input1, Object output) {
        return Tier1.maximumImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ maximum_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.maximumBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ maximum(Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.maximum(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ maximum_x_projection(Object input, Object output) {
        return Tier1.maximumXProjection(device, push(input), push(output));
    }

    public ArrayJ maximum_y_projection(Object input, Object output) {
        return Tier1.maximumYProjection(device, push(input), push(output));
    }

    public ArrayJ maximum_z_projection(Object input, Object output) {
        return Tier1.maximumZProjection(device, push(input), push(output));
    }

    public ArrayJ mean_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.meanBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ mean_sphere(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.meanSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ mean(Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.mean(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ mean_x_projection(Object input, Object output) {
        return Tier1.meanXProjection(device, push(input), push(output));
    }

    public ArrayJ mean_y_projection(Object input, Object output) {
        return Tier1.meanYProjection(device, push(input), push(output));
    }

    public ArrayJ mean_z_projection(Object input, Object output) {
        return Tier1.meanZProjection(device, push(input), push(output));
    }

    public ArrayJ median_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.medianBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ median_sphere(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.medianSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ median(Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.median(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ minimum_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.minimumBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ minimum(Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.minimum(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ minimum_image_and_scalar(Object input, Object output, float scalar) {
        return Tier1.minimumImageAndScalar(device, push(input), push(output), scalar);
    }

    public ArrayJ minimum_images(Object input0, Object input1, Object output) {
        return Tier1.minimumImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ minimum_x_projection(Object input, Object output) {
        return Tier1.minimumXProjection(device, push(input), push(output));
    }

    public ArrayJ minimum_y_projection(Object input, Object output) {
        return Tier1.minimumYProjection(device, push(input), push(output));
    }

    public ArrayJ minimum_z_projection(Object input, Object output) {
        return Tier1.minimumZProjection(device, push(input), push(output));
    }

    public ArrayJ mode_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.modeBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ mode_sphere(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.modeSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ mode(Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.mode(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ modulo_images(Object input0, Object input1, Object output) {
        return Tier1.moduloImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ multiply_image_and_position(Object input, Object output, int dimension) {
        return Tier1.multiplyImageAndPosition(device, push(input), push(output), dimension);
    }

    public ArrayJ multiply_image_and_scalar(Object input, Object output, float scalar) {
        return Tier1.multiplyImageAndScalar(device, push(input), push(output), scalar);
    }

    public ArrayJ multiply_images(Object input0, Object input1, Object output) {
        return Tier1.multiplyImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ nan_to_num(Object input, Object output, float nan, float posinf, float neginf) {
        return Tier1.nanToNum(device, push(input), push(output), nan, posinf, neginf);
    }

    public ArrayJ nonzero_maximum_box(Object input, Object output0, Object output1) {
        return Tier1.nonzeroMaximumBox(device, push(input), push(output0), push(output1));
    }

    public ArrayJ nonzero_maximum_diamond(Object input, Object output0, Object output1) {
        return Tier1.nonzeroMaximumDiamond(device, push(input), push(output0), push(output1));
    }

    public ArrayJ nonzero_maximum(Object input, Object output0, Object output1, String connectivity) {
        return Tier1.nonzeroMaximum(device, push(input), push(output0), push(output1), connectivity);
    }

    public ArrayJ nonzero_minimum_box(Object input, Object output0, Object output1) {
        return Tier1.nonzeroMinimumBox(device, push(input), push(output0), push(output1));
    }

    public ArrayJ nonzero_minimum_diamond(Object input, Object output0, Object output1) {
        return Tier1.nonzeroMinimumDiamond(device, push(input), push(output0), push(output1));
    }

    public ArrayJ nonzero_minimum(Object input, Object output0, Object output1, String connectivity) {
        return Tier1.nonzeroMinimum(device, push(input), push(output0), push(output1), connectivity);
    }

    public ArrayJ not_equal(Object input0, Object input1, Object output) {
        return Tier1.notEqual(device, push(input0), push(input1), push(output));
    }

    public ArrayJ not_equal_constant(Object input, Object output, float scalar) {
        return Tier1.notEqualConstant(device, push(input), push(output), scalar);
    }

    public ArrayJ paste(Object input, Object output, int index_x, int index_y, int index_z) {
        return Tier1.paste(device, push(input), push(output), index_x, index_y, index_z);
    }

    public ArrayJ onlyzero_overwrite_maximum_box(Object input, Object output0, Object output1) {
        return Tier1.onlyzeroOverwriteMaximumBox(device, push(input), push(output0), push(output1));
    }

    public ArrayJ onlyzero_overwrite_maximum_diamond(Object input, Object output0, Object output1) {
        return Tier1.onlyzeroOverwriteMaximumDiamond(device, push(input), push(output0), push(output1));
    }

    public ArrayJ onlyzero_overwrite_maximum(Object input, Object output0, Object output1, String connectivity) {
        return Tier1.onlyzeroOverwriteMaximum(device, push(input), push(output0), push(output1), connectivity);
    }

    public ArrayJ power(Object input, Object output, float scalar) {
        return Tier1.power(device, push(input), push(output), scalar);
    }

    public ArrayJ power_images(Object input0, Object input1, Object output) {
        return Tier1.powerImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ range(Object input, Object output, int start_x, int stop_x, int step_x, int start_y, int stop_y,
            int step_y, int start_z, int stop_z, int step_z) {
        return Tier1.range(device, push(input), push(output), start_x, stop_x, step_x, start_y, stop_y, step_y, start_z,
                stop_z, step_z);
    }

    public ArrayJ read_values_from_positions(Object input, Object list, Object output) {
        return Tier1.readValuesFromPositions(device, push(input), push(list), push(output));
    }

    public ArrayJ replace_values(Object input0, Object input1, Object output) {
        return Tier1.replaceValues(device, push(input0), push(input1), push(output));
    }

    public ArrayJ replace_value(Object input, Object output, float scalar0, float scalar1) {
        return Tier1.replaceValue(device, push(input), push(output), scalar0, scalar1);
    }

    public ArrayJ maximum_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.maximumSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ minimum_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.minimumSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ multiply_matrix(Object input0, Object input1, Object output) {
        return Tier1.multiplyMatrix(device, push(input0), push(input1), push(output));
    }

    public ArrayJ reciprocal(Object input, Object output) {
        return Tier1.reciprocal(device, push(input), push(output));
    }

    public ArrayJ set(Object input, float scalar) {
        return Tier1.set(device, push(input), scalar);
    }

    public ArrayJ set_column(Object input, int column, float value) {
        return Tier1.setColumn(device, push(input), column, value);
    }

    public ArrayJ set_image_borders(Object input, float value) {
        return Tier1.setImageBorders(device, push(input), value);
    }

    public ArrayJ set_plane(Object input, int plane, float value) {
        return Tier1.setPlane(device, push(input), plane, value);
    }

    public ArrayJ set_ramp_x(Object input) {
        return Tier1.setRampX(device, push(input));
    }

    public ArrayJ set_ramp_y(Object input) {
        return Tier1.setRampY(device, push(input));
    }

    public ArrayJ set_ramp_z(Object input) {
        return Tier1.setRampZ(device, push(input));
    }

    public ArrayJ set_row(Object input, int row, float value) {
        return Tier1.setRow(device, push(input), row, value);
    }

    public ArrayJ set_nonzero_pixels_to_pixelindex(Object input, Object output, int offset) {
        return Tier1.setNonzeroPixelsToPixelindex(device, push(input), push(output), offset);
    }

    public ArrayJ set_where_x_equals_y(Object input, float value) {
        return Tier1.setWhereXEqualsY(device, push(input), value);
    }

    public ArrayJ set_where_x_greater_than_y(Object input, float value) {
        return Tier1.setWhereXGreaterThanY(device, push(input), value);
    }

    public ArrayJ set_where_x_smaller_than_y(Object input, float value) {
        return Tier1.setWhereXSmallerThanY(device, push(input), value);
    }

    public ArrayJ sign(Object input, Object output) {
        return Tier1.sign(device, push(input), push(output));
    }

    public ArrayJ smaller(Object input0, Object input1, Object output) {
        return Tier1.smaller(device, push(input0), push(input1), push(output));
    }

    public ArrayJ smaller_constant(Object input, Object output, float scalar) {
        return Tier1.smallerConstant(device, push(input), push(output), scalar);
    }

    public ArrayJ smaller_or_equal(Object input0, Object input1, Object output) {
        return Tier1.smallerOrEqual(device, push(input0), push(input1), push(output));
    }

    public ArrayJ smaller_or_equal_constant(Object input, Object output, float scalar) {
        return Tier1.smallerOrEqualConstant(device, push(input), push(output), scalar);
    }

    public ArrayJ sobel(Object input, Object output) {
        return Tier1.sobel(device, push(input), push(output));
    }

    public ArrayJ square_root(Object input, Object output) {
        return Tier1.squareRoot(device, push(input), push(output));
    }

    public ArrayJ std_z_projection(Object input, Object output) {
        return Tier1.stdZProjection(device, push(input), push(output));
    }

    public ArrayJ subtract_image_from_scalar(Object input, Object output, float scalar) {
        return Tier1.subtractImageFromScalar(device, push(input), push(output), scalar);
    }

    public ArrayJ sum_reduction_x(Object input, Object output, int blocksize) {
        return Tier1.sumReductionX(device, push(input), push(output), blocksize);
    }

    public ArrayJ sum_x_projection(Object input, Object output) {
        return Tier1.sumXProjection(device, push(input), push(output));
    }

    public ArrayJ sum_y_projection(Object input, Object output) {
        return Tier1.sumYProjection(device, push(input), push(output));
    }

    public ArrayJ sum_z_projection(Object input, Object output) {
        return Tier1.sumZProjection(device, push(input), push(output));
    }

    public ArrayJ transpose_xy(Object input, Object output) {
        return Tier1.transposeXy(device, push(input), push(output));
    }

    public ArrayJ transpose_xz(Object input, Object output) {
        return Tier1.transposeXz(device, push(input), push(output));
    }

    public ArrayJ transpose_yz(Object input, Object output) {
        return Tier1.transposeYz(device, push(input), push(output));
    }

    public ArrayJ undefined_to_zero(Object input, Object output) {
        return Tier1.undefinedToZero(device, push(input), push(output));
    }

    public ArrayJ variance_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.varianceBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ variance_sphere(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.varianceSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ variance(Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.variance(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ write_values_to_positions(Object input, Object output) {
        return Tier1.writeValuesToPositions(device, push(input), push(output));
    }

    public ArrayJ x_position_of_maximum_x_projection(Object input, Object output) {
        return Tier1.xPositionOfMaximumXProjection(device, push(input), push(output));
    }

    public ArrayJ x_position_of_minimum_x_projection(Object input, Object output) {
        return Tier1.xPositionOfMinimumXProjection(device, push(input), push(output));
    }

    public ArrayJ y_position_of_maximum_y_projection(Object input, Object output) {
        return Tier1.yPositionOfMaximumYProjection(device, push(input), push(output));
    }

    public ArrayJ y_position_of_minimum_y_projection(Object input, Object output) {
        return Tier1.yPositionOfMinimumYProjection(device, push(input), push(output));
    }

    public ArrayJ z_position_of_maximum_z_projection(Object input, Object output) {
        return Tier1.zPositionOfMaximumZProjection(device, push(input), push(output));
    }

    public ArrayJ z_position_of_minimum_z_projection(Object input, Object output) {
        return Tier1.zPositionOfMinimumZProjection(device, push(input), push(output));
    }

    public ArrayJ absolute_difference(Object input0, Object input1, Object output) {
        return Tier2.absoluteDifference(device, push(input0), push(input1), push(output));
    }

    public ArrayJ add_images(Object input0, Object input1, Object output) {
        return Tier2.addImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ bottom_hat_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.bottomHatBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ bottom_hat_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.bottomHatSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ bottom_hat(Object input, Object output, float radius_x, float radius_y, float radius_z,
            String connectivity) {
        return Tier2.bottomHat(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ clip(Object input, Object output, float min_intensity, float max_intensity) {
        return Tier2.clip(device, push(input), push(output), min_intensity, max_intensity);
    }

    public ArrayJ closing_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.closingBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ closing_sphere(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.closingSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ closing(Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier2.closing(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ concatenate_along_x(Object input0, Object input1, Object output) {
        return Tier2.concatenateAlongX(device, push(input0), push(input1), push(output));
    }

    public ArrayJ concatenate_along_y(Object input0, Object input1, Object output) {
        return Tier2.concatenateAlongY(device, push(input0), push(input1), push(output));
    }

    public ArrayJ concatenate_along_z(Object input0, Object input1, Object output) {
        return Tier2.concatenateAlongZ(device, push(input0), push(input1), push(output));
    }

    public ArrayJ count_touching_neighbors(Object input, Object output, boolean ignore_background) {
        return Tier2.countTouchingNeighbors(device, push(input), push(output), ignore_background);
    }

    public ArrayJ crop_border(Object input, Object output, int border_size) {
        return Tier2.cropBorder(device, push(input), push(output), border_size);
    }

    public ArrayJ divide_by_gaussian_background(Object input, Object output, float sigma_x, float sigma_y,
            float sigma_z) {
        return Tier2.divideByGaussianBackground(device, push(input), push(output), sigma_x, sigma_y, sigma_z);
    }

    public ArrayJ degrees_to_radians(Object input, Object output) {
        return Tier2.degreesToRadians(device, push(input), push(output));
    }

    public ArrayJ detect_maxima_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.detectMaximaBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ detect_maxima(Object input, Object output, int radius_x, int radius_y, int radius_z,
            String connectivity) {
        return Tier2.detectMaxima(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ detect_minima_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.detectMinimaBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ detect_minima(Object input, Object output, int radius_x, int radius_y, int radius_z,
            String connectivity) {
        return Tier2.detectMinima(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ difference_of_gaussian(Object input, Object output, float sigma1_x, float sigma1_y, float sigma1_z,
            float sigma2_x, float sigma2_y, float sigma2_z) {
        return Tier2.differenceOfGaussian(device, push(input), push(output), sigma1_x, sigma1_y, sigma1_z, sigma2_x,
                sigma2_y, sigma2_z);
    }

    public ArrayJ extend_labeling_via_voronoi(Object input, Object output) {
        return Tier2.extendLabelingViaVoronoi(device, push(input), push(output));
    }

    public ArrayJ invert(Object input, Object output) {
        return Tier2.invert(device, push(input), push(output));
    }

    public ArrayJ label_spots(Object input, Object output) {
        return Tier2.labelSpots(device, push(input), push(output));
    }

    public ArrayJ large_hessian_eigenvalue(Object input, Object output) {
        return Tier2.largeHessianEigenvalue(device, push(input), push(output));
    }

    public float maximum_of_all_pixels(Object input) {
        return Tier2.maximumOfAllPixels(device, push(input));
    }

    public float minimum_of_all_pixels(Object input) {
        return Tier2.minimumOfAllPixels(device, push(input));
    }

    public float minimum_of_masked_pixels(Object input, Object mask) {
        return Tier2.minimumOfMaskedPixels(device, push(input), push(mask));
    }

    public ArrayJ opening_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.openingBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ opening_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.openingSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ opening(Object input, Object output, float radius_x, float radius_y, float radius_z,
            String connectivity) {
        return Tier2.opening(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ radians_to_degrees(Object input, Object output) {
        return Tier2.radiansToDegrees(device, push(input), push(output));
    }

    public ArrayJ reduce_labels_to_label_edges(Object input, Object output) {
        return Tier2.reduceLabelsToLabelEdges(device, push(input), push(output));
    }

    public ArrayJ small_hessian_eigenvalue(Object input, Object output) {
        return Tier2.smallHessianEigenvalue(device, push(input), push(output));
    }

    public ArrayJ square(Object input, Object output) {
        return Tier2.square(device, push(input), push(output));
    }

    public ArrayJ squared_difference(Object input0, Object input1, Object output) {
        return Tier2.squaredDifference(device, push(input0), push(input1), push(output));
    }

    public ArrayJ standard_deviation_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.standardDeviationBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ standard_deviation_sphere(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.standardDeviationSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ standard_deviation(Object input, Object output, int radius_x, int radius_y, int radius_z,
            String connectivity) {
        return Tier2.standardDeviation(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ subtract_gaussian_background(Object input, Object output, float sigma_x, float sigma_y,
            float sigma_z) {
        return Tier2.subtractGaussianBackground(device, push(input), push(output), sigma_x, sigma_y, sigma_z);
    }

    public ArrayJ subtract_images(Object input0, Object input1, Object output) {
        return Tier2.subtractImages(device, push(input0), push(input1), push(output));
    }

    public float sum_of_all_pixels(Object input) {
        return Tier2.sumOfAllPixels(device, push(input));
    }

    public ArrayJ top_hat_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.topHatBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ top_hat_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.topHatSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ top_hat(Object input, Object output, float radius_x, float radius_y, float radius_z,
            String connectivity) {
        return Tier2.topHat(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayList<Float> bounding_box(Object input) {
        return Tier3.boundingBox(device, push(input));
    }

    public ArrayList<Float> center_of_mass(Object input) {
        return Tier3.centerOfMass(device, push(input));
    }

    public ArrayJ exclude_labels(Object input, Object list, Object output) {
        return Tier3.excludeLabels(device, push(input), push(list), push(output));
    }

    public ArrayJ exclude_labels_on_edges(Object input, Object output, boolean exclude_x, boolean exclude_y,
            boolean exclude_z) {
        return Tier3.excludeLabelsOnEdges(device, push(input), push(output), exclude_x, exclude_y, exclude_z);
    }

    public ArrayJ flag_existing_labels(Object input, Object output) {
        return Tier3.flagExistingLabels(device, push(input), push(output));
    }

    public ArrayJ gamma_correction(Object input, Object output, float gamma) {
        return Tier3.gammaCorrection(device, push(input), push(output), gamma);
    }

    public ArrayJ generate_binary_overlap_matrix(Object input0, Object input1, Object output) {
        return Tier3.generateBinaryOverlapMatrix(device, push(input0), push(input1), push(output));
    }

    public ArrayJ generate_touch_matrix(Object input, Object output) {
        return Tier3.generateTouchMatrix(device, push(input), push(output));
    }

    public ArrayJ histogram(Object input, Object output, int nbins, float min, float max) {
        return Tier3.histogram(device, push(input), push(output), nbins, min, max);
    }

    public float jaccard_index(Object input0, Object input1) {
        return Tier3.jaccardIndex(device, push(input0), push(input1));
    }

    public ArrayJ labelled_spots_to_pointlist(Object input, Object output) {
        return Tier3.labelledSpotsToPointlist(device, push(input), push(output));
    }

    public ArrayList<Float> maximum_position(Object input) {
        return Tier3.maximumPosition(device, push(input));
    }

    public float mean_of_all_pixels(Object input) {
        return Tier3.meanOfAllPixels(device, push(input));
    }

    public ArrayList<Float> minimum_position(Object input) {
        return Tier3.minimumPosition(device, push(input));
    }

    public ArrayJ morphological_chan_vese(Object input, Object output, int num_iter, int smoothing, float lambda1,
            float lambda2) {
        return Tier3.morphologicalChanVese(device, push(input), push(output), num_iter, smoothing, lambda1, lambda2);
    }

    public HashMap<String, ArrayList<Float>> statistics_of_labelled_pixels(Object input, Object intensity) {
        return Tier3.statisticsOfLabelledPixels(device, push(input), push(intensity));
    }

    public ArrayList<Float> label_bounding_box(Object input, int label_id) {
        return Tier4.labelBoundingBox(device, push(input), label_id);
    }

    public float mean_squared_error(Object input0, Object input1) {
        return Tier4.meanSquaredError(device, push(input0), push(input1));
    }

    public ArrayJ spots_to_pointlist(Object input, Object output) {
        return Tier4.spotsToPointlist(device, push(input), push(output));
    }

    public ArrayJ relabel_sequential(Object input, Object output, int blocksize) {
        return Tier4.relabelSequential(device, push(input), push(output), blocksize);
    }

    public ArrayJ threshold_otsu(Object input, Object output) {
        return Tier4.thresholdOtsu(device, push(input), push(output));
    }

    public boolean array_equal(Object input0, Object input1) {
        return Tier5.arrayEqual(device, push(input0), push(input1));
    }

    public ArrayJ combine_labels(Object input0, Object input1, Object output) {
        return Tier5.combineLabels(device, push(input0), push(input1), push(output));
    }

    public ArrayJ connected_components_labeling(Object input, Object output, String connectivity) {
        return Tier5.connectedComponentsLabeling(device, push(input), push(output), connectivity);
    }

    public ArrayJ dilate_labels(Object input, Object output, int radius) {
        return Tier6.dilateLabels(device, push(input), push(output), radius);
    }

    public ArrayJ erode_labels(Object input, Object output, int radius, boolean relabel) {
        return Tier6.erodeLabels(device, push(input), push(output), radius, relabel);
    }

    public ArrayJ gauss_otsu_labeling(Object input0, Object output, float outline_sigma) {
        return Tier6.gaussOtsuLabeling(device, push(input0), push(output), outline_sigma);
    }

    public ArrayJ masked_voronoi_labeling(Object input, Object mask, Object output) {
        return Tier6.maskedVoronoiLabeling(device, push(input), push(mask), push(output));
    }

    public ArrayJ voronoi_labeling(Object input, Object output) {
        return Tier6.voronoiLabeling(device, push(input), push(output));
    }

    public ArrayJ affine_transform(Object input, Object output, ArrayList<Float> transform_matrix, boolean interpolate,
            boolean resize) {
        return Tier7.affineTransform(device, push(input), push(output), transform_matrix, interpolate, resize);
    }

    public ArrayJ eroded_otsu_labeling(Object input, Object output, int number_of_erosions, float outline_sigma) {
        return Tier7.erodedOtsuLabeling(device, push(input), push(output), number_of_erosions, outline_sigma);
    }

    public ArrayJ rigid_transform(Object input, Object output, float translate_x, float translate_y, float translate_z,
            float angle_x, float angle_y, float angle_z, boolean centered, boolean interpolate, boolean resize) {
        return Tier7.rigidTransform(device, push(input), push(output), translate_x, translate_y, translate_z, angle_x,
                angle_y, angle_z, centered, interpolate, resize);
    }

    public ArrayJ rotate(Object input, Object output, float angle_x, float angle_y, float angle_z, boolean centered,
            boolean interpolate, boolean resize) {
        return Tier7.rotate(device, push(input), push(output), angle_x, angle_y, angle_z, centered, interpolate,
                resize);
    }

    public ArrayJ scale(Object input, Object output, float factor_x, float factor_y, float factor_z, boolean centered,
            boolean interpolate, boolean resize) {
        return Tier7.scale(device, push(input), push(output), factor_x, factor_y, factor_z, centered, interpolate,
                resize);
    }

    public ArrayJ translate(Object input, Object output, float translate_x, float translate_y, float translate_z,
            boolean interpolate) {
        return Tier7.translate(device, push(input), push(output), translate_x, translate_y, translate_z, interpolate);
    }

    public ArrayJ closing_labels(Object input, Object output, int radius) {
        return Tier7.closingLabels(device, push(input), push(output), radius);
    }

    public ArrayJ erode_connected_labels(Object input, Object output, int radius) {
        return Tier7.erodeConnectedLabels(device, push(input), push(output), radius);
    }

    public ArrayJ opening_labels(Object input, Object output, int radius) {
        return Tier7.openingLabels(device, push(input), push(output), radius);
    }

    public ArrayJ voronoi_otsu_labeling(Object input, Object output, float spot_sigma, float outline_sigma) {
        return Tier7.voronoiOtsuLabeling(device, push(input), push(output), spot_sigma, outline_sigma);
    }

    public ArrayJ smooth_labels(Object input, Object output, int radius) {
        return Tier8.smoothLabels(device, push(input), push(output), radius);
    }

    public ArrayJ smooth_connected_labels(Object input, Object output, int radius) {
        return Tier8.smoothConnectedLabels(device, push(input), push(output), radius);
    }

    /* END AUTO-GENERATED FUNCTIONS */

}
