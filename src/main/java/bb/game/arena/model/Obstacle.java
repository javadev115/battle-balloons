package bb.game.arena.model;

import bb.game.arena.event.EntityState;

/**
 * Created by wwheeler on 6/11/17.
 */
public class Obstacle extends AbstractEntity {
	public static final int SCORE = 50;
	
	private static final int WIDTH = 8;
	private static final int HEIGHT = 8;
	
	public Obstacle(ArenaScene arenaScene) {
		super(arenaScene, EntityState.ACTIVE);
		randomizeLocation();
	}
	
	@Override
	public int getWidth() {
		return WIDTH;
	}
	
	@Override
	public int getHeight() {
		return HEIGHT;
	}
	
	@Override
	public void update() {
		// Nothing to updateModel here for now.
		// At some point we will add animations though.
	}
}