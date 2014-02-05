
#include "stdafx.h"
#include <fstream>
#include <iostream>
#include <sstream>
using namespace std;

int _tmain(int argc, _TCHAR* argv[])
{
	// Step 0
	ifstream readFirstFile, readSecondFile;
	string firstFileName, secondFileName, thirdFileName, line1, line2;
	int itemOne = 0, itemTwo = 0;
	ofstream writeThirdFile;

	// Step 1
	cout << "Please provide first filename: ";
	getline(cin,firstFileName);

	// Step 2
	readFirstFile.open(firstFileName);
	getline(readFirstFile,line1);
	istringstream iss_1(line1);
	iss_1 >> itemOne;
	while (iss_1 >> itemTwo) {
		if (itemTwo < itemOne) {
			cout << "File " << firstFileName
				<< " is not sorted." << endl;
			return -1;
		} else
			itemOne = itemTwo;
	}
	readFirstFile.close();
	iss_1.clear();
	readFirstFile.open(firstFileName);
	itemOne = 0;
	itemTwo = 0;

	// Step 3
	cout << "Please provide second filename: ";
	getline(cin,secondFileName);

	// Step 4
	readSecondFile.open(secondFileName);
	getline(readSecondFile,line2);
	istringstream iss_2(line2);
	iss_2 >> itemOne;
	while (iss_2 >> itemTwo) {
		if (itemTwo < itemOne) {
			cout << "File " << secondFileName
				<< " is not sorted." << endl;
			return -1;
		} else
			itemOne = itemTwo;
	}
	readFirstFile.close();
	iss_2.clear();
	readFirstFile.open(firstFileName);
	itemOne = 0;
	itemTwo = 0;

	// Step 5
	cout << "Please provide an output file name: ";
	getline(cin,thirdFileName);
	writeThirdFile.open(thirdFileName);

	// Step 6
	getline(readFirstFile,line1);
	iss_1.str(line1);
	getline(readSecondFile,line2);
	iss_2.str(line2);

	iss_1 >> itemOne;
	iss_2 >> itemTwo;

	while (!iss_1.eof() && !iss_2.eof()) {
		// Step 7
		if (itemOne <= itemTwo) {
			writeThirdFile << itemOne + " ";
			iss_1 >> itemOne;
		} else {
			writeThirdFile << itemTwo + " ";
			iss_2 >> itemTwo;
		}

		// Step 8
		// return to top of loop
	} // whileLoop

	// Step 9
	while (!iss_1.eof()) {
		iss_1 >> itemOne;
		writeThirdFile << itemOne + " ";
	}
	while (!iss_1.eof()) {
		iss_2 >> itemTwo;
		writeThirdFile << itemTwo + " ";
	}

	// Step 10
	readFirstFile.close();
	readSecondFile.close();

	return 0;
}

