/* ****************************************************************************
 * Copyright (c) 2011-2014 VMware, Inc. All Rights Reserved.
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

package com.vmware.upgrade.dsl.model

import com.vmware.upgrade.sequencing.AbstractGraph
import com.vmware.upgrade.sequencing.Graph
import com.vmware.upgrade.sequencing.Version

/**
 * A DSL model object representing a {@link Graph} of {@link Graph.Edge}s represented by
 * {@link UpgradeTaskModel}s.
 *
 * @author Emil Sit <sit@vmware.com>
 * @version 1.0
 * @since 1.0
 */
class ManifestModel extends AbstractGraph {
    private Map<Version, UpgradeTaskModel> upgrades = [:]
    def name

    def addUpgrade(UpgradeTaskModel upgrade) {
        Version source = upgrade.getSource()
        upgrades.put(source, upgrade)
    }

    def addAll(Collection<UpgradeTaskModel> upgrades) {
        for (UpgradeTaskModel upgrade : upgrades) {
            addUpgrade(upgrade)
        }
    }

    UpgradeTaskModel getUpgrade(Version source) {
        return upgrades.get(source)
    }

    Collection<UpgradeTaskModel> getUpgrades() {
        return upgrades.values()
    }

    @Override
    public Map<Version, Graph.Edge> getEdges() {
        return upgrades
    }

    @Override
    public String toString() {
        return name ?: "Unnamed Manifest"
    }
}