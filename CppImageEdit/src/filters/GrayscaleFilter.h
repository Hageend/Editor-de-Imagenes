#pragma once
#include "IFilter.h"

class GrayscaleFilter : public IFilter {
public:
    void apply(Image& image) const override;
};
