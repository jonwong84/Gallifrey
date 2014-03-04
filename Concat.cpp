// Concat.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <map>
using namespace std;

static const char* tempFile = "concat.txt";
static int total = 0;
static int list1 = 0;
static int list2 = 0;

void concat(ifstream& file1, ifstream& file2) {



	ofstream out(tempFile);
	cout << "Concatting the following elements to " << tempFile << ":" << endl;
	int get;

	while(file1 >> get) {
		cout << "LIST1_" << list1 << ": " << get << endl;
		out << get << " ";
		list1++;
		total++;
	}
	while(file2 >> get) {
		cout << "LIST2_" << list2 << ": " << get << endl;
		out << get << " ";
		list2++;
		total++;
	}
	out.close();
	file1.close();
	file2.close();
} // concat

int main(int argc, char* argv[])
{

	if (argc < 3) {
		cout << "Run as follows:" << endl;
		cout <<" MergeCheck <list1> <list2> <mergedList>" << endl;
		return 0;
	}

	string args[2] = {argv[1], argv[2]};
	ifstream file1(args[0]);
	ifstream file2(args[1]);
	concat(file1,file2);

	cout << "Elements in List 1: " << list1 << endl;
	cout << "Elements in List 2: " << list2 << endl;
	cout << "Elements Total: " << total << endl;
	cout << "Please see " << tempFile  << "." << endl;

	return 0;
}

