package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;

/**
 * Wrapper for a single value of type <code>Object</code>.
 * @author Alen Carin
 *
 */
public class ValueWrapper {

	/**Value that is stored in this wrapper.*/
	private Object value;
	
	/**Tests if in values of type <code>Object</code> is at least one floating point value.*/
	public static BiPredicate<Object, Object> atLeastOneDouble;
	
	static {
		atLeastOneDouble = (v1, v2) -> {
			return (v1.toString().contains(".") || v1.toString().contains("E")) 
					|| (v2.toString().contains(".") || v2.toString().contains("E"));
		};
	}
	
	/**
	 * Constructor which accepts a value of type <code>Object</code>.
	 * @param value {@link #value}
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}
	
	/**
	 * Returns value
	 * @return {@link #value}
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Sets value to the value given in arguments.
	 * @param value {@link #value}
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Adds the given value to the value stored in this wrapper.
	 * @param incValue is the value by which the value in this wrapper will be incremented.
	 */
	public void add(Object incValue) {
		BinaryOperator<Integer> integerAdder = (v1, v2) -> v1 + v2;
		BinaryOperator<Double> doubleAdder = (v1, v2) -> v1 + v2;
		
		this.value = doArithmeticOperation(
				integerAdder, 
				doubleAdder, 
				this.value == null ? Integer.valueOf(0) : this.value, 
				incValue == null ? Integer.valueOf(0) : incValue
				);
	}

	/**
	 * Subtracts the given value from the value stored in this wrapper.
	 * @param decValue is the value from which the value in this wrapper will be subtracted.
	 */
	public void subtract(Object decValue) {
		BinaryOperator<Integer> integerSubtracter = (v1, v2) -> v1 - v2;
		BinaryOperator<Double> doubleSubtracter = (v1, v2) -> v1 - v2;
		
		this.value = doArithmeticOperation(
				integerSubtracter, 
				doubleSubtracter, 
				this.value == null ? Integer.valueOf(0) : this.value, 
				decValue == null ? Integer.valueOf(0) : decValue
				);
	}
	
	/**
	 * Multiplies the value stored in this wrapper with the given value.
	 * @param mulValue which multiplies value stored in this wrapper.
	 */
	public void multiply(Object mulValue) {
		BinaryOperator<Integer> integerMultiplier = (v1, v2) -> v1 * v2;
		BinaryOperator<Double> doubleMultiplier = (v1, v2) -> v1 * v2;
		
		this.value = doArithmeticOperation(
				integerMultiplier, 
				doubleMultiplier, 
				this.value == null ? Integer.valueOf(0) : this.value, 
				mulValue == null ? Integer.valueOf(0) : mulValue
				);
	}
	
	/**
	 * Divides the value stored in this wrapper with the given value.
	 * @param divValue by which the value stored in this wrapper is divided.
	 */
	public void divide(Object divValue) {
		BinaryOperator<Integer> integerDivider = (v1, v2) -> v1 / v2;
		BinaryOperator<Double> doubleDivider = (v1, v2) -> v1 / v2;
		
		this.value = doArithmeticOperation(
				integerDivider, 
				doubleDivider, 
				this.value == null ? Integer.valueOf(0) : this.value, 
				divValue == null ? Integer.valueOf(0) : divValue
				);
	}
	
	/**
	 * Converts the given arguments in the correct type of value and
	 * applies one of the binary operators that are passed trough arguments.
	 * If both values are of type integer then integerOperator is applied,
	 * otherwise the doubleOperator is applied.
	 * @param integerOperator binary operator which is applied if values are of type Integer.
	 * @param doubleOperator binary operator which is applied if values are not of type Integer.
	 * @param value1 is the first argument in arithmetic operation.
	 * @param value2 is the second argument in arithmetic operation.
	 * @return result of the arithmetic operation.
	 * @throws RuntimeException 
	 * 				if value that is passed could not be converted to Integer nor Double.
	 */
	private Object doArithmeticOperation(BinaryOperator<Integer> integerOperator, 
			BinaryOperator<Double> doubleOperator, 
			Object value1, 
			Object value2) throws RuntimeException {
		
		try {
			if(!atLeastOneDouble.test(value1, value2)) {
				int intValue1 = Integer.parseInt(value1.toString());
				int intValue2 = Integer.parseInt(value2.toString());
				return integerOperator.apply(intValue1, intValue2);
			} else {
				double doubleValue1 = Double.parseDouble(value1.toString());
				double doubleValue2 = Double.parseDouble(value2.toString());
				return doubleOperator.apply(doubleValue1, doubleValue2);
			}
		} catch(NumberFormatException e) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * Compares the value passed through arguments with the value stored in this wrapper.
	 * @param withValue is the second value in the comparison.
	 * @return int value greater than zero if the first value is bigger than the second,
	 * value less than zero if the first value is less than the second,
	 * value equal to zero if they are the same.
	 */
	public int numCompare(Object withValue) {
		Object objectValue1 = this.value == null ? Integer.valueOf(0) : this.value;
		Object objectValue2 = withValue == null ? Integer.valueOf(0) : withValue;
		
		try {
			if(!atLeastOneDouble.test(objectValue1, objectValue2)) {
				int intValue1 = Integer.parseInt(objectValue1.toString());
				int intValue2 = Integer.parseInt(objectValue2.toString());
				return Integer.compare(intValue1, intValue2);
			} else {
				double doubleValue1 = Double.parseDouble(objectValue1.toString());
				double doubleValue2 = Double.parseDouble(objectValue2.toString());
				return Double.compare(doubleValue1, doubleValue2);
			}
		} catch(NumberFormatException e) {
			throw new RuntimeException();
		}
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
