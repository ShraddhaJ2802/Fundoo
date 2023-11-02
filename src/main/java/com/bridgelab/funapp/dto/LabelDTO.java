package com.bridgelab.funapp.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
@Data
public class LabelDTO {
	@NotEmpty(message = "Label not be empty")
	public String label;

}
