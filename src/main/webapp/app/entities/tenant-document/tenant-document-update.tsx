import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './tenant-document.reducer';
import { ITenantDocument } from 'app/shared/model/tenant-document.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITenantDocumentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TenantDocumentUpdate = (props: ITenantDocumentUpdateProps) => {
  const [uploadedById, setUploadedById] = useState('0');
  const [approvedById, setApprovedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { tenantDocumentEntity, employees, loading, updating } = props;

  const { documentFile, documentFileContentType } = tenantDocumentEntity;

  const handleClose = () => {
    props.history.push('/tenant-document' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEmployees();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.uploadedDate = convertDateTimeToServer(values.uploadedDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...tenantDocumentEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="carePlannerApp.tenantDocument.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.tenantDocument.home.createOrEditLabel">Create or edit a TenantDocument</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : tenantDocumentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="tenant-document-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="tenant-document-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="documentNameLabel" for="tenant-document-documentName">
                  <Translate contentKey="carePlannerApp.tenantDocument.documentName">Document Name</Translate>
                </Label>
                <AvField
                  id="tenant-document-documentName"
                  type="text"
                  name="documentName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="documentCodeLabel" for="tenant-document-documentCode">
                  <Translate contentKey="carePlannerApp.tenantDocument.documentCode">Document Code</Translate>
                </Label>
                <AvField
                  id="tenant-document-documentCode"
                  type="text"
                  name="documentCode"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="documentNumberLabel" for="tenant-document-documentNumber">
                  <Translate contentKey="carePlannerApp.tenantDocument.documentNumber">Document Number</Translate>
                </Label>
                <AvField id="tenant-document-documentNumber" type="text" name="documentNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="documentTypeLabel" for="tenant-document-documentType">
                  <Translate contentKey="carePlannerApp.tenantDocument.documentType">Document Type</Translate>
                </Label>
                <AvInput
                  id="tenant-document-documentType"
                  type="select"
                  className="form-control"
                  name="documentType"
                  value={(!isNew && tenantDocumentEntity.documentType) || 'POLICY'}
                >
                  <option value="POLICY">{translate('carePlannerApp.TenantDocumentType.POLICY')}</option>
                  <option value="PROCEDURE">{translate('carePlannerApp.TenantDocumentType.PROCEDURE')}</option>
                  <option value="FORM">{translate('carePlannerApp.TenantDocumentType.FORM')}</option>
                  <option value="OTHER">{translate('carePlannerApp.TenantDocumentType.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="documentStatusLabel" for="tenant-document-documentStatus">
                  <Translate contentKey="carePlannerApp.tenantDocument.documentStatus">Document Status</Translate>
                </Label>
                <AvInput
                  id="tenant-document-documentStatus"
                  type="select"
                  className="form-control"
                  name="documentStatus"
                  value={(!isNew && tenantDocumentEntity.documentStatus) || 'EXPIRED'}
                >
                  <option value="EXPIRED">{translate('carePlannerApp.DocumentStatus.EXPIRED')}</option>
                  <option value="ACTIVE">{translate('carePlannerApp.DocumentStatus.ACTIVE')}</option>
                  <option value="ARCHIVED">{translate('carePlannerApp.DocumentStatus.ARCHIVED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="tenant-document-note">
                  <Translate contentKey="carePlannerApp.tenantDocument.note">Note</Translate>
                </Label>
                <AvField id="tenant-document-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="issuedDateLabel" for="tenant-document-issuedDate">
                  <Translate contentKey="carePlannerApp.tenantDocument.issuedDate">Issued Date</Translate>
                </Label>
                <AvField id="tenant-document-issuedDate" type="date" className="form-control" name="issuedDate" />
              </AvGroup>
              <AvGroup>
                <Label id="expiryDateLabel" for="tenant-document-expiryDate">
                  <Translate contentKey="carePlannerApp.tenantDocument.expiryDate">Expiry Date</Translate>
                </Label>
                <AvField id="tenant-document-expiryDate" type="date" className="form-control" name="expiryDate" />
              </AvGroup>
              <AvGroup>
                <Label id="uploadedDateLabel" for="tenant-document-uploadedDate">
                  <Translate contentKey="carePlannerApp.tenantDocument.uploadedDate">Uploaded Date</Translate>
                </Label>
                <AvInput
                  id="tenant-document-uploadedDate"
                  type="datetime-local"
                  className="form-control"
                  name="uploadedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.tenantDocumentEntity.uploadedDate)}
                />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="documentFileLabel" for="documentFile">
                    <Translate contentKey="carePlannerApp.tenantDocument.documentFile">Document File</Translate>
                  </Label>
                  <br />
                  {documentFile ? (
                    <div>
                      {documentFileContentType ? (
                        <a onClick={openFile(documentFileContentType, documentFile)}>
                          <Translate contentKey="entity.action.open">Open</Translate>
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {documentFileContentType}, {byteSize(documentFile)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('documentFile')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_documentFile" type="file" onChange={onBlobChange(false, 'documentFile')} />
                  <AvInput type="hidden" name="documentFile" value={documentFile} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="documentFileUrlLabel" for="tenant-document-documentFileUrl">
                  <Translate contentKey="carePlannerApp.tenantDocument.documentFileUrl">Document File Url</Translate>
                </Label>
                <AvField id="tenant-document-documentFileUrl" type="text" name="documentFileUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="tenant-document-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.tenantDocument.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="tenant-document-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.tenantDocumentEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tenantIdLabel" for="tenant-document-tenantId">
                  <Translate contentKey="carePlannerApp.tenantDocument.tenantId">Tenant Id</Translate>
                </Label>
                <AvField
                  id="tenant-document-tenantId"
                  type="string"
                  className="form-control"
                  name="tenantId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="tenant-document-uploadedBy">
                  <Translate contentKey="carePlannerApp.tenantDocument.uploadedBy">Uploaded By</Translate>
                </Label>
                <AvInput id="tenant-document-uploadedBy" type="select" className="form-control" name="uploadedById">
                  <option value="" key="0" />
                  {employees
                    ? employees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.employeeCode}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="tenant-document-approvedBy">
                  <Translate contentKey="carePlannerApp.tenantDocument.approvedBy">Approved By</Translate>
                </Label>
                <AvInput id="tenant-document-approvedBy" type="select" className="form-control" name="approvedById">
                  <option value="" key="0" />
                  {employees
                    ? employees.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.employeeCode}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/tenant-document" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  employees: storeState.employee.entities,
  tenantDocumentEntity: storeState.tenantDocument.entity,
  loading: storeState.tenantDocument.loading,
  updating: storeState.tenantDocument.updating,
  updateSuccess: storeState.tenantDocument.updateSuccess,
});

const mapDispatchToProps = {
  getEmployees,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TenantDocumentUpdate);
