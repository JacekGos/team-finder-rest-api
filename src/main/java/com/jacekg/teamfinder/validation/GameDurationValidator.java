package com.jacekg.teamfinder.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GameDurationValidator implements ConstraintValidator<ValidDuration, Integer> {
	
	private final List<Integer> acceptedValues = new ArrayList<>(Arrays.asList(60, 120));

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return acceptedValues.contains(value);
	} 
}
