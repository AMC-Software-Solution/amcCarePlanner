import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './client-document.reducer';
import { IClientDocument } from 'app/shared/model/client-document.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClientDocumentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientDocumentDetail = (props: IClientDocumentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { clientDocumentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.clientDocument.detail.title">ClientDocument</Translate> [<b>{clientDocumentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="documentName">
              <Translate contentKey="carePlannerApp.clientDocument.documentName">Document Name</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.documentName}</dd>
          <dt>
            <span id="documentNumber">
              <Translate contentKey="carePlannerApp.clientDocument.documentNumber">Document Number</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.documentNumber}</dd>
          <dt>
            <span id="documentType">
              <Translate contentKey="carePlannerApp.clientDocument.documentType">Document Type</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.documentType}</dd>
          <dt>
            <span id="documentStatus">
              <Translate contentKey="carePlannerApp.clientDocument.documentStatus">Document Status</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.documentStatus}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="carePlannerApp.clientDocument.note">Note</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.note}</dd>
          <dt>
            <span id="issuedDate">
              <Translate contentKey="carePlannerApp.clientDocument.issuedDate">Issued Date</Translate>
            </span>
          </dt>
          <dd>
            {clientDocumentEntity.issuedDate ? (
              <TextFormat value={clientDocumentEntity.issuedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="expiryDate">
              <Translate contentKey="carePlannerApp.clientDocument.expiryDate">Expiry Date</Translate>
            </span>
          </dt>
          <dd>
            {clientDocumentEntity.expiryDate ? (
              <TextFormat value={clientDocumentEntity.expiryDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="uploadedDate">
              <Translate contentKey="carePlannerApp.clientDocument.uploadedDate">Uploaded Date</Translate>
            </span>
          </dt>
          <dd>
            {clientDocumentEntity.uploadedDate ? (
              <TextFormat value={clientDocumentEntity.uploadedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="documentFile">
              <Translate contentKey="carePlannerApp.clientDocument.documentFile">Document File</Translate>
            </span>
          </dt>
          <dd>
            {clientDocumentEntity.documentFile ? (
              <div>
                {clientDocumentEntity.documentFileContentType ? (
                  <a onClick={openFile(clientDocumentEntity.documentFileContentType, clientDocumentEntity.documentFile)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {clientDocumentEntity.documentFileContentType}, {byteSize(clientDocumentEntity.documentFile)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="documentFileUrl">
              <Translate contentKey="carePlannerApp.clientDocument.documentFileUrl">Document File Url</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.documentFileUrl}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.clientDocument.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {clientDocumentEntity.lastUpdatedDate ? (
              <TextFormat value={clientDocumentEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.clientDocument.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{clientDocumentEntity.clientId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.clientDocument.uploadedBy">Uploaded By</Translate>
          </dt>
          <dd>{clientDocumentEntity.uploadedByEmployeeCode ? clientDocumentEntity.uploadedByEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.clientDocument.approvedBy">Approved By</Translate>
          </dt>
          <dd>{clientDocumentEntity.approvedByEmployeeCode ? clientDocumentEntity.approvedByEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/client-document" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client-document/${clientDocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ clientDocument }: IRootState) => ({
  clientDocumentEntity: clientDocument.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ClientDocumentDetail);
