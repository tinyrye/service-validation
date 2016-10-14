package validation;

import org.junit.Assert;

public class Assertions
{
	public static void assertAllowed(String validationName, Inspection data) {
		Assert.assertTrue(data.isAllowed(validationName));
		Assert.assertFalse(data.isFailed(validationName));
		Assert.assertFalse(data.isPrecluded(validationName));
	}

	public static void assertFailed(String validationName, Inspection data) {
		Assert.assertTrue(data.isFailed(validationName));
		Assert.assertFalse(data.isAllowed(validationName));
		Assert.assertFalse(data.isPrecluded(validationName));
	}

	public static void assertPrecluded(String validationName, Inspection data) {
		Assert.assertTrue(data.isPrecluded(validationName));
		Assert.assertFalse(data.isAllowed(validationName));
		Assert.assertFalse(data.isFailed(validationName));
	}

	public static void assertContainsMessageMatching(String messageRegex, Inspection data)
	{
		for (String validationMessage: data.getValidationMessages()) {
			if (validationMessage.matches(messageRegex)) return;
		}
		throw new AssertionError(String.format("Found no message matching: %s", messageRegex));
	}
}