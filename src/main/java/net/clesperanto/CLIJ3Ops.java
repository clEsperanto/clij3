package net.clesperanto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.clesperanto.core.ArrayJ;
import net.clesperanto.kernels.*;

public abstract interface CLIJ3Ops {

    CLIJ3 clij = CLIJ3.getInstance();

    
    default ArrayJ absolute(Object input, Object output) {
        return Tier1.absolute(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ add_images_weighted(Object input0, Object input1, Object output, float factor1, float factor2) {
        return Tier1.addImagesWeighted(clij.device, clij.push(input0), clij.push(input1), clij.push(output), factor1, factor2);
    }

    default ArrayJ add_image_and_scalar(Object input, Object output, float scalar) {
        return Tier1.addImageAndScalar(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ binary_and(Object input0, Object input1, Object output) {
        return Tier1.binaryAnd(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ binary_edge_detection(Object input, Object output) {
        return Tier1.binaryEdgeDetection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ binary_not(Object input, Object output) {
        return Tier1.binaryNot(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ binary_or(Object input0, Object input1, Object output) {
        return Tier1.binaryOr(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ binary_subtract(Object input0, Object input1, Object output) {
        return Tier1.binarySubtract(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ binary_xor(Object input0, Object input1, Object output) {
        return Tier1.binaryXor(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ binary_supinf(Object input, Object output) {
        return Tier1.binarySupinf(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ binary_infsup(Object input, Object output) {
        return Tier1.binaryInfsup(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ block_enumerate(Object input0, Object input1, Object output, int blocksize) {
        return Tier1.blockEnumerate(clij.device, clij.push(input0), clij.push(input1), clij.push(output), blocksize);
    }

    default ArrayJ convolve(Object input0, Object input1, Object output) {
        return Tier1.convolve(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ copy(Object input, Object output) {
        return Tier1.copy(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ copy_slice(Object input, Object output, int slice_index) {
        return Tier1.copySlice(clij.device, clij.push(input), clij.push(output), slice_index);
    }

    default ArrayJ copy_horizontal_slice(Object input, Object output, int slice_index) {
        return Tier1.copyHorizontalSlice(clij.device, clij.push(input), clij.push(output), slice_index);
    }

    default ArrayJ copy_vertical_slice(Object input, Object output, int slice_index) {
        return Tier1.copyVerticalSlice(clij.device, clij.push(input), clij.push(output), slice_index);
    }

    default ArrayJ crop(Object input, Object output, int start_x, int start_y, int start_z, int width, int height, int depth) {
        return Tier1.crop(clij.device, clij.push(input), clij.push(output), start_x, start_y, start_z, width, height, depth);
    }

    default ArrayJ cubic_root(Object input, Object output) {
        return Tier1.cubicRoot(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ detect_label_edges(Object input, Object output) {
        return Tier1.detectLabelEdges(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ dilation(Object input, Object footprint, Object output) {
        return Tier1.dilation(clij.device, clij.push(input), clij.push(footprint), clij.push(output));
    }

    default ArrayJ dilate_box(Object input, Object output) {
        return Tier1.dilateBox(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ dilate_sphere(Object input, Object output) {
        return Tier1.dilateSphere(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ binary_dilate(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier1.binaryDilate(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ divide_images(Object dividend, Object divisor, Object output) {
        return Tier1.divideImages(clij.device, clij.push(dividend), clij.push(divisor), clij.push(output));
    }

    default ArrayJ divide_scalar_by_image(Object input, Object output, float scalar) {
        return Tier1.divideScalarByImage(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ equal(Object input0, Object input1, Object output) {
        return Tier1.equal(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ equal_constant(Object input, Object output, float scalar) {
        return Tier1.equalConstant(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ erosion(Object input, Object footprint, Object output) {
        return Tier1.erosion(clij.device, clij.push(input), clij.push(footprint), clij.push(output));
    }

    default ArrayJ erode_box(Object input, Object output) {
        return Tier1.erodeBox(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ erode_sphere(Object input, Object output) {
        return Tier1.erodeSphere(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ binary_erode(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier1.binaryErode(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ exponential(Object input, Object output) {
        return Tier1.exponential(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ flip(Object input, Object output, boolean flip_x, boolean flip_y, boolean flip_z) {
        return Tier1.flip(clij.device, clij.push(input), clij.push(output), flip_x, flip_y, flip_z);
    }

    default ArrayJ gaussian_blur(Object input, Object output, float sigma_x, float sigma_y, float sigma_z) {
        return Tier1.gaussianBlur(clij.device, clij.push(input), clij.push(output), sigma_x, sigma_y, sigma_z);
    }

    default ArrayJ generate_distance_matrix(Object coordinate_list1, Object coordinate_list2, Object distance_matrix_destination) {
        return Tier1.generateDistanceMatrix(clij.device, clij.push(coordinate_list1), clij.push(coordinate_list2), clij.push(distance_matrix_destination));
    }

    default ArrayJ gradient_x(Object input, Object output) {
        return Tier1.gradientX(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ gradient_y(Object input, Object output) {
        return Tier1.gradientY(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ gradient_z(Object input, Object output) {
        return Tier1.gradientZ(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ greater(Object input0, Object input1, Object output) {
        return Tier1.greater(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ greater_constant(Object input, Object output, float scalar) {
        return Tier1.greaterConstant(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ greater_or_equal(Object input0, Object input1, Object output) {
        return Tier1.greaterOrEqual(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ greater_or_equal_constant(Object input, Object output, float scalar) {
        return Tier1.greaterOrEqualConstant(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayList<ArrayJ> hessian_eigenvalues(Object input, Object small_eigenvalue, Object middle_eigenvalue, Object large_eigenvalue) {
        return Tier1.hessianEigenvalues(clij.device, clij.push(input), clij.push(small_eigenvalue), clij.push(middle_eigenvalue), clij.push(large_eigenvalue));
    }

    default ArrayJ laplace_box(Object input, Object output) {
        return Tier1.laplaceBox(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ laplace_diamond(Object input, Object output) {
        return Tier1.laplaceDiamond(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ laplace(Object input, Object output, String connectivity) {
        return Tier1.laplace(clij.device, clij.push(input), clij.push(output), connectivity);
    }

    default ArrayJ local_cross_correlation(Object input, Object kernel, Object output) {
        return Tier1.localCrossCorrelation(clij.device, clij.push(input), clij.push(kernel), clij.push(output));
    }

    default ArrayJ logarithm(Object input, Object output) {
        return Tier1.logarithm(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ mask(Object input, Object mask, Object output) {
        return Tier1.mask(clij.device, clij.push(input), clij.push(mask), clij.push(output));
    }

    default ArrayJ mask_label(Object input0, Object input1, Object output, float label) {
        return Tier1.maskLabel(clij.device, clij.push(input0), clij.push(input1), clij.push(output), label);
    }

    default ArrayJ maximum_image_and_scalar(Object input, Object output, float scalar) {
        return Tier1.maximumImageAndScalar(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ maximum_images(Object input0, Object input1, Object output) {
        return Tier1.maximumImages(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ maximum_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.maximumBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ maximum_filter(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier1.maximumFilter(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ grayscale_dilate(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier1.grayscaleDilate(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ maximum_x_projection(Object input, Object output) {
        return Tier1.maximumXProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ maximum_y_projection(Object input, Object output) {
        return Tier1.maximumYProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ maximum_z_projection(Object input, Object output) {
        return Tier1.maximumZProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ mean_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.meanBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ mean_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.meanSphere(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ mean_filter(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier1.meanFilter(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ mean_x_projection(Object input, Object output) {
        return Tier1.meanXProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ mean_y_projection(Object input, Object output) {
        return Tier1.meanYProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ mean_z_projection(Object input, Object output) {
        return Tier1.meanZProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ median_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.medianBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ median_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.medianSphere(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ median(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier1.median(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ minimum_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.minimumBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ minimum_filter(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier1.minimumFilter(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ grayscale_erode(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier1.grayscaleErode(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ minimum_image_and_scalar(Object input, Object output, float scalar) {
        return Tier1.minimumImageAndScalar(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ minimum_images(Object input0, Object input1, Object output) {
        return Tier1.minimumImages(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ minimum_x_projection(Object input, Object output) {
        return Tier1.minimumXProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ minimum_y_projection(Object input, Object output) {
        return Tier1.minimumYProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ minimum_z_projection(Object input, Object output) {
        return Tier1.minimumZProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ mode_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.modeBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ mode_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.modeSphere(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ mode(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier1.mode(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ modulo_images(Object input0, Object input1, Object output) {
        return Tier1.moduloImages(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ multiply_image_and_position(Object input, Object output, int dimension) {
        return Tier1.multiplyImageAndPosition(clij.device, clij.push(input), clij.push(output), dimension);
    }

    default ArrayJ multiply_image_and_scalar(Object input, Object output, float scalar) {
        return Tier1.multiplyImageAndScalar(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ multiply_images(Object input0, Object input1, Object output) {
        return Tier1.multiplyImages(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ nan_to_num(Object input, Object output, float nan, float posinf, float neginf) {
        return Tier1.nanToNum(clij.device, clij.push(input), clij.push(output), nan, posinf, neginf);
    }

    default ArrayJ nonzero_maximum_box(Object input, Object output0, Object output1) {
        return Tier1.nonzeroMaximumBox(clij.device, clij.push(input), clij.push(output0), clij.push(output1));
    }

    default ArrayJ nonzero_maximum_diamond(Object input, Object output0, Object output1) {
        return Tier1.nonzeroMaximumDiamond(clij.device, clij.push(input), clij.push(output0), clij.push(output1));
    }

    default ArrayJ nonzero_maximum(Object input, Object output0, Object output1, String connectivity) {
        return Tier1.nonzeroMaximum(clij.device, clij.push(input), clij.push(output0), clij.push(output1), connectivity);
    }

    default ArrayJ nonzero_minimum_box(Object input, Object output0, Object output1) {
        return Tier1.nonzeroMinimumBox(clij.device, clij.push(input), clij.push(output0), clij.push(output1));
    }

    default ArrayJ nonzero_minimum_diamond(Object input, Object output0, Object output1) {
        return Tier1.nonzeroMinimumDiamond(clij.device, clij.push(input), clij.push(output0), clij.push(output1));
    }

    default ArrayJ nonzero_minimum(Object input, Object output0, Object output1, String connectivity) {
        return Tier1.nonzeroMinimum(clij.device, clij.push(input), clij.push(output0), clij.push(output1), connectivity);
    }

    default ArrayJ not_equal(Object input0, Object input1, Object output) {
        return Tier1.notEqual(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ not_equal_constant(Object input, Object output, float scalar) {
        return Tier1.notEqualConstant(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ paste(Object input, Object output, int destination_x, int destination_y, int destination_z) {
        return Tier1.paste(clij.device, clij.push(input), clij.push(output), destination_x, destination_y, destination_z);
    }

    default ArrayJ onlyzero_overwrite_maximum_box(Object input, Object flag, Object output) {
        return Tier1.onlyzeroOverwriteMaximumBox(clij.device, clij.push(input), clij.push(flag), clij.push(output));
    }

    default ArrayJ onlyzero_overwrite_maximum_diamond(Object input, Object flag, Object output) {
        return Tier1.onlyzeroOverwriteMaximumDiamond(clij.device, clij.push(input), clij.push(flag), clij.push(output));
    }

    default ArrayJ onlyzero_overwrite_maximum(Object input, Object flag, Object output, String connectivity) {
        return Tier1.onlyzeroOverwriteMaximum(clij.device, clij.push(input), clij.push(flag), clij.push(output), connectivity);
    }

    default ArrayJ power(Object input, Object output, float scalar) {
        return Tier1.power(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ power_images(Object input0, Object input1, Object output) {
        return Tier1.powerImages(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ range(Object input, Object output, int start_x, int stop_x, int step_x, int start_y, int stop_y, int step_y, int start_z, int stop_z, int step_z) {
        return Tier1.range(clij.device, clij.push(input), clij.push(output), start_x, stop_x, step_x, start_y, stop_y, step_y, start_z, stop_z, step_z);
    }

    default ArrayJ read_values_from_positions(Object input, Object list, Object output) {
        return Tier1.readValuesFromPositions(clij.device, clij.push(input), clij.push(list), clij.push(output));
    }

    default ArrayJ replace_values(Object input0, Object input1, Object output) {
        return Tier1.replaceValues(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ replace_value(Object input, Object output, float value_to_replace, float value_replacement) {
        return Tier1.replaceValue(clij.device, clij.push(input), clij.push(output), value_to_replace, value_replacement);
    }

    default ArrayJ replace_intensity(Object input, Object output, float value_to_replace, float value_replacement) {
        return Tier1.replaceIntensity(clij.device, clij.push(input), clij.push(output), value_to_replace, value_replacement);
    }

    default ArrayJ replace_intensities(Object input0, Object input1, Object output) {
        return Tier1.replaceIntensities(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ maximum_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.maximumSphere(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ minimum_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.minimumSphere(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ multiply_matrix(Object matrix1, Object matrix2, Object matrix_destination) {
        return Tier1.multiplyMatrix(clij.device, clij.push(matrix1), clij.push(matrix2), clij.push(matrix_destination));
    }

    default ArrayJ reciprocal(Object input, Object output) {
        return Tier1.reciprocal(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ set(Object input, float scalar) {
        return Tier1.set(clij.device, clij.push(input), scalar);
    }

    default ArrayJ set_column(Object input, int column_index, float value) {
        return Tier1.setColumn(clij.device, clij.push(input), column_index, value);
    }

    default ArrayJ set_image_borders(Object input, float value) {
        return Tier1.setImageBorders(clij.device, clij.push(input), value);
    }

    default ArrayJ set_plane(Object input, int plane_index, float value) {
        return Tier1.setPlane(clij.device, clij.push(input), plane_index, value);
    }

    default ArrayJ set_ramp_x(Object input) {
        return Tier1.setRampX(clij.device, clij.push(input));
    }

    default ArrayJ set_ramp_y(Object input) {
        return Tier1.setRampY(clij.device, clij.push(input));
    }

    default ArrayJ set_ramp_z(Object input) {
        return Tier1.setRampZ(clij.device, clij.push(input));
    }

    default ArrayJ set_row(Object input, int row_index, float value) {
        return Tier1.setRow(clij.device, clij.push(input), row_index, value);
    }

    default ArrayJ set_nonzero_pixels_to_pixelindex(Object input, Object output, int offset) {
        return Tier1.setNonzeroPixelsToPixelindex(clij.device, clij.push(input), clij.push(output), offset);
    }

    default ArrayJ set_where_x_equals_y(Object input, float value) {
        return Tier1.setWhereXEqualsY(clij.device, clij.push(input), value);
    }

    default ArrayJ set_where_x_greater_than_y(Object input, float value) {
        return Tier1.setWhereXGreaterThanY(clij.device, clij.push(input), value);
    }

    default ArrayJ set_where_x_smaller_than_y(Object input, float value) {
        return Tier1.setWhereXSmallerThanY(clij.device, clij.push(input), value);
    }

    default ArrayJ sign(Object input, Object output) {
        return Tier1.sign(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ smaller(Object input0, Object input1, Object output) {
        return Tier1.smaller(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ smaller_constant(Object input, Object output, float scalar) {
        return Tier1.smallerConstant(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ smaller_or_equal(Object input0, Object input1, Object output) {
        return Tier1.smallerOrEqual(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ smaller_or_equal_constant(Object input, Object output, float scalar) {
        return Tier1.smallerOrEqualConstant(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ sobel(Object input, Object output) {
        return Tier1.sobel(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ square_root(Object input, Object output) {
        return Tier1.squareRoot(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ std_z_projection(Object input, Object output) {
        return Tier1.stdZProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ subtract_image_from_scalar(Object input, Object output, float scalar) {
        return Tier1.subtractImageFromScalar(clij.device, clij.push(input), clij.push(output), scalar);
    }

    default ArrayJ sum_reduction_x(Object input, Object output, int blocksize) {
        return Tier1.sumReductionX(clij.device, clij.push(input), clij.push(output), blocksize);
    }

    default ArrayJ sum_x_projection(Object input, Object output) {
        return Tier1.sumXProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ sum_y_projection(Object input, Object output) {
        return Tier1.sumYProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ sum_z_projection(Object input, Object output) {
        return Tier1.sumZProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ transpose_xy(Object input, Object output) {
        return Tier1.transposeXy(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ transpose_xz(Object input, Object output) {
        return Tier1.transposeXz(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ transpose_yz(Object input, Object output) {
        return Tier1.transposeYz(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ undefined_to_zero(Object input, Object output) {
        return Tier1.undefinedToZero(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ variance_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.varianceBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ variance_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier1.varianceSphere(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ variance_filter(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier1.varianceFilter(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ write_values_to_positions(Object input, Object output) {
        return Tier1.writeValuesToPositions(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ x_position_of_maximum_x_projection(Object input, Object output) {
        return Tier1.xPositionOfMaximumXProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ x_position_of_minimum_x_projection(Object input, Object output) {
        return Tier1.xPositionOfMinimumXProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ y_position_of_maximum_y_projection(Object input, Object output) {
        return Tier1.yPositionOfMaximumYProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ y_position_of_minimum_y_projection(Object input, Object output) {
        return Tier1.yPositionOfMinimumYProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ z_position_of_maximum_z_projection(Object input, Object output) {
        return Tier1.zPositionOfMaximumZProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ z_position_of_minimum_z_projection(Object input, Object output) {
        return Tier1.zPositionOfMinimumZProjection(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ absolute_difference(Object input0, Object input1, Object output) {
        return Tier2.absoluteDifference(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ add_images(Object input0, Object input1, Object output) {
        return Tier2.addImages(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ bottom_hat_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.bottomHatBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ bottom_hat_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.bottomHatSphere(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ bottom_hat(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.bottomHat(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ clip(Object input, Object output, float min_intensity, float max_intensity) {
        return Tier2.clip(clij.device, clij.push(input), clij.push(output), min_intensity, max_intensity);
    }

    default ArrayJ closing_box(Object input, Object output, int radius_x, int radius_y, int radius_z) {
        return Tier2.closingBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ closing_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.closingSphere(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ grayscale_closing(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.grayscaleClosing(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ closing(Object input, Object footprint, Object output) {
        return Tier2.closing(clij.device, clij.push(input), clij.push(footprint), clij.push(output));
    }

    default ArrayJ binary_closing(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.binaryClosing(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ concatenate_along_x(Object input0, Object input1, Object output) {
        return Tier2.concatenateAlongX(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ concatenate_along_y(Object input0, Object input1, Object output) {
        return Tier2.concatenateAlongY(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ concatenate_along_z(Object input0, Object input1, Object output) {
        return Tier2.concatenateAlongZ(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ count_touching_neighbors(Object touch_matrix, Object touching_neighbors_count_destination, boolean ignore_background) {
        return Tier2.countTouchingNeighbors(clij.device, clij.push(touch_matrix), clij.push(touching_neighbors_count_destination), ignore_background);
    }

    default ArrayJ crop_border(Object input, Object output, int border_size) {
        return Tier2.cropBorder(clij.device, clij.push(input), clij.push(output), border_size);
    }

    default ArrayJ divide_by_gaussian_background(Object input, Object output, float sigma_x, float sigma_y, float sigma_z) {
        return Tier2.divideByGaussianBackground(clij.device, clij.push(input), clij.push(output), sigma_x, sigma_y, sigma_z);
    }

    default ArrayJ degrees_to_radians(Object input, Object output) {
        return Tier2.degreesToRadians(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ detect_maxima_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.detectMaximaBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ detect_maxima(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.detectMaxima(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ detect_minima_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.detectMinimaBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ detect_minima(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.detectMinima(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ difference_of_gaussian(Object input, Object output, float sigma1_x, float sigma1_y, float sigma1_z, float sigma2_x, float sigma2_y, float sigma2_z) {
        return Tier2.differenceOfGaussian(clij.device, clij.push(input), clij.push(output), sigma1_x, sigma1_y, sigma1_z, sigma2_x, sigma2_y, sigma2_z);
    }

    default ArrayJ extend_labeling_via_voronoi(Object input, Object output) {
        return Tier2.extendLabelingViaVoronoi(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ invert(Object input, Object output) {
        return Tier2.invert(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ label_spots(Object input, Object output) {
        return Tier2.labelSpots(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ large_hessian_eigenvalue(Object input, Object output) {
        return Tier2.largeHessianEigenvalue(clij.device, clij.push(input), clij.push(output));
    }

    default float maximum_of_all_pixels(Object input) {
        return Tier2.maximumOfAllPixels(clij.device, clij.push(input));
    }

    default float minimum_of_all_pixels(Object input) {
        return Tier2.minimumOfAllPixels(clij.device, clij.push(input));
    }

    default float minimum_of_masked_pixels(Object input, Object mask) {
        return Tier2.minimumOfMaskedPixels(clij.device, clij.push(input), clij.push(mask));
    }

    default ArrayJ opening_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.openingBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ opening_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.openingSphere(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ grayscale_opening(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.grayscaleOpening(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ opening(Object input, Object footprint, Object output) {
        return Tier2.opening(clij.device, clij.push(input), clij.push(footprint), clij.push(output));
    }

    default ArrayJ binary_opening(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.binaryOpening(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ radians_to_degrees(Object input, Object output) {
        return Tier2.radiansToDegrees(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ reduce_labels_to_label_edges(Object input, Object output) {
        return Tier2.reduceLabelsToLabelEdges(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ small_hessian_eigenvalue(Object input, Object output) {
        return Tier2.smallHessianEigenvalue(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ square(Object input, Object output) {
        return Tier2.square(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ squared_difference(Object input0, Object input1, Object output) {
        return Tier2.squaredDifference(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ standard_deviation_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.standardDeviationBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ standard_deviation_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.standardDeviationSphere(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ standard_deviation(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.standardDeviation(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayJ subtract_gaussian_background(Object input, Object output, float sigma_x, float sigma_y, float sigma_z) {
        return Tier2.subtractGaussianBackground(clij.device, clij.push(input), clij.push(output), sigma_x, sigma_y, sigma_z);
    }

    default ArrayJ subtract_images(Object input0, Object input1, Object output) {
        return Tier2.subtractImages(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ sub_stack(Object input, Object output, int start_z, int end_z) {
        return Tier2.subStack(clij.device, clij.push(input), clij.push(output), start_z, end_z);
    }

    default ArrayJ reduce_stack(Object input, Object output, int reduction_factor, int offset) {
        return Tier2.reduceStack(clij.device, clij.push(input), clij.push(output), reduction_factor, offset);
    }

    default float sum_of_all_pixels(Object input) {
        return Tier2.sumOfAllPixels(clij.device, clij.push(input));
    }

    default ArrayJ top_hat_box(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.topHatBox(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ top_hat_sphere(Object input, Object output, float radius_x, float radius_y, float radius_z) {
        return Tier2.topHatSphere(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z);
    }

    default ArrayJ top_hat(Object input, Object output, float radius_x, float radius_y, float radius_z, String connectivity) {
        return Tier2.topHat(clij.device, clij.push(input), clij.push(output), radius_x, radius_y, radius_z, connectivity);
    }

    default ArrayList<Float> bounding_box(Object input) {
        return Tier3.boundingBox(clij.device, clij.push(input));
    }

    default ArrayList<Float> center_of_mass(Object input) {
        return Tier3.centerOfMass(clij.device, clij.push(input));
    }

    default ArrayJ remove_labels(Object input, Object list, Object output) {
        return Tier3.removeLabels(clij.device, clij.push(input), clij.push(list), clij.push(output));
    }

    default ArrayJ exclude_labels(Object input, Object list, Object output) {
        return Tier3.excludeLabels(clij.device, clij.push(input), clij.push(list), clij.push(output));
    }

    default ArrayJ remove_labels_on_edges(Object input, Object output, boolean exclude_x, boolean exclude_y, boolean exclude_z) {
        return Tier3.removeLabelsOnEdges(clij.device, clij.push(input), clij.push(output), exclude_x, exclude_y, exclude_z);
    }

    default ArrayJ exclude_labels_on_edges(Object input, Object output, boolean exclude_x, boolean exclude_y, boolean exclude_z) {
        return Tier3.excludeLabelsOnEdges(clij.device, clij.push(input), clij.push(output), exclude_x, exclude_y, exclude_z);
    }

    default ArrayJ flag_existing_labels(Object input, Object output) {
        return Tier3.flagExistingLabels(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ gamma_correction(Object input, Object output, float gamma) {
        return Tier3.gammaCorrection(clij.device, clij.push(input), clij.push(output), gamma);
    }

    default ArrayJ generate_binary_overlap_matrix(Object input0, Object input1, Object output) {
        return Tier3.generateBinaryOverlapMatrix(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ generate_touch_matrix(Object input, Object output) {
        return Tier3.generateTouchMatrix(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ histogram(Object input, Object output, int num_bins, float minimum_intensity, float maximum_intensity) {
        return Tier3.histogram(clij.device, clij.push(input), clij.push(output), num_bins, minimum_intensity, maximum_intensity);
    }

    default float jaccard_index(Object input0, Object input1) {
        return Tier3.jaccardIndex(clij.device, clij.push(input0), clij.push(input1));
    }

    default ArrayJ labelled_spots_to_pointlist(Object label, Object pointlist) {
        return Tier3.labelledSpotsToPointlist(clij.device, clij.push(label), clij.push(pointlist));
    }

    default ArrayList<Float> maximum_position(Object input) {
        return Tier3.maximumPosition(clij.device, clij.push(input));
    }

    default float mean_of_all_pixels(Object input) {
        return Tier3.meanOfAllPixels(clij.device, clij.push(input));
    }

    default ArrayList<Float> minimum_position(Object input) {
        return Tier3.minimumPosition(clij.device, clij.push(input));
    }

    default ArrayJ morphological_chan_vese(Object input, Object output, int num_iter, int smoothing, float lambda1, float lambda2) {
        return Tier3.morphologicalChanVese(clij.device, clij.push(input), clij.push(output), num_iter, smoothing, lambda1, lambda2);
    }

    default HashMap<String, ArrayList<Float>> statistics_of_labelled_pixels(Object intensity, Object label) {
        return Tier3.statisticsOfLabelledPixels(clij.device, clij.push(intensity), clij.push(label));
    }

    default HashMap<String, ArrayList<Float>> statistics_of_background_and_labelled_pixels(Object intensity, Object label) {
        return Tier3.statisticsOfBackgroundAndLabelledPixels(clij.device, clij.push(intensity), clij.push(label));
    }

    default ArrayList<Float> label_bounding_box(Object input, int label_id) {
        return Tier4.labelBoundingBox(clij.device, clij.push(input), label_id);
    }

    default float mean_squared_error(Object input0, Object input1) {
        return Tier4.meanSquaredError(clij.device, clij.push(input0), clij.push(input1));
    }

    default ArrayJ spots_to_pointlist(Object input, Object output) {
        return Tier4.spotsToPointlist(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ relabel_sequential(Object input, Object output, int blocksize) {
        return Tier4.relabelSequential(clij.device, clij.push(input), clij.push(output), blocksize);
    }

    default ArrayJ threshold_otsu(Object input, Object output) {
        return Tier4.thresholdOtsu(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ mean_intensity_map(Object input, Object labels, Object output) {
        return Tier4.meanIntensityMap(clij.device, clij.push(input), clij.push(labels), clij.push(output));
    }

    default ArrayJ pixel_count_map(Object input, Object output) {
        return Tier4.pixelCountMap(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ label_pixel_count_map(Object input, Object output) {
        return Tier4.labelPixelCountMap(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ centroids_of_labels(Object label_image, Object centroids_coordinates, boolean include_background) {
        return Tier4.centroidsOfLabels(clij.device, clij.push(label_image), clij.push(centroids_coordinates), include_background);
    }

    default ArrayJ remove_labels_with_map_values_out_of_range(Object input, Object values, Object output, float min_value, float max_value) {
        return Tier4.removeLabelsWithMapValuesOutOfRange(clij.device, clij.push(input), clij.push(values), clij.push(output), min_value, max_value);
    }

    default ArrayJ remove_labels_with_map_values_within_range(Object input, Object values, Object output, float min_value, float max_value) {
        return Tier4.removeLabelsWithMapValuesWithinRange(clij.device, clij.push(input), clij.push(values), clij.push(output), min_value, max_value);
    }

    default ArrayJ exclude_labels_with_map_values_out_of_range(Object values_map, Object label_map_input, Object output, float minimum_value_range, float maximum_value_range) {
        return Tier4.excludeLabelsWithMapValuesOutOfRange(clij.device, clij.push(values_map), clij.push(label_map_input), clij.push(output), minimum_value_range, maximum_value_range);
    }

    default ArrayJ exclude_labels_with_map_values_within_range(Object values_map, Object label_map_input, Object output, float minimum_value_range, float maximum_value_range) {
        return Tier4.excludeLabelsWithMapValuesWithinRange(clij.device, clij.push(values_map), clij.push(label_map_input), clij.push(output), minimum_value_range, maximum_value_range);
    }

    default ArrayJ extension_ratio_map(Object input, Object output) {
        return Tier4.extensionRatioMap(clij.device, clij.push(input), clij.push(output));
    }

    default boolean array_equal(Object input0, Object input1) {
        return Tier5.arrayEqual(clij.device, clij.push(input0), clij.push(input1));
    }

    default ArrayJ combine_labels(Object input0, Object input1, Object output) {
        return Tier5.combineLabels(clij.device, clij.push(input0), clij.push(input1), clij.push(output));
    }

    default ArrayJ connected_components_labeling(Object input, Object output, String connectivity) {
        return Tier5.connectedComponentsLabeling(clij.device, clij.push(input), clij.push(output), connectivity);
    }

    default ArrayJ connected_component_labeling(Object input, Object output, String connectivity) {
        return Tier5.connectedComponentLabeling(clij.device, clij.push(input), clij.push(output), connectivity);
    }

    default ArrayJ reduce_labels_to_centroids(Object input, Object output) {
        return Tier5.reduceLabelsToCentroids(clij.device, clij.push(input), clij.push(output));
    }

    default ArrayJ filter_label_by_size(Object input, Object output, float minimum_size, float maximum_size) {
        return Tier5.filterLabelBySize(clij.device, clij.push(input), clij.push(output), minimum_size, maximum_size);
    }

    default ArrayJ exclude_labels_outside_size_range(Object input, Object output, float minimum_size, float maximum_size) {
        return Tier5.excludeLabelsOutsideSizeRange(clij.device, clij.push(input), clij.push(output), minimum_size, maximum_size);
    }

    default ArrayJ dilate_labels(Object input, Object output, int radius) {
        return Tier6.dilateLabels(clij.device, clij.push(input), clij.push(output), radius);
    }

    default ArrayJ erode_labels(Object input, Object output, int radius, boolean relabel) {
        return Tier6.erodeLabels(clij.device, clij.push(input), clij.push(output), radius, relabel);
    }

    default ArrayJ gauss_otsu_labeling(Object input0, Object output, float outline_sigma) {
        return Tier6.gaussOtsuLabeling(clij.device, clij.push(input0), clij.push(output), outline_sigma);
    }

    default ArrayJ masked_voronoi_labeling(Object input, Object mask, Object output) {
        return Tier6.maskedVoronoiLabeling(clij.device, clij.push(input), clij.push(mask), clij.push(output));
    }

    default ArrayJ voronoi_labeling(Object input_binary, Object output_labels) {
        return Tier6.voronoiLabeling(clij.device, clij.push(input_binary), clij.push(output_labels));
    }

    default ArrayJ remove_small_labels(Object input, Object output, float minimum_size) {
        return Tier6.removeSmallLabels(clij.device, clij.push(input), clij.push(output), minimum_size);
    }

    default ArrayJ exclude_small_labels(Object input, Object output, float maximum_size) {
        return Tier6.excludeSmallLabels(clij.device, clij.push(input), clij.push(output), maximum_size);
    }

    default ArrayJ remove_large_labels(Object input, Object output, float maximum_size) {
        return Tier6.removeLargeLabels(clij.device, clij.push(input), clij.push(output), maximum_size);
    }

    default ArrayJ exclude_large_labels(Object input, Object output, float minimum_size) {
        return Tier6.excludeLargeLabels(clij.device, clij.push(input), clij.push(output), minimum_size);
    }

    default ArrayJ affine_transform(Object input, Object output, ArrayList<Float> transform_matrix, boolean interpolate, boolean resize) {
        return Tier7.affineTransform(clij.device, clij.push(input), clij.push(output), transform_matrix, interpolate, resize);
    }

    default ArrayJ eroded_otsu_labeling(Object input, Object output, int number_of_erosions, float outline_sigma) {
        return Tier7.erodedOtsuLabeling(clij.device, clij.push(input), clij.push(output), number_of_erosions, outline_sigma);
    }

    default ArrayJ rigid_transform(Object input, Object output, float translate_x, float translate_y, float translate_z, float angle_x, float angle_y, float angle_z, boolean centered, boolean interpolate, boolean resize) {
        return Tier7.rigidTransform(clij.device, clij.push(input), clij.push(output), translate_x, translate_y, translate_z, angle_x, angle_y, angle_z, centered, interpolate, resize);
    }

    default ArrayJ rotate(Object input, Object output, float angle_x, float angle_y, float angle_z, boolean centered, boolean interpolate, boolean resize) {
        return Tier7.rotate(clij.device, clij.push(input), clij.push(output), angle_x, angle_y, angle_z, centered, interpolate, resize);
    }

    default ArrayJ scale(Object input, Object output, float factor_x, float factor_y, float factor_z, boolean centered, boolean interpolate, boolean resize) {
        return Tier7.scale(clij.device, clij.push(input), clij.push(output), factor_x, factor_y, factor_z, centered, interpolate, resize);
    }

    default ArrayJ translate(Object input, Object output, float translate_x, float translate_y, float translate_z, boolean interpolate) {
        return Tier7.translate(clij.device, clij.push(input), clij.push(output), translate_x, translate_y, translate_z, interpolate);
    }

    default ArrayJ closing_labels(Object input, Object output, int radius) {
        return Tier7.closingLabels(clij.device, clij.push(input), clij.push(output), radius);
    }

    default ArrayJ erode_connected_labels(Object input, Object output, int radius) {
        return Tier7.erodeConnectedLabels(clij.device, clij.push(input), clij.push(output), radius);
    }

    default ArrayJ opening_labels(Object input, Object output, int radius) {
        return Tier7.openingLabels(clij.device, clij.push(input), clij.push(output), radius);
    }

    default ArrayJ voronoi_otsu_labeling(Object input, Object output, float spot_sigma, float outline_sigma) {
        return Tier7.voronoiOtsuLabeling(clij.device, clij.push(input), clij.push(output), spot_sigma, outline_sigma);
    }

    default ArrayJ smooth_labels(Object input, Object output, int radius) {
        return Tier8.smoothLabels(clij.device, clij.push(input), clij.push(output), radius);
    }

    default ArrayJ smooth_connected_labels(Object input, Object output, int radius) {
        return Tier8.smoothConnectedLabels(clij.device, clij.push(input), clij.push(output), radius);
    }

}
