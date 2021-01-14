import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITask } from 'app/shared/model/task.model';
import { getEntities as getTasks } from 'app/entities/task/task.reducer';
import { IServiceUser } from 'app/shared/model/service-user.model';
import { getEntities as getServiceUsers } from 'app/entities/service-user/service-user.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, reset } from './timesheet.reducer';
import { ITimesheet } from 'app/shared/model/timesheet.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITimesheetUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TimesheetUpdate = (props: ITimesheetUpdateProps) => {
  const [taskId, setTaskId] = useState('0');
  const [serviceUserId, setServiceUserId] = useState('0');
  const [careProviderId, setCareProviderId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { timesheetEntity, tasks, serviceUsers, employees, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/timesheet' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTasks();
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
        ...timesheetEntity,
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
          <h2 id="carePlannerApp.timesheet.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.timesheet.home.createOrEditLabel">Create or edit a Timesheet</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : timesheetEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="timesheet-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="timesheet-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descriptionLabel" for="timesheet-description">
                  <Translate contentKey="carePlannerApp.timesheet.description">Description</Translate>
                </Label>
                <AvField id="timesheet-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="timesheetDateLabel" for="timesheet-timesheetDate">
                  <Translate contentKey="carePlannerApp.timesheet.timesheetDate">Timesheet Date</Translate>
                </Label>
                <AvField
                  id="timesheet-timesheetDate"
                  type="date"
                  className="form-control"
                  name="timesheetDate"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="startTimeLabel" for="timesheet-startTime">
                  <Translate contentKey="carePlannerApp.timesheet.startTime">Start Time</Translate>
                </Label>
                <AvField
                  id="timesheet-startTime"
                  type="text"
                  name="startTime"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endTimeLabel" for="timesheet-endTime">
                  <Translate contentKey="carePlannerApp.timesheet.endTime">End Time</Translate>
                </Label>
                <AvField
                  id="timesheet-endTime"
                  type="text"
                  name="endTime"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="hoursWorkedLabel" for="timesheet-hoursWorked">
                  <Translate contentKey="carePlannerApp.timesheet.hoursWorked">Hours Worked</Translate>
                </Label>
                <AvField
                  id="timesheet-hoursWorked"
                  type="string"
                  className="form-control"
                  name="hoursWorked"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="breakStartTimeLabel" for="timesheet-breakStartTime">
                  <Translate contentKey="carePlannerApp.timesheet.breakStartTime">Break Start Time</Translate>
                </Label>
                <AvField id="timesheet-breakStartTime" type="text" name="breakStartTime" />
              </AvGroup>
              <AvGroup>
                <Label id="breakEndTimeLabel" for="timesheet-breakEndTime">
                  <Translate contentKey="carePlannerApp.timesheet.breakEndTime">Break End Time</Translate>
                </Label>
                <AvField id="timesheet-breakEndTime" type="text" name="breakEndTime" />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="timesheet-note">
                  <Translate contentKey="carePlannerApp.timesheet.note">Note</Translate>
                </Label>
                <AvField id="timesheet-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="timesheet-createdDate">
                  <Translate contentKey="carePlannerApp.timesheet.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="timesheet-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.timesheetEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="timesheet-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.timesheet.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="timesheet-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.timesheetEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="timesheet-clientId">
                  <Translate contentKey="carePlannerApp.timesheet.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="timesheet-clientId"
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
                  <AvInput id="timesheet-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.timesheet.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="timesheet-task">
                  <Translate contentKey="carePlannerApp.timesheet.task">Task</Translate>
                </Label>
                <AvInput id="timesheet-task" type="select" className="form-control" name="taskId">
                  <option value="" key="0" />
                  {tasks
                    ? tasks.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.taskName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="timesheet-serviceUser">
                  <Translate contentKey="carePlannerApp.timesheet.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="timesheet-serviceUser" type="select" className="form-control" name="serviceUserId">
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
                <Label for="timesheet-careProvider">
                  <Translate contentKey="carePlannerApp.timesheet.careProvider">Care Provider</Translate>
                </Label>
                <AvInput id="timesheet-careProvider" type="select" className="form-control" name="careProviderId">
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
              <Button tag={Link} id="cancel-save" to="/timesheet" replace color="info">
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
  tasks: storeState.task.entities,
  serviceUsers: storeState.serviceUser.entities,
  employees: storeState.employee.entities,
  timesheetEntity: storeState.timesheet.entity,
  loading: storeState.timesheet.loading,
  updating: storeState.timesheet.updating,
  updateSuccess: storeState.timesheet.updateSuccess,
});

const mapDispatchToProps = {
  getTasks,
  getServiceUsers,
  getEmployees,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TimesheetUpdate);
