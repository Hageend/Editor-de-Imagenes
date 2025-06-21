#include "BrightnessFilter.h"
#include <algorithm>

void BrightnessFilter::apply(Image& image) const {
    for (size_t i = 0; i < image.data.size(); ++i) {
        int val = static_cast<int>(image.data[i]) + delta;
        image.data[i] = static_cast<unsigned char>(std::clamp(val, 0, 255));
    }
}
