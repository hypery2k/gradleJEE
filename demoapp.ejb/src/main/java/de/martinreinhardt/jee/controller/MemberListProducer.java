/**
 * JEE6 gradle-based Demo App
 *
 * File: MemberListProducer.java, 24.07.2014, 10:19:55, mreinhardt
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
package de.martinreinhardt.jee.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.List;

import de.martinreinhardt.jee.domain.Member;
import de.martinreinhardt.jee.services.MemberRepository;

/**
 * Simple producer example which also utilize events observation
 * 
 * @author mreinhardt
 * 
 */
@RequestScoped
public class MemberListProducer {

	@Inject
	private MemberRepository memberRepository;

	private List<Member>     members;

	@PostConstruct
	public void retrieveAllMembersOrderedByName() {
		members = memberRepository.findAllOrderedByName();
	}

	/**
	 * usage of @Named provides access the return value via the EL variable name
	 * "members" in the UI (e.g. Facelets or JSP view)
	 */
	@Produces
	@Named
	public List<Member> getMembers() {
		return members;
	}

	/**
	 * Method is invoked when a new member is registered
	 * 
	 * @param member
	 * @see MemberRegistration#register(Member)
	 */
	public void onMemberListChanged(
	    @Observes(notifyObserver = Reception.IF_EXISTS) final Member member) {
		retrieveAllMembersOrderedByName();
	}
}
