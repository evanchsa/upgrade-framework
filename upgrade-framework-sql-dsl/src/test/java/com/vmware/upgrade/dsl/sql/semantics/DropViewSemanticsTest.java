/* ****************************************************************************
 * Copyright (c) 2012-2014 VMware, Inc. All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * ****************************************************************************/

package com.vmware.upgrade.dsl.sql.semantics;

import java.util.HashMap;

import com.vmware.upgrade.TestGroups;
import com.vmware.upgrade.dsl.model.UpgradeDefinitionModel;
import com.vmware.upgrade.dsl.sql.util.SQLStatementFactory;
import com.vmware.upgrade.sql.SQLStatement;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DropViewSemanticsTest {
    @SuppressWarnings("serial")
    @DataProvider
    public Object[][] dropViewStatements() {
        return new Object[][] {
                new Object[] {
                        "drop_view 'some_table'",
                        SQLStatementFactory.create("DROP VIEW some_table")
                },
                new Object[] {
                        "drop_view oracle: 'some_table', ms_sql: 'different_table', postgres: 'ps_table'",
                        SQLStatementFactory.create(
                                new HashMap<String, String>() {{
                                    put("ms_sql", "DROP VIEW different_table");
                                    put("oracle", "DROP VIEW some_table");
                                    put("postgres", "DROP VIEW ps_table");
                                }}
                        )
                },
        };
    }

    @Test(groups = { TestGroups.UNIT }, dataProvider = "dropViewStatements")
    public void compareDropViewStatements(String ddl, SQLStatement expected) {
        final UpgradeDefinitionModel upgrade = SemanticTestUtil.createUpgradeModel(ddl);

        SemanticTestUtil.compareModelToSQL(upgrade, expected);
    }
}
