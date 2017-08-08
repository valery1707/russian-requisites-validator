package name.valery1707.validator;

import com.tngtech.archunit.base.Optional;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.library.GeneralCodingRules;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.runner.RunWith;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(
		packages = "name.valery1707.validator"
//		, importOption = ImportOption.DontIncludeTests.class
)
public class ArchitectureTest {
	@ArchTest
	public static final ArchRule NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

	@ArchTest
	public static final ArchRule NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS = GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

	@ArchTest
	public static final ArchRule VALIDATOR_MUST_BE_FINAL = classes()
			.that().haveNameMatching("^.*Validator$").and().haveNameNotMatching("^.*ConstraintValidator$")
			.should().haveModifier(JavaModifier.FINAL)
			.because("Validator must not extended");

	@ArchTest
	public static final ArchRule CONSTRAINT_ANNOTATIONS_MUST_HAVE_RUNTIME_RETENTION = classes()
			.that().areAnnotatedWith(Constraint.class)
			.should().beAnnotatedWith(Retention.class)
			.andShould(retentionRuntime())
			.because("Constraint annotations must have runtime retention");

	private static ArchCondition<JavaClass> retention(final RetentionPolicy expected) {
		return new ArchCondition<JavaClass>("retention " + expected.name()) {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				Optional<Retention> annotation = item.tryGetAnnotationOfType(Retention.class);
				if (annotation.isPresent()) {
					RetentionPolicy actual = annotation.get().value();
					boolean equals = expected.equals(actual);
					String message = String.format("class %s is annotated with %s with value = '%s' which %s with required '%s'",
							item.getName(), Retention.class.getSimpleName(), actual.name(), equals ? "equals" : "not equals", expected.name()
					);
					events.add(equals ? SimpleConditionEvent.satisfied(item, message) : SimpleConditionEvent.violated(item, message));
				}
			}
		};
	}

	private static ArchCondition<JavaClass> retentionRuntime() {
		return retention(RetentionPolicy.RUNTIME);
	}

	@ArchTest
	public static final ArchRule INFO_MUST_IMPLEMENT_EQUALS_AND_HASHCODE = classes()
			.that().haveNameMatching("^.*Info")
			.should(equalsContract())
			.because("Info classes must correctly implement equals and hashCode methods");

	private static ArchCondition<JavaClass> equalsContract() {
		return new ArchCondition<JavaClass>("implement equals and hashCode methods") {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				String msg = "class " + item.getName();
				try {
					EqualsVerifier
							.forClass(item.reflect())
							.suppress(Warning.STRICT_INHERITANCE)
							.verify();
					events.add(SimpleConditionEvent.satisfied(item, msg + " match satisfy contract"));
				} catch (AssertionError e) {
					events.add(SimpleConditionEvent.violated(item, msg + " fail equals contract: " + e.getMessage()));
				}
			}
		};
	}
}
