package main;

import java.sql.*;
import java.util.Scanner;

import command.Gamble;
import command.command;
import exception.commandException;

public class SQL {

	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);
		Connection c = null;
		Statement stmt = null;
		int a = 0;
		int b = 0;
		int d = 0;
		double money = 0;
		int share = 0;

		String sql = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			int count = 0;
			String name = null;

			// sign in or sign up

			System.out.println("Would you like to sign in or sign up?");
			System.out.println("( sign in 登入 = 1 ; sign up 註冊 = 2 )");
			a = s.nextInt();
			
			//when a=1 ,sign in
			//when a=2 sign up
			switch (a) {
			case 1:
				while (true) {

					System.out.println("please entry your name ");
					name = s.next();
					System.out.println("please entry your password ,you have three times to try");
					String password = s.next();
					ResultSet rs = stmt.executeQuery("SELECT PASSWORD FROM USER WHERE NAME='" + name + "';");

					if ((rs.getString("PASSWORD").equals(password))) {
						System.out.println("success ");
						break;
					} else {
						System.out.println("wrong password ");
						count++;
						if (count >= 3) {
							System.out.println("wrong password for three times! ");
							System.exit(0);
						}
					}
				}
				break;

			case 2: {
				System.out.println("please entry your name");
				name = s.next();
				System.out.println("please entry your ID ");
				String id = s.next();
				System.out.println("please entry your password ");
				String password = s.next();
				System.out.println("please entry your address ");
				String address = s.next();

				sql = "INSERT INTO USER (NAME,ID,ADDRESS,PASSWORD,NTD,USD,STOCK_2330,STOCK_6547,STOCK_0050) "
						+ "VALUES ('" + name + "','" + id + "','" + address + "','" + password + "',0,0,0,0,0);";
				stmt.executeUpdate(sql);
				break;
			}
			}
			
			
			// get all data of user from database
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER WHERE NAME='" + name + "';");
			String NAME = rs.getString("NAME");
			double NTD = rs.getInt("NTD");
			double USD = rs.getInt("USD");
			double STOCK_2330 = rs.getInt("STOCK_2330");
			double STOCK_6547 = rs.getInt("STOCK_6547");
			double STOCK_0050 = rs.getInt("STOCK_0050");

			// start to use
			try {
				for (int i = 0; i < 100; i++) {
					System.out.println("-------------------------");
					System.out.println("deposit or withdraw money = 1 ");
					System.out.println("exchange foreign currency = 2");
					System.out.println("buy or sell stocks=3");
					System.out.println("transfer money = 4 ");
					System.out.println("gambling = 5");
					System.out.println("exit = 6");

					a = s.nextInt();
					
					//deposit or withdraw
					if (a == 1) {
						System.out.println("NTD = " + NTD);
						System.out.println("( deposit =1 ; withdraw = 2 )");
						b = s.nextInt();
						System.out.println("How much would you like to deposit/withdraw ?");
						money = s.nextFloat();
						NTD = command.DepositOrWithdraw(b, money, NTD);

					}
					//change NTD into USD or change USD into NTD
					else if (a == 2) {
						System.out.println("NTD = " + NTD);
						System.out.println("USD = " + USD);
						System.out.println("( change NTD into USD =1 ; change USD into NTD = 2 )");
						b = s.nextInt();
						System.out.println("How much would you like to change?");
						money = s.nextFloat();
						if (b == 1) {
							NTD = command.changeNTD(b, money, NTD);
							USD = command.changeUSD(b, money, USD);
						}
						if (b == 2) {
							USD = command.changeUSD(b, money, USD);
							NTD = command.changeNTD(b, money, NTD);
						} else {
							new commandException("please choose 1 or 2");
						}
					}
					
					//buy stocks or sell stocks
					else if (a == 3) {
						System.out.println("NTD = " + NTD);
						System.out.println("STOCK_2330 = " + STOCK_2330);
						System.out.println("STOCK_6547 = " + STOCK_6547);
						System.out.println("STOCK_0050 = " + STOCK_0050);

						System.out.println("( buy stocks = 1 ; sell stocks = 2 )");
						b = s.nextInt();
						if (b != 1 && b != 2) {
							throw new commandException("please choose 1 or 2");
						}
						System.out.println("Which stock would you like to buy/sell?");
						System.out.println("( 2330 台積電 = 1 ; 6547 高端 = 2 ; 0050 元大50 = 3 )");
						d = s.nextInt();
						if (d != 1 && d != 2 && d != 3) {
							throw new commandException("please choose 1 or 2 or 3");
						}

						System.out.println("How many shares would you like to buy/sell?");
						share = s.nextInt();
						if (!((Object) share).getClass().getSimpleName().equals("Integer")) {
							throw new commandException("invalid share to buy/sell");
						}
						if (b == 2) {
							if (d == 1) {
								if (STOCK_2330 < share) {
									throw new commandException("not enough share of 2330");
								}
								
								STOCK_2330 -= share;
								
							} else if (d == 2) {
								if (STOCK_6547 < share) {
									throw new commandException("not enough share of 6547");
								}
								STOCK_6547 -= share;
							} else {
								if (STOCK_0050 < share) {
									throw new commandException("not enough share of 0050");
								}
						
								STOCK_0050 -= share;
							}
						}
						NTD = command.share(b, d, NTD, share);
						if (b == 1) {
							if (d == 1) {
								STOCK_2330 += share;
							} else if (d == 2) {
								STOCK_6547 += share;
							} else if (d == 3) {
								STOCK_0050 += share;
							}
						}
					}					
					//transfer money to somebody
					else if (a == 4) {
						System.out.println("NTD = " + NTD);

						int flag = 0;
						double sbNTD;
						String n;
						System.out.println("Please enter the name you want to transfer money?");
						n = s.next();
						ResultSet ri = stmt.executeQuery("SELECT NAME FROM USER ;");
						while (ri.next()) {
							String n1 = rs.getString("NAME");
							if (n1.equals(n)) {
								flag = 1;
							}
						}
						if (flag == 0) {
							System.out.println("This name doesn't exist");
							continue;
						}
						System.out.println("How much would you like to transfer?");
						money = s.nextDouble();
						if (NTD - money <= 0) {
							throw new commandException("not enough money");
						}
						NTD -= money;
						ResultSet ro = stmt.executeQuery("SELECT * FROM USER WHERE NAME='" + n + "';");
						sbNTD = ro.getDouble("NTD");
						sbNTD += money;
						sql = "UPDATE USER set NTD =" + sbNTD + " where NAME ='" + n + "';";
						stmt.executeUpdate(sql);
					} 
					
					//use NTD to gamble
					else if (a == 5) {
						System.out.println("NTD = " + NTD);
						int type = 0;
						System.out.println("How much would you like to gamble?");
						money = s.nextDouble();
						if (NTD - money <= 0) {
							throw new commandException("not enough money");
						}
						System.out.println("What type would you like to choose?");
						System.out.println("( High-risk = 1 ; Low-risk = 2 )");
						type = s.nextInt();
						if (type != 1 && type != 2) {
							throw new commandException("invaild type");
						}
						Gamble gamble = new Gamble(money, type);
						NTD += gamble.startGamble();
						NTD -= money;
					}
					
					//exit the system
					else {
						System.exit(0);
					}
					
					sql = "UPDATE USER set NTD =" + NTD + ",USD=" + USD + ",STOCK_2330=" + STOCK_2330 + ",STOCK_6547="
							+ STOCK_6547 + ",STOCK_0050=" + STOCK_0050 + " where NAME ='" + name + "';";
					stmt.executeUpdate(sql);

					c.commit();
					System.out.println("-------------------------");
					rs = stmt.executeQuery("SELECT * FROM USER WHERE NAME='" + name + "';");
					tostring(rs);

				}
			} catch (commandException e) {
				
				System.out.println(e.getMessage());

			}
			s.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

	}

	/**
	 * @param rs
	 *show the data in the database
	 * @throws SQLException
	 */
	public static void tostring(ResultSet rs) throws SQLException {

		while (rs.next()) {

			String NAME = rs.getString("NAME");
			String ID = rs.getString("ID");
			String ADDRESS = rs.getString("ADDRESS");
			String PASSWORD = rs.getString("PASSWORD");
			float NTD = rs.getFloat("NTD");
			float USD = rs.getFloat("USD");
			float STOCK_2330 = rs.getFloat("STOCK_2330");
			float STOCK_6547 = rs.getFloat("STOCK_6547");
			float STOCK_0050 = rs.getFloat("STOCK_0050");

			System.out.println("NAME = " + NAME);
//			System.out.println("ID = " + ID);
//			System.out.println("ADDRESS = " + ADDRESS);
//			System.out.println("PASSWORD = " + PASSWORD);
			System.out.println("NTD = " + NTD);
			System.out.println("USD = " + USD);
			System.out.println("STOCK_2330 = " + STOCK_2330);
			System.out.println("STOCK_6547 = " + STOCK_6547);
			System.out.println("STOCK_0050 = " + STOCK_0050);

		
		}
	}
}
