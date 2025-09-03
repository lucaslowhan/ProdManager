CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,
    operation VARCHAR(50) NOT NULL,
    entity_name VARCHAR(100),
    entity_id BIGINT,
    actor VARCHAR(200),
    request_id VARCHAR(100),
    ip VARCHAR(50),
    before_data TEXT,
    after_data TEXT,
    sucess BOOLEAN NOT NULL,
    error_message TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

CREATE INDEX idx_audit_operation ON audit_logs(operation);
CREATE INDEX idx_audit_entity ON audit_logs(entity_name,entity_id);
CREATE INDEX idx_audit_created_at ON audit_logs(created_at);