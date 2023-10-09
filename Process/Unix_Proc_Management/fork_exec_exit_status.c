/*	
 *	@author Elia Renzoni
 *	@date 09/10/2023
 *	@brief fork-exec model with Exit status
 * */

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

int main(void) {
	int status;
	pid_t process_id = fork();
	if (process_id == 0) {
		execl("/usr/bin/firefox", "firefox", NULL);
	} else if (process_id > 0) {
		wait(&status);
		status = status >> 8;
		printf("Child Termianato con il seguente valore della terminazione = %d", status);
	} else {
		fprintf(stderr, "Errore !");
		exit(1);
	}

	return (0);
}
