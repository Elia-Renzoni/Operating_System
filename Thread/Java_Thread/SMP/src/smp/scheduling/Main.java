package smp.scheduling;

public class Main {
	public static void main(String ...args) {
		CodaScheduling coda = new CodaScheduling();
		Cpu[] cpu = new Cpu[6];
		Task[] task = new Task[72];
		
		for (int i = 0; i < cpu.length; i++) {
			cpu[i] = new Cpu(coda, i);
			cpu[i].start();
		}
		
		for (int i = 0; i < task.length; i++) {
			if (i < (task.length / 3)) {
				task[i] = new Task(coda, i, "interactive");
			} else if (i >= (task.length /3) && i < (task.length * 2)) {
				task[i] = new Task(coda, i, "io");
			} else {
				task[i] = new Task(coda, i, "batch");
			}
			task[i].start();
		}
		
		for (int i = 0; i < task.length; i++) {
			try {
				task[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < cpu.length; i++) {
			cpu[i].interrupt();
		}
		
		for (int i = 0; i < cpu.length; i++) {
			try {
				cpu[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("MO TERMINO PURE IO!!");
	}
}
