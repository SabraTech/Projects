package eg.edu.alexu.csd.oop.game.world;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.characters.falling.FallingCharacter;
import eg.edu.alexu.csd.oop.game.characters.falling.state.State;
import eg.edu.alexu.csd.oop.game.characters.players.ObserverIF;
import eg.edu.alexu.csd.oop.game.characters.players.Player;

public abstract class ExtendableWorld implements World {
	protected List<GameObject> constant = new LinkedList<GameObject>();
	protected List<GameObject> moving = new LinkedList<GameObject>();
	protected List<GameObject> control = new LinkedList<GameObject>();
	protected Stack<GameObject> rightControl;
	protected Stack<GameObject> leftControl;
	protected Stack<GameObject> rightControl2;
	protected Stack<GameObject> leftControl2;
	protected int score;
	protected static int MAX_TIME = 1 * 60 * 1000; // 1 minute
	protected long startTime;
	protected Player clown;
	protected Player clown2;
	protected MyCareTaker ct;
	protected ObserverIF observer;

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
	public abstract int getWidth();

	@Override
	public abstract int getHeight();

	protected final boolean intersect(GameObject o1, GameObject o2) {
		return (Math.abs((o1.getX() + o1.getWidth() / 2)
				- (o2.getX() + o2.getWidth() / 2)) <= o1.getWidth())
				&& (Math.abs((o1.getY() + o1.getHeight() / 2)
						- (o2.getY() + o2.getHeight() / 2)) <= o1.getHeight());
	}

	protected final boolean intersectClownLeftHand(Player o1, GameObject o2) {
		return (o1.getLeftHandX() - (o2.getX() + o2.getWidth() / 2) <= o2
				.getWidth() / 2)
				&& (o1.getLeftHandX() - (o2.getX() + o2.getWidth() / 2) >= 0)
				&& ((o1.getLeftHandY()) - (o2.getY() + o2.getHeight() / 2) <= o2
						.getHeight() / 2)
				&& ((o1.getLeftHandY()) - (o2.getY() + o2.getHeight() / 2) >= 0);
	}

	protected final boolean intersectClownRightHand(Player o1, GameObject o2) {
		return (o1.getRightHandX() - (o2.getX() + o2.getWidth() / 2) <= o2
				.getWidth() / 2)
				&& (o1.getRightHandX() - (o2.getX() + o2.getWidth() / 2) >= 0)
				&& ((o1.getRightHandY()) - (o2.getY() + o2.getHeight() / 2) <= o2
						.getHeight() / 2)
				&& ((o1.getRightHandY()) - (o2.getY() + o2.getHeight() / 2) >= 0);
	}

	@Override
	public abstract boolean refresh();

	@Override
	public abstract String getStatus();

	@Override
	public abstract int getSpeed();

	@Override
	public abstract int getControlSpeed();

	protected void setConstant(List<GameObject> constant) {
		this.constant = constant;
	}

	protected List<GameObject> getMoving() {
		return moving;
	}

	protected void setMoving(List<GameObject> moving) {
		this.moving = moving;
	}

	protected void setControl(List<GameObject> control) {
		this.control = control;
	}

	protected void setScore(int score) {
		this.score = score;
	}

	protected void setTime(long time) {
		this.startTime = time;
	}

	public void addObserver(ObserverIF observer) {
    this.observer = observer;
  }

	public void removeObserver(ObserverIF observer) {
    this.observer = null;
  }

  // call when change the score
	public void notify(int event) {
    // just if has a observer to notify
    if (this.observer != null) {
      observer.notify(event);
    }
  }

	public MomentoIf getCurrentShot() {
		return new Momento(clown, clown2, control, constant, moving,
				leftControl, leftControl2, rightControl, rightControl2,
				startTime, score);
	}

	public void makeShot() throws IOException {
		ct.saveState(this.getCurrentShot());
	}

	public void restoreState() {
		MomentoIf mom = ct.restoreState();
		Momento momento = (Momento) mom;
		this.setConstant(momento.getConstant());
		this.setMoving(momento.getMoving());
		this.setTime(momento.getTime());
		this.setScore(momento.getScore());
		this.rightControl = momento.getRightControl();
		this.leftControl = momento.getLeftControl();
		this.rightControl2 = momento.getRightControl2();
		this.leftControl2 = momento.getLeftControl2();
		this.clown = momento.getPlayer();
		this.clown2 = momento.getPlayer2();
		this.control = momento.control;
	}

	private class Momento implements MomentoIf, Serializable {
		/**
     * 
     */
		private static final long serialVersionUID = -2258819846931436119L;
		private Player player;
		private Player player2;
		private List<GameObject> moving;
		private List<GameObject> constant;
		private List<GameObject> control;
		private Stack<GameObject> leftControl;
		private Stack<GameObject> rightControl;
		private Stack<GameObject> leftControl2;
		private Stack<GameObject> rightControl2;
		private Long startTime;
		private int score;

		public Momento(Player clown, Player clown2, List<GameObject> control,
				List<GameObject> constant, List<GameObject> moving,
				Stack<GameObject> leftControl, Stack<GameObject> leftControl2,
				Stack<GameObject> rightControl,
				Stack<GameObject> rightControl2, Long startTime, int score) {
			this.constant = new ArrayList<GameObject>();
			this.control = new ArrayList<GameObject>();
			for (int counter = 0; counter < constant.size(); counter++) {
				this.constant.add(constant.get(counter));
			}
			this.moving = new ArrayList<GameObject>();
			for (int counter = 0; counter < moving.size(); counter++) {
				this.moving.add((FallingCharacter) ((FallingCharacter) moving
						.get(counter)).clone());
			}
			this.player = (Player) clown.clone();
			this.control.add(this.player);
			if (clown2 != null) {
				this.player2 = (Player) clown2.clone();
				this.control.add(this.player2);
			}

			this.startTime = startTime;
			this.score = score;
			this.leftControl = new Stack<GameObject>();
			for (int counter = 0; counter < leftControl.size(); counter++) {
				this.leftControl
						.push((FallingCharacter) ((FallingCharacter) leftControl
								.get(counter)).clone());
				this.control.add(this.leftControl.peek());
				((FallingCharacter) this.leftControl.peek()).processEvent(
						this.player, State.EVENT_LEFTHAND_COLLECTED,
						FallingCharacter.LEFT_HAND);
				((FallingCharacter) this.leftControl.peek())
						.addObserver(this.player);

			}
			if (leftControl2 != null) {
				this.leftControl2 = new Stack<GameObject>();
				for (int counter = 0; counter < leftControl2.size(); counter++) {
					this.leftControl2
							.push((FallingCharacter) ((FallingCharacter) leftControl2
									.get(counter)).clone());
					this.control.add(this.leftControl2.peek());
					((FallingCharacter) this.leftControl.peek()).processEvent(
							this.player2, State.EVENT_LEFTHAND_COLLECTED,
							FallingCharacter.LEFT_HAND);
					((FallingCharacter) this.leftControl.peek())
							.addObserver(this.player2);

				}
			}
			this.rightControl = new Stack<GameObject>();
			for (int counter = 0; counter < rightControl.size(); counter++) {
				this.rightControl
						.push((FallingCharacter) ((FallingCharacter) rightControl
								.get(counter)).clone());
				this.control.add(this.rightControl.peek());
				((FallingCharacter) this.rightControl.peek()).processEvent(
						this.player, State.EVENT_RIGHTHAND_COLLECTED,
						FallingCharacter.RIGHT_HAND);
				((FallingCharacter) this.rightControl.peek())
						.addObserver(this.player);

			}
			if (rightControl2 != null) {
				this.rightControl2 = new Stack<GameObject>();
				for (int counter = 0; counter < rightControl2.size(); counter++) {
					this.rightControl2
							.push((FallingCharacter) ((FallingCharacter) rightControl2
									.get(counter)).clone());
					this.control.add(this.rightControl2.peek());
					((FallingCharacter) this.rightControl.peek()).processEvent(
							this.player2, State.EVENT_RIGHTHAND_COLLECTED,
							FallingCharacter.RIGHT_HAND);
					((FallingCharacter) this.rightControl.peek())
							.addObserver(this.player2);

				}
			}
		}

		public Player getPlayer() {
			return player;
		}

		public Player getPlayer2() {
			return player2;
		}

		public List<GameObject> getConstant() {
			return constant;
		}

		public List<GameObject> getMoving() {
			return moving;
		}

		public Stack<GameObject> getLeftControl() {
			return leftControl;
		}

		public Stack<GameObject> getLeftControl2() {
			return leftControl2;
		}

		public Stack<GameObject> getRightControl() {
			return rightControl;
		}

		public Stack<GameObject> getRightControl2() {
			return rightControl2;
		}

		public Long getTime() {
			return startTime;
		}

		public int getScore() {
			return score;
		}
	}

}
