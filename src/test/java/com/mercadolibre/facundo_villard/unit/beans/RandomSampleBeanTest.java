package com.mercadolibre.facundo_villard.unit.beans;

import com.mercadolibre.facundo_villard.beans.RandomSampleBean;

import com.mercadolibre.facundo_villard.dtos.SampleDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomSampleBeanTest {

	@Test
	public void randomPositiveTestOK() {
		RandomSampleBean randomSample = new RandomSampleBean();

		SampleDTO sample = randomSample.random();
		
		assertTrue(sample.getRandom() >= 0);
	}
}
