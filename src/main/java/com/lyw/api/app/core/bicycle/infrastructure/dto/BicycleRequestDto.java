package com.lyw.api.app.core.bicycle.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Bicycle (Request)")
public class BicycleRequestDto {
    @Schema(description = "Bicycle Name")
    private String bicycleName;
    @Schema(description = "Bicycle Description")
    private String bicycleDescription;
    @Schema(description = "Bicycle Price")
    private double bicyclePrice;
    @Schema(description = "Bicycle Size")
    private String bicycleSize;
    @Schema(description = "Bicycle Model")
    private String bicycleModel;
    @Schema(description = "Image Data")
    private String imageData;
}
