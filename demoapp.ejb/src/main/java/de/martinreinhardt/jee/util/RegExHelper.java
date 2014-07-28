/**
 * JEE6 gradle-based Demo App
 *
 * File: RegExHelper.java, 28.07.2014, 12:49:55, mreinhardt
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
package de.martinreinhardt.jee.util;

/**
 * @author mreinhardt
 * 
 */
public final class RegExHelper {

  private RegExHelper() {
    // not called
  }

  /**
   * Pattern for time
   */
  public static final String SIMPLE_TIME = "([0-2|][0-9]:[0-5][0-9])|";

  /**
   * Name
   */
  public static final String NAME = "\\p{L}(.|'|-)?(\\s|\\p{L}+)((\\s)*(\\p{L}||[\u0020\u0027\u002C\u002D\u0030-\u0039\u0041-\u005A\u005F\u0061-\u007A\u00C0-\u00FF??])*(.|'|-)?(\\s)*)*(\\p{L})*(\\s)*";

  // vorher: "\\p{L}\\p{L}+((\\s)*(\\p{L})*(.|-)?(\\s)*)*(\\p{L})*(\\s)*"

  /**
   * Pattern for simple name
   */
  public static final String SIMPLE_NAME = "^" + NAME + "$";

  /**
   * Pattern for complex name
   */
  public static final String COMPLEX_NAME = "^[a-zA-Z?????????????? -]+$";

  /**
   * Pattern for complex string, like occupation
   */
  public static final String COMPLEX_STRING = "^[a-zA-Z??????????????[(][)]\\ -]+$";

  /**
   * Pattern for complex string with numbers
   */
  public static final String COMPLEX_STRING_AND_NUMBERS = "^[0-9a-zA-Z??????????????[(][)]\\ -]+$";

  /**
   * Postal Code
   */
  public static final String POSTAL_CODE = "[0-9]{5}";

  /**
   * PostalCode City
   */
  public static final String POSTCODE_CITY = POSTAL_CODE + " " + NAME;

  /**
   * Regex for street and number
   */
  public static final String STREET_AND_NO = "[\\s]*.+([\\s]+[0-9]*[A-Za-z]?)?[\\s]*";

  /**
   * Pattern for email addresses
   */
  public static final String EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  /**
   * Pattern for URLs
   */
  public static final String URL = "((((http|https)://)?(www\\.)?([a-zA-Z0-9][-a-zA-Z\\.0-9]*)\\.([a-z]){2,}(/.*)?)|)";

  /**
   * Regex for kita_id
   */
  public static final String KITA_ID = "[0-9.]*";
}
