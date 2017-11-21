package ecosystem_sheep_wolf;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public class Wolf extends Agent {

	private boolean died;
	private boolean ateSheep;

	public Wolf(int penergy) {
		super(penergy);
		this.died = false;
		this.ateSheep = false;
	}

	@Override
	public void compute() {

		if (this.died) {
			return;
		}

		Iterable<Agent> agents = ((Grid<Agent>) ContextUtils.getContext(this).getProjection("grid")).getObjectsAt(this.getPosX(), this.getPosY());
		for (Agent a : agents) {
			if (a instanceof Sheep && !((Sheep) a).isDead()) {
				((Sheep) a).hasBeenEaten();
				this.ateSheep = true;
			}
		}
	}

	@Override
	public void implement() {

		if (this.ateSheep) {
			this.ateSheep = false;
			this.setEnergy(this.getEnergy() + RunEnvironment.getInstance().getParameters().getInteger("gainFromSheep"));
		}

		this.setEnergy(this.getEnergy() - 1);

		// If he died
		if (this.getEnergy() <= 0 || this.died) {
			ContextUtils.getContext(this).remove(this);
			return;
		}

		// Reproduction
		if (this.getEnergy() > RunEnvironment.getInstance().getParameters().getInteger("limitReproduction")) {
			Wolf w = new Wolf(this.getEnergy() / 2);
			this.setEnergy(this.getEnergy() / 2);
			ContextUtils.getContext(this).add(w);
			((Grid<Agent>) ContextUtils.getContext(this).getProjection("grid")).moveTo(w, this.getPosX(),this.getPosY());
		}

		this.move();
	}
}
