package ecosystem_sheep_wolf;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.ContextUtils;

public abstract class Grass extends Agent {
	
	protected boolean isShown;
	protected boolean hasBeenEaten;
	protected int posX;
	protected int posY;

	public Grass(int x, int y, int penergy) {
		super(penergy);
		this.posX = x;
		this.posY = y;
		this.hasBeenEaten = false;
		this.isShown = false;
	}

	public abstract boolean isEatable(); 

	public void hasBeenEaten() {
		this.hasBeenEaten = true;
	}

	@Override
	public void compute() {
		
	}
	
	@Override
	public void implement() {
		this.setEnergy(this.getEnergy() + 1);

		if (this.hasBeenEaten) {
			this.setEnergy(0);
		}

	}

	@Override
	public int getPosX() {
		return this.posX;
	}

	@Override
	public void setPosX(int posX) {
		super.setPosX(posX);
		this.posX = posX;
	}

	@Override
	public int getPosY() {
		return this.posY;
	}

	@Override
	public void setPosY(int posY) {
		super.setPosY(posY);
		this.posY = posY;
	}

}
