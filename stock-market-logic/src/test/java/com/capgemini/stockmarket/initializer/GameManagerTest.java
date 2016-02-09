package com.capgemini.stockmarket.initializer;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/game-context.xml"})
public class GameManagerTest {

	@Inject
	private SimulationInitializer initializer;
	
	@Test
	public void shouldWireGameManager() {
		Assert.assertNotNull(initializer);
	}
	
}
