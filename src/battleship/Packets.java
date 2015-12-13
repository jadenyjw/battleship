package battleship;

public class Packets {

	public static class Packet00Request {
		String clientName;
	}

	public static class Packet01Response {
		boolean accepted;
	}

	public static class Packet02Message {
		String message;
		String userName;
	}

	public static class Packet03Coords {
		byte x;
		byte y;
	}

	public static class Packet04Hit {
		boolean isHit;
		byte x;
		byte y;
	}

	public static class Packet05Victory {
		boolean victory;
	}
	
	public static class Packet06Missed {
		byte x;
		byte y;
		
	}
	

}
