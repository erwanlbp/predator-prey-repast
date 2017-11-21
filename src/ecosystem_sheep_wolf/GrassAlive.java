package ecosystem_sheep_wolf;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public class GrassAlive extends Grass {

	public GrassAlive(int x, int y, int penergy) {
		super(x,y,penergy);
	}

	public boolean isEatable() {
		return !this.hasBeenEaten;
	}

	@Override
	public void implement() {
		super.implement();
		
		if(this.hasBeenEaten) {
			Context<Object> context = ContextUtils.getContext(this);
			context.remove(this);
			GrassDead a = new GrassDead(this.getPosX(), this.getPosY(), this.getEnergy());
			context.add(a);
			((Grid<Agent>)context.getProjection("grid")).moveTo(a, this.getPosX(), this.getPosY());
			return;
		}
		
		if (!this.isShown) {
			this.isShown = true;
			((Grid<Agent>) ContextUtils.getContext(this).getProjection("grid")).moveTo(this, this.getPosX(), this.getPosY());
		}
	}
}
