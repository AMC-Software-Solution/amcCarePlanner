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
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, reset } from './power-of-attorney.reducer';
import { IPowerOfAttorney } from 'app/shared/model/power-of-attorney.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPowerOfAttorneyUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PowerOfAttorneyUpdate = (props: IPowerOfAttorneyUpdateProps) => {
  const [serviceUserId, setServiceUserId] = useState('0');
  const [witnessedById, setWitnessedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { powerOfAttorneyEntity, serviceUsers, employees, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/power-of-attorney' + props.location.search);
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

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...powerOfAttorneyEntity,
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
          <h2 id="carePlannerApp.powerOfAttorney.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.powerOfAttorney.home.createOrEditLabel">Create or edit a PowerOfAttorney</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : powerOfAttorneyEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="power-of-attorney-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="power-of-attorney-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup check>
                <Label id="powerOfAttorneyConsentLabel">
                  <AvInput
                    id="power-of-attorney-powerOfAttorneyConsent"
                    type="checkbox"
                    className="form-check-input"
                    name="powerOfAttorneyConsent"
                  />
                  <Translate contentKey="carePlannerApp.powerOfAttorney.powerOfAttorneyConsent">Power Of Attorney Consent</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="healthAndWelfareLabel">
                  <AvInput id="power-of-attorney-healthAndWelfare" type="checkbox" className="form-check-input" name="healthAndWelfare" />
                  <Translate contentKey="carePlannerApp.powerOfAttorney.healthAndWelfare">Health And Welfare</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="healthAndWelfareNameLabel" for="power-of-attorney-healthAndWelfareName">
                  <Translate contentKey="carePlannerApp.powerOfAttorney.healthAndWelfareName">Health And Welfare Name</Translate>
                </Label>
                <AvField id="power-of-attorney-healthAndWelfareName" type="text" name="healthAndWelfareName" />
              </AvGroup>
              <AvGroup check>
                <Label id="propertyAndFinAffairsLabel">
                  <AvInput
                    id="power-of-attorney-propertyAndFinAffairs"
                    type="checkbox"
                    className="form-check-input"
                    name="propertyAndFinAffairs"
                  />
                  <Translate contentKey="carePlannerApp.powerOfAttorney.propertyAndFinAffairs">Property And Fin Affairs</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="propertyAndFinAffairsNameLabel" for="power-of-attorney-propertyAndFinAffairsName">
                  <Translate contentKey="carePlannerApp.powerOfAttorney.propertyAndFinAffairsName">Property And Fin Affairs Name</Translate>
                </Label>
                <AvField id="power-of-attorney-propertyAndFinAffairsName" type="text" name="propertyAndFinAffairsName" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="power-of-attorney-createdDate">
                  <Translate contentKey="carePlannerApp.powerOfAttorney.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="power-of-attorney-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.powerOfAttorneyEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="power-of-attorney-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.powerOfAttorney.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="power-of-attorney-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.powerOfAttorneyEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="power-of-attorney-clientId">
                  <Translate contentKey="carePlannerApp.powerOfAttorney.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="power-of-attorney-clientId"
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
                  <AvInput id="power-of-attorney-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.powerOfAttorney.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="power-of-attorney-serviceUser">
                  <Translate contentKey="carePlannerApp.powerOfAttorney.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="power-of-attorney-serviceUser" type="select" className="form-control" name="serviceUserId">
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
                <Label for="power-of-attorney-witnessedBy">
                  <Translate contentKey="carePlannerApp.powerOfAttorney.witnessedBy">Witnessed By</Translate>
                </Label>
                <AvInput id="power-of-attorney-witnessedBy" type="select" className="form-control" name="witnessedById">
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
              <Button tag={Link} id="cancel-save" to="/power-of-attorney" replace color="info">
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
  powerOfAttorneyEntity: storeState.powerOfAttorney.entity,
  loading: storeState.powerOfAttorney.loading,
  updating: storeState.powerOfAttorney.updating,
  updateSuccess: storeState.powerOfAttorney.updateSuccess,
});

const mapDispatchToProps = {
  getServiceUsers,
  getEmployees,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PowerOfAttorneyUpdate);
