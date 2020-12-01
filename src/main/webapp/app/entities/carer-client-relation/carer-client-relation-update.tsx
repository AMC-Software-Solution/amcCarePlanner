import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { IServiceUser } from 'app/shared/model/service-user.model';
import { getEntities as getServiceUsers } from 'app/entities/service-user/service-user.reducer';
import { getEntity, updateEntity, createEntity, reset } from './carer-client-relation.reducer';
import { ICarerClientRelation } from 'app/shared/model/carer-client-relation.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICarerClientRelationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CarerClientRelationUpdate = (props: ICarerClientRelationUpdateProps) => {
  const [employeeId, setEmployeeId] = useState('0');
  const [serviceUserId, setServiceUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { carerClientRelationEntity, employees, serviceUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/carer-client-relation' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEmployees();
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
        ...carerClientRelationEntity,
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
          <h2 id="carePlannerApp.carerClientRelation.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.carerClientRelation.home.createOrEditLabel">
              Create or edit a CarerClientRelation
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : carerClientRelationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="carer-client-relation-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="carer-client-relation-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="relationTypeLabel" for="carer-client-relation-relationType">
                  <Translate contentKey="carePlannerApp.carerClientRelation.relationType">Relation Type</Translate>
                </Label>
                <AvInput
                  id="carer-client-relation-relationType"
                  type="select"
                  className="form-control"
                  name="relationType"
                  value={(!isNew && carerClientRelationEntity.relationType) || 'CLIENT_UNFAVOURS_EMPLOYEE'}
                >
                  <option value="CLIENT_UNFAVOURS_EMPLOYEE">{translate('carePlannerApp.RelationType.CLIENT_UNFAVOURS_EMPLOYEE')}</option>
                  <option value="EMPLOYEE_VISITED_CLIENT_BEFORE">
                    {translate('carePlannerApp.RelationType.EMPLOYEE_VISITED_CLIENT_BEFORE')}
                  </option>
                  <option value="CLIENT_PREFERRED_EMPLOYEE">{translate('carePlannerApp.RelationType.CLIENT_PREFERRED_EMPLOYEE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="reasonLabel" for="carer-client-relation-reason">
                  <Translate contentKey="carePlannerApp.carerClientRelation.reason">Reason</Translate>
                </Label>
                <AvField id="carer-client-relation-reason" type="text" name="reason" />
              </AvGroup>
              <AvGroup>
                <Label id="countLabel" for="carer-client-relation-count">
                  <Translate contentKey="carePlannerApp.carerClientRelation.count">Count</Translate>
                </Label>
                <AvField id="carer-client-relation-count" type="string" className="form-control" name="count" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="carer-client-relation-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.carerClientRelation.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="carer-client-relation-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.carerClientRelationEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="carer-client-relation-clientId">
                  <Translate contentKey="carePlannerApp.carerClientRelation.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="carer-client-relation-clientId"
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
                <Label for="carer-client-relation-employee">
                  <Translate contentKey="carePlannerApp.carerClientRelation.employee">Employee</Translate>
                </Label>
                <AvInput id="carer-client-relation-employee" type="select" className="form-control" name="employeeId">
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
                <Label for="carer-client-relation-serviceUser">
                  <Translate contentKey="carePlannerApp.carerClientRelation.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="carer-client-relation-serviceUser" type="select" className="form-control" name="serviceUserId">
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
              <Button tag={Link} id="cancel-save" to="/carer-client-relation" replace color="info">
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
  serviceUsers: storeState.serviceUser.entities,
  carerClientRelationEntity: storeState.carerClientRelation.entity,
  loading: storeState.carerClientRelation.loading,
  updating: storeState.carerClientRelation.updating,
  updateSuccess: storeState.carerClientRelation.updateSuccess,
});

const mapDispatchToProps = {
  getEmployees,
  getServiceUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CarerClientRelationUpdate);
