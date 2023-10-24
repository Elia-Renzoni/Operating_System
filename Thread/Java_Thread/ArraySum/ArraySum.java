package threadImplements;

public class ArraySum implements Runnable {
	private final String thName;
	private final int[] nums;
	private static final int ADD = 12;
	
	public ArraySum(int[] nums, String name) {
		this.nums = nums;
		this.thName = name;
	}
	
	@Override
	public void run() {
		System.out.println("Inizio Somma Array Thread : " + this.thName);
		for (var value : this.nums) {
			value += ADD;
			System.out.println("Somma : " + value);
		}
		System.out.println("Fine Somma Array Thread : " + this.thName);
	}
}
