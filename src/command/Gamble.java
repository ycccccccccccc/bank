package command;

public class Gamble {
	private double money;
	private int type;
	
	/**
	 * @param money
	 * @param type represent two kinds of risk and return
	 * if type==1 20% of chance to earn 2 time of the original money and 80% of chance will lose 0.7 time if the original money
	 * if type==2 20% of chance to earn 1.1 time of the original money and 80% of chance will lose 0.1 time if the original money
	 */
	public Gamble(double money, int type) {
		this.money = money;
		this.type = type;  
		// TODO Auto-generated constructor stub
	}
	public double startGamble() {
		int random = (int) (Math.random()*10)+1;
		switch(type) {
		case 1:
			if(random > 0.2) {
				return (double)money*2;
			}else {
				return (double)money*0.3;
			}
		case 2:
			if(random > 0.2) {
				return (double)money*1.1;
			}else {
				return (double)money*0.9;
			}
		default:
			return money;
		}
	}

}