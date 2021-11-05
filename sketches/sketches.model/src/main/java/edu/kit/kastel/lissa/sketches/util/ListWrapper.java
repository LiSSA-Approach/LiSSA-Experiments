package edu.kit.kastel.lissa.sketches.util;

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
		return wrap(Lists.mutable.empty());
	}

	public static <S extends Serializable> ListWrapper<S> wrap(MutableList<S> list) {
		if (list instanceof ListWrapper) {
			return (ListWrapper<S>) list;
		}
		return new ListWrapper<>(list);
	}

	private ListWrapper(MutableList<ElemType> list) {
		this.list = list;
	}

	@Override
	public void forEach(Consumer<? super ElemType> action) {
		this.list.forEach(action);
	}

	@Override
	public void forEachWithIndex(ObjectIntProcedure<? super ElemType> objectIntProcedure) {
		this.list.forEachWithIndex(objectIntProcedure);
	}

	@Override
	public MutableList<ElemType> with(ElemType element) {
		return this.list.with(element);
	}

	@Override
	public void reverseForEach(Procedure<? super ElemType> procedure) {
		this.list.reverseForEach(procedure);
	}

	@Override
	public MutableList<ElemType> without(ElemType element) {
		return this.list.without(element);
	}

	@Override
	public MutableList<ElemType> withAll(Iterable<? extends ElemType> elements) {
		return this.list.withAll(elements);
	}

	@Override
	public <P> void forEachWith(Procedure2<? super ElemType, ? super P> procedure, P parameter) {
		this.list.forEachWith(procedure, parameter);
	}

	@Override
	public MutableList<ElemType> withoutAll(Iterable<? extends ElemType> elements) {
		return this.list.withoutAll(elements);
	}

	@Override
	public void reverseForEachWithIndex(ObjectIntProcedure<? super ElemType> procedure) {
		this.list.reverseForEachWithIndex(procedure);
	}

	@Override
	public MutableList<ElemType> newEmpty() {
		return this.list.newEmpty();
	}

	@Override
	public MutableList<ElemType> clone() {
		return this.list.clone();
	}

	@Override
	public MutableList<ElemType> tap(Procedure<? super ElemType> procedure) {
		return this.list.tap(procedure);
	}

	@Override
	public MutableList<ElemType> select(Predicate<? super ElemType> predicate) {
		return this.list.select(predicate);
	}

	@Override
	public Optional<ElemType> getFirstOptional() {
		return this.list.getFirstOptional();
	}

	@Override
	public <P> MutableList<ElemType> selectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
		return this.list.selectWith(predicate, parameter);
	}

	@Override
	public LazyIterable<ElemType> asReversed() {
		return this.list.asReversed();
	}

	@Override
	public MutableList<ElemType> reject(Predicate<? super ElemType> predicate) {
		return this.list.reject(predicate);
	}

	@Override
	public <P> MutableList<ElemType> rejectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
		return this.list.rejectWith(predicate, parameter);
	}

	@Override
	public int detectLastIndex(Predicate<? super ElemType> predicate) {
		return this.list.detectLastIndex(predicate);
	}

	@Override
	public PartitionMutableList<ElemType> partition(Predicate<? super ElemType> predicate) {
		return this.list.partition(predicate);
	}

	@Override
	public <P> PartitionMutableList<ElemType> partitionWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
		return this.list.partitionWith(predicate, parameter);
	}

	@Override
	public <S> MutableList<S> selectInstancesOf(Class<S> clazz) {
		return this.list.selectInstancesOf(clazz);
	}

	@Override
	public <V> MutableList<V> collect(Function<? super ElemType, ? extends V> function) {
		return this.list.collect(function);
	}

	@Override
	public void forEach(Procedure<? super ElemType> procedure) {
		this.list.forEach(procedure);
	}

	@Override
	public <V> MutableList<V> collectWithIndex(ObjectIntToObjectFunction<? super ElemType, ? extends V> function) {
		return this.list.collectWithIndex(function);
	}

	@Override
	public Optional<ElemType> getLastOptional() {
		return this.list.getLastOptional();
	}

	@Override
	public MutableBooleanList collectBoolean(BooleanFunction<? super ElemType> booleanFunction) {
		return this.list.collectBoolean(booleanFunction);
	}

	@Override
	public boolean notEmpty() {
		return this.list.notEmpty();
	}

	@Override
	public MutableByteList collectByte(ByteFunction<? super ElemType> byteFunction) {
		return this.list.collectByte(byteFunction);
	}

	@Override
	public MutableCharList collectChar(CharFunction<? super ElemType> charFunction) {
		return this.list.collectChar(charFunction);
	}

	@Override
	public ElemType getAny() {
		return this.list.getAny();
	}

	@Override
	public MutableDoubleList collectDouble(DoubleFunction<? super ElemType> doubleFunction) {
		return this.list.collectDouble(doubleFunction);
	}

	@Override
	public MutableFloatList collectFloat(FloatFunction<? super ElemType> floatFunction) {
		return this.list.collectFloat(floatFunction);
	}

	@Override
	public ElemType getFirst() {
		return this.list.getFirst();
	}

	@Override
	public MutableIntList collectInt(IntFunction<? super ElemType> intFunction) {
		return this.list.collectInt(intFunction);
	}

	@Override
	public MutableLongList collectLong(LongFunction<? super ElemType> longFunction) {
		return this.list.collectLong(longFunction);
	}

	@Override
	public MutableShortList collectShort(ShortFunction<? super ElemType> shortFunction) {
		return this.list.collectShort(shortFunction);
	}

	@Override
	public <P, V> MutableList<V> collectWith(Function2<? super ElemType, ? super P, ? extends V> function, P parameter) {
		return this.list.collectWith(function, parameter);
	}

	@Override
	public <V> MutableList<V> collectIf(Predicate<? super ElemType> predicate, Function<? super ElemType, ? extends V> function) {
		return this.list.collectIf(predicate, function);
	}

	@Override
	public <V> MutableList<V> flatCollect(Function<? super ElemType, ? extends Iterable<V>> function) {
		return this.list.flatCollect(function);
	}

	@Override
	public ElemType getLast() {
		return this.list.getLast();
	}

	@Override
	public <P, V> MutableList<V> flatCollectWith(Function2<? super ElemType, ? super P, ? extends Iterable<V>> function, P parameter) {
		return this.list.flatCollectWith(function, parameter);
	}

	@Override
	public <S> boolean corresponds(OrderedIterable<S> other, Predicate2<? super ElemType, ? super S> predicate) {
		return this.list.corresponds(other, predicate);
	}

	@Override
	public MutableList<ElemType> distinct() {
		return this.list.distinct();
	}

	@Override
	public MutableList<ElemType> distinct(HashingStrategy<? super ElemType> hashingStrategy) {
		return this.list.distinct(hashingStrategy);
	}

	@Override
	public ElemType getOnly() {
		return this.list.getOnly();
	}

	@Override
	public <V> MutableList<ElemType> distinctBy(Function<? super ElemType, ? extends V> function) {
		return this.list.distinctBy(function);
	}

	@Override
	public void forEach(int startIndex, int endIndex, Procedure<? super ElemType> procedure) {
		this.list.forEach(startIndex, endIndex, procedure);
	}

	@Override
	public MutableList<ElemType> sortThis(Comparator<? super ElemType> comparator) {
		return this.list.sortThis(comparator);
	}

	@Override
	public MutableList<ElemType> sortThis() {
		return this.list.sortThis();
	}

	@Override
	public <V> boolean containsBy(Function<? super ElemType, ? extends V> function, V value) {
		return this.list.containsBy(function, value);
	}

	@Override
	public <V extends Comparable<? super V>> MutableList<ElemType> sortThisBy(Function<? super ElemType, ? extends V> function) {
		return this.list.sortThisBy(function);
	}

	@Override
	public MutableList<ElemType> sortThisByInt(IntFunction<? super ElemType> function) {
		return this.list.sortThisByInt(function);
	}

	@Override
	public MutableList<ElemType> sortThisByBoolean(BooleanFunction<? super ElemType> function) {
		return this.list.sortThisByBoolean(function);
	}

	@Override
	public boolean containsAllIterable(Iterable<?> source) {
		return this.list.containsAllIterable(source);
	}

	@Override
	public MutableList<ElemType> sortThisByChar(CharFunction<? super ElemType> function) {
		return this.list.sortThisByChar(function);
	}

	@Override
	public void forEachWithIndex(int fromIndex, int toIndex, ObjectIntProcedure<? super ElemType> objectIntProcedure) {
		this.list.forEachWithIndex(fromIndex, toIndex, objectIntProcedure);
	}

	@Override
	public MutableList<ElemType> sortThisByByte(ByteFunction<? super ElemType> function) {
		return this.list.sortThisByByte(function);
	}

	@Override
	public MutableList<ElemType> sortThisByShort(ShortFunction<? super ElemType> function) {
		return this.list.sortThisByShort(function);
	}

	@Override
	public MutableList<ElemType> sortThisByFloat(FloatFunction<? super ElemType> function) {
		return this.list.sortThisByFloat(function);
	}

	@Override
	public boolean containsAllArguments(Object... elements) {
		return this.list.containsAllArguments(elements);
	}

	@Override
	public MutableList<ElemType> sortThisByLong(LongFunction<? super ElemType> function) {
		return this.list.sortThisByLong(function);
	}

	@Override
	public MutableList<ElemType> sortThisByDouble(DoubleFunction<? super ElemType> function) {
		return this.list.sortThisByDouble(function);
	}

	@Override
	public MutableList<ElemType> subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}

	@Override
	public MutableList<ElemType> asUnmodifiable() {
		return this.list.asUnmodifiable();
	}

	@Override
	public MutableStack<ElemType> toStack() {
		return this.list.toStack();
	}

	@Override
	public ParallelListIterable<ElemType> asParallel(ExecutorService executorService, int batchSize) {
		return this.list.asParallel(executorService, batchSize);
	}

	@Override
	public MutableList<ElemType> asSynchronized() {
		return this.list.asSynchronized();
	}

	@Override
	public ImmutableList<ElemType> toImmutable() {
		return this.list.toImmutable();
	}

	@Override
	public int binarySearch(ElemType key, Comparator<? super ElemType> comparator) {
		return this.list.binarySearch(key, comparator);
	}

	@Override
	public <V> MutableListMultimap<V, ElemType> groupBy(Function<? super ElemType, ? extends V> function) {
		return this.list.groupBy(function);
	}

	@Override
	public <V> MutableListMultimap<V, ElemType> groupByEach(Function<? super ElemType, ? extends Iterable<V>> function) {
		return this.list.groupByEach(function);
	}

	@Override
	public void each(Procedure<? super ElemType> procedure) {
		this.list.each(procedure);
	}

	@Override
	public <S> MutableList<Pair<ElemType, S>> zip(Iterable<S> that) {
		return this.list.zip(that);
	}

	@Override
	public MutableList<Pair<ElemType, Integer>> zipWithIndex() {
		return this.list.zipWithIndex();
	}

	@Override
	public int binarySearch(ElemType key) {
		return this.list.binarySearch(key);
	}

	@Override
	public MutableList<ElemType> take(int count) {
		return this.list.take(count);
	}

	@Override
	public MutableList<ElemType> takeWhile(Predicate<? super ElemType> predicate) {
		return this.list.takeWhile(predicate);
	}

	@Override
	public MutableList<ElemType> drop(int count) {
		return this.list.drop(count);
	}

	@Override
	public MutableList<ElemType> dropWhile(Predicate<? super ElemType> predicate) {
		return this.list.dropWhile(predicate);
	}

	@Override
	public PartitionMutableList<ElemType> partitionWhile(Predicate<? super ElemType> predicate) {
		return this.list.partitionWhile(predicate);
	}

	@Override
	public MutableList<ElemType> toReversed() {
		return this.list.toReversed();
	}

	@Override
	public MutableList<ElemType> reverseThis() {
		return this.list.reverseThis();
	}

	@Override
	public MutableList<ElemType> shuffleThis() {
		return this.list.shuffleThis();
	}

	@Override
	public <T2> void forEachInBoth(ListIterable<T2> other, Procedure2<? super ElemType, ? super T2> procedure) {
		this.list.forEachInBoth(other, procedure);
	}

	@Override
	public MutableList<ElemType> shuffleThis(Random random) {
		return this.list.shuffleThis(random);
	}

	@Override
	public <P> Twin<MutableList<ElemType>> selectAndRejectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
		return this.list.selectAndRejectWith(predicate, parameter);
	}

	@Override
	public <V, R extends Collection<V>> R collectWithIndex(ObjectIntToObjectFunction<? super ElemType, ? extends V> function, R target) {
		return this.list.collectWithIndex(function, target);
	}

	@Override
	public <R extends Collection<ElemType>> R select(Predicate<? super ElemType> predicate, R target) {
		return this.list.select(predicate, target);
	}

	@Override
	public boolean removeIf(Predicate<? super ElemType> predicate) {
		return this.list.removeIf(predicate);
	}

	@Override
	public <P> boolean removeIfWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
		return this.list.removeIfWith(predicate, parameter);
	}

	@Override
	public int detectIndex(Predicate<? super ElemType> predicate) {
		return this.list.detectIndex(predicate);
	}

	@Override
	public <P, R extends Collection<ElemType>> R selectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter, R targetCollection) {
		return this.list.selectWith(predicate, parameter, targetCollection);
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return this.list.contains(o);
	}

	@Override
	public Iterator<ElemType> iterator() {
		return this.list.iterator();
	}

	@Override
	public boolean addAll(int index, Collection<? extends ElemType> c) {
		return this.list.addAll(index, c);
	}

	@Override
	public Object[] toArray() {
		return this.list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.list.toArray(a);
	}

	@Override
	public <R extends Collection<ElemType>> R reject(Predicate<? super ElemType> predicate, R target) {
		return this.list.reject(predicate, target);
	}

	@Override
	public <IV, P> IV injectIntoWith(IV injectValue, Function3<? super IV, ? super ElemType, ? super P, ? extends IV> function, P parameter) {
		return this.list.injectIntoWith(injectValue, function, parameter);
	}

	@Override
	public <P, R extends Collection<ElemType>> R rejectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter, R targetCollection) {
		return this.list.rejectWith(predicate, parameter, targetCollection);
	}

	@Override
	public void replaceAll(UnaryOperator<ElemType> operator) {
		this.list.replaceAll(operator);
	}

	@Override
	public <T> T[] toArray(java.util.function.IntFunction<T[]> generator) {
		return this.list.toArray(generator);
	}

	@Override
	public <V> MutableObjectLongMap<V> sumByInt(Function<? super ElemType, ? extends V> groupBy, IntFunction<? super ElemType> function) {
		return this.list.sumByInt(groupBy, function);
	}

	@Override
	public <V> MutableObjectDoubleMap<V> sumByFloat(Function<? super ElemType, ? extends V> groupBy, FloatFunction<? super ElemType> function) {
		return this.list.sumByFloat(groupBy, function);
	}

	@Override
	public <V> MutableObjectLongMap<V> sumByLong(Function<? super ElemType, ? extends V> groupBy, LongFunction<? super ElemType> function) {
		return this.list.sumByLong(groupBy, function);
	}

	@Override
	public <V> MutableObjectDoubleMap<V> sumByDouble(Function<? super ElemType, ? extends V> groupBy, DoubleFunction<? super ElemType> function) {
		return this.list.sumByDouble(groupBy, function);
	}

	@Override
	public <V> MutableBag<V> countBy(Function<? super ElemType, ? extends V> function) {
		return this.list.countBy(function);
	}

	@Override
	public <V, P> MutableBag<V> countByWith(Function2<? super ElemType, ? super P, ? extends V> function, P parameter) {
		return this.list.countByWith(function, parameter);
	}

	@Override
	public void sort(Comparator<? super ElemType> c) {
		this.list.sort(c);
	}

	@Override
	public <V> MutableBag<V> countByEach(Function<? super ElemType, ? extends Iterable<V>> function) {
		return this.list.countByEach(function);
	}

	@Override
	public boolean add(ElemType e) {
		return this.list.add(e);
	}

	@Override
	public <V> MutableMap<V, ElemType> groupByUniqueKey(Function<? super ElemType, ? extends V> function) {
		return this.list.groupByUniqueKey(function);
	}

	@Override
	public boolean addAllIterable(Iterable<? extends ElemType> iterable) {
		return this.list.addAllIterable(iterable);
	}

	@Override
	public boolean removeAllIterable(Iterable<?> iterable) {
		return this.list.removeAllIterable(iterable);
	}

	@Override
	public boolean retainAllIterable(Iterable<?> iterable) {
		return this.list.retainAllIterable(iterable);
	}

	@Override
	public <K, V> MutableMap<K, V> aggregateInPlaceBy(Function<? super ElemType, ? extends K> groupBy, Function0<? extends V> zeroValueFactory,
			Procedure2<? super V, ? super ElemType> mutatingAggregator) {
		return this.list.aggregateInPlaceBy(groupBy, zeroValueFactory, mutatingAggregator);
	}

	@Override
	public <K, V> MutableMap<K, V> aggregateBy(Function<? super ElemType, ? extends K> groupBy, Function0<? extends V> zeroValueFactory,
			Function2<? super V, ? super ElemType, ? extends V> nonMutatingAggregator) {
		return this.list.aggregateBy(groupBy, zeroValueFactory, nonMutatingAggregator);
	}

	@Override
	public boolean remove(Object o) {
		return this.list.remove(o);
	}

	@Override
	public <V, R extends Collection<V>> R collect(Function<? super ElemType, ? extends V> function, R target) {
		return this.list.collect(function, target);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends ElemType> c) {
		return this.list.addAll(c);
	}

	@Override
	public <R extends MutableBooleanCollection> R collectBoolean(BooleanFunction<? super ElemType> booleanFunction, R target) {
		return this.list.collectBoolean(booleanFunction, target);
	}

	@Override
	public ElemType get(int index) {
		return this.list.get(index);
	}

	@Override
	public ElemType set(int index, ElemType element) {
		return this.list.set(index, element);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.list.removeAll(c);
	}

	@Override
	public void add(int index, ElemType element) {
		this.list.add(index, element);
	}

	@Override
	public <R extends MutableByteCollection> R collectByte(ByteFunction<? super ElemType> byteFunction, R target) {
		return this.list.collectByte(byteFunction, target);
	}

	@Override
	public ElemType remove(int index) {
		return this.list.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	@Override
	public <R extends MutableCharCollection> R collectChar(CharFunction<? super ElemType> charFunction, R target) {
		return this.list.collectChar(charFunction, target);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.list.retainAll(c);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	@Override
	public void clear() {
		this.list.clear();
	}

	@Override
	public ListIterator<ElemType> listIterator() {
		return this.list.listIterator();
	}

	@Override
	public boolean equals(Object o) {
		return this.list.equals(o);
	}

	@Override
	public ListIterator<ElemType> listIterator(int index) {
		return this.list.listIterator(index);
	}

	@Override
	public <R extends MutableDoubleCollection> R collectDouble(DoubleFunction<? super ElemType> doubleFunction, R target) {
		return this.list.collectDouble(doubleFunction, target);
	}

	@Override
	public int hashCode() {
		return this.list.hashCode();
	}

	@Override
	public <R extends MutableFloatCollection> R collectFloat(FloatFunction<? super ElemType> floatFunction, R target) {
		return this.list.collectFloat(floatFunction, target);
	}

	@Override
	public Spliterator<ElemType> spliterator() {
		return this.list.spliterator();
	}

	@Override
	public <R extends MutableIntCollection> R collectInt(IntFunction<? super ElemType> intFunction, R target) {
		return this.list.collectInt(intFunction, target);
	}

	@Override
	public Stream<ElemType> stream() {
		return this.list.stream();
	}

	@Override
	public Stream<ElemType> parallelStream() {
		return this.list.parallelStream();
	}

	@Override
	public <R extends MutableLongCollection> R collectLong(LongFunction<? super ElemType> longFunction, R target) {
		return this.list.collectLong(longFunction, target);
	}

	@Override
	public <R extends MutableShortCollection> R collectShort(ShortFunction<? super ElemType> shortFunction, R target) {
		return this.list.collectShort(shortFunction, target);
	}

	@Override
	public <P, V, R extends Collection<V>> R collectWith(Function2<? super ElemType, ? super P, ? extends V> function, P parameter, R targetCollection) {
		return this.list.collectWith(function, parameter, targetCollection);
	}

	@Override
	public <V, R extends Collection<V>> R collectIf(Predicate<? super ElemType> predicate, Function<? super ElemType, ? extends V> function, R target) {
		return this.list.collectIf(predicate, function, target);
	}

	@Override
	public <R extends MutableByteCollection> R flatCollectByte(Function<? super ElemType, ? extends ByteIterable> function, R target) {
		return this.list.flatCollectByte(function, target);
	}

	@Override
	public <R extends MutableCharCollection> R flatCollectChar(Function<? super ElemType, ? extends CharIterable> function, R target) {
		return this.list.flatCollectChar(function, target);
	}

	@Override
	public <R extends MutableIntCollection> R flatCollectInt(Function<? super ElemType, ? extends IntIterable> function, R target) {
		return this.list.flatCollectInt(function, target);
	}

	@Override
	public <R extends MutableShortCollection> R flatCollectShort(Function<? super ElemType, ? extends ShortIterable> function, R target) {
		return this.list.flatCollectShort(function, target);
	}

	@Override
	public <R extends MutableDoubleCollection> R flatCollectDouble(Function<? super ElemType, ? extends DoubleIterable> function, R target) {
		return this.list.flatCollectDouble(function, target);
	}

	@Override
	public <R extends MutableFloatCollection> R flatCollectFloat(Function<? super ElemType, ? extends FloatIterable> function, R target) {
		return this.list.flatCollectFloat(function, target);
	}

	@Override
	public <R extends MutableLongCollection> R flatCollectLong(Function<? super ElemType, ? extends LongIterable> function, R target) {
		return this.list.flatCollectLong(function, target);
	}

	@Override
	public <R extends MutableBooleanCollection> R flatCollectBoolean(Function<? super ElemType, ? extends BooleanIterable> function, R target) {
		return this.list.flatCollectBoolean(function, target);
	}

	@Override
	public <V, R extends Collection<V>> R flatCollect(Function<? super ElemType, ? extends Iterable<V>> function, R target) {
		return this.list.flatCollect(function, target);
	}

	@Override
	public <P, V, R extends Collection<V>> R flatCollectWith(Function2<? super ElemType, ? super P, ? extends Iterable<V>> function, P parameter, R target) {
		return this.list.flatCollectWith(function, parameter, target);
	}

	@Override
	public ElemType detect(Predicate<? super ElemType> predicate) {
		return this.list.detect(predicate);
	}

	@Override
	public <P> ElemType detectWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
		return this.list.detectWith(predicate, parameter);
	}

	@Override
	public Optional<ElemType> detectOptional(Predicate<? super ElemType> predicate) {
		return this.list.detectOptional(predicate);
	}

	@Override
	public <P> Optional<ElemType> detectWithOptional(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
		return this.list.detectWithOptional(predicate, parameter);
	}

	@Override
	public ElemType detectIfNone(Predicate<? super ElemType> predicate, Function0<? extends ElemType> function) {
		return this.list.detectIfNone(predicate, function);
	}

	@Override
	public <P> ElemType detectWithIfNone(Predicate2<? super ElemType, ? super P> predicate, P parameter, Function0<? extends ElemType> function) {
		return this.list.detectWithIfNone(predicate, parameter, function);
	}

	@Override
	public int count(Predicate<? super ElemType> predicate) {
		return this.list.count(predicate);
	}

	@Override
	public <P> int countWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
		return this.list.countWith(predicate, parameter);
	}

	@Override
	public boolean anySatisfy(Predicate<? super ElemType> predicate) {
		return this.list.anySatisfy(predicate);
	}

	@Override
	public <P> boolean anySatisfyWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
		return this.list.anySatisfyWith(predicate, parameter);
	}

	@Override
	public boolean allSatisfy(Predicate<? super ElemType> predicate) {
		return this.list.allSatisfy(predicate);
	}

	@Override
	public <P> boolean allSatisfyWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
		return this.list.allSatisfyWith(predicate, parameter);
	}

	@Override
	public boolean noneSatisfy(Predicate<? super ElemType> predicate) {
		return this.list.noneSatisfy(predicate);
	}

	@Override
	public <P> boolean noneSatisfyWith(Predicate2<? super ElemType, ? super P> predicate, P parameter) {
		return this.list.noneSatisfyWith(predicate, parameter);
	}

	@Override
	public <IV> IV injectInto(IV injectedValue, Function2<? super IV, ? super ElemType, ? extends IV> function) {
		return this.list.injectInto(injectedValue, function);
	}

	@Override
	public int injectInto(int injectedValue, IntObjectToIntFunction<? super ElemType> function) {
		return this.list.injectInto(injectedValue, function);
	}

	@Override
	public long injectInto(long injectedValue, LongObjectToLongFunction<? super ElemType> function) {
		return this.list.injectInto(injectedValue, function);
	}

	@Override
	public float injectInto(float injectedValue, FloatObjectToFloatFunction<? super ElemType> function) {
		return this.list.injectInto(injectedValue, function);
	}

	@Override
	public double injectInto(double injectedValue, DoubleObjectToDoubleFunction<? super ElemType> function) {
		return this.list.injectInto(injectedValue, function);
	}

	@Override
	public <R extends Collection<ElemType>> R into(R target) {
		return this.list.into(target);
	}

	@Override
	public MutableList<ElemType> toList() {
		return this.list.toList();
	}

	@Override
	public MutableList<ElemType> toSortedList() {
		return this.list.toSortedList();
	}

	@Override
	public MutableList<ElemType> toSortedList(Comparator<? super ElemType> comparator) {
		return this.list.toSortedList(comparator);
	}

	@Override
	public <V extends Comparable<? super V>> MutableList<ElemType> toSortedListBy(Function<? super ElemType, ? extends V> function) {
		return this.list.toSortedListBy(function);
	}

	@Override
	public MutableSet<ElemType> toSet() {
		return this.list.toSet();
	}

	@Override
	public MutableSortedSet<ElemType> toSortedSet() {
		return this.list.toSortedSet();
	}

	@Override
	public MutableSortedSet<ElemType> toSortedSet(Comparator<? super ElemType> comparator) {
		return this.list.toSortedSet(comparator);
	}

	@Override
	public <V extends Comparable<? super V>> MutableSortedSet<ElemType> toSortedSetBy(Function<? super ElemType, ? extends V> function) {
		return this.list.toSortedSetBy(function);
	}

	@Override
	public MutableBag<ElemType> toBag() {
		return this.list.toBag();
	}

	@Override
	public MutableSortedBag<ElemType> toSortedBag() {
		return this.list.toSortedBag();
	}

	@Override
	public MutableSortedBag<ElemType> toSortedBag(Comparator<? super ElemType> comparator) {
		return this.list.toSortedBag(comparator);
	}

	@Override
	public <V extends Comparable<? super V>> MutableSortedBag<ElemType> toSortedBagBy(Function<? super ElemType, ? extends V> function) {
		return this.list.toSortedBagBy(function);
	}

	@Override
	public <NK, NV> MutableMap<NK, NV> toMap(Function<? super ElemType, ? extends NK> keyFunction, Function<? super ElemType, ? extends NV> valueFunction) {
		return this.list.toMap(keyFunction, valueFunction);
	}

	@Override
	public <NK, NV, R extends Map<NK, NV>> R toMap(Function<? super ElemType, ? extends NK> keyFunction, Function<? super ElemType, ? extends NV> valueFunction,
			R target) {
		return this.list.toMap(keyFunction, valueFunction, target);
	}

	@Override
	public <NK, NV> MutableSortedMap<NK, NV> toSortedMap(Function<? super ElemType, ? extends NK> keyFunction,
			Function<? super ElemType, ? extends NV> valueFunction) {
		return this.list.toSortedMap(keyFunction, valueFunction);
	}

	@Override
	public <NK, NV> MutableSortedMap<NK, NV> toSortedMap(Comparator<? super NK> comparator, Function<? super ElemType, ? extends NK> keyFunction,
			Function<? super ElemType, ? extends NV> valueFunction) {
		return this.list.toSortedMap(comparator, keyFunction, valueFunction);
	}

	@Override
	public <KK extends Comparable<? super KK>, NK, NV> MutableSortedMap<NK, NV> toSortedMapBy(Function<? super NK, KK> sortBy,
			Function<? super ElemType, ? extends NK> keyFunction, Function<? super ElemType, ? extends NV> valueFunction) {
		return this.list.toSortedMapBy(sortBy, keyFunction, valueFunction);
	}

	@Override
	public <NK, NV> MutableBiMap<NK, NV> toBiMap(Function<? super ElemType, ? extends NK> keyFunction, Function<? super ElemType, ? extends NV> valueFunction) {
		return this.list.toBiMap(keyFunction, valueFunction);
	}

	@Override
	public LazyIterable<ElemType> asLazy() {
		return this.list.asLazy();
	}

	@Override
	public ElemType min(Comparator<? super ElemType> comparator) {
		return this.list.min(comparator);
	}

	@Override
	public ElemType max(Comparator<? super ElemType> comparator) {
		return this.list.max(comparator);
	}

	@Override
	public Optional<ElemType> minOptional(Comparator<? super ElemType> comparator) {
		return this.list.minOptional(comparator);
	}

	@Override
	public Optional<ElemType> maxOptional(Comparator<? super ElemType> comparator) {
		return this.list.maxOptional(comparator);
	}

	@Override
	public ElemType min() {
		return this.list.min();
	}

	@Override
	public ElemType max() {
		return this.list.max();
	}

	@Override
	public Optional<ElemType> minOptional() {
		return this.list.minOptional();
	}

	@Override
	public Optional<ElemType> maxOptional() {
		return this.list.maxOptional();
	}

	@Override
	public <V extends Comparable<? super V>> ElemType minBy(Function<? super ElemType, ? extends V> function) {
		return this.list.minBy(function);
	}

	@Override
	public <V extends Comparable<? super V>> ElemType maxBy(Function<? super ElemType, ? extends V> function) {
		return this.list.maxBy(function);
	}

	@Override
	public <V extends Comparable<? super V>> Optional<ElemType> minByOptional(Function<? super ElemType, ? extends V> function) {
		return this.list.minByOptional(function);
	}

	@Override
	public <V extends Comparable<? super V>> Optional<ElemType> maxByOptional(Function<? super ElemType, ? extends V> function) {
		return this.list.maxByOptional(function);
	}

	@Override
	public long sumOfInt(IntFunction<? super ElemType> function) {
		return this.list.sumOfInt(function);
	}

	@Override
	public double sumOfFloat(FloatFunction<? super ElemType> function) {
		return this.list.sumOfFloat(function);
	}

	@Override
	public long sumOfLong(LongFunction<? super ElemType> function) {
		return this.list.sumOfLong(function);
	}

	@Override
	public double sumOfDouble(DoubleFunction<? super ElemType> function) {
		return this.list.sumOfDouble(function);
	}

	@Override
	public IntSummaryStatistics summarizeInt(IntFunction<? super ElemType> function) {
		return this.list.summarizeInt(function);
	}

	@Override
	public DoubleSummaryStatistics summarizeFloat(FloatFunction<? super ElemType> function) {
		return this.list.summarizeFloat(function);
	}

	@Override
	public LongSummaryStatistics summarizeLong(LongFunction<? super ElemType> function) {
		return this.list.summarizeLong(function);
	}

	@Override
	public DoubleSummaryStatistics summarizeDouble(DoubleFunction<? super ElemType> function) {
		return this.list.summarizeDouble(function);
	}

	@Override
	public <R, A> R reduceInPlace(Collector<? super ElemType, A, R> collector) {
		return this.list.reduceInPlace(collector);
	}

	@Override
	public <R> R reduceInPlace(Supplier<R> supplier, BiConsumer<R, ? super ElemType> accumulator) {
		return this.list.reduceInPlace(supplier, accumulator);
	}

	@Override
	public Optional<ElemType> reduce(BinaryOperator<ElemType> accumulator) {
		return this.list.reduce(accumulator);
	}

	@Override
	public String makeString() {
		return this.list.makeString();
	}

	@Override
	public String makeString(String separator) {
		return this.list.makeString(separator);
	}

	@Override
	public String makeString(String start, String separator, String end) {
		return this.list.makeString(start, separator, end);
	}

	@Override
	public void appendString(Appendable appendable) {
		this.list.appendString(appendable);
	}

	@Override
	public void appendString(Appendable appendable, String separator) {
		this.list.appendString(appendable, separator);
	}

	@Override
	public void appendString(Appendable appendable, String start, String separator, String end) {
		this.list.appendString(appendable, start, separator, end);
	}

	@Override
	public <V, R extends MutableBagIterable<V>> R countBy(Function<? super ElemType, ? extends V> function, R target) {
		return this.list.countBy(function, target);
	}

	@Override
	public <V, P, R extends MutableBagIterable<V>> R countByWith(Function2<? super ElemType, ? super P, ? extends V> function, P parameter, R target) {
		return this.list.countByWith(function, parameter, target);
	}

	@Override
	public <V, R extends MutableBagIterable<V>> R countByEach(Function<? super ElemType, ? extends Iterable<V>> function, R target) {
		return this.list.countByEach(function, target);
	}

	@Override
	public <V, R extends MutableMultimap<V, ElemType>> R groupBy(Function<? super ElemType, ? extends V> function, R target) {
		return this.list.groupBy(function, target);
	}

	@Override
	public <V, R extends MutableMultimap<V, ElemType>> R groupByEach(Function<? super ElemType, ? extends Iterable<V>> function, R target) {
		return this.list.groupByEach(function, target);
	}

	@Override
	public <V, R extends MutableMapIterable<V, ElemType>> R groupByUniqueKey(Function<? super ElemType, ? extends V> function, R target) {
		return this.list.groupByUniqueKey(function, target);
	}

	@Override
	public String toString() {
		return this.list.toString();
	}

	@Override
	public <S, R extends Collection<Pair<ElemType, S>>> R zip(Iterable<S> that, R target) {
		return this.list.zip(that, target);
	}

	@Override
	public <R extends Collection<Pair<ElemType, Integer>>> R zipWithIndex(R target) {
		return this.list.zipWithIndex(target);
	}

	@Override
	public RichIterable<RichIterable<ElemType>> chunk(int size) {
		return this.list.chunk(size);
	}

	@Override
	public <K, V, R extends MutableMapIterable<K, V>> R aggregateBy(Function<? super ElemType, ? extends K> groupBy, Function0<? extends V> zeroValueFactory,
			Function2<? super V, ? super ElemType, ? extends V> nonMutatingAggregator, R target) {
		return this.list.aggregateBy(groupBy, zeroValueFactory, nonMutatingAggregator, target);
	}

	@Override
	public <K, V, R extends MutableMultimap<K, V>> R groupByAndCollect(Function<? super ElemType, ? extends K> groupByFunction,
			Function<? super ElemType, ? extends V> collectFunction, R target) {
		return this.list.groupByAndCollect(groupByFunction, collectFunction, target);
	}

}
