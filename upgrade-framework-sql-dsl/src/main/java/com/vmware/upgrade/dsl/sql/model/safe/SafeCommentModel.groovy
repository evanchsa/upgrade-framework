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

package com.vmware.upgrade.dsl.sql.model.safe

import com.vmware.upgrade.dsl.sql.model.defaults.DefaultCommentModel
import com.vmware.upgrade.dsl.sql.util.SQLStatementFactory
import com.vmware.upgrade.sql.DatabaseType
import com.vmware.upgrade.sql.SQLStatement

/**
 * {@link SafeCommentModel} extends the logic of {@link DefaultCommentModel} by
 * wrapping the raw SQL string returned by {@link DefaultCommentModel#get(DatabaseType)}
 * with SQL entity existence checks.
 * <p>
 * If the {@link CommentModel} represents
 * <ol>
 *   <li>a table comment, then a check is performed to ensure the table exists; or</li>
 *   <li>a column comment, then a check is performed to ensure both the table and
 *       column exists.</li>
 * </ol>
 *
 * @author Ryan Lewis <ryanlewis@vmware.com>
 * @version 1.0
 * @since 1.0
 */
class SafeCommentModel extends DefaultCommentModel {
    @Override
    public String get(DatabaseType databaseType) {
        switch (commentType) {
            case CommentType.COLUMN:
                return String.format(SafeSQLStatementWrapper.TABLE_AND_COLUMN_EXISTS.get(databaseType), tableName, entity, super.get(databaseType).replace("'", "''"))
            case CommentType.TABLE:
                return String.format(
                    SafeSQLStatementWrapper.TABLE_EXISTS.get(databaseType),
                    entity,
                    super.get(databaseType).replace("'", "''"))
        }
    }
}
