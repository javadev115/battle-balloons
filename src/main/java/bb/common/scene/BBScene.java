package bb.common.scene;

import bb.common.BBConfig;
import bb.common.actor.model.Balloon;
import bb.common.actor.model.BigBalloon;
import bb.common.actor.model.Bully;
import bb.common.actor.model.Dog;
import bb.common.actor.model.Judo;
import bb.common.actor.model.Lexi;
import bb.common.actor.model.Obstacle;
import bb.common.actor.model.Text;
import retroge.actor.Actor;
import retroge.actor.ActorLifecycleState;
import retroge.actor.Player;
import retroge.event.GameEvent;
import retroge.event.GameListener;
import retroge.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retroge.util.Assert;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by willie on 7/2/17.
 */
public class BBScene implements Scene {
	private static final Logger log = LoggerFactory.getLogger(BBScene.class);

	private Player player;

	private final List<List<? extends Actor>> allActors = new ArrayList<>();
	private final List<Balloon> balloons = new LinkedList<>();
	private final List<BigBalloon> bigBalloons = new LinkedList<>();
	private final List<Bully> bullies = new LinkedList<>();
	private final List<Dog> dogs = new LinkedList<>();
	private final List<Judo> judos = new LinkedList<>();
	private final List<Lexi> lexis = new LinkedList<>();
	private final List<Obstacle> obstacles = new LinkedList<>();
	private final List<Text> texts = new LinkedList<>();

	private final List<GameListener> gameListeners = new LinkedList<>();

	private boolean active = true;

	public BBScene() {
		allActors.add(balloons);
		allActors.add(bigBalloons);
		allActors.add(bullies);
		allActors.add(dogs);
		allActors.add(judos);
		allActors.add(lexis);
		allActors.add(obstacles);
		allActors.add(texts);
	}
	
	@Override
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public int getMinWorldX() {
		return 0;
	}
	
	@Override
	public int getMaxWorldX() {
		return BBConfig.WORLD_SIZE.width - 1;
	}
	
	@Override
	public int getMinWorldY() {
		return 0;
	}
	
	@Override
	public int getMaxWorldY() {
		return BBConfig.WORLD_SIZE.height - 1;
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public List<List<? extends Actor>> getAllActors() {
		return allActors;
	}

	public List<Balloon> getBalloons() {
		return balloons;
	}

	public List<BigBalloon> getBigBalloons() {
		return bigBalloons;
	}

	public List<Bully> getBullies() {
		return bullies;
	}

	public List<Dog> getDogs() {
		return dogs;
	}

	public List<Judo> getJudos() {
		return judos;
	}

	public List<Lexi> getLexis() {
		return lexis;
	}

	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	public List<Text> getTexts() {
		return texts;
	}

	public void addGameListener(GameListener listener) {
		Assert.notNull(listener, "listener can't be null");
		gameListeners.add(listener);
		log.trace("BBScene has {} GameListeners:", gameListeners.size());
		gameListeners.forEach(gl -> {
			log.trace("  {}", gl);
		});
	}
	
	@Override
	public void update() {
		garbageCollectActors();
		updateActors();
		CollisionDetector.checkCollisions(this);
	}

	public void fireGameEvent(GameEvent event) {
		gameListeners.forEach(listener -> listener.handleEvent(event));
	}

	private void garbageCollectActors() {
		allActors.forEach(actors -> {
			for (ListIterator<? extends Actor> it = actors.listIterator(); it.hasNext();) {
				Actor actor = it.next();
				if (actor.getState() == ActorLifecycleState.GONE) {
					it.remove();
				}
			}
		});
	}

	private void updateActors() {
		allActors.forEach(actors -> actors.forEach(actor -> actor.update()));
	}
}
