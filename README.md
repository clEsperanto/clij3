[![Build Status](https://github.com/clEsperanto/clij3/actions/workflows/build.yaml/badge.svg)](https://github.com/clEsperanto/clesperantoj_prototype/actions/workflows/build.yaml)
[![Tests Status](https://github.com/clEsperanto/clij3/actions/workflows/build.yaml/badge.svg)](https://github.com/clEsperanto/clesperantoj_prototype/actions/workflows/tests.yaml)
[![Image.sc forum](https://img.shields.io/badge/dynamic/json.svg?label=forum&url=https%3A%2F%2Fforum.image.sc%2Ftag%2Fclij.json&query=%24.topic_list.tags.0.topic_count&colorB=brightgreen&suffix=%20topics&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAOCAYAAAAfSC3RAAABPklEQVR42m3SyyqFURTA8Y2BER0TDyExZ+aSPIKUlPIITFzKeQWXwhBlQrmFgUzMMFLKZeguBu5y+//17dP3nc5vuPdee6299gohUYYaDGOyyACq4JmQVoFujOMR77hNfOAGM+hBOQqB9TjHD36xhAa04RCuuXeKOvwHVWIKL9jCK2bRiV284QgL8MwEjAneeo9VNOEaBhzALGtoRy02cIcWhE34jj5YxgW+E5Z4iTPkMYpPLCNY3hdOYEfNbKYdmNngZ1jyEzw7h7AIb3fRTQ95OAZ6yQpGYHMMtOTgouktYwxuXsHgWLLl+4x++Kx1FJrjLTagA77bTPvYgw1rRqY56e+w7GNYsqX6JfPwi7aR+Y5SA+BXtKIRfkfJAYgj14tpOF6+I46c4/cAM3UhM3JxyKsxiOIhH0IO6SH/A1Kb1WBeUjbkAAAAAElFTkSuQmCC)](https://forum.image.sc/tag/clij)

# CLIJ3: GPU-accelerated image processing for everyone

> [!WARNING]
> CLIJ3 is still in development and may not yet ready to use for your research project. Yet feel free to try it, feedback are very welcomed!

CLIJ3 is a GPU-accelerated image processing library for [ImageJ/Fiji](https://fiji.sc/). 
It comes with hundreds of operations for 
[filtering](https://clij.github.io/clij2-docs/reference__filter), 
[binarizing](https://clij.github.io/clij2-docs/reference__binary),
[labeling](https://clij.github.io/clij2-docs/reference__label),
[measuring](https://clij.github.io/clij2-docs/reference__measurement) in images,
[projections](https://clij.github.io/clij2-docs/reference__project),
[transformations](https://clij.github.io/clij2-docs/reference__transform) and 
[mathematical operations](https://clij.github.io/clij2-docs/reference__math) for images. 
While most of these are classical image processing operations, CLIJ3 also allows performing operations on 
[matrices](https://clij.github.io/clij2-docs/reference__matrix) potentially representing
[neighborhood relationships](https://clij.github.io/clij2-docs/reference__neighbor) between [cells](https://clij.github.io/clij2-docs/md/neighbors_of_neighbors) and pixels.

![img.png](demo/screenshot.png)

Under the hood it uses [OpenCL](https://www.khronos.org/opencl/) and rely on the [clEsperanto](https://github.com/clEsperanto) project.

## Plugin Installation

CLIJ3 requires you to have [Fiji](https://fiji.sc/) installed on your computer and up-to-date.
Install CLIJ3 by adding the CLIJ3 update site to your Fiji and apply the changes.
After restarting Fiji, CLIJ3 should be installed and ready to use.

> [!WARNING]
> CLIJ3 being under development, its update site is currently unlisted. If you want to install it you will need to add the update site `https://imagej.net/clij3` manually.

## Build and Install from source

Git clone the repository, and build the project using maven: `mvn clean install -U`.

If you want `maven` to update your local Fiji after the build, please update the `pom.xml` file with your local Fiji ([here](https://github.com/clEsperanto/clij3/blob/9f30fb3267e51d811792d2094d88f8d735498dd3/pom.xml#L86)). Rerun `mvn install` to install the plugin to your Fiji.

Once CLIJ3 installed, you should be execute example scripts like [this one](https://github.com/clEsperanto/clij3/blob/main/demo/basic_demo.py) in your Fiji script editor.

## Acknowledgements

We acknowledge support by the Deutsche Forschungsgemeinschaft under Germany’s Excellence Strategy (EXC2068) Cluster of Excellence Physics of Life of TU Dresden.
This project has been made possible in part by grant number 2021-237734 ([GPU-accelerating Fiji and friends using distributed CLIJ, NEUBIAS-style, EOSS4](https://chanzuckerberg.com/eoss/proposals/gpu-accelerating-fiji-and-friends-using-distributed-clij-neubias-style/)) from the Chan Zuckerberg Initiative DAF, an advised fund of the Silicon Valley Community Foundation.

