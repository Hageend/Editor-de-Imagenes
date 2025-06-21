#pragma once
#include <string>
#include <vector>

class Image {
public:
    int width, height, channels;
    std::vector<unsigned char> data;

    Image();
    Image(int w, int h, int c);
    bool load(const std::string& filename);
    bool save(const std::string& filename) const;
    bool isValid() const;
};
