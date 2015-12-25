package eg.edu.alexu.csd.oop.game.characters.players;


public interface PlayerState {

	public static final int EVENT_FREE = 0;
	public static final int EVENT_CAPTURED = 1;

	public PlayerState nextState(int event);
	public boolean isFree();
}
