/**
 * Copyright © 2014-2021 The SiteWhere Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sitewhere.grpc.label;

import com.google.protobuf.ByteString;
import com.sitewhere.grpc.model.LabelGenerationModel.GLabel;
import com.sitewhere.rest.model.label.Label;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.label.ILabel;

/**
 * Convert label generation entities between SiteWhere API model and GRPC model.
 */
public class LabelGenerationModelConverter {

    /**
     * Convert label from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static Label asApiLabel(GLabel grpc) throws SiteWhereException {
	Label label = new Label();
	label.setContent(grpc.getContent().toByteArray());
	return label;
    }

    /**
     * Convert label from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GLabel asGrpcLabel(ILabel api) throws SiteWhereException {
	GLabel.Builder grpc = GLabel.newBuilder();
	grpc.setContent(ByteString.copyFrom(api.getContent()));
	return grpc.build();
    }
}