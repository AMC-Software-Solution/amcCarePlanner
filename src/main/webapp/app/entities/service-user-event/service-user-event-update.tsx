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
import { getEntity, updateEntity, createEntity, reset } from './service-user-event.reducer';
import { IServiceUserEvent } from 'app/shared/model/service-user-event.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServiceUserEventUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceUserEventUpdate = (props: IServiceUserEventUpdateProps) => {
  const [reportedById, setReportedById] = useState('0');
  const [assignedToId, setAssignedToId] = useState('0');
  const [serviceUserId, setServiceUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { serviceUserEventEntity, employees, serviceUsers, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/service-user-event' + props.location.search);
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
    values.dateOfEvent = convertDateTimeToServer(values.dateOfEvent);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...serviceUserEventEntity,
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
          <h2 id="carePlannerApp.serviceUserEvent.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.serviceUserEvent.home.createOrEditLabel">Create or edit a ServiceUserEvent</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : serviceUserEventEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="service-user-event-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="service-user-event-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="eventTitleLabel" for="service-user-event-eventTitle">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.eventTitle">Event Title</Translate>
                </Label>
                <AvField
                  id="service-user-event-eventTitle"
                  type="text"
                  name="eventTitle"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="service-user-event-description">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.description">Description</Translate>
                </Label>
                <AvField id="service-user-event-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="serviceUserEventStatusLabel" for="service-user-event-serviceUserEventStatus">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.serviceUserEventStatus">Service User Event Status</Translate>
                </Label>
                <AvInput
                  id="service-user-event-serviceUserEventStatus"
                  type="select"
                  className="form-control"
                  name="serviceUserEventStatus"
                  value={(!isNew && serviceUserEventEntity.serviceUserEventStatus) || 'REPORTED'}
                >
                  <option value="REPORTED">{translate('carePlannerApp.ServiceUserEventStatus.REPORTED')}</option>
                  <option value="UNDER_INVESTIGATION">{translate('carePlannerApp.ServiceUserEventStatus.UNDER_INVESTIGATION')}</option>
                  <option value="RESOLVED">{translate('carePlannerApp.ServiceUserEventStatus.RESOLVED')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="serviceUserEventTypeLabel" for="service-user-event-serviceUserEventType">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.serviceUserEventType">Service User Event Type</Translate>
                </Label>
                <AvInput
                  id="service-user-event-serviceUserEventType"
                  type="select"
                  className="form-control"
                  name="serviceUserEventType"
                  value={(!isNew && serviceUserEventEntity.serviceUserEventType) || 'ACCIDENT'}
                >
                  <option value="ACCIDENT">{translate('carePlannerApp.ServiceUserEventType.ACCIDENT')}</option>
                  <option value="INCIDENT">{translate('carePlannerApp.ServiceUserEventType.INCIDENT')}</option>
                  <option value="SAFEGUARDING">{translate('carePlannerApp.ServiceUserEventType.SAFEGUARDING')}</option>
                  <option value="MEDICAL_ERROR">{translate('carePlannerApp.ServiceUserEventType.MEDICAL_ERROR')}</option>
                  <option value="COMPLIMENT">{translate('carePlannerApp.ServiceUserEventType.COMPLIMENT')}</option>
                  <option value="COMPLAINCE">{translate('carePlannerApp.ServiceUserEventType.COMPLAINCE')}</option>
                  <option value="COMMENT">{translate('carePlannerApp.ServiceUserEventType.COMMENT')}</option>
                  <option value="MISSED_VISIT">{translate('carePlannerApp.ServiceUserEventType.MISSED_VISIT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="priorityLabel" for="service-user-event-priority">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.priority">Priority</Translate>
                </Label>
                <AvInput
                  id="service-user-event-priority"
                  type="select"
                  className="form-control"
                  name="priority"
                  value={(!isNew && serviceUserEventEntity.priority) || 'HIGH'}
                >
                  <option value="HIGH">{translate('carePlannerApp.ServicePriority.HIGH')}</option>
                  <option value="LOW">{translate('carePlannerApp.ServicePriority.LOW')}</option>
                  <option value="MEDIUM">{translate('carePlannerApp.ServicePriority.MEDIUM')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="service-user-event-note">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.note">Note</Translate>
                </Label>
                <AvField id="service-user-event-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="dateOfEventLabel" for="service-user-event-dateOfEvent">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.dateOfEvent">Date Of Event</Translate>
                </Label>
                <AvInput
                  id="service-user-event-dateOfEvent"
                  type="datetime-local"
                  className="form-control"
                  name="dateOfEvent"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.serviceUserEventEntity.dateOfEvent)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="service-user-event-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="service-user-event-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.serviceUserEventEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="service-user-event-clientId">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="service-user-event-clientId"
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
                <Label for="service-user-event-reportedBy">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.reportedBy">Reported By</Translate>
                </Label>
                <AvInput id="service-user-event-reportedBy" type="select" className="form-control" name="reportedById">
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
                <Label for="service-user-event-assignedTo">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.assignedTo">Assigned To</Translate>
                </Label>
                <AvInput id="service-user-event-assignedTo" type="select" className="form-control" name="assignedToId">
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
                <Label for="service-user-event-serviceUser">
                  <Translate contentKey="carePlannerApp.serviceUserEvent.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="service-user-event-serviceUser" type="select" className="form-control" name="serviceUserId">
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
              <Button tag={Link} id="cancel-save" to="/service-user-event" replace color="info">
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
  serviceUserEventEntity: storeState.serviceUserEvent.entity,
  loading: storeState.serviceUserEvent.loading,
  updating: storeState.serviceUserEvent.updating,
  updateSuccess: storeState.serviceUserEvent.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ServiceUserEventUpdate);
