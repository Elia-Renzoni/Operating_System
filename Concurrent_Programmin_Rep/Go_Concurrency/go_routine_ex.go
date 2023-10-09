/*
*
*	@author Elia Renzoni
*	@date 09/10/2023
*	@brief Go routine first example
*
 */
package main

import (
	"fmt"
	"math/rand"
	"time"
)

type arr [12]int

func main() {
	var nums *arr = new(arr)
	go initArray(nums)
	go sumArrayElements(nums)
}

func initArray(nums *arr) {
	rand.Seed(time.Now().Unix())
	for i := range nums {
		nums[i] = rand.Intn(100)
	}
}

func sumArrayElements(nums *arr) {
	var sum int
	for j := range nums {
		sum += nums[j]
	}
	fmt.Printf("Sum = %d", sum)
}
