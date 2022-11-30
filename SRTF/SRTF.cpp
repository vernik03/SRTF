﻿#include "bits/stdc++.h"
using namespace std;

struct Process {
	int pid; 
	int bt; 

	int art; 

};


void findWaitingTime(Process proc[], int n,
	int wt[])
{
	int *rt = new int[n]();


	for (int i = 0; i < n; i++)
		rt[i] = proc[i].bt;

	int complete = 0, t = 0, minm = INT_MAX;

	int shortest = 0, finish_time;
	bool check = false;


	while (complete != n) {



		for (int j = 0; j < n; j++) {
			if ((proc[j].art <= t) &&
				(rt[j] < minm) && rt[j] > 0) {
				minm = rt[j];
				shortest = j;
				check = true;
			}
		}

		if (check == false) {
			t++;
			continue;
		}



		rt[shortest]--;


		minm = rt[shortest];
		if (minm == 0) {
		minm = INT_MAX;
	}

		if (rt[shortest] == 0) {


			complete++;
			check = false;


			finish_time = t + 1;



			wt[shortest] = finish_time -
				proc[shortest].bt -
				proc[shortest].art;

			if (wt[shortest] < 0)
				wt[shortest] = 0;
		}

		t++;
	}
}


void findTurnAroundTime(Process proc[], int n,
	int wt[], int tat[])
{

	for (int i = 0; i < n; i++)
		tat[i] = proc[i].bt + wt[i];
}


int main()
{
	Process proc[] = { { 1, 6, 2 }, { 2, 2, 5 },
					{ 3, 8, 1 }, { 4, 3, 0}, {5, 4, 4} };
	int n = sizeof(proc) / sizeof(proc[0]);

}
