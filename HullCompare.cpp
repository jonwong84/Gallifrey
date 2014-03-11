// HullCompare.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <cmath>
#include <ctime>
using namespace std;

static int goodSize, badSize;

struct Point {

	int x, y;

	Point() {
		x = 0;
		y = 0;
	}

	Point(int a, int b) {
		x = a;
		y = b;
	} // constructor

	~Point() {}

}; // Point

/**************************************************************************/

int sizeCheck(string name) {
	cout << "Initial readthrough:" << endl;
	int i = 0;
	fstream f(name);
	int x, y;
	while (f >> x) {
		f >> y;
		cout << "(" << x << "," << y << ")" << endl;
		i++;
	}
	f.close();
	return i;
}

Point* parseList(string name, int size) {
	fstream f(name);
	Point* p = new Point[size];
	int x, y;
	for (int i = 0; i < size; i++) {
		f >> x;
		f >> y;
		p[i] = Point(x, y);
	}
	return p;
} // parseList

bool compareLists(Point* good, int gSize, Point* bad, int bSize) {

	if (gSize != bSize) {
		cout << "Lengths of both lists are inconsistent." << endl;
		return false;
	}

	bool b = true;
	for (int i = 0; i < gSize; i++)
		if (good[i].x != bad[i].x && good[i].y != bad[i].y) {
			b = false;
			cout << " " << good[i].x << "," << good[i].y << " does not match " << bad[i].x << "," << bad[i].y
				<< "." << endl;
		}
		return b;

} // compareLists

Point* randAdd(Point* p, int& length) {
	Point* p_prime = new Point[length+1];
	for (int i = 0; i < length; i++)
		p_prime[i] = p[i];
	srand(time(0));
	Point add(rand()%100,rand()%100);
	cout << "New Point: " << add.x << "," << add.y << endl;
	p_prime[length] = add;
	delete[] p;
	length++;
	return p_prime;
} // randAdd

Point* randSub(Point* p, int& length) {
	Point* p_prime = new Point[length-1];
	srand(time(0));
	int mark = rand()%length;
	cout << "Removed Point: " << p[mark].x << "," << p[mark].y << endl;
	int index = 0;
	for (int i = 0; i < length; i++) {
		if (i == mark) continue;
		p_prime[index] = p[i];
		index++;
	}
	delete[] p;
	length--;
	return p_prime;
} // randAdd

void printList(Point* p, int length) {
	cout << "Runthrough:" << endl;
	for (int i = 0; i < length; i++)
		cout << "(" << p[i].x << "," << p[i].y << ")" << endl;
} // printList

void sort(Point* p, int length) {
	int startIndex = 0;
	int endIndex = length - 1;

	// Step 8
	while (!(startIndex >= endIndex)) {
		int minIndex = startIndex;
		int walkerIndex = startIndex + 1;

		// Step 9
		while (!(walkerIndex > endIndex)) {
			if (p[walkerIndex].x < p[minIndex].x)
				minIndex = walkerIndex;
			else if (p[walkerIndex].x == p[minIndex].x && p[walkerIndex].y < p[minIndex].y)
				minIndex = walkerIndex;
			// Step 10
			walkerIndex++;

			// Step 11
			// return to whileLoop
		}

		// Step 12
		Point temp = p[startIndex];
		p[startIndex] = p[minIndex];
		p[minIndex] = temp;

		// Step 13
		startIndex++;

		// Step 14
		// return to whileLoop
	}

} // sort

int main(int argc, char* argv[])
{
	if (argc < 1 || argv[1] == NULL) {
		cout << "Run as:\n HullCompare goodFile.txt badFile.txt" << endl;
		return 0;
	}

	string args[2] = { argv[1], argv[2] };
	goodSize = sizeCheck(args[0]);
	badSize = sizeCheck(args[1]);
	Point* good = parseList(args[0],goodSize);
	Point* bad = parseList(args[1],badSize);
	sort(good, goodSize);
	sort(bad, badSize);
	bool equal = compareLists(good, goodSize, bad, badSize);
	if (equal)
		cout << "OK: Lists are equal." << endl;
	else
		cout << "ERR: Lists are not equal." << endl;

	printList(good, goodSize);
	printList(bad, badSize);

	cout << "User Options:" << endl;
	cout << " 0. Exit" << endl;
	cout << " 1. Add Coord" << endl;
	cout << " 2. Sub Coord" << endl;

	int input;
	cin >> input;

	if (input == 1) bad = randAdd(bad, badSize);
	else if (input == 2) bad = randSub(bad, badSize);
	else {
		cout << "Exiting." << endl;
		return 0;
	}

	sort(good, goodSize);
	sort(bad, badSize);
	printList(good, goodSize);
	printList(bad, badSize);

	equal = compareLists(good, goodSize, bad, badSize);
	if (equal)
		cout << "OK: Lists are equal." << endl;
	else
		cout << "ERR: Lists are not equal." << endl;

	delete[] good;
	delete[] bad;
	return 0;
}

