package code.libPlayer;

import java.util.HashSet;
import java.util.Set;

import code.libMap.Terrain;

/*
 * Each Player has name,four card(terrain), total points, points for each turn
 */
public class Player {
	final int CARD_QUANTITY = 4;
	private String name;
	private int pointsTotal = 0;
	private Terrain[] terrainsToChoose = new Terrain[4];
	private int pointsThisRound = 0;
	private Terrain chosenTerrain;

	public Player(String name) {
		this.name = name;
		generateDifferentTerrains();
	}

	public String getName() {
		return name;
	}

	public void addPoints(int points) {
		this.pointsTotal += points;
	}

	public int getPoints() {
		return pointsTotal;
	}

	public int getPointThisRound() {
		return this.pointsThisRound;
	}

	public void setPointThisRound(int newPoints) {
		this.pointsThisRound = newPoints;
	}

	public void setChosenTerrain(int i) {
		this.chosenTerrain = terrainsToChoose[i];
	}

	public Terrain getChosenTerrain() {
		return this.chosenTerrain;
	}

	public void generateDifferentTerrains() {
		Set<String> arrSet = new HashSet<String>();
		do {
			for (int i = 0; i < 4; i++) {
				terrainsToChoose[i] = new Terrain();
				arrSet.add(terrainsToChoose[i].toString());
			}
		} while (arrSet.size() < 4);
	}

	public void showTerrainsToChoose() {
		System.out.println("Your terrain list: ");
		System.out.println("+----[1]----+----[2]----+----[3]----+----[4]----+");
		for (int n = 0; n < Terrain.SIZE_TERRAIN; n++) {
			System.out.print("| ");
			for (int k = 0; k < CARD_QUANTITY; k++) {
				System.out.print(terrainsToChoose[k].getRow(n) + " | ");
			}
			System.out.println();
		}
		System.out.println("+-----------+-----------+-----------+-----------+");
	}
}