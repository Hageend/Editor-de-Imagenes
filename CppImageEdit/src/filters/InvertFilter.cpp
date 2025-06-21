#include "InvertFilter.h"
#include <vector>

void InvertFilter::apply(Image& image) const {
    for (size_t i = 0; i < image.data.size(); ++i) {
        image.data[i] = 255 - image.data[i];
    }
}
