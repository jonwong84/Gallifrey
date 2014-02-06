#include "stdafx.h"
#include <fstream>
#include <iostream>
#include <sstream>
using namespace std;

int _tmain(int argc, _TCHAR* argv[])
{
	// Step 0
	ifstream readFirstFile, readSecondFile;
	string firstFileName, secondFileName, thirdFileName;
	int itemOne = 0, itemTwo = 0;
	ofstream writeThirdFile;

	// Step 1
	cout << "Please provide first filename: ";
	getline(cin, firstFileName);

	// Step 2
	readFirstFile.open(firstFileName);
	readFirstFile >> itemOne;
	while (readFirstFile >> itemTwo) {
		if (itemTwo < itemOne) {
			cout << "File " << firstFileName
				<< " is not sorted." << endl;
			return -1;
		}
		else
			itemOne = itemTwo;
	}
	readFirstFile.close();
	readFirstFile.open(firstFileName);
	itemOne = 0;
	itemTwo = 0;

	// Step 3
	cout << "Please provide second filename: ";
	getline(cin, secondFileName);

	// Step 4
	readSecondFile.open(secondFileName);
	readSecondFile >> itemOne;
	while (readSecondFile >> itemTwo) {
		if (itemTwo < itemOne) {
			cout << "File " << secondFileName
				<< " is not sorted." << endl;
			return -1;
		}
		else
			itemOne = itemTwo;
	}
	readSecondFile.close();
	readSecondFile.open(secondFileName);
	itemOne = 0;
	itemTwo = 0;

	// Step 5
	cout << "Please provide an output file name: ";
	getline(cin, thirdFileName);
	writeThirdFile.open(thirdFileName);

	// Step 6
	readFirstFile >> itemOne;
	readSecondFile >> itemTwo;

	while (!readFirstFile.eof() && !readSecondFile.eof()) {
		// Step 7
		if (itemOne <= itemTwo) {
			writeThirdFile << itemOne << " ";
			readFirstFile >> itemOne;
		}
		else {
			writeThirdFile << itemTwo << " ";
			readSecondFile >> itemTwo;
		}

		// Step 8
		// return to top of loop
	} // whileLoop

	// Step 9
	while (readFirstFile >> itemOne)
		writeThirdFile << itemOne << " ";
	while (readSecondFile >> itemTwo)
		writeThirdFile << itemTwo << " ";

	// Step 10
	readFirstFile.close();
	readSecondFile.close();
	writeThirdFile.close();

	return 0;
}
