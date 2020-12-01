import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IServiceUser } from 'app/shared/model/service-user.model';
import { getEntities as getServiceUsers } from 'app/entities/service-user/service-user.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './servce-user-document.reducer';
import { IServceUserDocument } from 'app/shared/model/servce-user-document.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServceUserDocumentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServceUserDocumentUpdate = (props: IServceUserDocumentUpdateProps) => {
  const [ownerId, setOwnerId] = useState('0');
  const [approvedById, setApprovedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { servceUserDocumentEntity, serviceUsers, employees, loading, updating } = props;

  const { documentFile, documentFileContentType } = servceUserDocumentEntity;

  const handleClose = () => {
    props.history.push('/servce-user-document' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getServiceUsers();
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
        ...servceUserDocumentEntity,
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
          <h2 id="carePlannerApp.servceUserDocument.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.servceUserDocument.home.createOrEditLabel">Create or edit a ServceUserDocument</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : servceUserDocumentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="servce-user-document-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="servce-user-document-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="documentNameLabel" for="servce-user-document-documentName">
                  <Translate contentKey="carePlannerApp.servceUserDocument.documentName">Document Name</Translate>
                </Label>
                <AvField
                  id="servce-user-document-documentName"
                  type="text"
                  name="documentName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="documentNumberLabel" for="servce-user-document-documentNumber">
                  <Translate contentKey="carePlannerApp.servceUserDocument.documentNumber">Document Number</Translate>
                </Label>
                <AvField id="servce-user-document-documentNumber" type="text" name="documentNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="documentStatusLabel" for="servce-user-document-documentStatus">
                  <Translate contentKey="carePlannerApp.servceUserDocument.documentStatus">Document Status</Translate>
                </Label>
                <AvInput
                  id="servce-user-document-documentStatus"
                  type="select"
                  className="form-control"
                  name="documentStatus"
                  value={(!isNew && servceUserDocumentEntity.documentStatus) || 'EXPIRED'}
                >
                  <option value="EXPIRED">{translate('carePlannerApp.DocumentStatus.EXPIRED')}</option>
                  <option value="ACTIVE">{translate('carePlannerApp.DocumentStatus.ACTIVE')}</option>
                  <option value="ARCHIVED">{translate('carePlannerApp.DocumentStatus.ARCHIVED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="servce-user-document-note">
                  <Translate contentKey="carePlannerApp.servceUserDocument.note">Note</Translate>
                </Label>
                <AvField id="servce-user-document-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="issuedDateLabel" for="servce-user-document-issuedDate">
                  <Translate contentKey="carePlannerApp.servceUserDocument.issuedDate">Issued Date</Translate>
                </Label>
                <AvField id="servce-user-document-issuedDate" type="date" className="form-control" name="issuedDate" />
              </AvGroup>
              <AvGroup>
                <Label id="expiryDateLabel" for="servce-user-document-expiryDate">
                  <Translate contentKey="carePlannerApp.servceUserDocument.expiryDate">Expiry Date</Translate>
                </Label>
                <AvField id="servce-user-document-expiryDate" type="date" className="form-control" name="expiryDate" />
              </AvGroup>
              <AvGroup>
                <Label id="uploadedDateLabel" for="servce-user-document-uploadedDate">
                  <Translate contentKey="carePlannerApp.servceUserDocument.uploadedDate">Uploaded Date</Translate>
                </Label>
                <AvInput
                  id="servce-user-document-uploadedDate"
                  type="datetime-local"
                  className="form-control"
                  name="uploadedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.servceUserDocumentEntity.uploadedDate)}
                />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="documentFileLabel" for="documentFile">
                    <Translate contentKey="carePlannerApp.servceUserDocument.documentFile">Document File</Translate>
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
                <Label id="documentFileUrlLabel" for="servce-user-document-documentFileUrl">
                  <Translate contentKey="carePlannerApp.servceUserDocument.documentFileUrl">Document File Url</Translate>
                </Label>
                <AvField id="servce-user-document-documentFileUrl" type="text" name="documentFileUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="servce-user-document-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.servceUserDocument.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="servce-user-document-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.servceUserDocumentEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="servce-user-document-clientId">
                  <Translate contentKey="carePlannerApp.servceUserDocument.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="servce-user-document-clientId"
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
                <Label for="servce-user-document-owner">
                  <Translate contentKey="carePlannerApp.servceUserDocument.owner">Owner</Translate>
                </Label>
                <AvInput id="servce-user-document-owner" type="select" className="form-control" name="ownerId">
                  <option value="" key="0" />
                  {serviceUsers
                    ? serviceUsers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.serviceUserCode}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="servce-user-document-approvedBy">
                  <Translate contentKey="carePlannerApp.servceUserDocument.approvedBy">Approved By</Translate>
                </Label>
                <AvInput id="servce-user-document-approvedBy" type="select" className="form-control" name="approvedById">
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
              <Button tag={Link} id="cancel-save" to="/servce-user-document" replace color="info">
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
  serviceUsers: storeState.serviceUser.entities,
  employees: storeState.employee.entities,
  servceUserDocumentEntity: storeState.servceUserDocument.entity,
  loading: storeState.servceUserDocument.loading,
  updating: storeState.servceUserDocument.updating,
  updateSuccess: storeState.servceUserDocument.updateSuccess,
});

const mapDispatchToProps = {
  getServiceUsers,
  getEmployees,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServceUserDocumentUpdate);
