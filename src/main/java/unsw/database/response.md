Assumption:
-Assume that once the database is created, the database structure from that point will remain the same.
-Assume there won't be duplicates in a given row
-Any columns that aren't specified are presumed to have the value "" for text and 0 for marks
-Assume won't be given dependencies such that A depends on B and B depends on A.

Question d) 
Use observer pattern to ensure all fields with a derived column are updated.

A listener should be attached to every row, to the derived columns.

This way if a change is made to a row, that field in the particular row can automatically update their value

Question e)
Use composite pattern to parse the boolean logic via a tree
Use Factory pattern too!

There are 2 boolean operators (AND/OR), 
where AND has higher precedence than OR. 


## Notes:
Parse the input into simpler input
Use Postorder traversal of query tree to get desired input

Queries will be stored in a tree format.

Each ComparisonOperatorNode will have
- column  
- value 
- Queryfunction (evaluate the expression)
- reference to database

Each BooleanComparatorNode will have
-reference to left and right nodes.
-QueryFunction (evaluate the expression)
-reference to database



Functions for operators:
simpleQuery -> "="
greaterThanQuery -> ">"



StudentId = 'z1234' 
AND 
Assignment = 10 

OR 

Tutorial = 'tue13a' 
AND 
Assignment > 15