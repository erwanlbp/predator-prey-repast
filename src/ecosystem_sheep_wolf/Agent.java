package ecosystem_sheep_wolf;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;

public abstract class Agent {

	private int energy;

	public Agent(int penergy) {
		this.energy = penergy;
	}

	@ScheduledMethod(start = 1, interval = 1, priority = 1)
	public abstract void compute();

	@ScheduledMethod(start = 1, interval = 1, priority = 2)
	public abstract void implement();

	protected void move() {
		int width = RunEnvironment.getInstance().getParameters().getInteger("gridWidth");
		int height = RunEnvironment.getInstance().getParameters().getInteger("gridHeight");
		int rX = ContextCreator.rand.nextInt(3) - 1;
		int rY = ContextCreator.rand.nextInt(3) - 1;
		this.setPosX((this.getPosX() + rX) % width);
		this.setPosY((this.getPosY() + rY) % height);
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getPosX() {
		Grid<Agent> grid = (Grid<Agent>) ContextUtils.getContext(this).getProjection("grid");
		GridPoint gp = grid.getLocation(this);
		return gp.getX();
	}

	public void setPosX(int posX) {
		Grid<Agent> grid = (Grid<Agent>) ContextUtils.getContext(this).getProjection("grid");
		grid.moveTo(this, posX, this.getPosY());
	}

	public int getPosY() {
		Grid<Agent> grid = (Grid<Agent>) ContextUtils.getContext(this).getProjection("grid");
		GridPoint gp = grid.getLocation(this);
		return gp.getY();
	}

	public void setPosY(int posY) {
		Grid<Agent> grid = (Grid<Agent>) ContextUtils.getContext(this).getProjection("grid");
		grid.moveTo(this, this.getPosX(), posY);
	}
}
