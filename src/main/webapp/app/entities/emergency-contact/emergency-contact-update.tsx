import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IServiceUser } from 'app/shared/model/service-user.model';
import { getEntities as getServiceUsers } from 'app/entities/service-user/service-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './emergency-contact.reducer';
import { IEmergencyContact } from 'app/shared/model/emergency-contact.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmergencyContactUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmergencyContactUpdate = (props: IEmergencyContactUpdateProps) => {
  const [serviceUserId, setServiceUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { emergencyContactEntity, serviceUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/emergency-contact' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getServiceUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...emergencyContactEntity,
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
          <h2 id="carePlannerApp.emergencyContact.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.emergencyContact.home.createOrEditLabel">Create or edit a EmergencyContact</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : emergencyContactEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="emergency-contact-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="emergency-contact-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="emergency-contact-name">
                  <Translate contentKey="carePlannerApp.emergencyContact.name">Name</Translate>
                </Label>
                <AvField
                  id="emergency-contact-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="contactRelationshipLabel" for="emergency-contact-contactRelationship">
                  <Translate contentKey="carePlannerApp.emergencyContact.contactRelationship">Contact Relationship</Translate>
                </Label>
                <AvField
                  id="emergency-contact-contactRelationship"
                  type="text"
                  name="contactRelationship"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isKeyHolderLabel">
                  <AvInput id="emergency-contact-isKeyHolder" type="checkbox" className="form-check-input" name="isKeyHolder" />
                  <Translate contentKey="carePlannerApp.emergencyContact.isKeyHolder">Is Key Holder</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="infoSharingConsentGivenLabel">
                  <AvInput
                    id="emergency-contact-infoSharingConsentGiven"
                    type="checkbox"
                    className="form-check-input"
                    name="infoSharingConsentGiven"
                  />
                  <Translate contentKey="carePlannerApp.emergencyContact.infoSharingConsentGiven">Info Sharing Consent Given</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="preferredContactNumberLabel" for="emergency-contact-preferredContactNumber">
                  <Translate contentKey="carePlannerApp.emergencyContact.preferredContactNumber">Preferred Contact Number</Translate>
                </Label>
                <AvField
                  id="emergency-contact-preferredContactNumber"
                  type="text"
                  name="preferredContactNumber"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="fullAddressLabel" for="emergency-contact-fullAddress">
                  <Translate contentKey="carePlannerApp.emergencyContact.fullAddress">Full Address</Translate>
                </Label>
                <AvField id="emergency-contact-fullAddress" type="text" name="fullAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="emergency-contact-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.emergencyContact.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="emergency-contact-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.emergencyContactEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="emergency-contact-clientId">
                  <Translate contentKey="carePlannerApp.emergencyContact.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="emergency-contact-clientId"
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
                <Label for="emergency-contact-serviceUser">
                  <Translate contentKey="carePlannerApp.emergencyContact.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="emergency-contact-serviceUser" type="select" className="form-control" name="serviceUserId">
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
              <Button tag={Link} id="cancel-save" to="/emergency-contact" replace color="info">
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
  emergencyContactEntity: storeState.emergencyContact.entity,
  loading: storeState.emergencyContact.loading,
  updating: storeState.emergencyContact.updating,
  updateSuccess: storeState.emergencyContact.updateSuccess,
});

const mapDispatchToProps = {
  getServiceUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmergencyContactUpdate);
