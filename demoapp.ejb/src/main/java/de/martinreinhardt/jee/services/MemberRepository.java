/**
 * JEE6 gradle-based Demo App
 *
 * File: AppServlet.java, 23.07.2014, 12:49:55, mreinhardt
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
package de.martinreinhardt.jee.services;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import de.martinreinhardt.jee.domain.Member;

/**
 * Member Repository
 * 
 * @author mreinhardt
 *
 */
@Stateless
@PermitAll
public class MemberRepository {

	@Inject
	private Logger        log;

	@Inject
	private EntityManager em;

	public Member findById(Long id) {
		return em.find(Member.class, id);
	}

	/**
	 * Find member by email address
	 * 
	 * @param email
	 *          to use
	 * @return member or null if not found
	 */
	public Member findByEmail(String email) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		criteria.select(member).where(cb.equal(member.get("email"), email));
		Member result = null;
		try {
			result = em.createQuery(criteria).getSingleResult();
		} catch (Exception e) {
			log.info("Found no result.");
		}
		return result;
	}

	/**
	 * Get all members ordered by name
	 * 
	 * @return list of member, maybe null if no members were found
	 */
	public List<Member> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		criteria.select(member).orderBy(cb.asc(member.get("name")));
		List<Member> result = null;
		try {
			result = em.createQuery(criteria).getResultList();
		} catch (Exception e) {
			log.info("Found no result.");
		}
		return result;
	}
}
