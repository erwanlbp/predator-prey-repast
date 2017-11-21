package ecosystem_sheep_wolf;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public class Sheep extends Agent {

	private boolean ateGrass;
	private boolean hasBeenEaten;

	public Sheep(int penergy) {
		super(penergy);
		this.ateGrass = false;
		this.hasBeenEaten = false;
	}

	public void hasBeenEaten() {
		this.hasBeenEaten = true;
	}

	public boolean isDead() {
		return this.hasBeenEaten;
	}

	@Override
	public void compute() {

		if (this.hasBeenEaten) {
			return;
		}

		Iterable<Agent> agents = ((Grid<Agent>) ContextUtils.getContext(this).getProjection("grid")).getObjectsAt(this.getPosX(), this.getPosY());
		for (Agent a : agents) {
			if (a instanceof Grass && ((Grass) a).isEatable()) {
				this.ateGrass = true;
				((Grass) a).hasBeenEaten();
			}
		}
	}

	@Override
	public void implement() {

		if (this.ateGrass) {
			this.ateGrass = false;
			this.setEnergy(this.getEnergy() + RunEnvironment.getInstance().getParameters().getInteger("gainFromGrass"));
		}

		this.setEnergy(this.getEnergy() - 1);

		// If he died
		if (this.getEnergy() <= 0 || this.hasBeenEaten) {
			ContextUtils.getContext(this).remove(this);
			return;
		}

		// Reproduction
		if (this.getEnergy() > RunEnvironment.getInstance().getParameters().getInteger("limitReproduction")) {
			Sheep s = new Sheep(this.getEnergy() / 2);
			this.setEnergy(this.getEnergy() / 2);
			ContextUtils.getContext(this).add(s);
			((Grid<Agent>) ContextUtils.getContext(this).getProjection("grid")).moveTo(s, this.getPosX(),this.getPosY());
		}

		this.move();
	}
}
