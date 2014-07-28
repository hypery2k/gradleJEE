/**
 * JEE6 gradle-based Demo App
 *
 * File: AbstractBeanValidator.java, 28.07.2014, 10:49:55, mreinhardt
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

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;

/**
 * Common superclass for bean validators to share some common functionality for reflection etc
 * 
 * @author mreinhardt
 * 
 */
public abstract class AbstractBeanValidator < K extends Annotation,O> implements ConstraintValidator<K,O> {

  /**
   * @param pAttribute to get the method for
   * @return the getter method name for the given attribute
   */
  protected String getGetterDeclaration(final String pAttribute) {
    final StringBuilder builder = new StringBuilder("get");
    builder.append(Character.toUpperCase(pAttribute.charAt(0)));
    builder.append(pAttribute.substring(1));
    return builder.toString();
  }

  /**
   * @param pAttribute to get the method for
   * @return the is method name for the given boolean attribute
   */
  protected String getIsDeclaration(final String pAttribute) {
    final StringBuilder builder = new StringBuilder("is");
    builder.append(Character.toUpperCase(pAttribute.charAt(0)));
    builder.append(pAttribute.substring(1));
    return builder.toString();
  }
}
