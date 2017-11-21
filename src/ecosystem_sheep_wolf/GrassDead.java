package ecosystem_sheep_wolf;

import repast.simphony.context.Context;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public class GrassDead extends Grass {

	public GrassDead(int x, int y, int penergy) {
		super(x,y,penergy);
	}

	public boolean isEatable() {
		return false;
	}

	@Override
	public void implement() {
		super.implement();
		
		if(this.getEnergy() > RunEnvironment.getInstance().getParameters().getInteger("grassRegeneration")) {
			Context<Object> context = ContextUtils.getContext(this);
			context.remove(this);
			GrassAlive a = new GrassAlive(this.getPosX(), this.getPosY(), this.getEnergy());
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
