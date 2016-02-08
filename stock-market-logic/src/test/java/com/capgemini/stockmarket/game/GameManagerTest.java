package com.capgemini.stockmarket.game;

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
	private GameManager gameManager;
	
	@Test
	public void shouldWireGameManager() {
		Assert.assertNotNull(gameManager);
	}
	
	@Test
	public void shouldGetDelimeterFromProperties() {
		Assert.assertNotNull(gameManager.getCsvHandler());
	}
}
