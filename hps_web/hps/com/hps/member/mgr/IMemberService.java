package com.hps.member.mgr;

import com.newsoft.sysmanager.po.User;

/**
 * the interface for memeber mgr
 * @author mengxw
 *
 */
public interface IMemberService {

	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String register(User user) throws Exception;
}
