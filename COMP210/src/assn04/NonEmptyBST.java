package assn04;

import java.util.LinkedList;
import java.util.Queue;

public class NonEmptyBST<T extends Comparable<T>> implements BST<T> {
    private T _element;
    private BST<T> _left;
    private BST<T> _right;

    public NonEmptyBST(T element) {
        _left = new EmptyBST<T>();
        _right = new EmptyBST<T>();
        _element = element;
    }

    @Override
    public BST<T> insert(T element) {
        if (element.compareTo(_element) > 0) {
            if (_right.isEmpty()) {
                _right = new NonEmptyBST<>(element);
            } else {
                _right.insert(element);
            }
        } else if (element.compareTo(_element) < 0) {
            if (_left.isEmpty()) {
                _left = new NonEmptyBST<>(element);
            } else {
                _left.insert(element);
            }
        }
        return this;
    }

    // TODO: remove
    @Override
    public BST<T> remove(T element) {

        // these first two if statements move us to the element that we want to remove
        if (element.compareTo(_element) < 0) {
            // if elem we're inserting is smaller than current node
            _left = _left.remove(element);
        } else if (element.compareTo(_element) > 0) {
            // if elem we're inserting is greater than current node
            _right = _right.remove(element);
        } else {
            if (_right.isEmpty()) {
                return _left;
            } else if (_left.isEmpty()) {
                return _right;
            } else { // has two kids or none
                // smallest from right subtree
                _element = _right.findMin();
                _right = _right.remove(_element);
            }
        }
        return this;
    }


    @Override
    public BST<T> remove_range(T start, T end) {
        if (isEmpty()) {
            return this;
        }
        NonEmptyBST<T> BST = (NonEmptyBST<T>) this;
        _left = _left.remove_range(start, end);
        _right = _right.remove_range(start, end);

        if ((BST.getElement().compareTo(start)) > 0) {
            BST._left = BST._left.remove_range(start, end);
        }
        if ((BST.getElement().compareTo(end)) < 0) {
            BST._right = BST._right.remove_range(start, end);
        }
        if ((BST.getElement().compareTo(start)) >= 0 && (BST.getElement().compareTo(end)) <= 0) {
            return BST.remove(BST.getElement());
        }
        return this;
    }
    @Override
    public void printPreOrderTraversal() {
        System.out.print(_element + " ");
        if (!(_left.isEmpty())) {
            _left.printPreOrderTraversal();
        }
        if (!(_right.isEmpty())) {
            _right.printPreOrderTraversal();
        }
    }
    @Override
    public void printPostOrderTraversal() {
        if (!(_left.isEmpty())) {
            _left.printPostOrderTraversal();
        }
        if (!(_right.isEmpty())) {
            _right.printPostOrderTraversal();
        }
        System.out.print(_element + " ");
    }

    @Override
    public T findMin() {
        if (_left.isEmpty()) {
            return _element;
        }
        return _left.findMin();
    }

    @Override
    public int getHeight() {
        return Math.max(_left.getHeight(), _right.getHeight()) + 1;
    }

    @Override
    public BST<T> getLeft() {
        return _left;
    }

    @Override
    public BST<T> getRight() {
        return _right;
    }

    @Override
    public T getElement() {
        return _element;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

}
