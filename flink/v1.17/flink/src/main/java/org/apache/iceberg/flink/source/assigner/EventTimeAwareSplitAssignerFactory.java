/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one
 *  * or more contributor license agreements.  See the NOTICE file
 *  * distributed with this work for additional information
 *  * regarding copyright ownership.  The ASF licenses this file
 *  * to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance
 *  * with the License.  You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied.  See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *
 */

package org.apache.iceberg.flink.source.assigner;

import java.util.Collection;
import org.apache.flink.api.common.eventtime.TimestampAssigner;
import org.apache.iceberg.flink.source.split.IcebergSourceSplit;
import org.apache.iceberg.flink.source.split.IcebergSourceSplitState;

public class EventTimeAwareSplitAssignerFactory implements SplitAssignerFactory {
    private final WatermarkTracker watermarkTracker;
    private final TimestampAssigner<IcebergSourceSplit> timestampAssigner;

    private final EventTimeAlignedAssigner.Options options;

    public EventTimeAwareSplitAssignerFactory(
            WatermarkTracker watermarkTracker,
            TimestampAssigner<IcebergSourceSplit> timestampAssigner, EventTimeAlignedAssigner.Options options) {
        this.watermarkTracker = watermarkTracker;
        this.timestampAssigner = timestampAssigner;
        this.options = options;
    }

    @Override
    public SplitAssigner createAssigner() {
        return new EventTimeAlignedAssigner(watermarkTracker, timestampAssigner, options);
    }

    @Override
    public SplitAssigner createAssigner(Collection<IcebergSourceSplitState> assignerState) {
        return new EventTimeAlignedAssigner(watermarkTracker, timestampAssigner, options);
    }
}