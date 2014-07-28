/**
 * JEE6 gradle-based Demo App
 *
 * File: GroupValidationTest.java, 28.07.2014, 11:49:55, mreinhardt
 *
 * @project https://github.com/hypery2k/gradleJEE
 *
 * @copyright 2014 Martin Reinhardt contact@martinreinhardt-online.de
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package de.martinreinhardt.jee.domain.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import de.martinreinhardt.jee.domain.Group;
import de.martinreinhardt.jee.util.TimeUtil;
import de.martinreinhardt.jee.util.ValidationTestBase;

/**
 * Custom validator test
 * 
 * @author mreinhardt
 * 
 */
@RunWith(Parameterized.class)
public class GroupDateValidationTest extends ValidationTestBase {

	private final String expectedMsg    = "{validation.date.range_error}";

	private Group        groupToTest;

	private boolean      testShouldFail = false;

	public GroupDateValidationTest(Group pGroupToTest, boolean pShouldFail) {
		this.groupToTest = pGroupToTest;
		this.testShouldFail = pShouldFail;
	}

	@Test
	public void testInvalidGroupDate() {

		// check validation
		final Set<ConstraintViolation<Group>> constraintViolations = validator.validate(groupToTest);
		if (testShouldFail) {
			assertEquals(1, constraintViolations.size());
		}

		// check for right validation message
		if (constraintViolations.size() >= 1) {
			if (testShouldFail) {
				for (final ConstraintViolation<Group> constraintViolation : constraintViolations) {
					final String msg = constraintViolation.getMessageTemplate();

					assertTrue("Wrong message. Expected " + expectedMsg + " but was " + msg,
					    msg.equalsIgnoreCase(expectedMsg));
				}
			} else {
				fail("Test should not fail");
			}
		}
	}

	@Parameters
	public static Collection<Object[]> data() {
		// add them as parameters
		Object[][] data = new Object[][] {
		    { new Group("abc", TimeUtil.now(), TimeUtil.now()), true },
		    { new Group("abc", TimeUtil.now(), TimeUtil.yearMonthMinusMonths(TimeUtil.now(), 2)), true },
		    { new Group("abc", TimeUtil.now(), TimeUtil.yearMonthPlusMonths(TimeUtil.now(), 2)), false } };
		return Arrays.asList(data);
	}
}
