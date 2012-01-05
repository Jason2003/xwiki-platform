/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.security.authorization;

import org.xwiki.security.authorization.internal.XWikiAccessLevel;

import junit.framework.Assert;
import junit.framework.TestCase;

public class AccessLevelTest extends TestCase
{
    public static void assertDefaultAccessLevel()
    {
        Assert.assertEquals(new XWikiAccessLevel()
        {
            {
                allow(Right.VIEW);
                allow(Right.EDIT);
                allow(Right.COMMENT);
                allow(Right.LOGIN);
                allow(Right.REGISTER);
                deny(Right.DELETE);
                deny(Right.ADMIN);
                deny(Right.PROGRAM);
                deny(Right.ILLEGAL);
            }
        },XWikiAccessLevel.getDefaultAccessLevel());
    }

    public void testAccessLevel() throws Exception
    {
        assertDefaultAccessLevel();

        XWikiAccessLevel l = XWikiAccessLevel.getDefaultAccessLevel().clone();

        Assert.assertEquals(RuleState.ALLOW, l.get(Right.VIEW));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.EDIT));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.COMMENT));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.LOGIN));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.REGISTER));
        Assert.assertEquals(RuleState.DENY, l.get(Right.DELETE));
        Assert.assertEquals(RuleState.DENY, l.get(Right.ADMIN));
        Assert.assertEquals(RuleState.DENY, l.get(Right.PROGRAM));
        Assert.assertEquals(RuleState.DENY, l.get(Right.ILLEGAL));

        l.clear(Right.VIEW);
        l.clear(Right.COMMENT);
        l.clear(Right.LOGIN);
        l.clear(Right.ADMIN);

        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.VIEW));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.EDIT));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.COMMENT));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.LOGIN));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.REGISTER));
        Assert.assertEquals(RuleState.DENY, l.get(Right.DELETE));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.ADMIN));
        Assert.assertEquals(RuleState.DENY, l.get(Right.PROGRAM));
        Assert.assertEquals(RuleState.DENY, l.get(Right.ILLEGAL));

        l.deny(Right.VIEW);
        l.deny(Right.LOGIN);
        l.allow(Right.ADMIN);
            
        Assert.assertEquals(RuleState.DENY, l.get(Right.VIEW));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.EDIT));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.COMMENT));
        Assert.assertEquals(RuleState.DENY, l.get(Right.LOGIN));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.REGISTER));
        Assert.assertEquals(RuleState.DENY, l.get(Right.DELETE));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.ADMIN));
        Assert.assertEquals(RuleState.DENY, l.get(Right.PROGRAM));
        Assert.assertEquals(RuleState.DENY, l.get(Right.ILLEGAL));

        l.clear(Right.VIEW);
        l.clear(Right.EDIT);
        l.clear(Right.COMMENT);
        l.clear(Right.LOGIN);
        l.clear(Right.REGISTER);
        l.clear(Right.DELETE);
        l.clear(Right.ADMIN);
        l.clear(Right.PROGRAM);
        l.clear(Right.ILLEGAL);

        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.VIEW));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.EDIT));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.COMMENT));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.LOGIN));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.REGISTER));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.DELETE));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.ADMIN));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.PROGRAM));
        Assert.assertEquals(RuleState.UNDETERMINED, l.get(Right.ILLEGAL));

        l.allow(Right.VIEW);
        l.allow(Right.EDIT);
        l.allow(Right.COMMENT);
        l.allow(Right.LOGIN);
        l.allow(Right.REGISTER);
        l.allow(Right.DELETE);
        l.allow(Right.ADMIN);
        l.allow(Right.PROGRAM);
        l.allow(Right.ILLEGAL);

        Assert.assertEquals(RuleState.ALLOW, l.get(Right.VIEW));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.EDIT));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.COMMENT));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.LOGIN));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.REGISTER));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.DELETE));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.ADMIN));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.PROGRAM));
        Assert.assertEquals(RuleState.ALLOW, l.get(Right.ILLEGAL));

        assertDefaultAccessLevel();
    }

    public void testClone() throws Exception
    {
        XWikiAccessLevel l = XWikiAccessLevel.getDefaultAccessLevel().clone();
        XWikiAccessLevel k = l.clone();
        Assert.assertEquals(l,k);
        Assert.assertNotSame(l,k);
        l = l.getExistingInstance();
        k = k.getExistingInstance();
        Assert.assertSame(l,k);

        assertDefaultAccessLevel();
    }
}
