import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRelationshipType } from 'app/shared/model/relationship-type.model';
import { getEntities as getRelationshipTypes } from 'app/entities/relationship-type/relationship-type.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IServiceUser } from 'app/shared/model/service-user.model';
import { getEntities as getServiceUsers } from 'app/entities/service-user/service-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './carer-service-user-relation.reducer';
import { ICarerServiceUserRelation } from 'app/shared/model/carer-service-user-relation.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICarerServiceUserRelationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CarerServiceUserRelationUpdate = (props: ICarerServiceUserRelationUpdateProps) => {
  const [relationTypeId, setRelationTypeId] = useState('0');
  const [employeeId, setEmployeeId] = useState('0');
  const [serviceUserId, setServiceUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { carerServiceUserRelationEntity, relationshipTypes, employees, serviceUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/carer-service-user-relation' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getRelationshipTypes();
    props.getEmployees();
    props.getServiceUsers();
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
        ...carerServiceUserRelationEntity,
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
          <h2 id="carePlannerApp.carerServiceUserRelation.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.carerServiceUserRelation.home.createOrEditLabel">
              Create or edit a CarerServiceUserRelation
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : carerServiceUserRelationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="carer-service-user-relation-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="carer-service-user-relation-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="reasonLabel" for="carer-service-user-relation-reason">
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.reason">Reason</Translate>
                </Label>
                <AvField id="carer-service-user-relation-reason" type="text" name="reason" />
              </AvGroup>
              <AvGroup>
                <Label id="countLabel" for="carer-service-user-relation-count">
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.count">Count</Translate>
                </Label>
                <AvField id="carer-service-user-relation-count" type="string" className="form-control" name="count" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="carer-service-user-relation-createdDate">
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="carer-service-user-relation-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.carerServiceUserRelationEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="carer-service-user-relation-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="carer-service-user-relation-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.carerServiceUserRelationEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="carer-service-user-relation-clientId">
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="carer-service-user-relation-clientId"
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
                  <AvInput id="carer-service-user-relation-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="carer-service-user-relation-relationType">
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.relationType">Relation Type</Translate>
                </Label>
                <AvInput id="carer-service-user-relation-relationType" type="select" className="form-control" name="relationTypeId">
                  <option value="" key="0" />
                  {relationshipTypes
                    ? relationshipTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.relationType}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="carer-service-user-relation-employee">
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.employee">Employee</Translate>
                </Label>
                <AvInput id="carer-service-user-relation-employee" type="select" className="form-control" name="employeeId">
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
                <Label for="carer-service-user-relation-serviceUser">
                  <Translate contentKey="carePlannerApp.carerServiceUserRelation.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="carer-service-user-relation-serviceUser" type="select" className="form-control" name="serviceUserId">
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
              <Button tag={Link} id="cancel-save" to="/carer-service-user-relation" replace color="info">
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
  relationshipTypes: storeState.relationshipType.entities,
  employees: storeState.employee.entities,
  serviceUsers: storeState.serviceUser.entities,
  carerServiceUserRelationEntity: storeState.carerServiceUserRelation.entity,
  loading: storeState.carerServiceUserRelation.loading,
  updating: storeState.carerServiceUserRelation.updating,
  updateSuccess: storeState.carerServiceUserRelation.updateSuccess,
});

const mapDispatchToProps = {
  getRelationshipTypes,
  getEmployees,
  getServiceUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CarerServiceUserRelationUpdate);
