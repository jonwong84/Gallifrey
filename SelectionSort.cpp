// ConsoleApplication1.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <fstream>
#include <iostream>
#include <sstream>
using namespace std;

int _tmain(int argc, _TCHAR* argv[])
{
	// Step 0
	int dataCount = 0;
	string filename, outGoing;
	int* dataSorted;
	int startIndex, endIndex, walkerIndex, minIndex, temp;
	ifstream inFile;
	ofstream outFile;

	// Step 1
	cout << "Please provide a filename: ";
	getline(cin,filename);
	inFile.open(filename);

	// Step 2
	int takeOut = 0;
	string line;
	while (getline(inFile,line)) {
		istringstream iss(line);
		while (iss >> takeOut) dataCount++;
	}

	// Step 3
	inFile.close();

	// Step 4
	dataSorted = new int[dataCount];

	// Step 5
	inFile.open(filename);

	// Step 6
	for (int i = 0; i < dataCount; i++) {
		while (getline(inFile,line)) {
			istringstream iss(line);
			while (iss >> takeOut) { 
				dataSorted[i] = takeOut;
				i++;
			}
		}
	}

	// Step 7
	startIndex = 0;
	endIndex = dataCount - 1;

	// Step 8
	while (!(startIndex >= endIndex)) {
		minIndex = startIndex;
		walkerIndex = startIndex + 1;

		// Step 9
		while (!(walkerIndex > endIndex)) {
			if (dataSorted[walkerIndex] < dataSorted[minIndex])
				minIndex = walkerIndex;

			// Step 10
			walkerIndex++;

			// Step 11
			// return to whileLoop
		}

		// Step 12
		temp = dataSorted[startIndex];
		dataSorted[startIndex] = dataSorted[minIndex];
		dataSorted[minIndex] = temp;

		// Step 13
		startIndex++;

		// Step 14
		// return to whileLoop
	}

	// Step 15
	cout << "Please provide an output file name: ";
	getline(cin,outGoing);
	outFile.open(outGoing);

	// Step 16
	for (int j = 0; j < dataCount; j++)
		outFile << dataSorted[j] << " ";

	// Closing
	delete[] dataSorted;
	outFile.close();
	inFile.close();
	return 0;
} // SelectionSort