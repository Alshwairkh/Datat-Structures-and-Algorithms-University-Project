public class AVLTree<T extends Comparable<? super T>> extends BST<T> {

   protected int height;

   public AVLTree() {
      super();
      height = -1;
   }

   public AVLTree(BSTNode<T> root) {
      super(root);
      height = -1;
   }

   public int getHeight() {
      return getHeight(root);
   }

   private int getHeight(BSTNode<T> node) {
      if (node == null)
         return -1;
      else
         return 1 + Math.max(getHeight(node.left), getHeight(node.right));
   }

   private AVLTree<T> getLeftAVL() {
      AVLTree<T> leftsubtree = new AVLTree<T>(root.left);
      return leftsubtree;
   }

   private AVLTree<T> getRightAVL() {
      AVLTree<T> rightsubtree = new AVLTree<T>(root.right);
      return rightsubtree;
   }

   protected int getBalanceFactor() {
      if (isEmpty())
         return 0;
      else
         return getRightAVL().getHeight() - getLeftAVL().getHeight();
   }

   public void insertAVL(T el) {
      super.insert(el);
      this.balance();
   }

   public void deleteAVL(T el) {
      // Q1
      deleteNode(root, el);
   }

   BSTNode<T> deleteNode(BSTNode<T> root, T key) {
      // STEP 1: PERFORM STANDARD BST DELETE
      if (root == null)
         return root;

      // If the key to be deleted is smaller than
      // the root's key, then it lies in left subtree
      if (key.compareTo(root.el) < 0)
         root.left = deleteNode(root.left, key);

      // If the key to be deleted is greater than the
      // root's key, then it lies in right subtree
      else if (key.compareTo(root.el) > 0)
         root.right = deleteNode(root.right, key);

      // if key is same as root's key, then this is the node
      // to be deleted
      else {

         // node with only one child or no child
         if ((root.left == null) || (root.right == null)) {
            BSTNode<T> temp = null;
            if (temp == root.left)
               temp = root.right;
            else
               temp = root.left;

            // No child case
            if (temp == null) {
               temp = root;
               root = null;
            } else // One child case
               root = temp; // Copy the contents of
                            // the non-empty child
         } else {

            // node with two children: Get the inorder
            // successor (smallest in the right subtree)
            BSTNode<T> current = root.right;
            /* loop down to find the leftmost leaf */
            while (current.left != null) {
               current = current.left;
            }
            BSTNode<T> temp = current;

            // Copy the inorder successor's data to this node
            root.el = temp.el;

            // Delete the inorder successor
            root.right = deleteNode(root.right, temp.el);
         }
      }

      // If the tree had only one node then return
      if (root == null)
         return root;

      // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
      this.adjustHeight();

      // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
      // this node became unbalanced)
      this.balance();

      return root;
   }

   //////

   protected void balance() {
      if (!isEmpty()) {
         getLeftAVL().balance();
         getRightAVL().balance();

         adjustHeight();

         int balanceFactor = getBalanceFactor();

         if (balanceFactor == -2) {
            //System.out.println("Balancing node with el: " + root.el);
            if (getLeftAVL().getBalanceFactor() < 0)
               rotateRight();
            else
               rotateLeftRight();
         }

         else if (balanceFactor == 2) {
            //System.out.println("Balancing node with el: " + root.el);
            if (getRightAVL().getBalanceFactor() > 0)
               rotateLeft();
            else
               rotateRightLeft();
         }
      }
   }

   protected void adjustHeight() {
      if (isEmpty())
         height = -1;
      else
         height = 1 + Math.max(getLeftAVL().getHeight(), getRightAVL().getHeight());
   }

   protected void rotateRight() {
      //System.out.println("RIGHT ROTATION");
      // Q1
      BSTNode<T> tempNode = root.right;
      root.right = root.left;
      root.left = root.right.left;
      root.right.left = root.right.right;
      root.right.right = tempNode;

      T val = (T) root.el;
      root.el = root.right.el;
      root.right.el = val;

      getLeftAVL().adjustHeight();
      adjustHeight();
   }

   protected void rotateLeft() {
      //System.out.println("LEFT ROTATION");
      BSTNode<T> tempNode = root.left;
      root.left = root.right;
      root.right = root.left.right;
      root.left.right = root.left.left;
      root.left.left = tempNode;

      T val = (T) root.el;
      root.el = root.left.el;
      root.left.el = val;

      getLeftAVL().adjustHeight();
      adjustHeight();
   }

   protected void rotateLeftRight() {
      //System.out.println("Double Rotation...");
      // Q1
      getLeftAVL().rotateLeft();
      getLeftAVL().adjustHeight();
      this.rotateRight();
      this.adjustHeight();

   }

   protected void rotateRightLeft() {
      //System.out.println("Double Rotation...");
      getRightAVL().rotateRight();
      getRightAVL().adjustHeight();
      this.rotateLeft();
      this.adjustHeight();
   }
   
   //helper method for treeToString method
   public String treeToString() {
      return treeToString(root);
   }
   //recursive method to convert tree to string
   public String treeToString(BSTNode<T> p) {
      if (p != null)
         return (p.el + " " + treeToString(p.left) + treeToString(p.right));
      return "";

   }

}