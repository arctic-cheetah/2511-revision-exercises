package unsw.friends;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WasteBookController<P extends Comparable<P>> {

    List<Person<P>> people = new ArrayList<Person<P>>();
    NetworkIterator<P> observer = null;

    /**
     * Adds a new member with the given name to the network.
     */
    public void addPersonToNetwork(P name) {
        people.add(new Person<P>(name));
        updateObserver();

    }

    private void updateObserver() {
        if (observer != null) {
            observer.notifyObserver();
        }
    }

    /**
     * @preconditions person1 and person2 already exist in the social media network.
     *                person1 follows person2 in the social media network.
     * @postconditions don't allow people to be followed or befriended twice
     */
    public void follow(P person1, P person2) {
        if (person1.equals(person2)) {
            return;
        }

        Person<P> member1 = people.stream().filter(e -> e.getId().equals(person1)).findFirst().get();
        Person<P> member2 = people.stream().filter(e -> e.getId().equals(person2)).findFirst().get();
        member1.addFollowing(member2);
        checkAddFriend(member1, member2);
        updateObserver();
    }

    private void checkAddFriend(Person<P> member1, Person<P> member2) {
        if (member2.isFollowing(member1)) {
            member1.addFriend(member2);
            member2.addFriend(member1);
        }
    }

    public int getPopularity(P person) {
        Person<P> member = people.stream().filter(p -> p.getId().equals(person)).findFirst().get();
        int popularity = 0;

        popularity = people.stream()
                .map(other -> other.isFollowing(member) ? 1 : 0)
                .reduce((acc, curr) -> acc + curr)
                .get();

        return popularity;
    }

    public int getFriends(P person) {
        Person<P> member = people.stream().filter(p -> p.getId().equals(person)).findFirst().get();
        return member.getFriends().size();
    }

    /**
     * Returns an iterator to the network (each member)
     * ordered by the given parameter.
     */
    public NetworkIterator<P> getIterator(String orderBy) {
        Comparator<Person<P>> comparator = comparatorFactory(orderBy).thenComparing(Person::getId);
        observer = new NetworkIterator<P>(people, comparator);
        return observer;
    }

    private Comparator<Person<P>> comparatorFactory(String orderBy) {
        if (orderBy.equals("popularity")) {
            return Comparator.comparing(p -> -getPopularity(p.getId()));
        } else if (orderBy.equals("friends")) {
            return Comparator.comparing(p -> -getFriends(p.getId()));
        }
        return null;
    }

    public void switchIteratorComparisonMethod(NetworkIterator<P> iter, String orderBy) {
        iter.setCompare(comparatorFactory(orderBy));
    }
}