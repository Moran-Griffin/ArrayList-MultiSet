import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * The multiset interface provides the framework for a Multiset collection.
 * 
 * @author Griffin Moran
 * @version 1,0
 * @param <E> Type parameter for the collection
 */
public interface Multiset<E> extends Collection<E> {
  
  @Override
  public boolean add(E item);
  
  public int add(E item, int occurances);
  
  @Override
  public boolean addAll(Collection<? extends E> c);
  
  @Override
  public void clear();
  
  @Override
  public boolean contains(Object item);
  
  @Override
  public boolean equals(Object other);
  
  public int getCount(Object integer);
  
  @Override
  public int hashCode(); 
  
  @Override
  public boolean isEmpty();
  
  @Override
  public Iterator<E> iterator();
  
  @Override
  public Stream<E> parallelStream();
  
  @Override
  public boolean removeAll(Collection<?> c);
  
  @Override
  public boolean removeIf(Predicate<? super E> p);
  
  @Override
  public boolean retainAll(Collection<?> c);
  
  @Override
  public int size();
  
  @Override
  public Spliterator<E> spliterator();
  
  @Override
  public Stream<E> stream();
  
  @Override
  public Object[] toArray();
  
  @Override
  public <T> T[] toArray(T[] a);
}
