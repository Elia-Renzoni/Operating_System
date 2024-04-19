package gestione.azienda;

public class Cliente {
	private int budget;
	private int id;
	
	public Cliente(int budget, int id) {
		this.budget = budget;
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getBudget() {
		return this.budget;
	}
}
