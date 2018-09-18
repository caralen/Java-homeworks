package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a special kind of map where each key is a single string value,
 * but for each key user can store multiple values in stack-like structure.
 * @author Alen Carin
 *
 */
public class ObjectMultistack {
	
	/**A map where key is a string and the value is of type <code>MultistackEntry</code>.*/
	private Map<String, MultistackEntry> map;
	
	/**
	 * This class represents a single stack entry value. 
	 * Each instance of this class has a reference to the next value which is stored for the same key.
	 * @author Alen Carin
	 *
	 */
	public static class MultistackEntry {
		
		/**Reference to the next MultistackEntry which is stored for the same key.*/
		private MultistackEntry next;
		
		/**Reference to the wrapper which holds the value of this entry.*/
		private ValueWrapper valueWrapper;
		
		/**
		 * Constructor which sets the field variables to the values passed through arguments.
		 * @param next
		 * @param valueWrapper
		 */
		public MultistackEntry(MultistackEntry next, ValueWrapper valueWrapper) {
			this.next = next;
			this.valueWrapper = valueWrapper;
		}

		/**
		 * Returns next MultistackEntry in the list.
		 * @return {@link #next}
		 */
		public MultistackEntry getNext() {
			return next;
		}

		/**
		 * Returns the ValueWrapper of this entry.
		 * @return {@link #valueWrapper}
		 */
		public ValueWrapper getValueWrapper() {
			return valueWrapper;
		}
	}
	
	/**
	 * Default constructor. Creates an instance of a HashMap.
	 */
	public ObjectMultistack() {
		this.map = new HashMap<>();
	}

	/**
	 * Creates a new MultistackEntry from the given ValueWrapper 
	 * and pushes it to the stack with the given key.
	 * @param name is the key under which the value is stored.
	 * @param valueWrapper is a reference to the wrapper which holds the value.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		MultistackEntry entry;
		if (map.get(name) == null) {
			entry = new MultistackEntry(null, valueWrapper);
		} else {
			entry = new MultistackEntry(map.get(name), valueWrapper);
		}
		map.put(name, entry);
	}

	/**
	 * Pops a single MultistackEntry from the stack.
	 * @param name is the key under which the value is stored.
	 * @return ValueWrapper which is a reference to the wrapper which holds the value.
	 */
	public ValueWrapper pop(String name) {
		MultistackEntry entry = map.get(name);
		try {
			if(entry.getNext() == null) {
				map.remove(name);
			} else {
				map.replace(name, entry.getNext());
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("Cannot do pop on an empty stack");
		}
		return entry.getValueWrapper();
	}
	
	/**
	 * Peeks at the value from the stack stored under the given key.
	 * @param name is the key under which the value is stored.
	 * @return ValueWrapper which is a reference to the wrapper which holds the value.
	 */
	public ValueWrapper peek(String name) {
		if(!map.containsKey(name)) {
			throw new NullPointerException("Cannot do peek on an empty stack");
		}
		return map.get(name).getValueWrapper();
	}
	
	/**
	 * Returns true if there is no value under the given key.
	 * @param name is the key under which the value is stored.
	 * @return true if there is no value under the given key, false otherwise.
	 */
	public boolean isEmpty(String name) {
		return map.get(name) == null;
	}
}
