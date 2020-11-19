import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './servce-user-document.reducer';
import { IServceUserDocument } from 'app/shared/model/servce-user-document.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServceUserDocumentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServceUserDocumentDetail = (props: IServceUserDocumentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { servceUserDocumentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.servceUserDocument.detail.title">ServceUserDocument</Translate> [
          <b>{servceUserDocumentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="documentName">
              <Translate contentKey="carePlannerApp.servceUserDocument.documentName">Document Name</Translate>
            </span>
          </dt>
          <dd>{servceUserDocumentEntity.documentName}</dd>
          <dt>
            <span id="documentCode">
              <Translate contentKey="carePlannerApp.servceUserDocument.documentCode">Document Code</Translate>
            </span>
          </dt>
          <dd>{servceUserDocumentEntity.documentCode}</dd>
          <dt>
            <span id="documentNumber">
              <Translate contentKey="carePlannerApp.servceUserDocument.documentNumber">Document Number</Translate>
            </span>
          </dt>
          <dd>{servceUserDocumentEntity.documentNumber}</dd>
          <dt>
            <span id="documentStatus">
              <Translate contentKey="carePlannerApp.servceUserDocument.documentStatus">Document Status</Translate>
            </span>
          </dt>
          <dd>{servceUserDocumentEntity.documentStatus}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="carePlannerApp.servceUserDocument.note">Note</Translate>
            </span>
          </dt>
          <dd>{servceUserDocumentEntity.note}</dd>
          <dt>
            <span id="issuedDate">
              <Translate contentKey="carePlannerApp.servceUserDocument.issuedDate">Issued Date</Translate>
            </span>
          </dt>
          <dd>
            {servceUserDocumentEntity.issuedDate ? (
              <TextFormat value={servceUserDocumentEntity.issuedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="expiryDate">
              <Translate contentKey="carePlannerApp.servceUserDocument.expiryDate">Expiry Date</Translate>
            </span>
          </dt>
          <dd>
            {servceUserDocumentEntity.expiryDate ? (
              <TextFormat value={servceUserDocumentEntity.expiryDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="uploadedDate">
              <Translate contentKey="carePlannerApp.servceUserDocument.uploadedDate">Uploaded Date</Translate>
            </span>
          </dt>
          <dd>
            {servceUserDocumentEntity.uploadedDate ? (
              <TextFormat value={servceUserDocumentEntity.uploadedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="documentFile">
              <Translate contentKey="carePlannerApp.servceUserDocument.documentFile">Document File</Translate>
            </span>
          </dt>
          <dd>
            {servceUserDocumentEntity.documentFile ? (
              <div>
                {servceUserDocumentEntity.documentFileContentType ? (
                  <a onClick={openFile(servceUserDocumentEntity.documentFileContentType, servceUserDocumentEntity.documentFile)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {servceUserDocumentEntity.documentFileContentType}, {byteSize(servceUserDocumentEntity.documentFile)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="documentFileUrl">
              <Translate contentKey="carePlannerApp.servceUserDocument.documentFileUrl">Document File Url</Translate>
            </span>
          </dt>
          <dd>{servceUserDocumentEntity.documentFileUrl}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.servceUserDocument.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {servceUserDocumentEntity.lastUpdatedDate ? (
              <TextFormat value={servceUserDocumentEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tenantId">
              <Translate contentKey="carePlannerApp.servceUserDocument.tenantId">Tenant Id</Translate>
            </span>
          </dt>
          <dd>{servceUserDocumentEntity.tenantId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.servceUserDocument.owner">Owner</Translate>
          </dt>
          <dd>{servceUserDocumentEntity.ownerServiceUserCode ? servceUserDocumentEntity.ownerServiceUserCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.servceUserDocument.approvedBy">Approved By</Translate>
          </dt>
          <dd>{servceUserDocumentEntity.approvedByEmployeeCode ? servceUserDocumentEntity.approvedByEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/servce-user-document" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/servce-user-document/${servceUserDocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ servceUserDocument }: IRootState) => ({
  servceUserDocumentEntity: servceUserDocument.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServceUserDocumentDetail);
