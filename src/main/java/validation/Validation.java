package validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public abstract class Validation<T> {
	public String name;
	public final List<String> prerequisites = new ArrayList<String>();
	public Inspector<T> inspector;
}