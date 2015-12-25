package eg.edu.alexu.csd.oop.game.world;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.GameWindow;
import eg.edu.alexu.csd.oop.game.characters.falling.FallingCharacter;
import eg.edu.alexu.csd.oop.game.characters.falling.state.State;
import eg.edu.alexu.csd.oop.game.characters.loading.FallingCharacterPool;
import eg.edu.alexu.csd.oop.game.characters.players.ClownType3;
import eg.edu.alexu.csd.oop.game.characters.players.ObserverIF;
import eg.edu.alexu.csd.oop.game.characters.players.PlayerState;

public class HardWorld extends ExtendableWorld {

	private List<GameObject> constant = new LinkedList<GameObject>();
	private List<GameObject> moving = new LinkedList<GameObject>();
	private Stack<GameObject> rightControl1 = new Stack<GameObject>();
	private Stack<GameObject> leftControl1 = new Stack<GameObject>();
	private Stack<GameObject> rightControl2 = new Stack<GameObject>();
	private Stack<GameObject> checkScore = new Stack<GameObject>();

	private Stack<GameObject> leftControl2 = new Stack<GameObject>();
	private List<GameObject> control = new LinkedList<GameObject>();
	private int score;
	private static int MAX_TIME = 1 * 60 * 100000; // 1 minute
	private long startTime = System.currentTimeMillis();
	private final int width;
	private final int height;
	private ClownType3 clown1;
	private ClownType3 clown2;

	private boolean shotTaken;
	private boolean shotRestored;
	private final static int PLAYER_1 = 1;
	private final static int PLAYER_2 = 2;

	// private ShapePool shapePool;

	public HardWorld(int screenWidth, int screenHeight) {
		FallingCharacterPool.destroyInstance();
		width = screenWidth;
		height = screenHeight;
		clown1 = new ClownType3(width * 5 / 11, 460);
		clown2 = new ClownType3(width * 6 / 30, 460);

		control.add(clown1);
		control.add(clown2);
		GameWindow.log.debug("request falling character by entering pool");
		for (int counter = 0; counter < 20; counter++) {
			moving.add(FallingCharacterPool.getInstance(
					getClass().getSimpleName()).getShape());
		}
		shotRestored = false;
		shotTaken = false;
		constant.add(new Image("/circus1.jpg"));
		GameWindow.log.debug("window of the game now started");
	}

	@Override
	public List<GameObject> getConstantObjects() {
		return constant;
	}

	@Override
	public List<GameObject> getMovableObjects() {
		return moving;
	}

	@Override
	public List<GameObject> getControlableObjects() {
		return control;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean refresh() {
		clown1.setMaxX(this.getWidth() - clown2.getWidth());
		clown1.setMinX(clown2.getX() + clown2.getWidth() + 10);
		clown2.setMaxX(clown1.getX() - clown2.getWidth() - 10);
		clown2.setMinX(0);

		boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME;
		for (int counter = 0; counter < moving.size(); counter++) {
			GameObject o = moving.get(counter);
			moving.get(counter).setY((moving.get(counter).getY() + 1));

			if (o.getY() >= getHeight()) {
				// reuse the star in another position
				o.setY(-1 * (int) (Math.random() * getHeight()));
				o.setX((int) (Math.random() * getWidth()));
			}
		}
		for (int counter = 0; counter < moving.size(); counter++) {
			// first check left hand
			if (clown1.leftIsFree()
					&& intersectClownLeftHand(clown1, moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the left hand of the Clown");
			  clown1.setLeftHandState(PlayerState.EVENT_CAPTURED);
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				moving.add(FallingCharacterPool.getInstance(
						getClass().getSimpleName()).getShape());
				// first falling object has clown1 observer
				dummy.addObserver(clown1);
				dummy.processEvent(clown1, State.EVENT_LEFTHAND_COLLECTED,
						FallingCharacter.LEFT_HAND);
				leftControl1.push(dummy);
				control.add(dummy);
				counter--;
			} else if (clown1.rightIsFree()
					&& intersectClownRightHand(clown1, moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the right hand of the Clown");
				clown1.setRightHandState(PlayerState.EVENT_CAPTURED);
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				moving.add(FallingCharacterPool.getInstance(
						getClass().getSimpleName()).getShape());
				// first falling object has clown1 observer
				dummy.addObserver(clown1);
				dummy.processEvent(clown1, State.EVENT_RIGHTHAND_COLLECTED,
						FallingCharacter.RIGHT_HAND);
				rightControl1.push(dummy);
				control.add(dummy);
				counter--;
			} else if (!clown1.leftIsFree()
					&& intersect(leftControl1.peek(), moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the left hand of the Clown");
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				moving.add(FallingCharacterPool.getInstance(
						getClass().getSimpleName()).getShape());
				dummy.processEvent(clown1, State.EVENT_LEFTHAND_COLLECTED,
						FallingCharacter.LEFT_HAND);
				leftControl1.push(dummy);
				control.add(dummy);
				if (leftControl1.size() >= 3) {
					checkLeftScore(dummy, PLAYER_1);
				}
			} else if (!clown1.rightIsFree() && rightControl1.size() != 0
					&& intersect(rightControl1.peek(), moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the right hand of the Clown");
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				moving.add(FallingCharacterPool.getInstance(
						getClass().getSimpleName()).getShape());
				dummy.processEvent(clown1, State.EVENT_RIGHTHAND_COLLECTED,
						FallingCharacter.RIGHT_HAND);
				control.add(dummy);
				rightControl1.push(dummy);
				if (rightControl1.size() >= 3) {
					checkRightScore(dummy, PLAYER_1);
				}
				counter--;
			}

			else if (clown2.leftIsFree()
					&& intersectClownLeftHand(clown2, moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the left hand of the Clown");
			  clown2.setLeftHandState(PlayerState.EVENT_CAPTURED);
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				moving.add(FallingCharacterPool.getInstance(
						getClass().getSimpleName()).getShape());
				// first falling object has clown2 observer
				dummy.addObserver(clown2);
				dummy.processEvent(clown2, State.EVENT_LEFTHAND_COLLECTED,
						FallingCharacter.LEFT_HAND);
				leftControl2.push(dummy);
				control.add(dummy);
				counter--;
			} else if (clown2.rightIsFree()
					&& intersectClownRightHand(clown2, moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the right hand of the Clown");
				clown2.setRightHandState(PlayerState.EVENT_CAPTURED);
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				moving.add(FallingCharacterPool.getInstance(
						getClass().getSimpleName()).getShape());
				// first falling object has clown2 observer
				dummy.addObserver(clown2);
				dummy.processEvent(clown2, State.EVENT_RIGHTHAND_COLLECTED,
						FallingCharacter.RIGHT_HAND);
				rightControl2.push(dummy);
				control.add(dummy);
				counter--;
			} else if (!clown2.leftIsFree()
					&& intersect(leftControl2.peek(), moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the left hand of the Clown");
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				moving.add(FallingCharacterPool.getInstance(
						getClass().getSimpleName()).getShape());
				dummy.processEvent(clown2, State.EVENT_LEFTHAND_COLLECTED,
						FallingCharacter.LEFT_HAND);
				leftControl2.push(dummy);
				control.add(dummy);
				if (leftControl2.size() >= 3) {
					checkLeftScore(dummy, PLAYER_2);
				}
				counter--;
			} else if (!clown2.rightIsFree() && rightControl2.size() != 0
					&& intersect(rightControl2.peek(), moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the right hand of the Clown");
			  FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				moving.add(FallingCharacterPool.getInstance(
						getClass().getSimpleName()).getShape());
				dummy.processEvent(clown2, State.EVENT_RIGHTHAND_COLLECTED,
						FallingCharacter.RIGHT_HAND);
				control.add(dummy);
				rightControl2.push(dummy);
				if (rightControl2.size() >= 3) {
					checkRightScore(dummy, PLAYER_2);
				}
				counter--;
			}

		}
		if (score >= 4) {
			notify(3);
		}

		return !timeout;
	}

	private void checkRightScore(GameObject lastObject, int type) {
		if (type == 1)
			checkScore = rightControl1;
		else
			checkScore = rightControl2;

		int color = ((FallingCharacter) lastObject).getColor();
		if (((FallingCharacter) (checkScore.get(checkScore.size() - 2)))
				.getColor() == color
				&& ((FallingCharacter) (checkScore.get(checkScore.size() - 3)))
						.getColor() == color) {
			score++;
			GameWindow.log.debug("score incresed by 1");
			((FallingCharacter) checkScore.get(checkScore.size() - 1))
					.setVisible(false);
			((FallingCharacter) checkScore.get(checkScore.size() - 2))
					.setVisible(false);
			((FallingCharacter) checkScore.get(checkScore.size() - 3))
					.setVisible(false);

			((FallingCharacter) checkScore.get(checkScore.size() - 1))
					.notify(FallingCharacter.RIGHT_HAND);
			((FallingCharacter) checkScore.get(checkScore.size() - 2))
					.notify(FallingCharacter.RIGHT_HAND);
			((FallingCharacter) checkScore.get(checkScore.size() - 3))
					.notify(FallingCharacter.RIGHT_HAND);
			checkScore.pop();
			checkScore.pop();
			checkScore.pop();
		}
	}

	private void checkLeftScore(GameObject lastObject, int type) {
		if (type == 1)
			checkScore = leftControl1;
		else
			checkScore = leftControl2;

		int color = ((FallingCharacter) lastObject).getColor();
		if (((FallingCharacter) (checkScore.get(checkScore.size() - 2)))
				.getColor() == color
				&& ((FallingCharacter) (checkScore.get(checkScore.size() - 3)))
						.getColor() == color) {
			score++;
			GameWindow.log.debug("score incresed by 1");
			((FallingCharacter) checkScore.get(checkScore.size() - 1))
					.setVisible(false);
			((FallingCharacter) checkScore.get(checkScore.size() - 2))
					.setVisible(false);
			((FallingCharacter) checkScore.get(checkScore.size() - 3))
					.setVisible(false);

			((FallingCharacter) checkScore.get(checkScore.size() - 1))
					.notify(FallingCharacter.LEFT_HAND);
			((FallingCharacter) checkScore.get(checkScore.size() - 2))
					.notify(FallingCharacter.LEFT_HAND);
			((FallingCharacter) checkScore.get(checkScore.size() - 3))
					.notify(FallingCharacter.LEFT_HAND);
			checkScore.pop();
			checkScore.pop();
			checkScore.pop();
		}
	}

	@Override
	public String getStatus() {
		return "Score="
				+ score
				+ "   |   Time="
				+ Math.max(
						0,
						(MAX_TIME - (System.currentTimeMillis() - startTime)) / 1000); // update
																						// status
	}

	@Override
	public int getSpeed() {
		return 0;
	}

	@Override
	public int getControlSpeed() {
		return 20;
	}

	

}
