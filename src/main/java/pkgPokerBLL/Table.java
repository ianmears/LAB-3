package pkgPokerBLL;

import java.util.ArrayList;
import java.util.UUID;

public class Table {

	private UUID TableID;
	
	//	Change this from ArrayList to HashMap.
	private ArrayList<Player> TablePlayers = new ArrayList<Player>();
	
	public Table() {
		super();
		TableID = UUID.randomUUID();
	}
	
	public void AddPlayerToTable(Player p)
	{
		TablePlayers.add(p);
	}
	
	public void RemovePlayerFromTable(Player p)
	{
		TablePlayers.remove(p);
	}
}
