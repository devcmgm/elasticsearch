/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.core.ml.action;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.action.ActionType;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.xpack.core.ml.job.config.Job;
import org.elasticsearch.xpack.core.ml.job.process.autodetect.state.ModelSnapshotField;
import org.elasticsearch.xpack.core.ml.utils.ExceptionsHelper;

import java.io.IOException;

public class DeleteModelSnapshotAction extends ActionType<AcknowledgedResponse> {

    public static final DeleteModelSnapshotAction INSTANCE = new DeleteModelSnapshotAction();
    public static final String NAME = "cluster:admin/xpack/ml/job/model_snapshots/delete";

    private DeleteModelSnapshotAction() {
        super(NAME, AcknowledgedResponse::new);
    }

    public static class Request extends ActionRequest {

        private String jobId;
        private String snapshotId;

        public Request() {
        }

        public Request(StreamInput in) throws IOException {
            super(in);
            jobId = in.readString();
            snapshotId = in.readString();
        }

        public Request(String jobId, String snapshotId) {
            this.jobId = ExceptionsHelper.requireNonNull(jobId, Job.ID.getPreferredName());
            this.snapshotId = ExceptionsHelper.requireNonNull(snapshotId, ModelSnapshotField.SNAPSHOT_ID.getPreferredName());
        }

        public String getJobId() {
            return jobId;
        }

        public String getSnapshotId() {
            return snapshotId;
        }

        @Override
        public ActionRequestValidationException validate() {
            return null;
        }

        @Override
        public void readFrom(StreamInput in) throws IOException {
            throw new UnsupportedOperationException("usage of Streamable is to be replaced by Writeable");
        }

        @Override
        public void writeTo(StreamOutput out) throws IOException {
            super.writeTo(out);
            out.writeString(jobId);
            out.writeString(snapshotId);
        }
    }

    public static class RequestBuilder extends ActionRequestBuilder<Request, AcknowledgedResponse> {

        public RequestBuilder(ElasticsearchClient client, DeleteModelSnapshotAction action) {
            super(client, action, new Request());
        }
    }

}
