package tests;

import org.junit.Test;
import enamel.ScenarioCreator;

public class testScenarioCreator {

	@Test
	public void testInit() {
		
		ScenarioCreator creator = new ScenarioCreator();
		creator.launch(creator.getClass());
	}
}
