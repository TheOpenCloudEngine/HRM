/**
 * Copyright (C) 2011 Flamingo Project (http://www.opencloudengine.org).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opencloudengine.garuda.backend.system;

public interface SystemService {

	/**
	 * 시스템 사용자가 존재하는지 확인한다.
	 *
	 * @param username 사용자명
	 * @return	true or false
	 */
	boolean existUser(String username);

	/**
	 * 시스템 사용자를 생성한다.
	 *
	 * @param home		사용자 홈 경로
	 * @param name		사용자
	 * @param username	사용자명
	 * @return	true or false
	 */
	boolean createUser(String home, String name, String username);

	/**
	 * 시스템 사용자의 비밀번호를 변경한다.
	 *
	 * @param username	사용자명
	 * @param password	비밀번호
	 * @return true or false
	 */
	boolean changeUser(String username, String password);

	/**
	 * 시스템 사용자를 삭제한다.
	 *
	 * @param username	사용자명
	 * @return	true or false
	 */
	boolean deleteUser(String username);
}