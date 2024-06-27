package com.lyw.api.app.core.bicycle.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Bicycle (Request)")
public class BicycleRequestDto {
    public BicycleRequestDto(String string, String string2, double d, String string3, String string4, String string5) {
		this.bicycleName = string;
		this.bicycleDescription= string2;
		this.bicyclePrice = d;
		this.bicycleSize = string3;
		this.bicycleModel = string4;
		this.imageData = string5;
	}
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
