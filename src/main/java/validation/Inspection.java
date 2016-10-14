package validation;

@FunctionalInterface
public interface Inspection<T> {
	void inspect(T validatable, InspectionPost post);
}