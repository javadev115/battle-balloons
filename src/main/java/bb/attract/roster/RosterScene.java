package bb.attract.roster;

import bb.common.BBConfig;
import bb.common.actor.model.Lexi;
import bb.common.actor.model.Obstacle;
import bb.common.actor.model.Text;
import bb.common.scene.TtlScene;

/**
 * Created by willie on 7/2/17.
 */
public class RosterScene extends TtlScene {
	private static final int TTL = 15 * BBConfig.FRAMES_PER_SECOND;

	public RosterScene() {
		super(TTL);
		initScene();
	}

	@Override
	public void update() {
		super.update();
		// TODO Introduce new actors according to the frame
	}

	private void initScene() {
		getTexts().add(new Text(this, "Hello, I'm Lexi.", 40, 140));
		getLexis().add(new Lexi(this, new RosterLexiBrain(), 50, 180));
		getObstacles().add(new Obstacle(this, 240, 180));
	}
}
