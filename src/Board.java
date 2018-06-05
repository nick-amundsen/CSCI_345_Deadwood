//Board class
import java.util.*;

public class Board{
	private ArrayList<Room> rooms;
  private ArrayList<Player> players;
	private int daysRemaining;
	private boolean gameOver;

	//Constructors
	Board(){
		this.players = new ArrayList<Player>();
		setGameLength(daysRemaining);
	}

	//Only room parameters
	Board(ArrayList<Room> parsedRooms){
		this.players = new ArrayList<Player>();
		this.rooms = new ArrayList<Room>();
		this.setRooms(parsedRooms);
	}

	//Both room and player parameters
	Board(ArrayList<Room> parsedRooms, ArrayList<Player> parsedPlayers){
		this.players = new ArrayList<Player>();
		this.rooms = new ArrayList<Room>();
		populateRooms(parsedRooms);
		populatePlayers(players);
		setGameLength(parsedPlayers.size());
	}

	//Helper methods for Constructors
	private void populateRooms(ArrayList<Room> parsedRooms) {
		int i = 0;
		while(i < parsedRooms.size()){
			this.rooms.add(parsedRooms.get(i));
			i++;
		}
	}

	private void populatePlayers(ArrayList<Player> parsedPlayers) {
		int i = 0;
		while(i < parsedPlayers.size()){
			this.players.add(parsedPlayers.get(i));
			i++;
		}
	}

	//Determines length of gameplay in game days
	private void setGameLength(int players){
		if(players <= 3){
			this.daysRemaining = 3;
		} else {
			this.daysRemaining = 4;
		}
		if(this.daysRemaining > 0){
			this.gameOver = false;
		}
	}
	//Setters

	public void setRooms(ArrayList<Room> parsedRooms) {
		this.rooms = parsedRooms;
	}

	//Getters

   public ArrayList<Room> getRooms(){
      return rooms;
   }
	public boolean isGameOver(){
		return this.gameOver;
	}

	/* GUI Related method
	public void renderBoard(){
	} */


	//Outputs score of all players
	public void showScore(){
		int i = 0;
		while(i < players.size()){
			System.out.println("Cr/Fame: " +  players.get(i).getFame()
																+ " $" + players.get(i).getFame());
			i++;
		}
	}

	//Outputs score of specified player
	public void showScore(Player player){
		System.out.print("Cr/Fame: " +  player.getFame()+ " Money: $" + player.getMoney());
	}

	public int getDaysLeft(){
		return this.daysRemaining;
	}

	//Other Methods
	public int rollDice(){
		int result;
		Random roll = new Random();
	  result = roll.nextInt(6) + 1;

		
		return result;
	}

	public static int calcScore(Player player){
		//dollars + fame + (5* rank)
		int score = player.getFame() + player.getMoney() + (5 * player.getRank());
		return score;
	}

	public void printScore(Player player){
		System.out.println(player.getName() + "'s score was: " + calcScore(player));
	}

	public Player printAllScores(ArrayList<Player> ps){
		int score;
		int i = 0;
		Player winner = ps.get(0);

      for(Player p : players){

			score = calcScore(p);
         printScore(p);

			if(score > calcScore(winner)){
				winner = p;
			}
		}
		return winner;
	}

	public void nextDay(ArrayList<Player> players){
		this.daysRemaining--;
      for(Room r : rooms) {
         if (r instanceof Scene){
            ((Scene)r).reset();
         }
      }
      System.out.println("The day has ended. Everyone returns to the trailers to see the score");
      for (Player p : players) {
         p.setRoom(rooms.get(0));
      }
      Player leader = printAllScores(players);
      if (this.daysRemaining != 0) {
         System.out.println("The current leader is " + leader.getName());
      }
		if(this.daysRemaining == 0){

			//Game is over
			this.gameOver = true;
			System.out.println("Game has ended.");
         System.out.println("AND THE WINNER IS...");
         System.out.println(leader.getName().toUpperCase() + "!!!");
         System.out.println("Congratulations to everyone on a job well done!");
		}
	}
}
