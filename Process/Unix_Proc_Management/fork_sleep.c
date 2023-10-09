/*
 *	@author Elia Renzoni
 *	@date 09/10/2023
 *	@brief fork ex. with sleep
 * */

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h>

int *init_array(int *);
int sum(int *);


int main(void) {
	int *nums = (int *) calloc(20, sizeof(int));
	int sum_result;
	pid_t process_id = fork(); 
	
	if (process_id == 0) {
		printf("Child Run !\n");
		nums = init_array(nums);
		sum_result = sum(nums);
	} else if (process_id > 0) {
		sleep(5);
		printf("Father Run !\n");
		printf("Sum = %d\n", sum_result);
	} else {
		fprintf(stderr, "Errore !");
		exit(1);
	}
	free(nums);
	return (0);
}

int *init_array(int *nums) {
	int i;
	srand(time(NULL));
	for (i = 0; (i < 20); i++) {
		nums[i] = rand();
	}
	return (nums);
}

int sum(int *nums) {
	int sum = 0;
	int i;
	for (i = 0; (i < 20); i++) {
		sum += nums[i];
	}
	return (sum);
}


