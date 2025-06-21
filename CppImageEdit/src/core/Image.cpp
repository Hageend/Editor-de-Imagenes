#include "Image.h"
#include <iostream>
#include <vector>
#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include "stb_image_write.h"

Image::Image() : width(0), height(0), channels(0), data() {}
Image::Image(int w, int h, int c) : width(w), height(h), channels(c), data() {
    data.resize(w * h * c);
}

bool Image::load(const std::string& filename) {
    int w, h, c;
    unsigned char* imgData = stbi_load(filename.c_str(), &w, &h, &c, 0);
    if (!imgData) return false;
    width = w; height = h; channels = c;
    data.assign(imgData, imgData + w * h * c);
    stbi_image_free(imgData);
    return true;
}

bool Image::save(const std::string& filename) const {
    if (data.empty()) return false;
    std::string ext = filename.substr(filename.find_last_of('.') + 1);
    int success = 0;
    if (ext == "png") {
        success = stbi_write_png(filename.c_str(), width, height, channels, data.data(), width * channels);
    } else if (ext == "jpg" || ext == "jpeg") {
        success = stbi_write_jpg(filename.c_str(), width, height, channels, data.data(), 95);
    } else if (ext == "bmp") {
        success = stbi_write_bmp(filename.c_str(), width, height, channels, data.data());
    } else if (ext == "tga") {
        success = stbi_write_tga(filename.c_str(), width, height, channels, data.data());
    }
    return success != 0;
}

bool Image::isValid() const {
    return data.size() > 0 && width > 0 && height > 0 && channels > 0;
}
