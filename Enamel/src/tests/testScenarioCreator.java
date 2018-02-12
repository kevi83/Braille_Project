package tests;

import org.junit.*;

import enamel.ScenarioCreator;

import static org.junit.Assert.*;

public class testScenarioCreator {

	@Test
	public void testInit() {
		
		ScenarioCreator creator = new ScenarioCreator();
		creator.launch(creator.getClass());
	}
}
