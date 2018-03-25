package sim;

public class Stack<V> {
	public class Item<K>{
		K data;
		Item<K> next;
		public Item(K data) {
			this.data = data;
			this.next = null;
		}
		public void addOnto(Item<K> item) {
			this.next = item;
		}
	}
	Item<V> head;
	public Stack(){
		head = null;
	}
	public void push(V item) {
		Item<V> i = new Item<>(item);
		i.addOnto(head); 
		head = i;
	}
	public Item<V> pop(){
		Item<V> retval = head;
		head = head.next;
		return retval;
	}
}
