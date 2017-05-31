To run the application and view 1,2,3 point, you need to write in console in the target folder (without args):
java -jar reviews-1.0-SNAPSHOT-jar-with-dependencies.jar 

for 4 point need to add args 'translate=true':
java -jar reviews-1.0-SNAPSHOT-jar-with-dependencies.jar translate=true

I tested the first three point changing vmoptions:
-Xms100m
-Xmx100m

The first two jobs work very fast, the third - fast, because it is necessary to cut off all unnecessary columns.

For 4 jobs, I used split line by a limit (0, 1000) and then I found the last point, cut and added to the list.
In the remainder I added a line without split line.

For the best solution of the 4th task, I could suggest to use Thread.Need to find size all line and split by thread evenly for fast algorithm .But I have not expirience with Multithreading.

For translation by https://api.google.com/translate I used Restemplate.

P.S. also you can run reviews-1.0-SNAPSHOT.jar, but this jar without dependencies and will work only 1,2,3 point.