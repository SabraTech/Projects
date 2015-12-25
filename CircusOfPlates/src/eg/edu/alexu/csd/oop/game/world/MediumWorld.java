package eg.edu.alexu.csd.oop.game.world;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.GameWindow;
import eg.edu.alexu.csd.oop.game.characters.falling.FallingCharacter;
import eg.edu.alexu.csd.oop.game.characters.falling.state.State;
import eg.edu.alexu.csd.oop.game.characters.loading.FallingCharacterPool;
import eg.edu.alexu.csd.oop.game.characters.players.ClownType1;
import eg.edu.alexu.csd.oop.game.characters.players.ObserverIF;
import eg.edu.alexu.csd.oop.game.characters.players.PlayerState;

public class MediumWorld extends ExtendableWorld {

	private final int width;
	private final int height;
	// private Player clown;
	private boolean shotTaken;
	private boolean shotRestored;

	public MediumWorld(int screenWidth, int screenHeight) {
		FallingCharacterPool.destroyInstance();
		MAX_TIME *= 10;
		ct = new MyCareTaker();
		leftControl = new Stack<GameObject>();
		rightControl = new Stack<GameObject>();
		constant = new LinkedList<GameObject>();
		moving = new LinkedList<GameObject>();
		control = new LinkedList<GameObject>();
		startTime = System.currentTimeMillis();
		width = screenWidth;
		height = screenHeight;
		clown = new ClownType1(width * 5 / 11, 300);
		clown.setMinX(0);
		clown.setMaxX(this.width);
		control.add(clown);
		GameWindow.log.debug("request falling character by entering pool");
		for (int counter = 0; counter < 10; counter++) {
			moving.add(FallingCharacterPool.getInstance(
					getClass().getSimpleName()).getShape());
		}
		shotRestored = false;
		shotTaken = false;
		constant.add(new Image("/background.jpg"));
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
		boolean timeout = System.currentTimeMillis() - startTime > MAX_TIME;
		for (int counter = 0; counter < moving.size(); counter++) {
			// first check left hand
			GameObject o = moving.get(counter);
			moving.get(counter).setY((moving.get(counter).getY() + 1));
			if (o.getY() >= getHeight()) {
				// reuse the star in another position
				o.setY(-1 * (int) (Math.random() * getHeight()));
				o.setX((int) (Math.random() * getWidth()));
			}
			if (clown.leftIsFree()
					&& intersectClownLeftHand(clown, moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the left hand of the Clown");
			  clown.setLeftHandState(PlayerState.EVENT_CAPTURED);
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				// first falling object has clown observer
				dummy.addObserver(clown);
				dummy.processEvent(clown, State.EVENT_LEFTHAND_COLLECTED,
						FallingCharacter.LEFT_HAND);
				leftControl.push(dummy);
				control.add(dummy);
				dummy.setX(0);
				dummy.setY(clown.getLeftHandY() + dummy.getHeight());
				counter--;
			} else if (clown.rightIsFree()
					&& intersectClownRightHand(clown, moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the right hand of the Clown");
			  clown.setRightHandState(PlayerState.EVENT_CAPTURED);
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				// first falling object has clown observer
				dummy.addObserver(clown);
				dummy.processEvent(clown, State.EVENT_RIGHTHAND_COLLECTED,
						FallingCharacter.RIGHT_HAND);
				rightControl.push(dummy);
				control.add(dummy);
				dummy.setX(0);
				dummy.setY(clown.getRightHandY() + dummy.getHeight());
				counter--;
			} else if (!clown.leftIsFree()
					&& intersect(leftControl.peek(), moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the left hand of the Clown");
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				dummy.processEvent(clown, State.EVENT_LEFTHAND_COLLECTED,
						FallingCharacter.LEFT_HAND);
				leftControl.push(dummy);
				control.add(dummy);
				dummy.setX(0);
				dummy.setY(leftControl.peek().getY() + dummy.getHeight());
				if (leftControl.size() >= 3) {
					checkLeftScore(dummy);
				}
				counter--;
			} else if (!clown.rightIsFree() && rightControl.size() != 0
					&& intersect(rightControl.peek(), moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the right hand of the Clown");
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				dummy.processEvent(clown, State.EVENT_RIGHTHAND_COLLECTED,
						FallingCharacter.RIGHT_HAND);
				control.add(dummy);
				rightControl.push(dummy);
				dummy.setY(rightControl.peek().getY() + dummy.getHeight());
				dummy.setX(0);
				if (rightControl.size() >= 3) {
					checkRightScore(dummy);
				}
				counter--;
			}

		}
		if (score >= 3 && !shotTaken) {
			try {
			  GameWindow.log.debug("snap shot has been taken");
				makeShot();
				shotTaken = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(score >= 4 && shotRestored){
		  notify(2);
		}

		if (leftControl.size() == 12 || rightControl.size() == 12
				|| leftControl.size() + rightControl.size() == 20) {
		  
			if (shotTaken && !shotRestored) {
				System.out.println(score + " " + startTime);
				restoreState();
				GameWindow.log.debug("snap shot has been restored");
				shotRestored = true;
				return true;
			} else {
			  GameWindow.log.warn("you are about to lose");
			}

		}
		if (moving.size() < 10) {
			for (int counter = 0; counter < 10 - moving.size(); counter++) {
				GameObject o = FallingCharacterPool.getInstance(
						getClass().getSimpleName()).getShape();
				o.setY(0);
				moving.add(o);
			}
		}
		return !timeout;

	}

	private void checkRightScore(GameObject lastObject) {

		int color = ((FallingCharacter) lastObject).getColor();
		if (((FallingCharacter) (rightControl.get(rightControl.size() - 2)))
				.getColor() == color
				&& ((FallingCharacter) (rightControl
						.get(rightControl.size() - 3))).getColor() == color) {
			score++;
			GameWindow.log.debug("score incresed by 1");
			((FallingCharacter) rightControl.get(rightControl.size() - 1))
					.notify(FallingCharacter.RIGHT_HAND);
			((FallingCharacter) rightControl.get(rightControl.size() - 2))
					.notify(FallingCharacter.RIGHT_HAND);
			((FallingCharacter) rightControl.get(rightControl.size() - 3))
					.notify(FallingCharacter.RIGHT_HAND);
			FallingCharacter dummy1 = (FallingCharacter) rightControl.pop();
			FallingCharacter dummy2 = (FallingCharacter) rightControl.pop();
			FallingCharacter dummy3 = (FallingCharacter) rightControl.pop();
			control.remove(dummy1);
			control.remove(dummy2);
			control.remove(dummy3);
			FallingCharacterPool.getInstance(getClass().getSimpleName())
					.returnObject(dummy1);
			FallingCharacterPool.getInstance(getClass().getSimpleName())
					.returnObject(dummy2);
			FallingCharacterPool.getInstance(getClass().getSimpleName())
					.returnObject(dummy3);
			for (int counter = 0; counter < 3; counter++) {
				moving.add(FallingCharacterPool.getInstance(
						getClass().getSimpleName()).getShape());
			}

		}
	}

	private void checkLeftScore(GameObject lastObject) {

		int color = ((FallingCharacter) lastObject).getColor();
		if (((FallingCharacter) (leftControl.get(leftControl.size() - 2)))
				.getColor() == color
				&& ((FallingCharacter) (leftControl.get(leftControl.size() - 3)))
						.getColor() == color) {
			score++;
			GameWindow.log.debug("score incresed by 1");
			((FallingCharacter) leftControl.get(leftControl.size() - 1))
					.notify(FallingCharacter.LEFT_HAND);
			((FallingCharacter) leftControl.get(leftControl.size() - 2))
					.notify(FallingCharacter.LEFT_HAND);
			((FallingCharacter) leftControl.get(leftControl.size() - 3))
					.notify(FallingCharacter.LEFT_HAND);
			FallingCharacter dummy1 = (FallingCharacter) leftControl.pop();
			FallingCharacter dummy2 = (FallingCharacter) leftControl.pop();
			FallingCharacter dummy3 = (FallingCharacter) leftControl.pop();
			control.remove(dummy1);
			control.remove(dummy2);
			control.remove(dummy3);
			FallingCharacterPool.getInstance(getClass().getSimpleName())
					.returnObject(dummy1);
			FallingCharacterPool.getInstance(getClass().getSimpleName())
					.returnObject(dummy2);
			FallingCharacterPool.getInstance(getClass().getSimpleName())
					.returnObject(dummy3);
			for (int counter = 0; counter < 3; counter++) {
				moving.add(FallingCharacterPool.getInstance(
						getClass().getSimpleName()).getShape());
			}
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
		return 5;
	}

	@Override
	public int getControlSpeed() {
		return 10;
	}

	

}
