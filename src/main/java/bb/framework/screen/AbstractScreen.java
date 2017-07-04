package bb.framework.screen;

import bb.framework.GameConfig;
import bb.framework.GameContext;
import bb.framework.event.ScreenEvent;
import bb.framework.event.ScreenListener;
import bb.framework.util.Assert;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by willie on 7/3/17.
 */
public abstract class AbstractScreen implements Screen {
	private String name;
	private GameConfig config;
	private GameContext context;
	private JComponent jComponent;
	private KeyListener keyHandler;
	private ActionListener timerHandler;
	private Timer timer;

	private final List<ScreenListener> screenListeners = new LinkedList<>();

	public AbstractScreen(String name, GameConfig config, GameContext context) {
		Assert.notNull(name, "name can't be null");
		Assert.notNull(name, "config can't be null");
		Assert.notNull(name, "context can't be null");

		this.name = name;
		this.config = config;
		this.context = context;
	}

	// FIXME Ugh, clients have to call this to finish construction.
	// This is an ugly hack, but we need to build the components *after* the screen constructors complete. [WLW]
	@Deprecated
	public void postConstruct() {
		this.jComponent = buildJComponent();
		this.keyHandler = buildKeyHandler();
		this.timerHandler = buildTimerHandler();
		this.timer = new Timer(config.getFramePeriodMs(), timerHandler);
	}

	public GameConfig getConfig() {
		return config;
	}

	public GameContext getContext() {
		return context;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public JComponent getJComponent() {
		return jComponent;
	}

	@Override
	public KeyListener getKeyHandler() {
		return keyHandler;
	}

	@Override
	public void start() {
		timer.start();
	}

	@Override
	public void stop() {
		timer.stop();
	}

	@Override
	public void addScreenListener(ScreenListener listener) {
		Assert.notNull(listener, "listener can't be null");
		screenListeners.add(listener);
	}

	/**
	 * The config and context are already set when this is called.
	 */
	public abstract JComponent buildJComponent();

	/**
	 * The config and context are already set when this is called.
	 */
	public abstract KeyListener buildKeyHandler();

	/**
	 * The config and context are already set when this is called.
	 */
	public abstract ActionListener buildTimerHandler();

	protected void fireScreenEvent(int type) {
		ScreenEvent event = new ScreenEvent(type);
		screenListeners.forEach(listener -> listener.handleEvent(event));
	}
}