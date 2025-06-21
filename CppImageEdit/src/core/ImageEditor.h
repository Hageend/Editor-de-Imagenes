#pragma once
#include "Image.h"
#include "IFilter.h"
#include <vector>
#include <memory>

class ImageEditor {
    Image image;
    std::vector<std::unique_ptr<IFilter>> filters;
public:
    bool loadImage(const std::string& filename);
    bool saveImage(const std::string& filename) const;
    void addFilter(std::unique_ptr<IFilter> filter);
    void applyFilters();
    Image& getImage();
};
