//============================================================================
// Name        : FastPrime.cpp
// Author      : Rafael Moraes
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
using namespace std;

int main() {

	unsigned short size;
	cin >> size;

	for (unsigned short var = 0; var < size; ++var) {
		unsigned int in;
		cin >> in;

		if (in % 2 == 0) {
			if (in == 2) {
				cout << "Prime" << endl;
				continue;
			} else {
				cout << "Not Prime" << endl;
				continue;
			}
		}

		bool isPrime = true;
		for (unsigned short it = 3; it < in; it += 2) {
			if (in % it == 0) {
				isPrime = false;
				break;
			}
			if (in / it < it) {
				isPrime = true;
				break;
			}
		}

		if (isPrime) {
			cout << "Prime" << endl;
		} else {
			cout << "Not Prime" << endl;
		}
	}

	return 0;
}
