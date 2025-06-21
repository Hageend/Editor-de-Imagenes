#include "ImageEditor.h"
#include <vector>
#include <memory>
#include <utility>

bool ImageEditor::loadImage(const std::string& filename) {
    return image.load(filename);
}

bool ImageEditor::saveImage(const std::string& filename) const {
    return image.save(filename);
}

void ImageEditor::addFilter(std::unique_ptr<IFilter> filter) {
    filters.push_back(std::move(filter));
}

void ImageEditor::applyFilters() {
    for (const auto& filter : filters) {
        filter->apply(image);
    }
    filters.clear();
}

Image& ImageEditor::getImage() {
    return image;
}
