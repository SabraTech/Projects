package eg.edu.alexu.csd.oop.game.world;

import java.util.List;
import java.util.Stack;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.GameWindow;
import eg.edu.alexu.csd.oop.game.characters.falling.FallingCharacter;
import eg.edu.alexu.csd.oop.game.characters.falling.state.State;
import eg.edu.alexu.csd.oop.game.characters.loading.FallingCharacterPool;
import eg.edu.alexu.csd.oop.game.characters.players.ObserverIF;
import eg.edu.alexu.csd.oop.game.characters.players.Olaf;
import eg.edu.alexu.csd.oop.game.characters.players.Player;
import eg.edu.alexu.csd.oop.game.characters.players.PlayerState;

public class EasyWorld extends ExtendableWorld {

	private Stack<GameObject> rightControl = new Stack<GameObject>();
	private Stack<GameObject> leftControl = new Stack<GameObject>();

	private final int width;
	private final int height;
	private Player olaf;
	private boolean shotTaken;
	private boolean shotRestored;

	

	public EasyWorld(int screenWidth, int screenHeight) {
		MAX_TIME = 1 * 60 * 100000; // 1 minute
		startTime = System.currentTimeMillis();
		FallingCharacterPool.destroyInstance();
		this.height = screenHeight;
		this.width = screenWidth;

		olaf = new Olaf(width * 5 / 11, 350);
		olaf.setMaxX(this.width);
		olaf.setMinX(0);
		control.add(olaf);
		int shifted = -80 * 10;
    GameWindow.log.debug("request falling character by entering pool");
		for (int counter = 1; counter <= 10; counter++) {
			GameObject o = FallingCharacterPool.getInstance(
					getClass().getSimpleName()).getShape();
			o.setY(60);
			o.setX(shifted);
			moving.add(o);
			shifted += 80;
		}
		shifted = width * 2;
		for (int counter = 1; counter < 10; counter++) {
			GameObject o = FallingCharacterPool.getInstance(
					getClass().getSimpleName()).getShape();
			o.setY(60);
			o.setX(shifted);
			moving.add(o);
			shifted -= 80;
		}
		shotRestored = false;
		shotTaken = false;
		constant.add(new Image("/ice.jpg"));
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
		int shifted = -80;
		for (int counter = 0; counter < moving.size() / 2; counter++) {
			GameObject o = moving.get(counter);
			moving.get(counter).setX((moving.get(counter).getX() + 1));
			if (moving.get(counter).getX() >= width / 4) {
				moving.get(counter).setX((moving.get(counter).getX() - 1));
				moving.get(counter).setY((moving.get(counter).getY() + 1));
			}
			if (o.getY() >= getHeight()) {
				// reuse the star in another position
				o.setY(60);
				o.setX(shifted);
				shifted += 80;
			}
		}
		shifted = width;
		for (int counter = moving.size() / 2; counter < moving.size(); counter++) {
			GameObject o = moving.get(counter);
			moving.get(counter).setX((moving.get(counter).getX() - 1));
			if (moving.get(counter).getX() <= width * 3 / 4) {
				moving.get(counter).setX((moving.get(counter).getX() + 1));
				moving.get(counter).setY((moving.get(counter).getY() + 1));
			}
			if (o.getY() >= getHeight()) {
				// reuse the star in another position
				o.setY(60);
				o.setX(shifted);
				shifted -= 80;
			}
		}

		for (int counter = 0; counter < moving.size(); counter++) {
			// first check left hand
			if (olaf.leftIsFree()
					&& intersectClownLeftHand(olaf, moving.get(counter))) {
				olaf.setLeftHandState(PlayerState.EVENT_CAPTURED);
				GameWindow.log.debug("Intersection with the left hand of the Clown");
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				// moving.add(FallingCharacterPool.getInstance().getShape());
				// first falling object has olaf observer
				dummy.addObserver(olaf);
				dummy.processEvent(olaf, State.EVENT_LEFTHAND_COLLECTED,
						FallingCharacter.LEFT_HAND);
				leftControl.push(dummy);
				control.add(dummy);
				counter--;
			} else if (olaf.rightIsFree()
					&& intersectClownRightHand(olaf, moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the right hand of the Clown");
			  olaf.setRightHandState(PlayerState.EVENT_CAPTURED);
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				// moving.add(FallingCharacterPool.getInstance().getShape());
				// first falling object has clown observer
				dummy.addObserver(olaf);
				dummy.processEvent(olaf, State.EVENT_RIGHTHAND_COLLECTED,
						FallingCharacter.RIGHT_HAND);
				rightControl.push(dummy);
				control.add(dummy);
				counter--;
			} else if (!olaf.leftIsFree()
					&& intersect(leftControl.peek(), moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the left hand of the Clown");
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				// moving.add(FallingCharacterPool.getInstance().getShape());
				dummy.processEvent(olaf, State.EVENT_LEFTHAND_COLLECTED,
						FallingCharacter.LEFT_HAND);
				leftControl.push(dummy);
				control.add(dummy);
				if (leftControl.size() >= 3) {
					checkLeftScore(dummy);
				}
			} else if (!olaf.rightIsFree() && rightControl.size() != 0
					&& intersect(rightControl.peek(), moving.get(counter))) {
			  GameWindow.log.debug("Intersection with the right hand of the Clown");
				FallingCharacter dummy = (FallingCharacter) moving
						.remove(counter);
				// moving.add(FallingCharacterPool.getInstance().getShape());
				dummy.processEvent(olaf, State.EVENT_RIGHTHAND_COLLECTED,
						FallingCharacter.RIGHT_HAND);
				control.add(dummy);
				rightControl.push(dummy);
				if (rightControl.size() >= 3) {
					checkRightScore(dummy);
				}
				counter--;
			}
		}

		if (score >= 2) {
		  notify(1);
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
					.setVisible(false);
			((FallingCharacter) rightControl.get(rightControl.size() - 2))
					.setVisible(false);
			((FallingCharacter) rightControl.get(rightControl.size() - 3))
					.setVisible(false);

			((FallingCharacter) rightControl.get(rightControl.size() - 1))
					.notify(FallingCharacter.RIGHT_HAND);
			((FallingCharacter) rightControl.get(rightControl.size() - 2))
					.notify(FallingCharacter.RIGHT_HAND);
			((FallingCharacter) rightControl.get(rightControl.size() - 3))
					.notify(FallingCharacter.RIGHT_HAND);
			rightControl.pop();
			rightControl.pop();
			rightControl.pop();
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
					.setVisible(false);
			((FallingCharacter) leftControl.get(leftControl.size() - 2))
					.setVisible(false);
			((FallingCharacter) leftControl.get(leftControl.size() - 3))
					.setVisible(false);

			((FallingCharacter) leftControl.get(leftControl.size() - 1))
					.notify(FallingCharacter.LEFT_HAND);
			((FallingCharacter) leftControl.get(leftControl.size() - 2))
					.notify(FallingCharacter.LEFT_HAND);
			((FallingCharacter) leftControl.get(leftControl.size() - 3))
					.notify(FallingCharacter.LEFT_HAND);
			leftControl.pop();
			leftControl.pop();
			leftControl.pop();
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
		return 1;
	}

	@Override
	public int getControlSpeed() {
		return 10;
	}

	

}
