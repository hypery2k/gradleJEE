/**
 * JEE6 gradle-based Demo App
 *
 * File: DateRangeValidator.java, 28.07.2014, 10:49:55, mreinhardt
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

import java.lang.reflect.Method;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

/**
 * Validator for ensure that a start date is before a end date
 * 
 * @author mreinhardt
 * 
 */
public class DateRangeValidator extends AbstractBeanValidator<ValidateDateRange, Object> {

	private String  start;

	private String  end;

	private boolean equal;

	/**
	 * @see javax.validation.ConstraintValidator#initialize(java.lang.annotation.Annotation)
	 */
	@Override
	public void initialize(final ValidateDateRange constraintAnnotation) {
		this.start = constraintAnnotation.start();
		this.end = constraintAnnotation.end();
		this.equal = constraintAnnotation.equal();
	}

	/**
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 *      javax.validation.ConstraintValidatorContext)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		 boolean isValid = false;
	    final Class clazz = value.getClass();

	    try {
	      Date startDate = null;
	      final Method startMethod = clazz.getMethod(this.getGetterDeclaration(this.start), new Class[0]);
	      if (startMethod != null) {
	        startDate = (Date) startMethod.invoke(value, null);
	      }

	      Date endDate = null;
	      final Method endMethod = clazz.getMethod(this.getGetterDeclaration(this.end), new Class[0]);
	      if (startMethod != null) {
	        endDate = (Date) endMethod.invoke(value, null);
	      }

	      if (endDate != null && startDate != null) {
	        if (this.equal) {
	          isValid = endDate.compareTo(startDate) >= 0;
	        } else {
	          isValid = endDate.after(startDate);
	        }
	      } else {
	        isValid = true;
	      }
		} catch (Exception e) {
			isValid = false;
		}
		if (!isValid) {
			final String template = context.getDefaultConstraintMessageTemplate();
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(template).addConstraintViolation();
		}
		return isValid;
	}
}
