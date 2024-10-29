package assn05;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class MaxBinHeapER<V, P extends Comparable<P>> implements BinaryHeap<V, P> {

    private List<Prioritized<V, P>> _heap;

    /**
     * Constructor that creates an empty heap of hospital.Prioritized objects.
     */
    public MaxBinHeapER() {
        _heap = new ArrayList<>();
    }

    @Override
    public int size() {
        return _heap.size();
    }

    // TODO (Task 2A): enqueue
    public void enqueue(V value) {
        // Create new hospital.Prioritized object and insert it into the heap
        Prioritized<V, P> intake = new Patient(value);

        _heap.add(intake);
        bubbleUp(_heap.size() - 1);
        // don't need bubble down bc inserting at end, right?

    }

    // TODO (Task 2A): enqueue
    @Override
    public void enqueue(V value, P priority) { // Create new hospital.Prioritized object and insert it into the heap
        Prioritized<V, P> intake = new Patient(value, priority);

        // adds to end regardless of length
        _heap.add(intake);
        bubbleUp(_heap.size() - 1);
        // don't need bubble down bc inserting at end, right?
    }
    @Override
    public V dequeue() {
        // Remove the element with the largest priority from the heap and return its value
        if (_heap.isEmpty()) {
            return null;
        }
        V maxValue = _heap.get(0).getValue();
        // Prioritized<V,P> maxPriority = new Patient(_heap.get(0).getValue(), _heap.get(0).getPriority());
        if (_heap.size() == 1) {
            return _heap.remove(0).getValue();
        }
        // top swaps with biggest child, bubble down
        _heap.set(0, _heap.remove(_heap.size() - 1)); // move last node to root place
        bubbleDown(0); // start idx is 0
        return maxValue;

    }
    @Override
    public V getMax() {
        // return the largest value in the heap without removing it
        if (_heap.isEmpty()) {
            return null;
        }
        Prioritized<V, P> maxVal = _heap.get(0);
        return maxVal.getValue();
    }

    public void updatePriority(V value, P newPriority) {
        // search thru heap, find patient based on value.
        // if no patient: do nothing
        // if patient: update priority, then restructure

        int idx = -1;
        boolean update = false;
        Prioritized<V, P> updated = new Patient(value, newPriority);

        for (int i = 0; i < _heap.size(); i++) {
            if (value.equals(_heap.get(i).getValue())) {
                _heap.set(i, updated);
                idx = i;
                update = true;
            }
        }
        if (update) {
            if (updated.compareTo(_heap.get(parent(idx))) < 0) {
                // child < parent, bubble down
                bubbleDown(idx);
            } else {
                bubbleUp(idx);
            }
        }
    }

    /**
     * Constructor that builds a heap given an initial array of hospital.Prioritized objects.
     */
    public MaxBinHeapER(Prioritized<V, P>[] initialEntries) {
        _heap = new ArrayList<>();
        for (int i = 0; i < initialEntries.length; i++) {
            enqueue(initialEntries[i].getValue(), initialEntries[i].getPriority());
        }
    }

    @Override
    public Prioritized<V, P>[] getAsArray() {
        Prioritized<V, P>[] result = (Prioritized<V, P>[]) Array.newInstance(Prioritized.class, size());
        return _heap.toArray(result);
    }

    private int parent(int i) {
        // returns index val of parent
        return (i - 1) / 2;
    }

    private int rc(int i) {
        // returns index val of right child
        return i * 2 + 2;
    }

    private int lc(int i) {
        // returns index val of left child
        return i * 2 + 1;
    }

    private void bubbleDown(int i) {
        // i = start index
        boolean incomplete = true;
        while (incomplete) {
            if (i > _heap.size() - 1) {
                incomplete = false;
                break;
            }
            Prioritized<V, P> current = new Patient(_heap.get(i).getValue(), _heap.get(i).getPriority());

            Prioritized<V, P> left = null;
            Prioritized<V, P> right = null;

            if (lc(i) > _heap.size() - 1 && rc(i) > _heap.size() - 1) {
                incomplete = false;
                break;
            }
            if (lc(i) < _heap.size()) {
                left = _heap.get(lc(i));
            }
            if (rc(i) < _heap.size()) {
                right = _heap.get(rc(i));
            }

            // if both nodes exist:
            if (left != null && right != null) {
                if (left.compareTo(right) >= 0) { // if left bigger than right
                    if (left.compareTo(current) > 0) { // if left bigger than dad
                        _heap.set(i, left);
                        _heap.set(lc(i), current);
                        i = lc(i);
                    } else {
                        incomplete = false;
                    }
                } else if (left.compareTo(right) < 0) { // if right bigger than left
                    if (right.compareTo(current) > 0) { // if right bigger than dad
                        _heap.set(i, right);
                        _heap.set(rc(i), current);
                        i = rc(i);
                    } else {
                        incomplete = false;
                    }
                }
            } else if (left != null) { // if left exists but right doesn't
                if (left.compareTo(current) > 0) { // if left > dad
                    _heap.set(i, left);
                    _heap.set(lc(i), current);
                    i = lc(i);
                } else {
                    incomplete = false;
                }
            } // not possible to have RC and no LC so no need to check that
            else { // no kids just in case
                incomplete = false;
            }
        }
    }

    private void bubbleUp(int i) {
        // i = starting idx

        if (i == 0) {
            return;
        }
        if (_heap.get(i).getPriority().compareTo(_heap.get(parent(i)).getPriority()) > 0) {
            // swap em
            Prioritized<V, P> parent = _heap.get(parent(i));
            _heap.set(parent(i), _heap.get(i));
            _heap.set(i, parent);
            bubbleUp(parent(i));
        }
    }
}
