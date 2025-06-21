#include "ThresholdFilter.h"

void ThresholdFilter::apply(Image& image) const {
    for (size_t i = 0; i < image.data.size(); i += image.channels) {
        unsigned char gray = 0;
        if (image.channels >= 3) {
            gray = static_cast<unsigned char>(0.299*image.data[i] + 0.587*image.data[i+1] + 0.114*image.data[i+2]);
        } else {
            gray = image.data[i];
        }
        unsigned char val = (gray > threshold) ? 255 : 0;
        for (int c = 0; c < image.channels; ++c) {
            image.data[i+c] = val;
        }
    }
}
