package web;
import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * Generic node, holds any type of object
 * @author seshv
 *
 */
public class Node<T> {
	public T data;
	public Node<T> next;
	// constructor name does not get the <T>
	public Node(T data, Node<T> next) {
		this.data = data;
		this.next = next;
	}
	
	public static <T> Node<T> deleeteAll(T item, Node<T> rear)
			throws NoSuchElementException {
		Node<T> front = rear;
		Node<T> ptr = front;
		if(front.next == rear && rear.data == item){
			rear = null;
			return rear;
		}
		boolean m = true;
		while(m){
			if(front==rear){
				break;
			}
			if(front.next.data==item){
				m = false;
				front.next = front.next.next;
			}
			front = front.next;
			
			
		}
		if(m = false){
			throw new NoSuchElementException();
		}
		return rear;
	}
}

