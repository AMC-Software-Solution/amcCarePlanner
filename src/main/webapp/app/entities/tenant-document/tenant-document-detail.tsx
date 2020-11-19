import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './tenant-document.reducer';
import { ITenantDocument } from 'app/shared/model/tenant-document.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITenantDocumentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TenantDocumentDetail = (props: ITenantDocumentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { tenantDocumentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.tenantDocument.detail.title">TenantDocument</Translate> [<b>{tenantDocumentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="documentName">
              <Translate contentKey="carePlannerApp.tenantDocument.documentName">Document Name</Translate>
            </span>
          </dt>
          <dd>{tenantDocumentEntity.documentName}</dd>
          <dt>
            <span id="documentCode">
              <Translate contentKey="carePlannerApp.tenantDocument.documentCode">Document Code</Translate>
            </span>
          </dt>
          <dd>{tenantDocumentEntity.documentCode}</dd>
          <dt>
            <span id="documentNumber">
              <Translate contentKey="carePlannerApp.tenantDocument.documentNumber">Document Number</Translate>
            </span>
          </dt>
          <dd>{tenantDocumentEntity.documentNumber}</dd>
          <dt>
            <span id="documentType">
              <Translate contentKey="carePlannerApp.tenantDocument.documentType">Document Type</Translate>
            </span>
          </dt>
          <dd>{tenantDocumentEntity.documentType}</dd>
          <dt>
            <span id="documentStatus">
              <Translate contentKey="carePlannerApp.tenantDocument.documentStatus">Document Status</Translate>
            </span>
          </dt>
          <dd>{tenantDocumentEntity.documentStatus}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="carePlannerApp.tenantDocument.note">Note</Translate>
            </span>
          </dt>
          <dd>{tenantDocumentEntity.note}</dd>
          <dt>
            <span id="issuedDate">
              <Translate contentKey="carePlannerApp.tenantDocument.issuedDate">Issued Date</Translate>
            </span>
          </dt>
          <dd>
            {tenantDocumentEntity.issuedDate ? (
              <TextFormat value={tenantDocumentEntity.issuedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="expiryDate">
              <Translate contentKey="carePlannerApp.tenantDocument.expiryDate">Expiry Date</Translate>
            </span>
          </dt>
          <dd>
            {tenantDocumentEntity.expiryDate ? (
              <TextFormat value={tenantDocumentEntity.expiryDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="uploadedDate">
              <Translate contentKey="carePlannerApp.tenantDocument.uploadedDate">Uploaded Date</Translate>
            </span>
          </dt>
          <dd>
            {tenantDocumentEntity.uploadedDate ? (
              <TextFormat value={tenantDocumentEntity.uploadedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="documentFile">
              <Translate contentKey="carePlannerApp.tenantDocument.documentFile">Document File</Translate>
            </span>
          </dt>
          <dd>
            {tenantDocumentEntity.documentFile ? (
              <div>
                {tenantDocumentEntity.documentFileContentType ? (
                  <a onClick={openFile(tenantDocumentEntity.documentFileContentType, tenantDocumentEntity.documentFile)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {tenantDocumentEntity.documentFileContentType}, {byteSize(tenantDocumentEntity.documentFile)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="documentFileUrl">
              <Translate contentKey="carePlannerApp.tenantDocument.documentFileUrl">Document File Url</Translate>
            </span>
          </dt>
          <dd>{tenantDocumentEntity.documentFileUrl}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.tenantDocument.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {tenantDocumentEntity.lastUpdatedDate ? (
              <TextFormat value={tenantDocumentEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tenantId">
              <Translate contentKey="carePlannerApp.tenantDocument.tenantId">Tenant Id</Translate>
            </span>
          </dt>
          <dd>{tenantDocumentEntity.tenantId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.tenantDocument.uploadedBy">Uploaded By</Translate>
          </dt>
          <dd>{tenantDocumentEntity.uploadedByEmployeeCode ? tenantDocumentEntity.uploadedByEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.tenantDocument.approvedBy">Approved By</Translate>
          </dt>
          <dd>{tenantDocumentEntity.approvedByEmployeeCode ? tenantDocumentEntity.approvedByEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/tenant-document" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tenant-document/${tenantDocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ tenantDocument }: IRootState) => ({
  tenantDocumentEntity: tenantDocument.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TenantDocumentDetail);
