package assn06;

public class AVLTree<T extends Comparable<T>> implements SelfBalancingBST<T> {
    // Fields
    private T _value;
    private AVLTree<T> _left;
    private AVLTree<T> _right;
    private int _height;
    private int _size;

    public AVLTree() {
        _value = null;
        _left = null;
        _right = null;
        _height = -1;
        _size = 0;
    }

    /**
     * Rotates the tree left and returns
     * AVLTree root for rotated result.
     */
    private AVLTree<T> rotateLeft() {
        // implement left rotation and then use this method as needed when fixing imbalances.
        AVLTree<T> rightChild = _right;
        AVLTree<T> rightLeftChild = rightChild._left;

        rightChild._left = this;
        this._right = rightLeftChild;

        this.updateSizeAndHeight();
        rightChild.updateSizeAndHeight();
        return rightChild;
    }

    /**
     * Rotates the tree right and returns
     * AVLTree root for rotated result.
     */
    private AVLTree<T> rotateRight() {
        // You should implement right rotation and then use this
        // method as needed when fixing imbalances.
        AVLTree<T> leftChild = _left;
        AVLTree<T> leftRightChild = leftChild._right;

        leftChild._right = this;
        this._left = leftRightChild;
        this.updateSizeAndHeight();
        leftChild.updateSizeAndHeight();
        return leftChild;
    }

    private AVLTree<T> balance() {
        int bf = getBF();

        // if right-heavy:
        if (bf < -1) {
            if (getBF(_right) > 0) {
                _right = _right.rotateRight();
            }
            return rotateLeft();
        }
        // else if left-heavy:
        else if (bf > 1) {
            if (getBF(_left) < 0) {
                _left = _left.rotateLeft();
            }
            return rotateRight();
        }
        return this;
    }

    private int getBF() {
        // returns balance factor
        return getBF(this);
    }

    private int getBF(AVLTree<T> node) {
        // returns balance factor
        return node._left.height() - node._right.height();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int height() {
        return _height;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public SelfBalancingBST<T> insert(T element) {
        // TODO

        if (element == null) {
            throw new IllegalArgumentException("Can't insert a null element, sorry!");
        }
        // BST insertion
        // if empty....
        if (this.isEmpty()) {
            this._value = element;
            this._left = new AVLTree<>();
            this._right = new AVLTree<>();
            this._height = 0;
            this._size = 1;
        } else { // otherwise insert
            int comparisonResult = element.compareTo(this._value);
            if (comparisonResult < 0) {
                this._left = (AVLTree<T>) this._left.insert(element);
            } else if (comparisonResult > 0) {
                this._right = (AVLTree<T>) this._right.insert(element);
            } else { // if elem is already in tree you don't need to do anything
                return this;
            }
        }
        updateSizeAndHeight();
        return balance();
    }

    private void updateSizeAndHeight() {
        this._height = 1 + Math.max(_left.height(), _right.height());
        this._size = 1 + _left.size() + _right.size();
    }

    @Override
    public SelfBalancingBST<T> remove(T element) {
        if (element == null) {
            throw new IllegalArgumentException("You cannot remove a null element.");
        }

        if (isEmpty()) {
            return this;
        }

        int comparisonResult = element.compareTo(this._value);

        if (comparisonResult > 0) {
            // move right
            this._right = (AVLTree<T>) this._right.remove(element);
        } else if (comparisonResult < 0) {
            // move left
            this._left = (AVLTree<T>) this._left.remove(element);
        } else {
            // found the node we have to remove!
            if (this._left.isEmpty() && this._right.isEmpty()) {
                // no kids
                return new AVLTree<>(); // so return an empty tree
            } else if (!this._left.isEmpty() && this._right.isEmpty()) {
                // Only left child
                return this._left;
            } else if (this._left.isEmpty()) {
                // Only right child
                return this._right;
            } else {
                // Two children
                this._value = this._right.findMin();
                this._right = (AVLTree<T>) this._right.remove(this._value);
            }
        }
        this.updateSizeAndHeight();
        return this.balance();
    }

    @Override
    public T findMin() {
        if (isEmpty()) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
        AVLTree<T> current = this;
        while (current._left != null && !current._left.isEmpty()) { // Ensure the left child is not an empty node
            current = current._left;
        }
        return current._value;
    }

    @Override
    public T findMax() {
        if (isEmpty()) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
        if (isEmpty()) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
        AVLTree<T> current = this;
        while (current._right != null && !current._right.isEmpty()) { // Ensure the right child is not an empty node
            current = current._right;
        }
        return current._value;
    }

    @Override
    public boolean contains(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot search for null element.");
        }
        AVLTree<T> current = this;
        while (current != null && !current.isEmpty()) { //
            int comparisonResult = element.compareTo(current.getValue());
            if (comparisonResult < 0) {
                current = current._left;
            } else if (comparisonResult > 0) {
                current = current._right;
            } else {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean rangeContain(T start, T end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Range start and end must be non-null.");
        }
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("Range start must be less than or equal to range end.");
        }
        if (isEmpty()) {
            return false;
        }
        if ((start.compareTo(getValue()) <= 0) && (end.compareTo(getValue()) >= 0)) {
            return true;
        }
        if (start.compareTo(getValue()) < 0) {
            if (getLeft() != null && getLeft().rangeContain(start, end)) {
                return true;
            }
        }
        if (end.compareTo(getValue()) > 0) {
            if (getRight() != null && getRight().rangeContain(start, end)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T getValue() {
        return _value;
    }

    @Override
    public SelfBalancingBST<T> getLeft() {
        if (isEmpty()) {
            return null;
        }
        return _left;
    }

    @Override
    public SelfBalancingBST<T> getRight() {
        if (isEmpty()) {
            return null;
        }
        return _right;
    }

}

