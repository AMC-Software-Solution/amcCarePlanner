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
import { getEntity, updateEntity, createEntity, setBlob, reset } from './consent.reducer';
import { IConsent } from 'app/shared/model/consent.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConsentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConsentUpdate = (props: IConsentUpdateProps) => {
  const [serviceUserId, setServiceUserId] = useState('0');
  const [witnessedById, setWitnessedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { consentEntity, serviceUsers, employees, loading, updating } = props;

  const { signatureImage, signatureImageContentType } = consentEntity;

  const handleClose = () => {
    props.history.push('/consent' + props.location.search);
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
    values.consentDate = convertDateTimeToServer(values.consentDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...consentEntity,
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
          <h2 id="carePlannerApp.consent.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.consent.home.createOrEditLabel">Create or edit a Consent</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : consentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="consent-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="consent-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="consent-title">
                  <Translate contentKey="carePlannerApp.consent.title">Title</Translate>
                </Label>
                <AvField
                  id="consent-title"
                  type="text"
                  name="title"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="consent-description">
                  <Translate contentKey="carePlannerApp.consent.description">Description</Translate>
                </Label>
                <AvField id="consent-description" type="text" name="description" />
              </AvGroup>
              <AvGroup check>
                <Label id="giveConsentLabel">
                  <AvInput id="consent-giveConsent" type="checkbox" className="form-check-input" name="giveConsent" />
                  <Translate contentKey="carePlannerApp.consent.giveConsent">Give Consent</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="arrangementsLabel" for="consent-arrangements">
                  <Translate contentKey="carePlannerApp.consent.arrangements">Arrangements</Translate>
                </Label>
                <AvField id="consent-arrangements" type="text" name="arrangements" />
              </AvGroup>
              <AvGroup>
                <Label id="serviceUserSignatureLabel" for="consent-serviceUserSignature">
                  <Translate contentKey="carePlannerApp.consent.serviceUserSignature">Service User Signature</Translate>
                </Label>
                <AvField id="consent-serviceUserSignature" type="text" name="serviceUserSignature" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="signatureImageLabel" for="signatureImage">
                    <Translate contentKey="carePlannerApp.consent.signatureImage">Signature Image</Translate>
                  </Label>
                  <br />
                  {signatureImage ? (
                    <div>
                      {signatureImageContentType ? (
                        <a onClick={openFile(signatureImageContentType, signatureImage)}>
                          <img src={`data:${signatureImageContentType};base64,${signatureImage}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {signatureImageContentType}, {byteSize(signatureImage)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('signatureImage')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_signatureImage" type="file" onChange={onBlobChange(true, 'signatureImage')} accept="image/*" />
                  <AvInput type="hidden" name="signatureImage" value={signatureImage} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="signatureImageUrlLabel" for="consent-signatureImageUrl">
                  <Translate contentKey="carePlannerApp.consent.signatureImageUrl">Signature Image Url</Translate>
                </Label>
                <AvField id="consent-signatureImageUrl" type="text" name="signatureImageUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="consentDateLabel" for="consent-consentDate">
                  <Translate contentKey="carePlannerApp.consent.consentDate">Consent Date</Translate>
                </Label>
                <AvInput
                  id="consent-consentDate"
                  type="datetime-local"
                  className="form-control"
                  name="consentDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.consentEntity.consentDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="consent-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.consent.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="consent-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.consentEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tenantIdLabel" for="consent-tenantId">
                  <Translate contentKey="carePlannerApp.consent.tenantId">Tenant Id</Translate>
                </Label>
                <AvField
                  id="consent-tenantId"
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
                <Label for="consent-serviceUser">
                  <Translate contentKey="carePlannerApp.consent.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="consent-serviceUser" type="select" className="form-control" name="serviceUserId">
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
                <Label for="consent-witnessedBy">
                  <Translate contentKey="carePlannerApp.consent.witnessedBy">Witnessed By</Translate>
                </Label>
                <AvInput id="consent-witnessedBy" type="select" className="form-control" name="witnessedById">
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
              <Button tag={Link} id="cancel-save" to="/consent" replace color="info">
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
  consentEntity: storeState.consent.entity,
  loading: storeState.consent.loading,
  updating: storeState.consent.updating,
  updateSuccess: storeState.consent.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ConsentUpdate);
