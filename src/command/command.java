package command;

import java.sql.Connection;

import exception.commandException;

public class command {
	// deposit b=1 ; withdraw b=2
	public static double DepositOrWithdraw(int b, double c, double now) throws commandException {

		if (!((Object) c).getClass().getSimpleName().equals("Double")) {
			throw new commandException("invalid money for deposit/withdraw");
		}
		if (b == 1) {
			return c + now;
		}
		if (b == 2) {
			if ((-c) + now < 0) {
				System.out.print("not enough money");
				return now;
			}
			return -c + now;
		}
		throw new commandException("please choose 1 or 2");

	}

	// change NTD into USD b=1 ; change USD into NTD b=2
	public static double changeNTD(int b, double money, double now) throws commandException {
		if (!((Object) money).getClass().getSimpleName().equals("Double")) {
			throw new commandException("invalid money for exchange");
		}
		if (b == 1) {
			if ((-(money / 0.036)) + now < 0) {
				throw new commandException("not enough money for exchange");
			}
			return -(money / 0.036) + now;
		} else {

			return money + now;
		}

	}

	// change NTD into USD b=1 ; change USD into NTD b=2
	public static double changeUSD(int b, double money, double now) throws commandException {
		if (b == 1) {
			return money + now;
		} else {
			if ((-(money * 0.036)) + now < 0) {
				throw new commandException("not enough money for exchange");
			}
			return (-money * 0.036) + now;
		}

	}

	// buy stocks b= 1 ; sell stocks b= 2
	// 2330 台積電 d = 1 ; 6547 高端 d = 2 ; 0050 元大50 d = 3"
	public static double share(int b, int d, double now, int share) throws commandException {
		if (b == 2) {
			if (d == 1) {
				return now + share * 590;
			}
			if (d == 2) {
				return now + share * 279.5;
			}
			if (d == 3) {
				return now + share * 137.05;
			}
		}

		else {

			if (d == 1) {
				if (now - share * 590 < 0) {
					throw new commandException("not enough money for buy stock");
				}
				return now - share * 590;
			}

			if (d == 2) {
				if (now - share * 279.5 < 0) {
					throw new commandException("not enough money for buy stock");
				}
				return now - share * 279.5;
			}
			if (d == 3) {
				if (now - share * 137.05 < 0) {
					throw new commandException("not enough money for buy stock");
				}
				return now - share * 137.05;
			}
		}
		return 0;

	}

}
