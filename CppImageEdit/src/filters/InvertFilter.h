#pragma once
#include "IFilter.h"

class InvertFilter : public IFilter {
public:
    void apply(Image& image) const override;
};
