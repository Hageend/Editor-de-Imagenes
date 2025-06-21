#include "SepiaFilter.h"
#include <algorithm>

void SepiaFilter::apply(Image& image) const {
    if (image.channels < 3) return;
    for (int i = 0; i < image.width * image.height; ++i) {
        int idx = i * image.channels;
        unsigned char r = image.data[idx];
        unsigned char g = image.data[idx+1];
        unsigned char b = image.data[idx+2];
        int tr = std::clamp(int(0.393*r + 0.769*g + 0.189*b), 0, 255);
        int tg = std::clamp(int(0.349*r + 0.686*g + 0.168*b), 0, 255);
        int tb = std::clamp(int(0.272*r + 0.534*g + 0.131*b), 0, 255);
        image.data[idx] = tr;
        image.data[idx+1] = tg;
        image.data[idx+2] = tb;
    }
}
