import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee-document.reducer';
import { IEmployeeDocument } from 'app/shared/model/employee-document.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeDocumentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeDocumentDetail = (props: IEmployeeDocumentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeDocumentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.employeeDocument.detail.title">EmployeeDocument</Translate> [
          <b>{employeeDocumentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="documentName">
              <Translate contentKey="carePlannerApp.employeeDocument.documentName">Document Name</Translate>
            </span>
          </dt>
          <dd>{employeeDocumentEntity.documentName}</dd>
          <dt>
            <span id="documentCode">
              <Translate contentKey="carePlannerApp.employeeDocument.documentCode">Document Code</Translate>
            </span>
          </dt>
          <dd>{employeeDocumentEntity.documentCode}</dd>
          <dt>
            <span id="documentNumber">
              <Translate contentKey="carePlannerApp.employeeDocument.documentNumber">Document Number</Translate>
            </span>
          </dt>
          <dd>{employeeDocumentEntity.documentNumber}</dd>
          <dt>
            <span id="documentStatus">
              <Translate contentKey="carePlannerApp.employeeDocument.documentStatus">Document Status</Translate>
            </span>
          </dt>
          <dd>{employeeDocumentEntity.documentStatus}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="carePlannerApp.employeeDocument.note">Note</Translate>
            </span>
          </dt>
          <dd>{employeeDocumentEntity.note}</dd>
          <dt>
            <span id="issuedDate">
              <Translate contentKey="carePlannerApp.employeeDocument.issuedDate">Issued Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeDocumentEntity.issuedDate ? (
              <TextFormat value={employeeDocumentEntity.issuedDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="expiryDate">
              <Translate contentKey="carePlannerApp.employeeDocument.expiryDate">Expiry Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeDocumentEntity.expiryDate ? (
              <TextFormat value={employeeDocumentEntity.expiryDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="uploadedDate">
              <Translate contentKey="carePlannerApp.employeeDocument.uploadedDate">Uploaded Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeDocumentEntity.uploadedDate ? (
              <TextFormat value={employeeDocumentEntity.uploadedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="documentFile">
              <Translate contentKey="carePlannerApp.employeeDocument.documentFile">Document File</Translate>
            </span>
          </dt>
          <dd>
            {employeeDocumentEntity.documentFile ? (
              <div>
                {employeeDocumentEntity.documentFileContentType ? (
                  <a onClick={openFile(employeeDocumentEntity.documentFileContentType, employeeDocumentEntity.documentFile)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {employeeDocumentEntity.documentFileContentType}, {byteSize(employeeDocumentEntity.documentFile)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="documentFileUrl">
              <Translate contentKey="carePlannerApp.employeeDocument.documentFileUrl">Document File Url</Translate>
            </span>
          </dt>
          <dd>{employeeDocumentEntity.documentFileUrl}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.employeeDocument.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeDocumentEntity.lastUpdatedDate ? (
              <TextFormat value={employeeDocumentEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tenantId">
              <Translate contentKey="carePlannerApp.employeeDocument.tenantId">Tenant Id</Translate>
            </span>
          </dt>
          <dd>{employeeDocumentEntity.tenantId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.employeeDocument.documentType">Document Type</Translate>
          </dt>
          <dd>{employeeDocumentEntity.documentTypeDocumentTypeTitle ? employeeDocumentEntity.documentTypeDocumentTypeTitle : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.employeeDocument.employee">Employee</Translate>
          </dt>
          <dd>{employeeDocumentEntity.employeeEmployeeCode ? employeeDocumentEntity.employeeEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.employeeDocument.approvedBy">Approved By</Translate>
          </dt>
          <dd>{employeeDocumentEntity.approvedByEmployeeCode ? employeeDocumentEntity.approvedByEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-document" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-document/${employeeDocumentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employeeDocument }: IRootState) => ({
  employeeDocumentEntity: employeeDocument.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeDocumentDetail);
