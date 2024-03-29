package project3;

import java.io.Serializable;
import java.util.Random;


public class MySingleWithTailLinkedList implements Serializable
{
	private Node top;
	private Node tail;

	public MySingleWithTailLinkedList() {
		top = tail = null;
	}

	// This method has been provided and you are not permitted to modify
	public int size() {
		if (top == null)
			return 0;

		int total = 0;
		Node temp = top;
		while (temp.getNext() != null) {
			total++;
			temp = temp.getNext();
		}

		if (temp != tail)
			throw new RuntimeException("Tail is not pointing at the end of the list");
		else
			total++;

		return total;
	}

	// This method has been provided and you are not permitted to modify
	public void clear () {
		Random rand = new Random(13);
		while (size() > 0) {
			int number = rand.nextInt(size());
			remove(number);
		}
	}

	/********************************************************************************************
	 *
	 *    Your task is to complete this method.
	 *
	 *
	 *
	 * @param rental the unit begin rented
	 */
	public void add(Rental rental) {

		//if list is empty
		if (top == null) {
			tail = top = new Node(rental, null);
		}

		//sets top to rental if rental is due before top
		else if(rental instanceof Game && 
				rental.dueBack.before(top.getData().dueBack)) {
			top = new Node(rental, top);
		}
		else if(rental instanceof Game && 
				rental.dueBack.equals(top.getData().dueBack)) {

			//if rental's name of renter is before or equal to top's, set top
			if(rental.getNameOfRenter().compareTo(top.getData().getNameOfRenter()) <= 0) {
				top = new Node(rental, top);
			}

			//if rental's name is after top's, set it after top
			else {
				Node newNode = new Node(rental, top.getNext());
				if(top.getNext() == null) {
					tail = newNode;
				}
				top.setNext(newNode);
			}
		}
		else {
			Node current = top;

			//if rental is a console, set current to last game
			if(rental instanceof Console) {
				while(current.getNext() != null &&
						current.getNext().getData() instanceof Game) {
					current = current.getNext();
				}
			}

			//loops through until rental is not due after the next node
			while(current.getNext() != null && 
					(rental.dueBack.after(current.getNext().getData().dueBack) || 
							(rental.dueBack.equals(current.getNext().getData().dueBack)
									&& rental.nameOfRenter.compareTo(current.getNext().getData().nameOfRenter) > 0)
							)
					) {

				//checks if rental is a game and the next node is a console
				if(rental instanceof Game && 
						current.getNext().getData() instanceof Console) {
					break;
				}
				current = current.getNext();
			}

			//insert after current
			Node newNode = new Node(rental, current.getNext());
			if(current.getNext() == null) {
				tail = newNode;
			}
			current.setNext(newNode);
		}
	}

	public Rental remove(int index) {
		if(index < 0 || index >= size() || top == null) {
			throw new IllegalArgumentException();
		}
		else if(index == 0) {
			Rental data = top.getData();
			top = top.getNext();
			return data;
		}
		else {
			int currentIndex = 0;
			Node current = top;

			//current is at index - 1
			while(currentIndex < index - 1) {
				current = current.getNext();
				currentIndex++;
			}
			Rental data = current.getNext().getData();

			//if the space after index is null, index is the tail
			if(current.getNext().getNext() != null) {
				current.setNext(current.getNext().getNext());
			}
			else {
				current.setNext(null);
				tail = current;
			}

			return data;
		}
	}

	public Rental get(int index) {
		if(index < 0 || index >= size()) {
			throw new IllegalArgumentException();
		}
		else {
			int currentIndex = 0;
			Node current = top;

			//current is at index
			while(currentIndex < index) {
				current = current.getNext();
				currentIndex++;
			}
			return current.getData();
		}
	}

	public void display() {
		Node temp = top;
		while (temp != null) {
			System.out.println(temp.getData());
			temp = temp.getNext();
		}
	}

	@Override
	public String toString() {
		return "LL {" +
				"top=" + top +
				", size=" + size() +
				'}';
	}
}

