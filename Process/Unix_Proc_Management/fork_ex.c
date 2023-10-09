/*
 *	@author Elia Renzoni
 *	@date 09/10/2023
 *	@brief fork ex.
 * */

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

int main(void) {
	pid_t process_id;

	printf("Sono il processo Padre con PID = %d\nGenerato dal Processo Padre con PID + %d\n", getpid(), getppid());
	printf("Questo viene eseguito solo dal padre\n");
	if ((process_id = fork()) == 0) {
		printf("Sono il figlio !");
	} else if (process_id > 0) {
		printf("Sono il padre !\n");
	} else {
		fprintf(stderr, "Errore !");
		exit(1);
	}
	return (0);
}	
