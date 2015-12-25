package eg.edu.alexu.csd.oop.game.characters.players;


public class CapturedState  implements PlayerState {

	@Override
	public PlayerState nextState(int event) {
		// TODO Auto-generated method stub
		if(event == EVENT_FREE){
			return new FreeState();
		}
		return this;
	}

	@Override
	public boolean isFree() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

//	@Override
//	public void setX(int x) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setY(int y) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setDimensions(int width, int height) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setXLimits(ClownType1 clown) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public int getX() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getY() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

}
