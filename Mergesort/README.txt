****************
* IUDoubleLinkedList
* CS221
* May 9 2021
* Elena Yang
**************** 

OVERVIEW:

IUDoubleLinkedList.java implements an IndexedUnosrtedList interface. 
It uses DNode.java which is a double linked linear node interface to store
elements of the list and it has a ListIterator and DLLIterator. ListTester 
tests the files to ensure each method is functioning properly.

INCLUDED FILES:

 * IUDoubeLinkedList.java - source file, contains methods for the program
 * ListTester.java - testing file, tests to make sure methods work properly
 * DNode.java - source file, created the double linked node
 * IndexedUnsortedList.java - interface file
 * README - this file

COMPILING AND RUNNING:

 From the directory containing all source files, compile the
 driver class for the Eclipse window by running the file.
 
 Console output will give the results after the program finishes.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

IUDoubleLinkedList is based off of the IndexedUnsortedList interface
so the methods are organized that way. These methods are specific
such that a user can use them to organize items in a list and access
data at various points. There's a class inside IUDoubleLinkedList
that is an iterator which is used to navigate and hold position in the
list. Similarly, its methods and organization are determined
by a ListIterator interface.

In addition to IUDoubleLinkedList, I had to write a DNode.java class
as well as update my ListTester file to include more iterator tests.
For the former, I based it off of the Node.java file that was a source
file previously and included the same methods as well as methods that 
allow a user to traverse backwards through the list, the main difference
between a singlelinkedlist and a doublelinkedlist. This file provides
the backbone for code inside the methods of IUDoubleLInkedList.

I also had to update my ListTester.java file. Besides adding the given
ListITerator concurrency tests, I had to implement list tests for all of the
ListITerator methods. These are introduced in a similar way as previous one's were.
I also had to implement change scenarios which were implemented the same way
previous change scenarios were.


TESTING:

I used ListTester.java to test the main program to make sure it meets all
of the requirements. In the output console it told me how many tests I failed
as well as what type of tests I failed. 

My testing strategy was to write each method and then after one method was complete
compile the program to see if the percent of cases that passed was higher. If it was,
I moved on. If it went lower, I assumed I wrote the method completely wrong and
went back, deleted it, moved on, and came back to it later. 

I found a TON of bugs, especially with my iterator. I didn't construct the iterator
properly, and didn't implement the interface correctly. I used the eclipse suggested 
methods but it wasn't correct so I did some research involving the interface and
asked other students in class and was able to fix it to function properly (and after
implementing it my test cases went up by a few hundred!).

When I was writing the ListTester class I got so tired because it was so much copy and
pasting and changing simple things. However, when I moved on to debug the IUDoubleLinkedList
file it was so helpful because it gave me specifics with what was wrong with the code. It
also shows/proves that the code is working, at least for a few base cases. At the moment
majority (90%+) of the cases pass according to ListTester. The remaining involve add methods
but I was unable to successfully debug these on my own and plan to fix them in the redemption
project.

DISCUSSION:
 
Because I spend a lot of time before on the listtester, arraylist, and 
IUSingleLinkedList, portions of this assignment were much more manageable.

Majority of my issues were being lost as to what to do.
I had a lot of problems figuring out how to start as I missed a lot of 
instructional time, but my classmates were very helpful. At first, I was
confused about the Node.java file and how methods such as previous were 
supposed to be implemented. After some googling I discovered how to write
a DNode file and promptly did that. 

A lot of the methods in IUDoubleLinkedList were very similar to 
IUSingleLinkedList (especially some like "size" and "isEmpty". Having an
IUSingleLinkedList that passed 100% of the cases were really helpful
as I debugged that program so I knew where some errors were when my ListTester
wasn't passing all of it's cases when I was trying to improve the file
as it was a double-linked node and not a single-linked one anymore.

In regards to updating the ListTester.java file, it was very tedious because
all of the cases were almost doubled regarding the change scenarios.
I had to relearn how I implemented them before, with the instantiation, the scenario,
and finally the method. However, once I wrote a few the rest were relatively 
simple to write, but tedious, of course.

The main thing that "clicked" for me while writing this project was the Iterator.
During the IUSingleLInkedList assignment I wasn't exactly sure the purpose behind the iterator,
but after writing additional methods I defintiely understand it a lot more.