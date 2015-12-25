package eg.edu.alexu.csd.oop.game.world;

import java.util.Iterator;

import eg.edu.alexu.csd.oop.game.World;

public class WorldItrator implements Iterator<World> {

	// private ExtendableWorld easy;
	// private ExtendableWorld meduim;
	// private ExtendableWorld hard;
	private int index;
	private World[] worldArray;

	public WorldItrator() {

		index = -1;
		worldArray = new World[3];
		worldArray[0] = new EasyWorld(780, 580);
		worldArray[1] = new MediumWorld(780, 580);
		worldArray[2] = new HardWorld(780, 580);

	}

	@Override
	public boolean hasNext() {

		return index + 1 < 3;
	}

	@Override
	public World next() {
		if (hasNext()) {
			index++;
			return worldArray[index];
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void remove() {// dummy method :D

	}

}
