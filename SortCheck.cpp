// SortCheck.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <sstream>
using namespace std;

int main(int argc, char* argv[])
{

	int counter = 0;
	bool sorted = true;
	cout << "Filename: ";
	string file;
	getline(cin,file);
	ifstream in(file);

	int i = 0, j = 0;
	in >> i;
	cout << "S_" << counter << ": " << i << endl;
	counter++;
	while (in >> j) {
		cout << "S_" << counter << ": " << j << endl;
		counter++;
		if (j < i) {
			cout << " Sorting Error: value " << j << " < " << i << endl;
			sorted = false;
		}
		else i = j;
	}
	if (sorted) cout << "File " << file << " is sorted." << endl;
	else cout << "File " << file << " has not been properly sorted." << endl;
	return 0;
}

