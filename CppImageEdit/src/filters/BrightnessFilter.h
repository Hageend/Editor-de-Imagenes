#pragma once
#include "IFilter.h"

class BrightnessFilter : public IFilter {
    int delta;
public:
    BrightnessFilter(int d) : delta(d) {}
    void apply(Image& image) const override;
};
