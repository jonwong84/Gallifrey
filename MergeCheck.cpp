// MergeCheck.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <map>
using namespace std;

static int listCount = 0, mergeCount = 0;
static const char* tempFile = "~merge.tmp";

void concat(ifstream& file1, ifstream& file2) {

	ofstream out(tempFile);
	cout << "Elements found in concatted sorted lists (CAT):" << endl;
	int get;

	while(file1 >> get) {
		cout << "CAT_" << listCount << ": " << get << endl;
		out << get << " ";
		listCount++;
	}
	while(file2 >> get) {
		cout << "CAT_" << listCount << ": " << get << endl;
		out << get << " ";
		listCount++;
	}
	out.close();
	file1.close();
	file2.close();
} // concat

map<int,int> hashParse(istream& file) {
	map<int,int> h;
	ifstream reader(tempFile);
	cout << "Elements found in merged list (ML):" << endl;
	int i;
	while (reader >> i) {
		cout << "ML_" << mergeCount << ": " << i << endl;
		mergeCount++;
		h[i]+=1;
	}
	reader.close();
	return h;
} // harshParse

bool compareMerged(ifstream& file,map<int,int>& hash) {
	bool b = true;
	int i;
	while(file >> i) {
		if (hash.find(i) == hash.end()) {
			b = false;
			cout << " Element " << i << " from sorted lists not found in merged list." << endl;
		}
		else if (hash[i] <= 0) {
			b = false;
			cout << " Copy of Element " << i << " missing in merged list." << endl;
		}
		else hash[i]--;
	}
	file.close();
	return b;
} // compareMerged

int main(int argc, char* argv[])
{

	if (argc < 3) {
		cout << "Run as follows:" << endl;
		cout <<" MergeCheck <list1> <list2> <mergedList>" << endl;
		return 0;
	}

	string args[3] = {argv[1], argv[2], argv[3]};

	ifstream file1(args[0]);
	ifstream file2(args[1]);
	ifstream file3(args[2]);

	concat(file1, file2);
	map<int,int> hash;
	hash = hashParse(file3);

	ifstream file4(tempFile);
	bool consistent = compareMerged(file4,hash);
	remove(tempFile);

	if (consistent) cout << "All elements in " << args[0] << " and " << args[1] << " accounted for in " << args[2] << endl;
	else {
		cout << "Error: inconsistency between sorted lists and merged list." << endl;
	}

	cout << "Total number of elements in sorted lists: " << listCount << endl;
	cout << "Total number of elements in merged list: " << mergeCount << endl;

	return 0;
} // main