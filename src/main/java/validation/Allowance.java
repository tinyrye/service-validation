package validation;

public enum Allowance
{
	/**
	 * There were no problems or flags raised with the object that was inspected.
	 * Object is definitely allowed.
	 */
	SUCCESS,
	
	/**
	 * There were problems or flags raised with the object that was inspected; however,
	 * the object is allowed, but there are validation messages.
	 */
	WARN,

	/**
	 * There were problems or flags raised with the object that was inspected; those
	 * problems or flags are not allowed for the object.
	 */
	FAILED;

	/**
	 * Determine what the aggregate allowance is between an existing, aggegrate
	 * allowance and a validation's decided allowance.
	 */
	public static Allowance merge(Allowance existing, Allowance nextValue) {
		if (existing == null) return nextValue;
		else if (nextValue == Allowance.FAILED) return nextValue;
		else if (existing == Allowance.FAILED) return existing;
		else if (nextValue == Allowance.WARN) return nextValue;
		else if (existing == Allowance.WARN) return existing;
		else return existing; /* both are SUCCESS */
	}
}