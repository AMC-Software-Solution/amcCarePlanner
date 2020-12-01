import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDocumentType } from 'app/shared/model/document-type.model';
import { getEntities as getDocumentTypes } from 'app/entities/document-type/document-type.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './employee-document.reducer';
import { IEmployeeDocument } from 'app/shared/model/employee-document.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeDocumentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeDocumentUpdate = (props: IEmployeeDocumentUpdateProps) => {
  const [documentTypeId, setDocumentTypeId] = useState('0');
  const [employeeId, setEmployeeId] = useState('0');
  const [approvedById, setApprovedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { employeeDocumentEntity, documentTypes, employees, loading, updating } = props;

  const { documentFile, documentFileContentType } = employeeDocumentEntity;

  const handleClose = () => {
    props.history.push('/employee-document' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDocumentTypes();
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
        ...employeeDocumentEntity,
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
          <h2 id="carePlannerApp.employeeDocument.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.employeeDocument.home.createOrEditLabel">Create or edit a EmployeeDocument</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : employeeDocumentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="employee-document-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="employee-document-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="documentNameLabel" for="employee-document-documentName">
                  <Translate contentKey="carePlannerApp.employeeDocument.documentName">Document Name</Translate>
                </Label>
                <AvField
                  id="employee-document-documentName"
                  type="text"
                  name="documentName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="documentNumberLabel" for="employee-document-documentNumber">
                  <Translate contentKey="carePlannerApp.employeeDocument.documentNumber">Document Number</Translate>
                </Label>
                <AvField id="employee-document-documentNumber" type="text" name="documentNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="documentStatusLabel" for="employee-document-documentStatus">
                  <Translate contentKey="carePlannerApp.employeeDocument.documentStatus">Document Status</Translate>
                </Label>
                <AvInput
                  id="employee-document-documentStatus"
                  type="select"
                  className="form-control"
                  name="documentStatus"
                  value={(!isNew && employeeDocumentEntity.documentStatus) || 'EXPIRED'}
                >
                  <option value="EXPIRED">{translate('carePlannerApp.DocumentStatus.EXPIRED')}</option>
                  <option value="ACTIVE">{translate('carePlannerApp.DocumentStatus.ACTIVE')}</option>
                  <option value="ARCHIVED">{translate('carePlannerApp.DocumentStatus.ARCHIVED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="employee-document-note">
                  <Translate contentKey="carePlannerApp.employeeDocument.note">Note</Translate>
                </Label>
                <AvField id="employee-document-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="issuedDateLabel" for="employee-document-issuedDate">
                  <Translate contentKey="carePlannerApp.employeeDocument.issuedDate">Issued Date</Translate>
                </Label>
                <AvField id="employee-document-issuedDate" type="date" className="form-control" name="issuedDate" />
              </AvGroup>
              <AvGroup>
                <Label id="expiryDateLabel" for="employee-document-expiryDate">
                  <Translate contentKey="carePlannerApp.employeeDocument.expiryDate">Expiry Date</Translate>
                </Label>
                <AvField id="employee-document-expiryDate" type="date" className="form-control" name="expiryDate" />
              </AvGroup>
              <AvGroup>
                <Label id="uploadedDateLabel" for="employee-document-uploadedDate">
                  <Translate contentKey="carePlannerApp.employeeDocument.uploadedDate">Uploaded Date</Translate>
                </Label>
                <AvInput
                  id="employee-document-uploadedDate"
                  type="datetime-local"
                  className="form-control"
                  name="uploadedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeDocumentEntity.uploadedDate)}
                />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="documentFileLabel" for="documentFile">
                    <Translate contentKey="carePlannerApp.employeeDocument.documentFile">Document File</Translate>
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
                <Label id="documentFileUrlLabel" for="employee-document-documentFileUrl">
                  <Translate contentKey="carePlannerApp.employeeDocument.documentFileUrl">Document File Url</Translate>
                </Label>
                <AvField id="employee-document-documentFileUrl" type="text" name="documentFileUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="employee-document-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.employeeDocument.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="employee-document-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeDocumentEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="employee-document-clientId">
                  <Translate contentKey="carePlannerApp.employeeDocument.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="employee-document-clientId"
                  type="string"
                  className="form-control"
                  name="clientId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="employee-document-documentType">
                  <Translate contentKey="carePlannerApp.employeeDocument.documentType">Document Type</Translate>
                </Label>
                <AvInput id="employee-document-documentType" type="select" className="form-control" name="documentTypeId">
                  <option value="" key="0" />
                  {documentTypes
                    ? documentTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.documentTypeTitle}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="employee-document-employee">
                  <Translate contentKey="carePlannerApp.employeeDocument.employee">Employee</Translate>
                </Label>
                <AvInput id="employee-document-employee" type="select" className="form-control" name="employeeId">
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
                <Label for="employee-document-approvedBy">
                  <Translate contentKey="carePlannerApp.employeeDocument.approvedBy">Approved By</Translate>
                </Label>
                <AvInput id="employee-document-approvedBy" type="select" className="form-control" name="approvedById">
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
              <Button tag={Link} id="cancel-save" to="/employee-document" replace color="info">
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
  documentTypes: storeState.documentType.entities,
  employees: storeState.employee.entities,
  employeeDocumentEntity: storeState.employeeDocument.entity,
  loading: storeState.employeeDocument.loading,
  updating: storeState.employeeDocument.updating,
  updateSuccess: storeState.employeeDocument.updateSuccess,
});

const mapDispatchToProps = {
  getDocumentTypes,
  getEmployees,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeDocumentUpdate);
