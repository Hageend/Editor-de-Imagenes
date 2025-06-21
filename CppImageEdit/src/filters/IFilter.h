#pragma once
#include "Image.h"

class IFilter {
public:
    virtual ~IFilter() = default;
    virtual void apply(Image& image) const = 0;
};
