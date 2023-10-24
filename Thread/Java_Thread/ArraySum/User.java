package threadImplements;

public class User {
	public static void main(String[] args) {
		int[] nums1 = new int[]{10, 4, 3};
		int[] nums2 = new int[]{9, 6, 5};
		int[] nums3 = new int[]{8, 2, 7};
		ArraySum arr1 = new ArraySum(nums1, "Th1");
		ArraySum arr2 = new ArraySum(nums2, "Th2");
		ArraySum arr3 = new ArraySum(nums3, "Th3");
		
		Thread t1 = new Thread(arr1);
		Thread t2 = new Thread(arr2);
		Thread t3 = new Thread(arr3);
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
		} catch(Exception ex) {
			System.err.println("Errore nella JOIN" + ex);
		}
		System.out.println("Processo Main Terminato !");
	}
}
