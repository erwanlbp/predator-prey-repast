package ecosystem_sheep_wolf;

import java.util.Random;

import repast.simphony.context.Context;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;

public class ContextCreator implements ContextBuilder<Agent> {

	public static Random rand = new Random(System.currentTimeMillis());

	@Override
	public Context<Agent> build(Context<Agent> context) {
		context.setId("ecosystem_sheep_wolf");

		int width = RunEnvironment.getInstance().getParameters().getInteger("gridWidth");
		int height = RunEnvironment.getInstance().getParameters().getInteger("gridHeight");
		int initEnergy = RunEnvironment.getInstance().getParameters().getInteger("initEnergy");
		int grassRegeneration = RunEnvironment.getInstance().getParameters().getInteger("grassRegeneration");

		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		Grid<Agent> grid = gridFactory.createGrid("grid", context, new GridBuilderParameters<Agent>(new WrapAroundBorders(), new SimpleGridAdder<Agent>(), true, width, height));

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int r = (int) (rand.nextInt(8));

				Agent a = null;
				if (r == 0 || r == 3) {
					a = new GrassDead(x, y, grassRegeneration / 2);
				} else if (r == 1) {
					a = new Sheep(initEnergy);
				} else if (r == 2) {
					a = new Wolf(initEnergy);
				}
				
				if(a != null) {
					context.add(a);
					grid.moveTo(a, x, y);
				}
			}
		}
		return context;
	}

}
