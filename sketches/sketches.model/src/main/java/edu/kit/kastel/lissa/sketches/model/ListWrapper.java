package edu.kit.kastel.lissa.sketches.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Spliterator;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Stream;

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.ByteIterable;
import org.eclipse.collections.api.CharIterable;
import org.eclipse.collections.api.DoubleIterable;
import org.eclipse.collections.api.FloatIterable;
import org.eclipse.collections.api.IntIterable;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.LongIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.ShortIterable;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.MutableBagIterable;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.bimap.MutableBiMap;
import org.eclipse.collections.api.block.HashingStrategy;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.function.Function3;
import org.eclipse.collections.api.block.function.primitive.BooleanFunction;
import org.eclipse.collections.api.block.function.primitive.ByteFunction;
import org.eclipse.collections.api.block.function.primitive.CharFunction;
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.function.primitive.DoubleObjectToDoubleFunction;
import org.eclipse.collections.api.block.function.primitive.FloatFunction;
import org.eclipse.collections.api.block.function.primitive.FloatObjectToFloatFunction;
import org.eclipse.collections.api.block.function.primitive.IntFunction;
import org.eclipse.collections.api.block.function.primitive.IntObjectToIntFunction;
import org.eclipse.collections.api.block.function.primitive.LongFunction;
import org.eclipse.collections.api.block.function.primitive.LongObjectToLongFunction;
import org.eclipse.collections.api.block.function.primitive.ObjectIntToObjectFunction;
import org.eclipse.collections.api.block.function.primitive.ShortFunction;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.collection.primitive.MutableByteCollection;
import org.eclipse.collections.api.collection.primitive.MutableCharCollection;
import org.eclipse.collections.api.collection.primitive.MutableDoubleCollection;
import org.eclipse.collections.api.collection.primitive.MutableFloatCollection;
import org.eclipse.collections.api.collection.primitive.MutableIntCollection;
import org.eclipse.collections.api.collection.primitive.MutableLongCollection;
import org.eclipse.collections.api.collection.primitive.MutableShortCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.ParallelListIterable;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.api.list.primitive.MutableByteList;
import org.eclipse.collections.api.list.primitive.MutableCharList;
import org.eclipse.collections.api.list.primitive.MutableDoubleList;
import org.eclipse.collections.api.list.primitive.MutableFloatList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.api.list.primitive.MutableShortList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.MutableMapIterable;
import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.MutableObjectLongMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.ordered.OrderedIterable;
import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.Twin;

@SuppressWarnings("deprecation")
public class ListWrapper<ElemType extends Serializable> implements MutableList<ElemType>, Serializable {
    private static final long serialVersionUID = -7470936882610209480L;
    private final MutableList<ElemType> list;

    public static <S extends Serializable> ListWrapper<S> empty() {
        return new ListWrapper<>(Lists.mutable.empty());
    }

    public ListWrapper(MutableList<ElemType> list) {
        this.list = list;
    }

    @Override
    public void forEach(Consumer<? super ElemType> action) {
        list.forEach(action);
    }

    @Override
    public void forEachWithIndex(ObjectIntProcedure<? super ElemType> objectIntProcedure) {
        list.forEachWithIndex(objectIntProcedure);
    }

    @Override
    public MutableList<ElemType> with(ElemType element) {
        return list.with(element);
    }

    @Override
    public void reverseForEach(Procedure<? super ElemType> procedure) {
        list.reverseForEach(procedure);
    }

    @Override
    public MutableList<ElemType> without(ElemType element) {
        return list.without(element);
    }

    @Override
    public MutableList<ElemType> withAll(Iterable<? extends ElemType> elements) {
        return list.withAll(elements);
    }

    @Override
    public <P> void forEachWith(Procedure2<? super ElemType, ? super P> procedure, P parameter) {
        list.forEachWith(procedure, parameter);
    }

    @Override
    public MutableList<ElemType> withoutAll(Iterable<? extends ElemType> elements) {
        return list.withoutAll(elements);
    }

    @Override
    public void reverseForEachWithIndex(ObjectIntProcedure<? super ElemType> procedure) {
        list.reverseForEachWithIndex(procedure);
    }

    @Override
    public MutableList<ElemType> newEmpty() {
        return list.newEmpty();
    }

    @Override
    public MutableList<ElemType> clone() {
        return list.clone();
    }

    @Override
    public MutableList<ElemType> tap(Procedure<? super ElemType> procedure) {
        return list.tap(procedure);
    }

    @Override
    public MutableList<ElemType> select(Predicate<? super ElemType> predicate) {
        return list.select(predicate);
    }

    @Override
    public Optional<ElemType> getFirstOptional() {
        return list.getFirstOptional();
    }

    @Override
    public <P> MutableList<ElemType> selectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
        return list.selectWith(predicate, parameter);
    }

    @Override
    public LazyIterable<ElemType> asReversed() {
        return list.asReversed();
    }

    @Override
    public MutableList<ElemType> reject(Predicate<? super ElemType> predicate) {
        return list.reject(predicate);
    }

    @Override
    public <P> MutableList<ElemType> rejectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
        return list.rejectWith(predicate, parameter);
    }

    @Override
    public int detectLastIndex(Predicate<? super ElemType> predicate) {
        return list.detectLastIndex(predicate);
    }

    @Override
    public PartitionMutableList<ElemType> partition(Predicate<? super ElemType> predicate) {
        return list.partition(predicate);
    }

    @Override
    public <P> PartitionMutableList<ElemType> partitionWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
        return list.partitionWith(predicate, parameter);
    }

    @Override
    public <S> MutableList<S> selectInstancesOf(Class<S> clazz) {
        return list.selectInstancesOf(clazz);
    }

    @Override
    public <V> MutableList<V> collect(Function<? super ElemType, ? extends V> function) {
        return list.collect(function);
    }

    @Override
    public void forEach(Procedure<? super ElemType> procedure) {
        list.forEach(procedure);
    }

    @Override
    public <V> MutableList<V> collectWithIndex(ObjectIntToObjectFunction<? super ElemType, ? extends V> function) {
        return list.collectWithIndex(function);
    }

    @Override
    public Optional<ElemType> getLastOptional() {
        return list.getLastOptional();
    }

    @Override
    public MutableBooleanList collectBoolean(BooleanFunction<? super ElemType> booleanFunction) {
        return list.collectBoolean(booleanFunction);
    }

    @Override
    public boolean notEmpty() {
        return list.notEmpty();
    }

    @Override
    public MutableByteList collectByte(ByteFunction<? super ElemType> byteFunction) {
        return list.collectByte(byteFunction);
    }

    @Override
    public MutableCharList collectChar(CharFunction<? super ElemType> charFunction) {
        return list.collectChar(charFunction);
    }

    @Override
    public ElemType getAny() {
        return list.getAny();
    }

    @Override
    public MutableDoubleList collectDouble(DoubleFunction<? super ElemType> doubleFunction) {
        return list.collectDouble(doubleFunction);
    }

    @Override
    public MutableFloatList collectFloat(FloatFunction<? super ElemType> floatFunction) {
        return list.collectFloat(floatFunction);
    }

    @Override
    public ElemType getFirst() {
        return list.getFirst();
    }

    @Override
    public MutableIntList collectInt(IntFunction<? super ElemType> intFunction) {
        return list.collectInt(intFunction);
    }

    @Override
    public MutableLongList collectLong(LongFunction<? super ElemType> longFunction) {
        return list.collectLong(longFunction);
    }

    @Override
    public MutableShortList collectShort(ShortFunction<? super ElemType> shortFunction) {
        return list.collectShort(shortFunction);
    }

    @Override
    public <P, V> MutableList<V> collectWith(Function2<? super ElemType, ? super P, ? extends V> function, P parameter) {
        return list.collectWith(function, parameter);
    }

    @Override
    public <V> MutableList<V> collectIf(Predicate<? super ElemType> predicate, Function<? super ElemType, ? extends V> function) {
        return list.collectIf(predicate, function);
    }

    @Override
    public <V> MutableList<V> flatCollect(Function<? super ElemType, ? extends Iterable<V>> function) {
        return list.flatCollect(function);
    }

    @Override
    public ElemType getLast() {
        return list.getLast();
    }

    @Override
    public <P, V> MutableList<V> flatCollectWith(Function2<? super ElemType, ? super P, ? extends Iterable<V>> function, P parameter) {
        return list.flatCollectWith(function, parameter);
    }

    @Override
    public <S> boolean corresponds(OrderedIterable<S> other, Predicate2<? super ElemType, ? super S> predicate) {
        return list.corresponds(other, predicate);
    }

    @Override
    public MutableList<ElemType> distinct() {
        return list.distinct();
    }

    @Override
    public MutableList<ElemType> distinct(HashingStrategy<? super ElemType> hashingStrategy) {
        return list.distinct(hashingStrategy);
    }

    @Override
    public ElemType getOnly() {
        return list.getOnly();
    }

    @Override
    public <V> MutableList<ElemType> distinctBy(Function<? super ElemType, ? extends V> function) {
        return list.distinctBy(function);
    }

    @Override
    public void forEach(int startIndex, int endIndex, Procedure<? super ElemType> procedure) {
        list.forEach(startIndex, endIndex, procedure);
    }

    @Override
    public MutableList<ElemType> sortThis(Comparator<? super ElemType> comparator) {
        return list.sortThis(comparator);
    }

    @Override
    public MutableList<ElemType> sortThis() {
        return list.sortThis();
    }

    @Override
    public <V> boolean containsBy(Function<? super ElemType, ? extends V> function, V value) {
        return list.containsBy(function, value);
    }

    @Override
    public <V extends Comparable<? super V>> MutableList<ElemType> sortThisBy(Function<? super ElemType, ? extends V> function) {
        return list.sortThisBy(function);
    }

    @Override
    public MutableList<ElemType> sortThisByInt(IntFunction<? super ElemType> function) {
        return list.sortThisByInt(function);
    }

    @Override
    public MutableList<ElemType> sortThisByBoolean(BooleanFunction<? super ElemType> function) {
        return list.sortThisByBoolean(function);
    }

    @Override
    public boolean containsAllIterable(Iterable<?> source) {
        return list.containsAllIterable(source);
    }

    @Override
    public MutableList<ElemType> sortThisByChar(CharFunction<? super ElemType> function) {
        return list.sortThisByChar(function);
    }

    @Override
    public void forEachWithIndex(int fromIndex, int toIndex, ObjectIntProcedure<? super ElemType> objectIntProcedure) {
        list.forEachWithIndex(fromIndex, toIndex, objectIntProcedure);
    }

    @Override
    public MutableList<ElemType> sortThisByByte(ByteFunction<? super ElemType> function) {
        return list.sortThisByByte(function);
    }

    @Override
    public MutableList<ElemType> sortThisByShort(ShortFunction<? super ElemType> function) {
        return list.sortThisByShort(function);
    }

    @Override
    public MutableList<ElemType> sortThisByFloat(FloatFunction<? super ElemType> function) {
        return list.sortThisByFloat(function);
    }

    @Override
    public boolean containsAllArguments(Object... elements) {
        return list.containsAllArguments(elements);
    }

    @Override
    public MutableList<ElemType> sortThisByLong(LongFunction<? super ElemType> function) {
        return list.sortThisByLong(function);
    }

    @Override
    public MutableList<ElemType> sortThisByDouble(DoubleFunction<? super ElemType> function) {
        return list.sortThisByDouble(function);
    }

    @Override
    public MutableList<ElemType> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public MutableList<ElemType> asUnmodifiable() {
        return list.asUnmodifiable();
    }

    @Override
    public MutableStack<ElemType> toStack() {
        return list.toStack();
    }

    @Override
    public ParallelListIterable<ElemType> asParallel(ExecutorService executorService, int batchSize) {
        return list.asParallel(executorService, batchSize);
    }

    @Override
    public MutableList<ElemType> asSynchronized() {
        return list.asSynchronized();
    }

    @Override
    public ImmutableList<ElemType> toImmutable() {
        return list.toImmutable();
    }

    @Override
    public int binarySearch(ElemType key, Comparator<? super ElemType> comparator) {
        return list.binarySearch(key, comparator);
    }

    @Override
    public <V> MutableListMultimap<V, ElemType> groupBy(Function<? super ElemType, ? extends V> function) {
        return list.groupBy(function);
    }

    @Override
    public <V> MutableListMultimap<V, ElemType> groupByEach(Function<? super ElemType, ? extends Iterable<V>> function) {
        return list.groupByEach(function);
    }

    @Override
    public void each(Procedure<? super ElemType> procedure) {
        list.each(procedure);
    }

    @Override
    public <S> MutableList<Pair<ElemType, S>> zip(Iterable<S> that) {
        return list.zip(that);
    }

    @Override
    public MutableList<Pair<ElemType, Integer>> zipWithIndex() {
        return list.zipWithIndex();
    }

    @Override
    public int binarySearch(ElemType key) {
        return list.binarySearch(key);
    }

    @Override
    public MutableList<ElemType> take(int count) {
        return list.take(count);
    }

    @Override
    public MutableList<ElemType> takeWhile(Predicate<? super ElemType> predicate) {
        return list.takeWhile(predicate);
    }

    @Override
    public MutableList<ElemType> drop(int count) {
        return list.drop(count);
    }

    @Override
    public MutableList<ElemType> dropWhile(Predicate<? super ElemType> predicate) {
        return list.dropWhile(predicate);
    }

    @Override
    public PartitionMutableList<ElemType> partitionWhile(Predicate<? super ElemType> predicate) {
        return list.partitionWhile(predicate);
    }

    @Override
    public MutableList<ElemType> toReversed() {
        return list.toReversed();
    }

    @Override
    public MutableList<ElemType> reverseThis() {
        return list.reverseThis();
    }

    @Override
    public MutableList<ElemType> shuffleThis() {
        return list.shuffleThis();
    }

    @Override
    public <T2> void forEachInBoth(ListIterable<T2> other, Procedure2<? super ElemType, ? super T2> procedure) {
        list.forEachInBoth(other, procedure);
    }

    @Override
    public MutableList<ElemType> shuffleThis(Random random) {
        return list.shuffleThis(random);
    }

    @Override
    public <P> Twin<MutableList<ElemType>> selectAndRejectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
        return list.selectAndRejectWith(predicate, parameter);
    }

    @Override
    public <V, R extends Collection<V>> R collectWithIndex(ObjectIntToObjectFunction<? super ElemType, ? extends V> function, R target) {
        return list.collectWithIndex(function, target);
    }

    @Override
    public <R extends Collection<ElemType>> R select(Predicate<? super ElemType> predicate, R target) {
        return list.select(predicate, target);
    }

    @Override
    public boolean removeIf(Predicate<? super ElemType> predicate) {
        return list.removeIf(predicate);
    }

    @Override
    public <P> boolean removeIfWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
        return list.removeIfWith(predicate, parameter);
    }

    @Override
    public int detectIndex(Predicate<? super ElemType> predicate) {
        return list.detectIndex(predicate);
    }

    @Override
    public <P, R extends Collection<ElemType>> R selectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter, R targetCollection) {
        return list.selectWith(predicate, parameter, targetCollection);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<ElemType> iterator() {
        return list.iterator();
    }

    @Override
    public boolean addAll(int index, Collection<? extends ElemType> c) {
        return list.addAll(index, c);
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public <R extends Collection<ElemType>> R reject(Predicate<? super ElemType> predicate, R target) {
        return list.reject(predicate, target);
    }

    @Override
    public <IV, P> IV injectIntoWith(IV injectValue, Function3<? super IV, ? super ElemType, ? super P, ? extends IV> function, P parameter) {
        return list.injectIntoWith(injectValue, function, parameter);
    }

    @Override
    public <P, R extends Collection<ElemType>> R rejectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter, R targetCollection) {
        return list.rejectWith(predicate, parameter, targetCollection);
    }

    @Override
    public void replaceAll(UnaryOperator<ElemType> operator) {
        list.replaceAll(operator);
    }

    @Override
    public <T> T[] toArray(java.util.function.IntFunction<T[]> generator) {
        return list.toArray(generator);
    }

    @Override
    public <V> MutableObjectLongMap<V> sumByInt(Function<? super ElemType, ? extends V> groupBy, IntFunction<? super ElemType> function) {
        return list.sumByInt(groupBy, function);
    }

    @Override
    public <V> MutableObjectDoubleMap<V> sumByFloat(Function<? super ElemType, ? extends V> groupBy, FloatFunction<? super ElemType> function) {
        return list.sumByFloat(groupBy, function);
    }

    @Override
    public <V> MutableObjectLongMap<V> sumByLong(Function<? super ElemType, ? extends V> groupBy, LongFunction<? super ElemType> function) {
        return list.sumByLong(groupBy, function);
    }

    @Override
    public <V> MutableObjectDoubleMap<V> sumByDouble(Function<? super ElemType, ? extends V> groupBy, DoubleFunction<? super ElemType> function) {
        return list.sumByDouble(groupBy, function);
    }

    @Override
    public <V> MutableBag<V> countBy(Function<? super ElemType, ? extends V> function) {
        return list.countBy(function);
    }

    @Override
    public <V, P> MutableBag<V> countByWith(Function2<? super ElemType, ? super P, ? extends V> function, P parameter) {
        return list.countByWith(function, parameter);
    }

    @Override
    public void sort(Comparator<? super ElemType> c) {
        list.sort(c);
    }

    @Override
    public <V> MutableBag<V> countByEach(Function<? super ElemType, ? extends Iterable<V>> function) {
        return list.countByEach(function);
    }

    @Override
    public boolean add(ElemType e) {
        return list.add(e);
    }

    @Override
    public <V> MutableMap<V, ElemType> groupByUniqueKey(Function<? super ElemType, ? extends V> function) {
        return list.groupByUniqueKey(function);
    }

    @Override
    public boolean addAllIterable(Iterable<? extends ElemType> iterable) {
        return list.addAllIterable(iterable);
    }

    @Override
    public boolean removeAllIterable(Iterable<?> iterable) {
        return list.removeAllIterable(iterable);
    }

    @Override
    public boolean retainAllIterable(Iterable<?> iterable) {
        return list.retainAllIterable(iterable);
    }

    @Override
    public <K, V> MutableMap<K, V> aggregateInPlaceBy(Function<? super ElemType, ? extends K> groupBy, Function0<? extends V> zeroValueFactory,
            Procedure2<? super V, ? super ElemType> mutatingAggregator) {
        return list.aggregateInPlaceBy(groupBy, zeroValueFactory, mutatingAggregator);
    }

    @Override
    public <K, V> MutableMap<K, V> aggregateBy(Function<? super ElemType, ? extends K> groupBy, Function0<? extends V> zeroValueFactory,
            Function2<? super V, ? super ElemType, ? extends V> nonMutatingAggregator) {
        return list.aggregateBy(groupBy, zeroValueFactory, nonMutatingAggregator);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public <V, R extends Collection<V>> R collect(Function<? super ElemType, ? extends V> function, R target) {
        return list.collect(function, target);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends ElemType> c) {
        return list.addAll(c);
    }

    @Override
    public <R extends MutableBooleanCollection> R collectBoolean(BooleanFunction<? super ElemType> booleanFunction, R target) {
        return list.collectBoolean(booleanFunction, target);
    }

    @Override
    public ElemType get(int index) {
        return list.get(index);
    }

    @Override
    public ElemType set(int index, ElemType element) {
        return list.set(index, element);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public void add(int index, ElemType element) {
        list.add(index, element);
    }

    @Override
    public <R extends MutableByteCollection> R collectByte(ByteFunction<? super ElemType> byteFunction, R target) {
        return list.collectByte(byteFunction, target);
    }

    @Override
    public ElemType remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public <R extends MutableCharCollection> R collectChar(CharFunction<? super ElemType> charFunction, R target) {
        return list.collectChar(charFunction, target);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public ListIterator<ElemType> listIterator() {
        return list.listIterator();
    }

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public ListIterator<ElemType> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public <R extends MutableDoubleCollection> R collectDouble(DoubleFunction<? super ElemType> doubleFunction, R target) {
        return list.collectDouble(doubleFunction, target);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public <R extends MutableFloatCollection> R collectFloat(FloatFunction<? super ElemType> floatFunction, R target) {
        return list.collectFloat(floatFunction, target);
    }

    @Override
    public Spliterator<ElemType> spliterator() {
        return list.spliterator();
    }

    @Override
    public <R extends MutableIntCollection> R collectInt(IntFunction<? super ElemType> intFunction, R target) {
        return list.collectInt(intFunction, target);
    }

    @Override
    public Stream<ElemType> stream() {
        return list.stream();
    }

    @Override
    public Stream<ElemType> parallelStream() {
        return list.parallelStream();
    }

    @Override
    public <R extends MutableLongCollection> R collectLong(LongFunction<? super ElemType> longFunction, R target) {
        return list.collectLong(longFunction, target);
    }

    @Override
    public <R extends MutableShortCollection> R collectShort(ShortFunction<? super ElemType> shortFunction, R target) {
        return list.collectShort(shortFunction, target);
    }

    @Override
    public <P, V, R extends Collection<V>> R collectWith(Function2<? super ElemType, ? super P, ? extends V> function, P parameter, R targetCollection) {
        return list.collectWith(function, parameter, targetCollection);
    }

    @Override
    public <V, R extends Collection<V>> R collectIf(Predicate<? super ElemType> predicate, Function<? super ElemType, ? extends V> function, R target) {
        return list.collectIf(predicate, function, target);
    }

    @Override
    public <R extends MutableByteCollection> R flatCollectByte(Function<? super ElemType, ? extends ByteIterable> function, R target) {
        return list.flatCollectByte(function, target);
    }

    @Override
    public <R extends MutableCharCollection> R flatCollectChar(Function<? super ElemType, ? extends CharIterable> function, R target) {
        return list.flatCollectChar(function, target);
    }

    @Override
    public <R extends MutableIntCollection> R flatCollectInt(Function<? super ElemType, ? extends IntIterable> function, R target) {
        return list.flatCollectInt(function, target);
    }

    @Override
    public <R extends MutableShortCollection> R flatCollectShort(Function<? super ElemType, ? extends ShortIterable> function, R target) {
        return list.flatCollectShort(function, target);
    }

    @Override
    public <R extends MutableDoubleCollection> R flatCollectDouble(Function<? super ElemType, ? extends DoubleIterable> function, R target) {
        return list.flatCollectDouble(function, target);
    }

    @Override
    public <R extends MutableFloatCollection> R flatCollectFloat(Function<? super ElemType, ? extends FloatIterable> function, R target) {
        return list.flatCollectFloat(function, target);
    }

    @Override
    public <R extends MutableLongCollection> R flatCollectLong(Function<? super ElemType, ? extends LongIterable> function, R target) {
        return list.flatCollectLong(function, target);
    }

    @Override
    public <R extends MutableBooleanCollection> R flatCollectBoolean(Function<? super ElemType, ? extends BooleanIterable> function, R target) {
        return list.flatCollectBoolean(function, target);
    }

    @Override
    public <V, R extends Collection<V>> R flatCollect(Function<? super ElemType, ? extends Iterable<V>> function, R target) {
        return list.flatCollect(function, target);
    }

    @Override
    public <P, V, R extends Collection<V>> R flatCollectWith(Function2<? super ElemType, ? super P, ? extends Iterable<V>> function, P parameter, R target) {
        return list.flatCollectWith(function, parameter, target);
    }

    @Override
    public ElemType detect(Predicate<? super ElemType> predicate) {
        return list.detect(predicate);
    }

    @Override
    public <P> ElemType detectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
        return list.detectWith(predicate, parameter);
    }

    @Override
    public Optional<ElemType> detectOptional(Predicate<? super ElemType> predicate) {
        return list.detectOptional(predicate);
    }

    @Override
    public <P> Optional<ElemType> detectWithOptional(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
        return list.detectWithOptional(predicate, parameter);
    }

    @Override
    public ElemType detectIfNone(Predicate<? super ElemType> predicate, Function0<? extends ElemType> function) {
        return list.detectIfNone(predicate, function);
    }

    @Override
    public <P> ElemType detectWithIfNone(Predicate2<? super ElemType, ? super P> predicate, P parameter, Function0<? extends ElemType> function) {
        return list.detectWithIfNone(predicate, parameter, function);
    }

    @Override
    public int count(Predicate<? super ElemType> predicate) {
        return list.count(predicate);
    }

    @Override
    public <P> int countWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
        return list.countWith(predicate, parameter);
    }

    @Override
    public boolean anySatisfy(Predicate<? super ElemType> predicate) {
        return list.anySatisfy(predicate);
    }

    @Override
    public <P> boolean anySatisfyWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
        return list.anySatisfyWith(predicate, parameter);
    }

    @Override
    public boolean allSatisfy(Predicate<? super ElemType> predicate) {
        return list.allSatisfy(predicate);
    }

    @Override
    public <P> boolean allSatisfyWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
        return list.allSatisfyWith(predicate, parameter);
    }

    @Override
    public boolean noneSatisfy(Predicate<? super ElemType> predicate) {
        return list.noneSatisfy(predicate);
    }

    @Override
    public <P> boolean noneSatisfyWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
        return list.noneSatisfyWith(predicate, parameter);
    }

    @Override
    public <IV> IV injectInto(IV injectedValue, Function2<? super IV, ? super ElemType, ? extends IV> function) {
        return list.injectInto(injectedValue, function);
    }

    @Override
    public int injectInto(int injectedValue, IntObjectToIntFunction<? super ElemType> function) {
        return list.injectInto(injectedValue, function);
    }

    @Override
    public long injectInto(long injectedValue, LongObjectToLongFunction<? super ElemType> function) {
        return list.injectInto(injectedValue, function);
    }

    @Override
    public float injectInto(float injectedValue, FloatObjectToFloatFunction<? super ElemType> function) {
        return list.injectInto(injectedValue, function);
    }

    @Override
    public double injectInto(double injectedValue, DoubleObjectToDoubleFunction<? super ElemType> function) {
        return list.injectInto(injectedValue, function);
    }

    @Override
    public <R extends Collection<ElemType>> R into(R target) {
        return list.into(target);
    }

    @Override
    public MutableList<ElemType> toList() {
        return list.toList();
    }

    @Override
    public MutableList<ElemType> toSortedList() {
        return list.toSortedList();
    }

    @Override
    public MutableList<ElemType> toSortedList(Comparator<? super ElemType> comparator) {
        return list.toSortedList(comparator);
    }

    @Override
    public <V extends Comparable<? super V>> MutableList<ElemType> toSortedListBy(Function<? super ElemType, ? extends V> function) {
        return list.toSortedListBy(function);
    }

    @Override
    public MutableSet<ElemType> toSet() {
        return list.toSet();
    }

    @Override
    public MutableSortedSet<ElemType> toSortedSet() {
        return list.toSortedSet();
    }

    @Override
    public MutableSortedSet<ElemType> toSortedSet(Comparator<? super ElemType> comparator) {
        return list.toSortedSet(comparator);
    }

    @Override
    public <V extends Comparable<? super V>> MutableSortedSet<ElemType> toSortedSetBy(Function<? super ElemType, ? extends V> function) {
        return list.toSortedSetBy(function);
    }

    @Override
    public MutableBag<ElemType> toBag() {
        return list.toBag();
    }

    @Override
    public MutableSortedBag<ElemType> toSortedBag() {
        return list.toSortedBag();
    }

    @Override
    public MutableSortedBag<ElemType> toSortedBag(Comparator<? super ElemType> comparator) {
        return list.toSortedBag(comparator);
    }

    @Override
    public <V extends Comparable<? super V>> MutableSortedBag<ElemType> toSortedBagBy(Function<? super ElemType, ? extends V> function) {
        return list.toSortedBagBy(function);
    }

    @Override
    public <NK, NV> MutableMap<NK, NV> toMap(Function<? super ElemType, ? extends NK> keyFunction, Function<? super ElemType, ? extends NV> valueFunction) {
        return list.toMap(keyFunction, valueFunction);
    }

    @Override
    public <NK, NV, R extends Map<NK, NV>> R toMap(Function<? super ElemType, ? extends NK> keyFunction, Function<? super ElemType, ? extends NV> valueFunction,
            R target) {
        return list.toMap(keyFunction, valueFunction, target);
    }

    @Override
    public <NK, NV> MutableSortedMap<NK, NV> toSortedMap(Function<? super ElemType, ? extends NK> keyFunction,
            Function<? super ElemType, ? extends NV> valueFunction) {
        return list.toSortedMap(keyFunction, valueFunction);
    }

    @Override
    public <NK, NV> MutableSortedMap<NK, NV> toSortedMap(Comparator<? super NK> comparator, Function<? super ElemType, ? extends NK> keyFunction,
            Function<? super ElemType, ? extends NV> valueFunction) {
        return list.toSortedMap(comparator, keyFunction, valueFunction);
    }

    @Override
    public <KK extends Comparable<? super KK>, NK, NV> MutableSortedMap<NK, NV> toSortedMapBy(Function<? super NK, KK> sortBy,
            Function<? super ElemType, ? extends NK> keyFunction, Function<? super ElemType, ? extends NV> valueFunction) {
        return list.toSortedMapBy(sortBy, keyFunction, valueFunction);
    }

    @Override
    public <NK, NV> MutableBiMap<NK, NV> toBiMap(Function<? super ElemType, ? extends NK> keyFunction, Function<? super ElemType, ? extends NV> valueFunction) {
        return list.toBiMap(keyFunction, valueFunction);
    }

    @Override
    public LazyIterable<ElemType> asLazy() {
        return list.asLazy();
    }

    @Override
    public ElemType min(Comparator<? super ElemType> comparator) {
        return list.min(comparator);
    }

    @Override
    public ElemType max(Comparator<? super ElemType> comparator) {
        return list.max(comparator);
    }

    @Override
    public Optional<ElemType> minOptional(Comparator<? super ElemType> comparator) {
        return list.minOptional(comparator);
    }

    @Override
    public Optional<ElemType> maxOptional(Comparator<? super ElemType> comparator) {
        return list.maxOptional(comparator);
    }

    @Override
    public ElemType min() {
        return list.min();
    }

    @Override
    public ElemType max() {
        return list.max();
    }

    @Override
    public Optional<ElemType> minOptional() {
        return list.minOptional();
    }

    @Override
    public Optional<ElemType> maxOptional() {
        return list.maxOptional();
    }

    @Override
    public <V extends Comparable<? super V>> ElemType minBy(Function<? super ElemType, ? extends V> function) {
        return list.minBy(function);
    }

    @Override
    public <V extends Comparable<? super V>> ElemType maxBy(Function<? super ElemType, ? extends V> function) {
        return list.maxBy(function);
    }

    @Override
    public <V extends Comparable<? super V>> Optional<ElemType> minByOptional(Function<? super ElemType, ? extends V> function) {
        return list.minByOptional(function);
    }

    @Override
    public <V extends Comparable<? super V>> Optional<ElemType> maxByOptional(Function<? super ElemType, ? extends V> function) {
        return list.maxByOptional(function);
    }

    @Override
    public long sumOfInt(IntFunction<? super ElemType> function) {
        return list.sumOfInt(function);
    }

    @Override
    public double sumOfFloat(FloatFunction<? super ElemType> function) {
        return list.sumOfFloat(function);
    }

    @Override
    public long sumOfLong(LongFunction<? super ElemType> function) {
        return list.sumOfLong(function);
    }

    @Override
    public double sumOfDouble(DoubleFunction<? super ElemType> function) {
        return list.sumOfDouble(function);
    }

    @Override
    public IntSummaryStatistics summarizeInt(IntFunction<? super ElemType> function) {
        return list.summarizeInt(function);
    }

    @Override
    public DoubleSummaryStatistics summarizeFloat(FloatFunction<? super ElemType> function) {
        return list.summarizeFloat(function);
    }

    @Override
    public LongSummaryStatistics summarizeLong(LongFunction<? super ElemType> function) {
        return list.summarizeLong(function);
    }

    @Override
    public DoubleSummaryStatistics summarizeDouble(DoubleFunction<? super ElemType> function) {
        return list.summarizeDouble(function);
    }

    @Override
    public <R, A> R reduceInPlace(Collector<? super ElemType, A, R> collector) {
        return list.reduceInPlace(collector);
    }

    @Override
    public <R> R reduceInPlace(Supplier<R> supplier, BiConsumer<R, ? super ElemType> accumulator) {
        return list.reduceInPlace(supplier, accumulator);
    }

    @Override
    public Optional<ElemType> reduce(BinaryOperator<ElemType> accumulator) {
        return list.reduce(accumulator);
    }

    @Override
    public String makeString() {
        return list.makeString();
    }

    @Override
    public String makeString(String separator) {
        return list.makeString(separator);
    }

    @Override
    public String makeString(String start, String separator, String end) {
        return list.makeString(start, separator, end);
    }

    @Override
    public void appendString(Appendable appendable) {
        list.appendString(appendable);
    }

    @Override
    public void appendString(Appendable appendable, String separator) {
        list.appendString(appendable, separator);
    }

    @Override
    public void appendString(Appendable appendable, String start, String separator, String end) {
        list.appendString(appendable, start, separator, end);
    }

    @Override
    public <V, R extends MutableBagIterable<V>> R countBy(Function<? super ElemType, ? extends V> function, R target) {
        return list.countBy(function, target);
    }

    @Override
    public <V, P, R extends MutableBagIterable<V>> R countByWith(Function2<? super ElemType, ? super P, ? extends V> function, P parameter, R target) {
        return list.countByWith(function, parameter, target);
    }

    @Override
    public <V, R extends MutableBagIterable<V>> R countByEach(Function<? super ElemType, ? extends Iterable<V>> function, R target) {
        return list.countByEach(function, target);
    }

    @Override
    public <V, R extends MutableMultimap<V, ElemType>> R groupBy(Function<? super ElemType, ? extends V> function, R target) {
        return list.groupBy(function, target);
    }

    @Override
    public <V, R extends MutableMultimap<V, ElemType>> R groupByEach(Function<? super ElemType, ? extends Iterable<V>> function, R target) {
        return list.groupByEach(function, target);
    }

    @Override
    public <V, R extends MutableMapIterable<V, ElemType>> R groupByUniqueKey(Function<? super ElemType, ? extends V> function, R target) {
        return list.groupByUniqueKey(function, target);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public <S, R extends Collection<Pair<ElemType, S>>> R zip(Iterable<S> that, R target) {
        return list.zip(that, target);
    }

    @Override
    public <R extends Collection<Pair<ElemType, Integer>>> R zipWithIndex(R target) {
        return list.zipWithIndex(target);
    }

    @Override
    public RichIterable<RichIterable<ElemType>> chunk(int size) {
        return list.chunk(size);
    }

    @Override
    public <K, V, R extends MutableMapIterable<K, V>> R aggregateBy(Function<? super ElemType, ? extends K> groupBy, Function0<? extends V> zeroValueFactory,
            Function2<? super V, ? super ElemType, ? extends V> nonMutatingAggregator, R target) {
        return list.aggregateBy(groupBy, zeroValueFactory, nonMutatingAggregator, target);
    }

    @Override
    public <K, V, R extends MutableMultimap<K, V>> R groupByAndCollect(Function<? super ElemType, ? extends K> groupByFunction,
            Function<? super ElemType, ? extends V> collectFunction, R target) {
        return list.groupByAndCollect(groupByFunction, collectFunction, target);
    }

}
