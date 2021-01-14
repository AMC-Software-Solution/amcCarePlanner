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
import { getEntity, updateEntity, createEntity, setBlob, reset } from './client-document.reducer';
import { IClientDocument } from 'app/shared/model/client-document.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IClientDocumentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ClientDocumentUpdate = (props: IClientDocumentUpdateProps) => {
  const [uploadedById, setUploadedById] = useState('0');
  const [approvedById, setApprovedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { clientDocumentEntity, employees, loading, updating } = props;

  const { documentFile, documentFileContentType } = clientDocumentEntity;

  const handleClose = () => {
    props.history.push('/client-document' + props.location.search);
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
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...clientDocumentEntity,
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
          <h2 id="carePlannerApp.clientDocument.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.clientDocument.home.createOrEditLabel">Create or edit a ClientDocument</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : clientDocumentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="client-document-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="client-document-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="documentNameLabel" for="client-document-documentName">
                  <Translate contentKey="carePlannerApp.clientDocument.documentName">Document Name</Translate>
                </Label>
                <AvField
                  id="client-document-documentName"
                  type="text"
                  name="documentName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="documentNumberLabel" for="client-document-documentNumber">
                  <Translate contentKey="carePlannerApp.clientDocument.documentNumber">Document Number</Translate>
                </Label>
                <AvField id="client-document-documentNumber" type="text" name="documentNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="documentTypeLabel" for="client-document-documentType">
                  <Translate contentKey="carePlannerApp.clientDocument.documentType">Document Type</Translate>
                </Label>
                <AvInput
                  id="client-document-documentType"
                  type="select"
                  className="form-control"
                  name="documentType"
                  value={(!isNew && clientDocumentEntity.documentType) || 'POLICY'}
                >
                  <option value="POLICY">{translate('carePlannerApp.ClientDocumentType.POLICY')}</option>
                  <option value="PROCEDURE">{translate('carePlannerApp.ClientDocumentType.PROCEDURE')}</option>
                  <option value="FORM">{translate('carePlannerApp.ClientDocumentType.FORM')}</option>
                  <option value="OTHER">{translate('carePlannerApp.ClientDocumentType.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="documentStatusLabel" for="client-document-documentStatus">
                  <Translate contentKey="carePlannerApp.clientDocument.documentStatus">Document Status</Translate>
                </Label>
                <AvInput
                  id="client-document-documentStatus"
                  type="select"
                  className="form-control"
                  name="documentStatus"
                  value={(!isNew && clientDocumentEntity.documentStatus) || 'EXPIRED'}
                >
                  <option value="EXPIRED">{translate('carePlannerApp.DocumentStatus.EXPIRED')}</option>
                  <option value="ACTIVE">{translate('carePlannerApp.DocumentStatus.ACTIVE')}</option>
                  <option value="ARCHIVED">{translate('carePlannerApp.DocumentStatus.ARCHIVED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="client-document-note">
                  <Translate contentKey="carePlannerApp.clientDocument.note">Note</Translate>
                </Label>
                <AvField id="client-document-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="issuedDateLabel" for="client-document-issuedDate">
                  <Translate contentKey="carePlannerApp.clientDocument.issuedDate">Issued Date</Translate>
                </Label>
                <AvField id="client-document-issuedDate" type="date" className="form-control" name="issuedDate" />
              </AvGroup>
              <AvGroup>
                <Label id="expiryDateLabel" for="client-document-expiryDate">
                  <Translate contentKey="carePlannerApp.clientDocument.expiryDate">Expiry Date</Translate>
                </Label>
                <AvField id="client-document-expiryDate" type="date" className="form-control" name="expiryDate" />
              </AvGroup>
              <AvGroup>
                <Label id="uploadedDateLabel" for="client-document-uploadedDate">
                  <Translate contentKey="carePlannerApp.clientDocument.uploadedDate">Uploaded Date</Translate>
                </Label>
                <AvInput
                  id="client-document-uploadedDate"
                  type="datetime-local"
                  className="form-control"
                  name="uploadedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.clientDocumentEntity.uploadedDate)}
                />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="documentFileLabel" for="documentFile">
                    <Translate contentKey="carePlannerApp.clientDocument.documentFile">Document File</Translate>
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
                <Label id="documentFileUrlLabel" for="client-document-documentFileUrl">
                  <Translate contentKey="carePlannerApp.clientDocument.documentFileUrl">Document File Url</Translate>
                </Label>
                <AvField id="client-document-documentFileUrl" type="text" name="documentFileUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="client-document-createdDate">
                  <Translate contentKey="carePlannerApp.clientDocument.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="client-document-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.clientDocumentEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="client-document-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.clientDocument.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="client-document-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.clientDocumentEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="client-document-clientId">
                  <Translate contentKey="carePlannerApp.clientDocument.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="client-document-clientId"
                  type="string"
                  className="form-control"
                  name="clientId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="hasExtraDataLabel">
                  <AvInput id="client-document-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.clientDocument.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="client-document-uploadedBy">
                  <Translate contentKey="carePlannerApp.clientDocument.uploadedBy">Uploaded By</Translate>
                </Label>
                <AvInput id="client-document-uploadedBy" type="select" className="form-control" name="uploadedById">
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
                <Label for="client-document-approvedBy">
                  <Translate contentKey="carePlannerApp.clientDocument.approvedBy">Approved By</Translate>
                </Label>
                <AvInput id="client-document-approvedBy" type="select" className="form-control" name="approvedById">
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
              <Button tag={Link} id="cancel-save" to="/client-document" replace color="info">
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
  clientDocumentEntity: storeState.clientDocument.entity,
  loading: storeState.clientDocument.loading,
  updating: storeState.clientDocument.updating,
  updateSuccess: storeState.clientDocument.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ClientDocumentUpdate);
