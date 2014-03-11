// PolyCheck.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <map>
using namespace std;

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

int check(Point p[], int size) {

	int code = 200;
	if (size <= 2) return 400;

	for (int i = 1; i < size; i++) {
		for (int j = 0; j < i; j++)
		if (p[i].x == p[j].x && p[i].y == p[j].y) {
			cout << " Problem: (" << p[i].x << "," << p[i].y << ")" << endl;
			code = 401;
		}
	}
	return code;
} // check

int main(int argc, char* argv[])
{
	if (argc < 1 || argv[1] == NULL) {
		cout << "Run as:\n PolyCheck file.txt" << endl;
		return 0;
	}
	string args = argv[1];
	int size = sizeCheck(args);
	Point* p = new Point[size];
	int index = 0;

	fstream f(args);
	int x, y;
	for (int i = 0; i < size; i++) {
		f >> x;
		f >> y;
		p[i] = Point(x, y);
	}

	int ok = check(p,size);
	if (ok == 200)
		cout << "OK " << ok << ": Polygon is valid." << endl;
	else if (ok == 400)
		cout << "ERR " << ok << ": Polygon features two or fewer data points." << endl;
	else if (ok == 401)
		cout << "ERR " << ok << ": Duplicate points detected." << endl;


	delete[] p;
	f.close();
	return 0;
}
