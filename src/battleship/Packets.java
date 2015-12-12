package battleship;

public class Packets {

	public static class Packet00Request{ String clientName;}
	public static class Packet01Response{ boolean accepted;}
	public static class Packet02Message{String message; String userName;}
	public static class Packet03Coords{int x; int y;}
	
}
