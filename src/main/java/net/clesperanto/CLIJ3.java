package net.clesperanto;

import java.util.ArrayList;
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

    /* BEGIN AUTO-GENERATED FUNCTIONS */

    public ArrayJ absolute (Object input, Object output) {
        return Tier1.absolute(device, push(input), push(output));
    }

    public ArrayJ addImagesWeighted (Object input0, Object input1, Object output, float factor0, float factor1) {
        return Tier1.addImagesWeighted(device, push(input0), push(input1), push(output), factor0, factor1);
    }

    public ArrayJ addImageAndScalar (Object input, Object output, float scalar) {
        return Tier1.addImageAndScalar(device, push(input), push(output), scalar);
    }

    public ArrayJ binaryAnd (Object input0, Object input1, Object output) {
        return Tier1.binaryAnd(device, push(input0), push(input1), push(output));
    }

    public ArrayJ binaryEdgeDetection (Object input, Object output) {
        return Tier1.binaryEdgeDetection(device, push(input), push(output));
    }

    public ArrayJ binaryNot (Object input, Object output) {
        return Tier1.binaryNot(device, push(input), push(output));
    }

    public ArrayJ binaryOr (Object input0, Object input1, Object output) {
        return Tier1.binaryOr(device, push(input0), push(input1), push(output));
    }

    public ArrayJ binarySubtract (Object input0, Object input1, Object output) {
        return Tier1.binarySubtract(device, push(input0), push(input1), push(output));
    }

    public ArrayJ binaryXor (Object input0, Object input1, Object output) {
        return Tier1.binaryXor(device, push(input0), push(input1), push(output));
    }

    public ArrayJ binarySupinf (Object input, Object output) {
        return Tier1.binarySupinf(device, push(input), push(output));
    }

    public ArrayJ binaryInfsup (Object input, Object output) {
        return Tier1.binaryInfsup(device, push(input), push(output));
    }

    public ArrayJ blockEnumerate (Object input0, Object input1, Object output, int blocksize) {
        return Tier1.blockEnumerate(device, push(input0), push(input1), push(output), blocksize);
    }

    public ArrayJ convolve (Object input0, Object input1, Object output) {
        return Tier1.convolve(device, push(input0), push(input1), push(output));
    }

    public ArrayJ copy (Object input, Object output) {
        return Tier1.copy(device, push(input), push(output));
    }

    public ArrayJ copySlice (Object input, Object output, int slice) {
        return Tier1.copySlice(device, push(input), push(output), slice);
    }

    public ArrayJ copyHorizontalSlice (Object input, Object output, int slice) {
        return Tier1.copyHorizontalSlice(device, push(input), push(output), slice);
    }

    public ArrayJ copyVerticalSlice (Object input, Object output, int slice) {
        return Tier1.copyVerticalSlice(device, push(input), push(output), slice);
    }

    public ArrayJ crop (Object input, Object output, int start_x, int start_y, int start_z, int width, int height, int depth) {
        return Tier1.crop(device, push(input), push(output), start_x, start_y, start_z, width, height, depth);
    }

    public ArrayJ cubicRoot (Object input, Object output) {
        return Tier1.cubicRoot(device, push(input), push(output));
    }

    public ArrayJ detectLabelEdges (Object input, Object output) {
        return Tier1.detectLabelEdges(device, push(input), push(output));
    }

    public ArrayJ dilateBox (Object input, Object output) {
        return Tier1.dilateBox(device, push(input), push(output));
    }

    public ArrayJ dilateSphere (Object input, Object output) {
        return Tier1.dilateSphere(device, push(input), push(output));
    }

    public ArrayJ dilate (Object input, Object output, String connectivity) {
        return Tier1.dilate(device, push(input), push(output), connectivity);
    }

    public ArrayJ divideImages (Object input0, Object input1, Object output) {
        return Tier1.divideImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ divideScalarByImage (Object input, Object output, float scalar) {
        return Tier1.divideScalarByImage(device, push(input), push(output), scalar);
    }

    public ArrayJ equal (Object input0, Object input1, Object output) {
        return Tier1.equal(device, push(input0), push(input1), push(output));
    }

    public ArrayJ equalConstant (Object input, Object output, float scalar) {
        return Tier1.equalConstant(device, push(input), push(output), scalar);
    }

    public ArrayJ erodeBox (Object input, Object output) {
        return Tier1.erodeBox(device, push(input), push(output));
    }

    public ArrayJ erodeSphere (Object input, Object output) {
        return Tier1.erodeSphere(device, push(input), push(output));
    }

    public ArrayJ erode (Object input, Object output, String connectivity) {
        return Tier1.erode(device, push(input), push(output), connectivity);
    }

    public ArrayJ exponential (Object input, Object output) {
        return Tier1.exponential(device, push(input), push(output));
    }

    public ArrayJ flip (Object input, Object output, boolean flip_x, boolean flip_y, boolean flip_z) {
        return Tier1.flip(device, push(input), push(output), flip_x, flip_y, flip_z);
    }

    public ArrayJ gaussianBlur (Object input, Object output, float sigma_x, float sigma_y, float sigma_z) {
        return Tier1.gaussianBlur(device, push(input), push(output), sigma_x, sigma_y, sigma_z);
    }

    public ArrayJ generateDistanceMatrix (Object input0, Object input1, Object output) {
        return Tier1.generateDistanceMatrix(device, push(input0), push(input1), push(output));
    }

    public ArrayJ gradientX (Object input, Object output) {
        return Tier1.gradientX(device, push(input), push(output));
    }

    public ArrayJ gradientY (Object input, Object output) {
        return Tier1.gradientY(device, push(input), push(output));
    }

    public ArrayJ gradientZ (Object input, Object output) {
        return Tier1.gradientZ(device, push(input), push(output));
    }

    public ArrayJ greater (Object input0, Object input1, Object output) {
        return Tier1.greater(device, push(input0), push(input1), push(output));
    }

    public ArrayJ greaterConstant (Object input, Object output, float scalar) {
        return Tier1.greaterConstant(device, push(input), push(output), scalar);
    }

    public ArrayJ greaterOrEqual (Object input0, Object input1, Object output) {
        return Tier1.greaterOrEqual(device, push(input0), push(input1), push(output));
    }

    public ArrayJ greaterOrEqualConstant (Object input, Object output, float scalar) {
        return Tier1.greaterOrEqualConstant(device, push(input), push(output), scalar);
    }

    public ArrayList<ArrayJ> hessianEigenvalues (Object input, Object small_eigenvalue, Object middle_eigenvalue, Object large_eigenvalue) {
        return Tier1.hessianEigenvalues(device, push(input), push(small_eigenvalue), push(middle_eigenvalue), push(large_eigenvalue));
    }

    public ArrayJ laplaceBox (Object input, Object output) {
        return Tier1.laplaceBox(device, push(input), push(output));
    }

    public ArrayJ laplaceDiamond (Object input, Object output) {
        return Tier1.laplaceDiamond(device, push(input), push(output));
    }

    public ArrayJ laplace (Object input, Object output, String connectivity) {
        return Tier1.laplace(device, push(input), push(output), connectivity);
    }

    public ArrayJ localCrossCorrelation (Object input0, Object input1, Object output) {
        return Tier1.localCrossCorrelation(device, push(input0), push(input1), push(output));
    }

    public ArrayJ logarithm (Object input, Object output) {
        return Tier1.logarithm(device, push(input), push(output));
    }

    public ArrayJ mask (Object input, Object mask, Object output) {
        return Tier1.mask(device, push(input), push(mask), push(output));
    }

    public ArrayJ maskLabel (Object input0, Object input1, Object output, float label) {
        return Tier1.maskLabel(device, push(input0), push(input1), push(output), label);
    }

    public ArrayJ maximumImageAndScalar (Object input, Object output, float scalar) {
        return Tier1.maximumImageAndScalar(device, push(input), push(output), scalar);
    }

    public ArrayJ maximumImages (Object input0, Object input1, Object output) {
        return Tier1.maximumImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ maximumBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.maximumBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ maximum (Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.maximum(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ maximumXProjection (Object input, Object output) {
        return Tier1.maximumXProjection(device, push(input), push(output));
    }

    public ArrayJ maximumYProjection (Object input, Object output) {
        return Tier1.maximumYProjection(device, push(input), push(output));
    }

    public ArrayJ maximumZProjection (Object input, Object output) {
        return Tier1.maximumZProjection(device, push(input), push(output));
    }

    public ArrayJ meanBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.meanBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ meanSphere (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.meanSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ mean (Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.mean(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ meanXProjection (Object input, Object output) {
        return Tier1.meanXProjection(device, push(input), push(output));
    }

    public ArrayJ meanYProjection (Object input, Object output) {
        return Tier1.meanYProjection(device, push(input), push(output));
    }

    public ArrayJ meanZProjection (Object input, Object output) {
        return Tier1.meanZProjection(device, push(input), push(output));
    }

    public ArrayJ medianBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.medianBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ medianSphere (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.medianSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ median (Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.median(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ minimumBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.minimumBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ minimum (Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.minimum(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ minimumImageAndScalar (Object input, Object output, float scalar) {
        return Tier1.minimumImageAndScalar(device, push(input), push(output), scalar);
    }

    public ArrayJ minimumImages (Object input0, Object input1, Object output) {
        return Tier1.minimumImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ minimumXProjection (Object input, Object output) {
        return Tier1.minimumXProjection(device, push(input), push(output));
    }

    public ArrayJ minimumYProjection (Object input, Object output) {
        return Tier1.minimumYProjection(device, push(input), push(output));
    }

    public ArrayJ minimumZProjection (Object input, Object output) {
        return Tier1.minimumZProjection(device, push(input), push(output));
    }

    public ArrayJ modeBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.modeBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ modeSphere (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.modeSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ mode (Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.mode(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ moduloImages (Object input0, Object input1, Object output) {
        return Tier1.moduloImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ multiplyImageAndPosition (Object input, Object output, int dimension) {
        return Tier1.multiplyImageAndPosition(device, push(input), push(output), dimension);
    }

    public ArrayJ multiplyImageAndScalar (Object input, Object output, float scalar) {
        return Tier1.multiplyImageAndScalar(device, push(input), push(output), scalar);
    }

    public ArrayJ multiplyImages (Object input0, Object input1, Object output) {
        return Tier1.multiplyImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ nanToNum (Object input, Object output, float nan, float posinf, float neginf) {
        return Tier1.nanToNum(device, push(input), push(output), nan, posinf, neginf);
    }

    public ArrayJ nonzeroMaximumBox (Object input, Object output0, Object output1) {
        return Tier1.nonzeroMaximumBox(device, push(input), push(output0), push(output1));
    }

    public ArrayJ nonzeroMaximumDiamond (Object input, Object output0, Object output1) {
        return Tier1.nonzeroMaximumDiamond(device, push(input), push(output0), push(output1));
    }

    public ArrayJ nonzeroMaximum (Object input, Object output0, Object output1, String connectivity) {
        return Tier1.nonzeroMaximum(device, push(input), push(output0), push(output1), connectivity);
    }

    public ArrayJ nonzeroMinimumBox (Object input, Object output0, Object output1) {
        return Tier1.nonzeroMinimumBox(device, push(input), push(output0), push(output1));
    }

    public ArrayJ nonzeroMinimumDiamond (Object input, Object output0, Object output1) {
        return Tier1.nonzeroMinimumDiamond(device, push(input), push(output0), push(output1));
    }

    public ArrayJ nonzeroMinimum (Object input, Object output0, Object output1, String connectivity) {
        return Tier1.nonzeroMinimum(device, push(input), push(output0), push(output1), connectivity);
    }

    public ArrayJ notEqual (Object input0, Object input1, Object output) {
        return Tier1.notEqual(device, push(input0), push(input1), push(output));
    }

    public ArrayJ notEqualConstant (Object input, Object output, float scalar) {
        return Tier1.notEqualConstant(device, push(input), push(output), scalar);
    }

    public ArrayJ paste (Object input, Object output, int index_x, int index_y, int index_z) {
        return Tier1.paste(device, push(input), push(output), index_x, index_y, index_z);
    }

    public ArrayJ onlyzeroOverwriteMaximumBox (Object input, Object output0, Object output1) {
        return Tier1.onlyzeroOverwriteMaximumBox(device, push(input), push(output0), push(output1));
    }

    public ArrayJ onlyzeroOverwriteMaximumDiamond (Object input, Object output0, Object output1) {
        return Tier1.onlyzeroOverwriteMaximumDiamond(device, push(input), push(output0), push(output1));
    }

    public ArrayJ onlyzeroOverwriteMaximum (Object input, Object output0, Object output1, String connectivity) {
        return Tier1.onlyzeroOverwriteMaximum(device, push(input), push(output0), push(output1), connectivity);
    }

    public ArrayJ power (Object input, Object output, float scalar) {
        return Tier1.power(device, push(input), push(output), scalar);
    }

    public ArrayJ powerImages (Object input0, Object input1, Object output) {
        return Tier1.powerImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ range (Object input, Object output, int start_x, int stop_x, int step_x, int start_y, int stop_y, int step_y, int start_z, int stop_z, int step_z) {
        return Tier1.range(device, push(input), push(output), start_x, stop_x, step_x, start_y, stop_y, step_y, start_z, stop_z, step_z);
    }

    public ArrayJ readValuesFromPositions (Object input, Object list, Object output) {
        return Tier1.readValuesFromPositions(device, push(input), push(list), push(output));
    }

    public ArrayJ replaceValues (Object input0, Object input1, Object output) {
        return Tier1.replaceValues(device, push(input0), push(input1), push(output));
    }

    public ArrayJ replaceValue (Object input, Object output, float scalar0, float scalar1) {
        return Tier1.replaceValue(device, push(input), push(output), scalar0, scalar1);
    }

    public ArrayJ maximumSphere (Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.maximumSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ minimumSphere (Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.minimumSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ multiplyMatrix (Object input0, Object input1, Object output) {
        return Tier1.multiplyMatrix(device, push(input0), push(input1), push(output));
    }

    public ArrayJ reciprocal (Object input, Object output) {
        return Tier1.reciprocal(device, push(input), push(output));
    }

    public ArrayJ set (Object input, float scalar) {
        return Tier1.set(device, push(input), scalar);
    }

    public ArrayJ setColumn (Object input, int column, float value) {
        return Tier1.setColumn(device, push(input), column, value);
    }

    public ArrayJ setImageBorders (Object input, float value) {
        return Tier1.setImageBorders(device, push(input), value);
    }

    public ArrayJ setPlane (Object input, int plane, float value) {
        return Tier1.setPlane(device, push(input), plane, value);
    }

    public ArrayJ setRampX (Object input) {
        return Tier1.setRampX(device, push(input));
    }

    public ArrayJ setRampY (Object input) {
        return Tier1.setRampY(device, push(input));
    }

    public ArrayJ setRampZ (Object input) {
        return Tier1.setRampZ(device, push(input));
    }

    public ArrayJ setRow (Object input, int row, float value) {
        return Tier1.setRow(device, push(input), row, value);
    }

    public ArrayJ setNonzeroPixelsToPixelindex (Object input, Object output, int offset) {
        return Tier1.setNonzeroPixelsToPixelindex(device, push(input), push(output), offset);
    }

    public ArrayJ setWhereXEqualsY (Object input, float value) {
        return Tier1.setWhereXEqualsY(device, push(input), value);
    }

    public ArrayJ setWhereXGreaterThanY (Object input, float value) {
        return Tier1.setWhereXGreaterThanY(device, push(input), value);
    }

    public ArrayJ setWhereXSmallerThanY (Object input, float value) {
        return Tier1.setWhereXSmallerThanY(device, push(input), value);
    }

    public ArrayJ sign (Object input, Object output) {
        return Tier1.sign(device, push(input), push(output));
    }

    public ArrayJ smaller (Object input0, Object input1, Object output) {
        return Tier1.smaller(device, push(input0), push(input1), push(output));
    }

    public ArrayJ smallerConstant (Object input, Object output, float scalar) {
        return Tier1.smallerConstant(device, push(input), push(output), scalar);
    }

    public ArrayJ smallerOrEqual (Object input0, Object input1, Object output) {
        return Tier1.smallerOrEqual(device, push(input0), push(input1), push(output));
    }

    public ArrayJ smallerOrEqualConstant (Object input, Object output, float scalar) {
        return Tier1.smallerOrEqualConstant(device, push(input), push(output), scalar);
    }

    public ArrayJ sobel (Object input, Object output) {
        return Tier1.sobel(device, push(input), push(output));
    }

    public ArrayJ squareRoot (Object input, Object output) {
        return Tier1.squareRoot(device, push(input), push(output));
    }

    public ArrayJ stdZProjection (Object input, Object output) {
        return Tier1.stdZProjection(device, push(input), push(output));
    }

    public ArrayJ subtractImageFromScalar (Object input, Object output, float scalar) {
        return Tier1.subtractImageFromScalar(device, push(input), push(output), scalar);
    }

    public ArrayJ sumReductionX (Object input, Object output, int blocksize) {
        return Tier1.sumReductionX(device, push(input), push(output), blocksize);
    }

    public ArrayJ sumXProjection (Object input, Object output) {
        return Tier1.sumXProjection(device, push(input), push(output));
    }

    public ArrayJ sumYProjection (Object input, Object output) {
        return Tier1.sumYProjection(device, push(input), push(output));
    }

    public ArrayJ sumZProjection (Object input, Object output) {
        return Tier1.sumZProjection(device, push(input), push(output));
    }

    public ArrayJ transposeXy (Object input, Object output) {
        return Tier1.transposeXy(device, push(input), push(output));
    }

    public ArrayJ transposeXz (Object input, Object output) {
        return Tier1.transposeXz(device, push(input), push(output));
    }

    public ArrayJ transposeYz (Object input, Object output) {
        return Tier1.transposeYz(device, push(input), push(output));
    }

    public ArrayJ undefinedToZero (Object input, Object output) {
        return Tier1.undefinedToZero(device, push(input), push(output));
    }

    public ArrayJ varianceBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.varianceBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ varianceSphere (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier1.varianceSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ variance (Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier1.variance(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ writeValuesToPositions (Object input, Object output) {
        return Tier1.writeValuesToPositions(device, push(input), push(output));
    }

    public ArrayJ xPositionOfMaximumXProjection (Object input, Object output) {
        return Tier1.xPositionOfMaximumXProjection(device, push(input), push(output));
    }

    public ArrayJ xPositionOfMinimumXProjection (Object input, Object output) {
        return Tier1.xPositionOfMinimumXProjection(device, push(input), push(output));
    }

    public ArrayJ yPositionOfMaximumYProjection (Object input, Object output) {
        return Tier1.yPositionOfMaximumYProjection(device, push(input), push(output));
    }

    public ArrayJ yPositionOfMinimumYProjection (Object input, Object output) {
        return Tier1.yPositionOfMinimumYProjection(device, push(input), push(output));
    }

    public ArrayJ zPositionOfMaximumZProjection (Object input, Object output) {
        return Tier1.zPositionOfMaximumZProjection(device, push(input), push(output));
    }

    public ArrayJ zPositionOfMinimumZProjection (Object input, Object output) {
        return Tier1.zPositionOfMinimumZProjection(device, push(input), push(output));
    }

    public ArrayJ absoluteDifference (Object input0, Object input1, Object output) {
        return Tier2.absoluteDifference(device, push(input0), push(input1), push(output));
    }

    public ArrayJ addImages (Object input0, Object input1, Object output) {
        return Tier2.addImages(device, push(input0), push(input1), push(output));
    }

    public ArrayJ bottomHatBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.bottomHatBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ bottomHatSphere (Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.bottomHatSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ bottomHat (Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.bottomHat(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ clip (Object input, Object output, float min_intensity, float max_intensity) {
        return Tier2.clip(device, push(input), push(output), min_intensity, max_intensity);
    }

    public ArrayJ closingBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.closingBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ closingSphere (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.closingSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ closing (Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier2.closing(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ concatenateAlongX (Object input0, Object input1, Object output) {
        return Tier2.concatenateAlongX(device, push(input0), push(input1), push(output));
    }

    public ArrayJ concatenateAlongY (Object input0, Object input1, Object output) {
        return Tier2.concatenateAlongY(device, push(input0), push(input1), push(output));
    }

    public ArrayJ concatenateAlongZ (Object input0, Object input1, Object output) {
        return Tier2.concatenateAlongZ(device, push(input0), push(input1), push(output));
    }

    public ArrayJ countTouchingNeighbors (Object input, Object output, boolean ignore_background) {
        return Tier2.countTouchingNeighbors(device, push(input), push(output), ignore_background);
    }

    public ArrayJ cropBorder (Object input, Object output, int border_size) {
        return Tier2.cropBorder(device, push(input), push(output), border_size);
    }

    public ArrayJ divideByGaussianBackground (Object input, Object output, float sigma_x, float sigma_y, float sigma_z) {
        return Tier2.divideByGaussianBackground(device, push(input), push(output), sigma_x, sigma_y, sigma_z);
    }

    public ArrayJ degreesToRadians (Object input, Object output) {
        return Tier2.degreesToRadians(device, push(input), push(output));
    }

    public ArrayJ detectMaximaBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.detectMaximaBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ detectMaxima (Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier2.detectMaxima(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ detectMinimaBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.detectMinimaBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ detectMinima (Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier2.detectMinima(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ differenceOfGaussian (Object input, Object output, float sigma1_x, float sigma1_y, float sigma1_z, float sigma2_x, float sigma2_y, float sigma2_z) {
        return Tier2.differenceOfGaussian(device, push(input), push(output), sigma1_x, sigma1_y, sigma1_z, sigma2_x, sigma2_y, sigma2_z);
    }

    public ArrayJ extendLabelingViaVoronoi (Object input, Object output) {
        return Tier2.extendLabelingViaVoronoi(device, push(input), push(output));
    }

    public ArrayJ invert (Object input, Object output) {
        return Tier2.invert(device, push(input), push(output));
    }

    public ArrayJ labelSpots (Object input, Object output) {
        return Tier2.labelSpots(device, push(input), push(output));
    }

    public ArrayJ largeHessianEigenvalue (Object input, Object output) {
        return Tier2.largeHessianEigenvalue(device, push(input), push(output));
    }

    public float maximumOfAllPixels (Object input) {
        return Tier2.maximumOfAllPixels(device, push(input));
    }

    public float minimumOfAllPixels (Object input) {
        return Tier2.minimumOfAllPixels(device, push(input));
    }

    public float minimumOfMaskedPixels (Object input, Object mask) {
        return Tier2.minimumOfMaskedPixels(device, push(input), push(mask));
    }

    public ArrayJ openingBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.openingBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ openingSphere (Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.openingSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ opening (Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.opening(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ radiansToDegrees (Object input, Object output) {
        return Tier2.radiansToDegrees(device, push(input), push(output));
    }

    public ArrayJ reduceLabelsToLabelEdges (Object input, Object output) {
        return Tier2.reduceLabelsToLabelEdges(device, push(input), push(output));
    }

    public ArrayJ smallHessianEigenvalue (Object input, Object output) {
        return Tier2.smallHessianEigenvalue(device, push(input), push(output));
    }

    public ArrayJ square (Object input, Object output) {
        return Tier2.square(device, push(input), push(output));
    }

    public ArrayJ squaredDifference (Object input0, Object input1, Object output) {
        return Tier2.squaredDifference(device, push(input0), push(input1), push(output));
    }

    public ArrayJ standardDeviationBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.standardDeviationBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ standardDeviationSphere (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.standardDeviationSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ standardDeviation (Object input, Object output, int radius_x, int radius_y, int radius_z, String connectivity) {
        return Tier2.standardDeviation(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayJ subtractGaussianBackground (Object input, Object output, float sigma_x, float sigma_y, float sigma_z) {
        return Tier2.subtractGaussianBackground(device, push(input), push(output), sigma_x, sigma_y, sigma_z);
    }

    public ArrayJ subtractImages (Object input0, Object input1, Object output) {
        return Tier2.subtractImages(device, push(input0), push(input1), push(output));
    }

    public float sumOfAllPixels (Object input) {
        return Tier2.sumOfAllPixels(device, push(input));
    }

    public ArrayJ topHatBox (Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.topHatBox(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ topHatSphere (Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.topHatSphere(device, push(input), push(output), radius_x, radius_y, radius_z);
    }

    public ArrayJ topHat (Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.topHat(device, push(input), push(output), radius_x, radius_y, radius_z, connectivity);
    }

    public ArrayList<Float> boundingBox (Object input) {
        return Tier3.boundingBox(device, push(input));
    }

    public ArrayList<Float> centerOfMass (Object input) {
        return Tier3.centerOfMass(device, push(input));
    }

    public ArrayJ excludeLabels (Object input, Object list, Object output) {
        return Tier3.excludeLabels(device, push(input), push(list), push(output));
    }

    public ArrayJ excludeLabelsOnEdges (Object input, Object output, boolean exclude_x, boolean exclude_y, boolean exclude_z) {
        return Tier3.excludeLabelsOnEdges(device, push(input), push(output), exclude_x, exclude_y, exclude_z);
    }

    public ArrayJ flagExistingLabels (Object input, Object output) {
        return Tier3.flagExistingLabels(device, push(input), push(output));
    }

    public ArrayJ gammaCorrection (Object input, Object output, float gamma) {
        return Tier3.gammaCorrection(device, push(input), push(output), gamma);
    }

    public ArrayJ generateBinaryOverlapMatrix (Object input0, Object input1, Object output) {
        return Tier3.generateBinaryOverlapMatrix(device, push(input0), push(input1), push(output));
    }

    public ArrayJ generateTouchMatrix (Object input, Object output) {
        return Tier3.generateTouchMatrix(device, push(input), push(output));
    }

    public ArrayJ histogram (Object input, Object output, int nbins, float min, float max) {
        return Tier3.histogram(device, push(input), push(output), nbins, min, max);
    }

    public float jaccardIndex (Object input0, Object input1) {
        return Tier3.jaccardIndex(device, push(input0), push(input1));
    }

    public ArrayJ labelledSpotsToPointlist (Object input, Object output) {
        return Tier3.labelledSpotsToPointlist(device, push(input), push(output));
    }

    public ArrayList<Float> maximumPosition (Object input) {
        return Tier3.maximumPosition(device, push(input));
    }

    public float meanOfAllPixels (Object input) {
        return Tier3.meanOfAllPixels(device, push(input));
    }

    public ArrayList<Float> minimumPosition (Object input) {
        return Tier3.minimumPosition(device, push(input));
    }

    public ArrayJ morphologicalChanVese (Object input, Object output, int num_iter, int smoothing, float lambda1, float lambda2) {
        return Tier3.morphologicalChanVese(device, push(input), push(output), num_iter, smoothing, lambda1, lambda2);
    }

    public HashMap<String, ArrayList<Float>> statisticsOfLabelledPixels (Object input, Object intensity) {
        return Tier3.statisticsOfLabelledPixels(device, push(input), push(intensity));
    }

    public ArrayList<Float> labelBoundingBox (Object input, int label_id) {
        return Tier4.labelBoundingBox(device, push(input), label_id);
    }

    public float meanSquaredError (Object input0, Object input1) {
        return Tier4.meanSquaredError(device, push(input0), push(input1));
    }

    public ArrayJ spotsToPointlist (Object input, Object output) {
        return Tier4.spotsToPointlist(device, push(input), push(output));
    }

    public ArrayJ relabelSequential (Object input, Object output, int blocksize) {
        return Tier4.relabelSequential(device, push(input), push(output), blocksize);
    }

    public ArrayJ thresholdOtsu (Object input, Object output) {
        return Tier4.thresholdOtsu(device, push(input), push(output));
    }

    public boolean arrayEqual (Object input0, Object input1) {
        return Tier5.arrayEqual(device, push(input0), push(input1));
    }

    public ArrayJ combineLabels (Object input0, Object input1, Object output) {
        return Tier5.combineLabels(device, push(input0), push(input1), push(output));
    }

    public ArrayJ connectedComponentsLabeling (Object input, Object output, String connectivity) {
        return Tier5.connectedComponentsLabeling(device, push(input), push(output), connectivity);
    }

    public ArrayJ dilateLabels (Object input, Object output, int radius) {
        return Tier6.dilateLabels(device, push(input), push(output), radius);
    }

    public ArrayJ erodeLabels (Object input, Object output, int radius, boolean relabel) {
        return Tier6.erodeLabels(device, push(input), push(output), radius, relabel);
    }

    public ArrayJ gaussOtsuLabeling (Object input0, Object output, float outline_sigma) {
        return Tier6.gaussOtsuLabeling(device, push(input0), push(output), outline_sigma);
    }

    public ArrayJ maskedVoronoiLabeling (Object input, Object mask, Object output) {
        return Tier6.maskedVoronoiLabeling(device, push(input), push(mask), push(output));
    }

    public ArrayJ voronoiLabeling (Object input, Object output) {
        return Tier6.voronoiLabeling(device, push(input), push(output));
    }

    public ArrayJ affineTransform (Object input, Object output, ArrayList<Float> transform_matrix, boolean interpolate, boolean resize) {
        return Tier7.affineTransform(device, push(input), push(output), transform_matrix, interpolate, resize);
    }

    public ArrayJ erodedOtsuLabeling (Object input, Object output, int number_of_erosions, float outline_sigma) {
        return Tier7.erodedOtsuLabeling(device, push(input), push(output), number_of_erosions, outline_sigma);
    }

    public ArrayJ rigidTransform (Object input, Object output, float translate_x, float translate_y, float translate_z, float angle_x, float angle_y, float angle_z, boolean centered, boolean interpolate, boolean resize) {
        return Tier7.rigidTransform(device, push(input), push(output), translate_x, translate_y, translate_z, angle_x, angle_y, angle_z, centered, interpolate, resize);
    }

    public ArrayJ rotate (Object input, Object output, float angle_x, float angle_y, float angle_z, boolean centered, boolean interpolate, boolean resize) {
        return Tier7.rotate(device, push(input), push(output), angle_x, angle_y, angle_z, centered, interpolate, resize);
    }

    public ArrayJ scale (Object input, Object output, float factor_x, float factor_y, float factor_z, boolean centered, boolean interpolate, boolean resize) {
        return Tier7.scale(device, push(input), push(output), factor_x, factor_y, factor_z, centered, interpolate, resize);
    }

    public ArrayJ translate (Object input, Object output, float translate_x, float translate_y, float translate_z, boolean interpolate) {
        return Tier7.translate(device, push(input), push(output), translate_x, translate_y, translate_z, interpolate);
    }

    public ArrayJ closingLabels (Object input, Object output, int radius) {
        return Tier7.closingLabels(device, push(input), push(output), radius);
    }

    public ArrayJ erodeConnectedLabels (Object input, Object output, int radius) {
        return Tier7.erodeConnectedLabels(device, push(input), push(output), radius);
    }

    public ArrayJ openingLabels (Object input, Object output, int radius) {
        return Tier7.openingLabels(device, push(input), push(output), radius);
    }

    public ArrayJ voronoiOtsuLabeling (Object input, Object output, float spot_sigma, float outline_sigma) {
        return Tier7.voronoiOtsuLabeling(device, push(input), push(output), spot_sigma, outline_sigma);
    }

    public ArrayJ smoothLabels (Object input, Object output, int radius) {
        return Tier8.smoothLabels(device, push(input), push(output), radius);
    }

    public ArrayJ smoothConnectedLabels (Object input, Object output, int radius) {
        return Tier8.smoothConnectedLabels(device, push(input), push(output), radius);
    }
    /* END AUTO-GENERATED FUNCTIONS */

}
