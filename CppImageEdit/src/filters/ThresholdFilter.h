#pragma once
#include "IFilter.h"

class ThresholdFilter : public IFilter {
    unsigned char threshold;
public:
    ThresholdFilter(unsigned char t) : threshold(t) {}
    void apply(Image& image) const override;
};
