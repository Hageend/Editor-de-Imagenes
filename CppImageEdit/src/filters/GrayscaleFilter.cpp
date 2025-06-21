#include "GrayscaleFilter.h"

void GrayscaleFilter::apply(Image& image) const {
    if (image.channels < 3) return;
    for (int i = 0; i < image.width * image.height; ++i) {
        int idx = i * image.channels;
        unsigned char r = image.data[idx];
        unsigned char g = image.data[idx+1];
        unsigned char b = image.data[idx+2];
        unsigned char gray = static_cast<unsigned char>(0.299*r + 0.587*g + 0.114*b);
        image.data[idx] = image.data[idx+1] = image.data[idx+2] = gray;
    }
}
