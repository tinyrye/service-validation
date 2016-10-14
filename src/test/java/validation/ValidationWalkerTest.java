package validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class ValidationWalkerTest
{
	@Test
	public void testProcess()
	{
		ValidationWalker<String> testedObject = new ValidationWalker<String>();

		Inspection data;
		List<Validation<String>> validations;

		validations = new ArrayList<Validation<String>>();
		validations.add(new LengthValidator(1, 6).setName("length"));
		validations.add(new RegexValidator("foo.*bar").setName("pattern"));

		data = new Inspection();
		testedObject.process("", validations, data);
		Assertions.assertFailed("length", data);
		Assertions.assertFailed("pattern", data);
		data = new Inspection();
		testedObject.process("foobar", validations, data);
		Assertions.assertAllowed("length", data);
		Assertions.assertAllowed("pattern", data);

		validations.get(1).addPrerequisite("length");

		data = new Inspection();
		testedObject.process("", validations, data);
		Assertions.assertFailed("length", data);
		Assertions.assertPrecluded("pattern", data);

		data = new Inspection();
		testedObject.process("hello", validations, data);
		Assertions.assertAllowed("length", data);
		Assertions.assertFailed("pattern", data);
	}

	public static class LengthValidator extends Validation<String>
	{
		public static final String MIN_MSG = "too short";
		public static final String MAX_MSG = "too long";
		private int min, max;
		public LengthValidator(int min, int max) {
			this.min = min; this.max = max;
		}
		@Override public Allowance inspect(String value, Inspection data)
		{
			if (value.length() < min) {
				data.addValidationMessage(MIN_MSG);
				return Allowance.FAILED;
			}
			else if (value.length() > max) {
				data.addValidationMessage(MAX_MSG);
				return Allowance.FAILED;
			}
			else {
				return Allowance.SUCCESS;
			}
		}
	}

	public static class RegexValidator extends Validation<String>
	{
		public static final String MISMATCH_MSG = "mismatch on regex";
		public Pattern compiledRegex;
		public RegexValidator(String regex) {
			compiledRegex = Pattern.compile(regex);
		}
		@Override public Allowance inspect(String value, Inspection data)
		{
			if (! compiledRegex.matcher(value).find()) {
				data.addValidationMessage(MISMATCH_MSG);
				return Allowance.FAILED;
			}
			else {
				return Allowance.SUCCESS;
			}
		}
	}
}