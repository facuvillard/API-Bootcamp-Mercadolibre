package com.mercadolibre.facundo_villard.beans;

import java.util.Random;

import com.mercadolibre.facundo_villard.dtos.SampleDTO;
import org.springframework.stereotype.Component;

@Component
public class RandomSampleBean {
	private Random random = new Random();

	public SampleDTO random() {
		return new SampleDTO(random.nextInt(Integer.MAX_VALUE));
	}
}

