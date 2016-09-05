/**
 * Copyright (C) 2011 Flamingo Project (http://www.cloudine.io).
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opencloudengine.garuda.backend.scheduler;

/**
 * Job 내부에서 사용하는 Constant Variable.
 *
 * @author Byoung Gon, Kim
 * @since 0.2
 */
public interface JobVariable {

    /**
     * ClientJob Domain을 꺼내오기 위한 Key
     */
    public final static String CLIENT_JOB = "CLIENT_JOB";

    /**
     * Job ID를 꺼내오기 위한 Key
     */
    public final static String JOB_ID = "JOB_ID";

    /**
     * Job Group을 꺼내오기 위한 Key
     */
    public final static String JOB_GROUP = "JOB_GROUP";

    /**
     * Job Type을 꺼내오기 위한 Key
     */
    public final static String JOB_TYPE = "JOB_TYPE";

    /**
     * Job Name을 꺼내오기 위한 Key
     */
    public final static String JOB_NAME = "JOB_NAME";

    /**
     * Job Key를 꺼내오기 위한 Key
     */
    public final static String JOB_KEY = "JOB_KEY";
}