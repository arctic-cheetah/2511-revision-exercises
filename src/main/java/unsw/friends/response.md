a) 
- Primitive obsession
- Nested conditionals AND loops
- people List is public 
- Single responsibility principle is violated. Why should WasteBookController be responsible for creating the iterator?
- Long method for void follow()
- Magic strings for "popularity" and "friends"
- Possibale high coupling and law of demeter violation because WasteBookController has direct access to the functions inside Person
- Person.java could become a lazy class because it doesn't do much
- This method is repeated
Person<P> member = people.stream().filter(p -> p.getId().equals(person)).findFirst().get();
- Bidirectional relationship of friends may be tricky to manage
- Exposed list for modification in Person.java via the getters
- People can follow another member twice!!

c)
Currently, the NetworkIterator<P> class is a benign abstraction (a wrapper that provides no functionality) around Iterator<P>.

 The iterator can be created via the getIterator method of the controller, which takes in a parameter, which is the order of the network members should be iterated over ("popularity" or "friends").


Additionally, when a new member is added to the network, the iterator is invalidated (no longer up to date) as the object it was iterating over has been modified. 


The iterator does not remain up to date when new members are added.



Modify the next method so that it returns the person in the network with the highest rank according to the given comparison method which has not already been traversed, and remains up-to-date with new members added to the network and new connections made between members of the network.


You are required to cache (store a copy) of the network members inside the iterator for performance. You do not need to store a copy of every member, just the data structure containing the members itself.
For example:


Need to use observer pattern to notify iterator of new user!
