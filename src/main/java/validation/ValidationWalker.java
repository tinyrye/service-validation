package validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 */
public class ValidatorWalker<T>
{
	/**
	 * Run the set of validations.
	 */
	public Allowance process(final T validatable, final List<Validator<T>> validations,
		final Inspection data)
	{
		Allowance objectAllowance = null;
		final Set<Validator<T>> pending = new HashSet<Validator<T>>(validations);
		final List<String> allValidatorNames = getAllValidatorNames(validations);
		while (! pending.isEmpty())
		{
			final Iterator<Validator<T>> pendingCycle = pending.iterator();
			while (pendingCycle.hasNext())
			{
				Validator<T> validation = pendingCycle.next();
				Readiness readiness = getReadiness(validation, allValidatorNames, data);
				if (readiness == Readiness.PRECLUDED) {
					data.addPrecluded(validation.name());
					pendingCycle.remove();
				}
				else if (readiness == Readiness.READY)
				{
					Allowance validationAllowance = validation.inspect(validatable, data);
					if ((validationAllowance == Allowance.SUCCESS)
							|| (validationAllowance == Allowance.WARN))
					{
						data.addAllowed(validation.name());
					}
					else {
						data.addFailed(validation.name());
					}
					pendingCycle.remove();
					objectAllowance = Allowance.merge(objectAllowance, validationAllowance);
				}
			}
		}
		return objectAllowance;
	}

	private Readiness getReadiness(
		Validator<T> validation,
		List<String> allValidatorNames,
		Inspection data)
	{
		if (validation.prerequisites().isEmpty()) {
			return Readiness.READY;
		}
		else
		{
			Boolean allPrerequisitesAllowing = true;
			for (String prerequisite: validation.prerequisites())
			{
				// if no prerequisite validation is in the global list of validations to
				// run on the validatable, then the prequisite cannot be met.
				if (! allValidatorNames.contains(prerequisite)) {
					return Readiness.PRECLUDED;
				}
				// or if prerequisite 1) ran and failed or 2) is precluded itself, then
				// this validation is precluded from running as well.
				else if (data.isFailed(prerequisite) || data.isPrecluded(prerequisite)) {
					return Readiness.PRECLUDED;
				}
				else {
					// if ! isAllowed then the validation has not run yet.
					allPrerequisitesAllowing = data.isAllowed(prerequisite);
				}
			}
			return (allPrerequisitesAllowing ? Readiness.READY : Readiness.REMAIN_PENDING);
		}
	}

	private List<String> getAllValidatorNames(List<Validator<T>> validations)
	{
		final List<String> allValidatorNames = new ArrayList<String>();
		for (Validator<T> validation: validations) {
			allValidatorNames.add(validation.name());
		}
		return allValidatorNames;
	}

	private static enum Readiness {
		READY, REMAIN_PENDING, PRECLUDED;
	}
}