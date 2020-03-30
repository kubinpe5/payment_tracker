Payment Tracker

Write a program that keeps a record of payments. Each payment includes a currency and an amount. Think about these payments in the same way, you have them on your bank account. Data should be kept in memory (please don’t introduce any database engines).

The program should output a list of all the currency and amounts to the console once per minute. The input can be typed into the command line with possibility to be automated in the future, and optionally also be loaded from a file when starting up.

Sample input:
USD 1000
HKD 100
USD -100
RMB 2000
HKD 200

Sample output:
USD 900
RMB 2000
HKD 300

Detailed requirements:
When your Java program is run, a filename can be optionally specified. The format of the file will be one or more lines with Currency Code Amount like in the Sample Input above, where the currency may be any uppercase 3 letter code, such as USD, HKD, RMB, NZD, GBP etc. The user can then enter more lines into the console by typing a currency and amount and pressing enter. Once per minute, the output showing the net amounts of each currency should be displayed. If the net amount is 0, that currency should not be displayed. 
When the user types "quit", the program should exit.

You may need to make some assumptions. For example, if the user enters invalid input, you can choose to display an error message or quit the program. For each assumption you make, write it down in a readme.txt and include it when you submit the project.

Things you may consider using:
• Unit testing
• Documentation
• Programming patterns
• Thread safety

Please put your code in a bitbucket/github repository. We should be able to build and run your program easily (you may wish to use Maven, Ant, etc). Include instructions on how to run your program.

Optional bonus task
Allow each currency to have the exchange rate compared to USD configured. When you display the output, write the USD equivalent amount next to it, for example:
USD 900
RMB 2000 (USD 314.60)
HKD 300 (USD 38.62)


Instruction for application:

Application is build as runnable jar with Maven. To build executable jar file use `mvn install`. You can run the executable jar file with `java -jar file.jar path/fileName` You also need to have Java 8+ JRE on your machine.

Filename parameter is optional, but you need to specify whole system path to file with his name `path/filename`. If you do not provide the file, the program will have some default values from the task.
Format of the file should be as specified. 
If the row is not valid it will not be in the output, but the file will be processed and the application will continue running.
After processing the file the user can add rows with the same format to add additional data to dataset.

Example input:
CZK 6512.50

If the user make mistake in the output the program will log error message and continue running.

The program will output values for every currency in memory every minute after the program started. Also it will print the output right on start, so user can check inserted file content.

If you want to stop the application you can use `QUIT` keyword. It is compared case insensitive.

Also there are exchange rate calculated, but the exchange rate dataset cannot be changed during the run of the application. It is hardcoded. For unknown currency and USD there is no exchange rate displayed. Other currencies have the exchange rate in separate repository file.
Supported currencies are:  "USD","EUR","GBP","AUD","CAD","CZK","CHF","JPY","HKD","RMB" 