import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Specified Multiset implimentation that contains
 *  paired objects and counters instead of separate instances.
 *
 * @author Griffin Moran
 * @version V1
 */
public class CounterMultiset<E> extends AbstractMultiset<E> {
  
  private ArrayList<Pair<E>> elements;
  private int count;
  
  public CounterMultiset() {
    elements = new ArrayList<Pair<E>>();
    count = 0;
  }
  
  @Override
  public boolean add(E item) {
    add(item, 1);
    return true;
  }
  
  /**
   * Increase the amount of a certain item by a certain count.
   *
   * @param item The element to add to
   * @param number The number of occurances to add to 
   * @return the previous number of occurances before calling add
   */
  public int add(E item, int number) {
    int old = 0;
    if (number < 1) {
      return old;
    }
    Pair<E> p = new Pair<E>(item, number);
    for (Pair<E> pair : elements) {
      if (pair.getFirst().equals(item)) {
        old += pair.getSecond();
        pair.setSecond(old + number);
        count += number;
        return old;
      }
    }
    elements.add(p);
    count += number;
    return old;
  }
  
  @Override
  public boolean contains(Object item) {
    for (Pair<E> pair : elements) {
      if (pair.getFirst().equals(item)) {
        return true;
      }
    }
    return false;
  }
  
  @Override
  public boolean equals(Object other) {

    if (!(other instanceof CounterMultiset) || ((CounterMultiset) other).size() != size()) {
      return false;
    }

    for (Pair<E> element : elements) {
      if  (this.getCount(element.getFirst()) != (
          (CounterMultiset) other).getCount(element.getFirst())) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int getCount(Object item) {
    int count = 0;
    for (Pair<E> pair : elements) {
      if (pair == null) {
        return count;
      }
      if (pair.getFirst().equals(item)) {
        count += pair.getSecond();
        return count;
      }
    }
    return count;
  }
  
  @Override
  public Iterator<E> iterator() {
    return new CounterMultisetIterator();
  }
  
  @Override
  public boolean remove(Object item) {
    for (Pair<E> pair : elements) {
      if (pair.getFirst().equals(item)) {
        if (pair.getSecond() > 1) {
          pair.setSecond(pair.getSecond() - 1);
          count -= 1;
          return true;
        }
        count -= 1;
        elements.remove(pair);
        return true;
      }
    }
    return false;
  }
  
  @Override
  public int size() {
    return count;
  }
  
  @Override
  public String toString() {
    HashSet<E> added = new HashSet<>();
    StringBuilder sb = new StringBuilder();
    sb.append("[");

    for (Pair<E> element : elements) {

      if (!added.contains(element.getFirst())) {

        if (added.size() > 0) {
          sb.append(", ");
        }

        sb.append(element.getFirst().toString() + " x " + this.getCount(element.getFirst()));
      }
      added.add(element.getFirst());
    }
    sb.append("]");
    return sb.toString();
  }
  
  
  private class CounterMultisetIterator implements Iterator<E> {
    int index = 0;
    int subIndex;
    boolean canRemove = false;
    E current;
    
    @Override
    public boolean hasNext() {
      if (index == elements.size() - 1 && elements.get(index).getSecond() == subIndex) {
        return false;
      }
      if (index < elements.size()) {
        return true;
      }
      canRemove = false;
      return false;
    }

    @Override
    public E next() {
      if (hasNext()) { //index not incrementing at the right time
        canRemove = true;
        if (subIndex >= elements.get(index).getSecond()) {
          index++;
          current = elements.get(index).getFirst();
          subIndex = 1;
        } else {
          current = elements.get(index).getFirst();
          subIndex++;
        }
        return current;
      }
      throw new NoSuchElementException();
    }
    
    /**
     * Remove the previous Pair returned by next().
     */
    @Override
    public void remove() {
      if (!canRemove) {
        throw new IllegalStateException();
      }
      if (getCount(current) == 1) {
        elements.remove(index);
        subIndex = 0;
        index--;
        if (index < 1) {
          index = 0;
        }
      } else {
        elements.get(index).setSecond(elements.get(index).getSecond() - 1);
        subIndex--;
      }
      count--;
      canRemove = false;
    }
  }
  
  private class Pair<E> {
    private E first;
    private int second;

    /**
     * Create a Pair with the provided objects.
     *
     * @param first The first object.
     * @param second The second object.
     */
    public Pair(E first, int second) {
      this.first = first;
      this.second = second;
    }
    
    /**
     * Access the first component.
     *
     * @return The first component.
     */
    public E getFirst()  {
      return first;
    }

    /**
     * Access the second component.
     *
     * @return The second component.
     */
    public int getSecond() {
      return second;
    }

    /**
     * Set the second component.
     *
     * @param second The new component.
     */
    public void setSecond(int second) {
      this.second = second;
    }
  }
}
