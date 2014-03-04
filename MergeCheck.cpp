#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <map>
using namespace std;

static int listCount = 0, mergeCount = 0, errors = 0;

map<int,int> hashParse(ifstream& file) {
	map<int,int> h;
	cout << "Elements found in merged list (ML):" << endl;
	int i;
	while (file >> i) {
		cout << "ML_" << mergeCount << ": " << i << endl;
		mergeCount++;
		h[i]+=1;
	}
	file.close();
	return h;
} // harshParse

bool compareMerged(ifstream& file,map<int,int>& hash) {
	bool b = true;
	int i;
	while(file >> i) {
		listCount++;
		if (hash.find(i) == hash.end()) {
			b = false;
			cout << " Element " << i << " from sorted lists not found in merged list." << endl;
			errors++;
		}
		else if (hash[i] <= 0) {
			b = false;
			cout << " Copy of Element " << i << " missing in merged list." << endl;
			errors++;
		}
		else hash[i]--;
	}
	file.close();
	return b;
} // compareMerged

int main(int argc, char* argv[])
{

	if (argc < 2) {
		cout << "Run as follows:" << endl;
		cout << " MergeCheck <concatList> <mergedList>" << endl;
		return 0;
	}

	string args[2] = {argv[1], argv[2]};

	ifstream file1(args[0]);
	ifstream file2(args[1]);

	map<int,int> hash;
	hash = hashParse(file2);

	bool consistent = compareMerged(file1,hash);

	if (consistent) cout << "All elements in " << args[0] << " accounted for in " << args[1] << endl;
	else {
		cout << "Error: inconsistency between sorted lists and merged list." << endl;
	}

	cout << "Total number of elements in sorted lists: " << listCount << endl;
	cout << "Total number of elements in merged list: " << mergeCount << endl;
	cout << "Total number of errors detected: " << errors << endl;

	return 0;
} // main