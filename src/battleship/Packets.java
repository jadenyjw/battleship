package battleship;

public class Packets {
    //The username of the joiner.
	public static class Packet00Request {
		String clientName;
	}
    //Whether or not the connection has been accepted
	public static class Packet01Response {
		boolean accepted;
		String name;
	}
    //Chat message class
	public static class Packet02Message {
		String message;
		String userName;
	}
    //Coordinates of a user's choice
	public static class Packet03Coords {
		int x;
		int y;
	}
    //The response of whether the shot was hit or miss
	public static class Packet04Hit {
		boolean isHit;
		int x;
		int y;
	}
    //Determines whether or not there has been a victory or not
	public static class Packet05Victory {
		boolean victory;
	}
	//Reveals to the loser all the spots he missed
	public static class Packet06Missed {
		int x;
		int y;
		
	}
	

}
