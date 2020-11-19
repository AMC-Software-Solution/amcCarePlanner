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
import { getEntity, updateEntity, createEntity, reset } from './medical-contact.reducer';
import { IMedicalContact } from 'app/shared/model/medical-contact.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMedicalContactUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MedicalContactUpdate = (props: IMedicalContactUpdateProps) => {
  const [serviceUserId, setServiceUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { medicalContactEntity, serviceUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/medical-contact' + props.location.search);
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
    values.lastVisitedDoctor = convertDateTimeToServer(values.lastVisitedDoctor);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...medicalContactEntity,
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
          <h2 id="carePlannerApp.medicalContact.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.medicalContact.home.createOrEditLabel">Create or edit a MedicalContact</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : medicalContactEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="medical-contact-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="medical-contact-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="doctorNameLabel" for="medical-contact-doctorName">
                  <Translate contentKey="carePlannerApp.medicalContact.doctorName">Doctor Name</Translate>
                </Label>
                <AvField id="medical-contact-doctorName" type="text" name="doctorName" />
              </AvGroup>
              <AvGroup>
                <Label id="doctorSurgeryLabel" for="medical-contact-doctorSurgery">
                  <Translate contentKey="carePlannerApp.medicalContact.doctorSurgery">Doctor Surgery</Translate>
                </Label>
                <AvField id="medical-contact-doctorSurgery" type="text" name="doctorSurgery" />
              </AvGroup>
              <AvGroup>
                <Label id="doctorAddressLabel" for="medical-contact-doctorAddress">
                  <Translate contentKey="carePlannerApp.medicalContact.doctorAddress">Doctor Address</Translate>
                </Label>
                <AvField id="medical-contact-doctorAddress" type="text" name="doctorAddress" />
              </AvGroup>
              <AvGroup>
                <Label id="doctorPhoneLabel" for="medical-contact-doctorPhone">
                  <Translate contentKey="carePlannerApp.medicalContact.doctorPhone">Doctor Phone</Translate>
                </Label>
                <AvField id="medical-contact-doctorPhone" type="text" name="doctorPhone" />
              </AvGroup>
              <AvGroup>
                <Label id="lastVisitedDoctorLabel" for="medical-contact-lastVisitedDoctor">
                  <Translate contentKey="carePlannerApp.medicalContact.lastVisitedDoctor">Last Visited Doctor</Translate>
                </Label>
                <AvInput
                  id="medical-contact-lastVisitedDoctor"
                  type="datetime-local"
                  className="form-control"
                  name="lastVisitedDoctor"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.medicalContactEntity.lastVisitedDoctor)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="districtNurseNameLabel" for="medical-contact-districtNurseName">
                  <Translate contentKey="carePlannerApp.medicalContact.districtNurseName">District Nurse Name</Translate>
                </Label>
                <AvField id="medical-contact-districtNurseName" type="text" name="districtNurseName" />
              </AvGroup>
              <AvGroup>
                <Label id="districtNursePhoneLabel" for="medical-contact-districtNursePhone">
                  <Translate contentKey="carePlannerApp.medicalContact.districtNursePhone">District Nurse Phone</Translate>
                </Label>
                <AvField id="medical-contact-districtNursePhone" type="text" name="districtNursePhone" />
              </AvGroup>
              <AvGroup>
                <Label id="careManagerNameLabel" for="medical-contact-careManagerName">
                  <Translate contentKey="carePlannerApp.medicalContact.careManagerName">Care Manager Name</Translate>
                </Label>
                <AvField id="medical-contact-careManagerName" type="text" name="careManagerName" />
              </AvGroup>
              <AvGroup>
                <Label id="careManagerPhoneLabel" for="medical-contact-careManagerPhone">
                  <Translate contentKey="carePlannerApp.medicalContact.careManagerPhone">Care Manager Phone</Translate>
                </Label>
                <AvField id="medical-contact-careManagerPhone" type="text" name="careManagerPhone" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="medical-contact-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.medicalContact.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="medical-contact-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.medicalContactEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tenantIdLabel" for="medical-contact-tenantId">
                  <Translate contentKey="carePlannerApp.medicalContact.tenantId">Tenant Id</Translate>
                </Label>
                <AvField
                  id="medical-contact-tenantId"
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
                <Label for="medical-contact-serviceUser">
                  <Translate contentKey="carePlannerApp.medicalContact.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="medical-contact-serviceUser" type="select" className="form-control" name="serviceUserId">
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
              <Button tag={Link} id="cancel-save" to="/medical-contact" replace color="info">
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
  medicalContactEntity: storeState.medicalContact.entity,
  loading: storeState.medicalContact.loading,
  updating: storeState.medicalContact.updating,
  updateSuccess: storeState.medicalContact.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(MedicalContactUpdate);
