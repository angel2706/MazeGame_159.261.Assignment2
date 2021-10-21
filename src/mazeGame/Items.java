package mazeGame;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Items {

	int numItems;
	
	int numCollected;

	//contains all pathway cells in grid
	ArrayList<Cell> valid;

	//contains all spawned items
	ArrayList<Item> items = new ArrayList<>();

	public Items(int numItems, ArrayList<Cell> valid) {
		this.numItems = numItems;

		this.valid = valid;
		createItems();
	}


	//creates items on random valid pathway cells
	private void createItems() {

		Random rand = new Random(); 

		for (int i = 0; i < numItems; i++) {

			Cell pos = valid.get(rand.nextInt(valid.size()));

			Item item;
			try {
				item = new Item(pos.xPos, pos.yPos);
				items.add(item);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			valid.remove(pos);
		}
	}

	
	//draws all items
	public void paint(Graphics g) {

		//sneaky update in paint :P
		int collected = 0;
		
		//draws each enemy
		for (int i = 0; i < this.items.size(); i++) {

			Item item = items.get(i);
			
			if (item.isCollected() == false) {
				g.drawImage(item.gem[0][1], item.x, item.y, null);
			}
			else {collected++;}
		}
		
		setNumCollected(collected);
	}


	public int getNumCollected() {
		return numCollected;
	}


	public void setNumCollected(int numCollected) {
		this.numCollected = numCollected;
	}


	public int getNumItems() {
		return numItems;
	}


	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}
	
	
	
	
}
