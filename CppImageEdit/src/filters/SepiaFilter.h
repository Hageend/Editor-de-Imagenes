#pragma once
#include "IFilter.h"

class SepiaFilter : public IFilter {
public:
    void apply(Image& image) const override;
};
