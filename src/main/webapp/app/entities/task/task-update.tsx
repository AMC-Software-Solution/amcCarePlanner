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
import { IServiceOrder } from 'app/shared/model/service-order.model';
import { getEntities as getServiceOrders } from 'app/entities/service-order/service-order.reducer';
import { getEntity, updateEntity, createEntity, reset } from './task.reducer';
import { ITask } from 'app/shared/model/task.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITaskUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TaskUpdate = (props: ITaskUpdateProps) => {
  const [serviceUserId, setServiceUserId] = useState('0');
  const [assignedToId, setAssignedToId] = useState('0');
  const [createdById, setCreatedById] = useState('0');
  const [allocatedById, setAllocatedById] = useState('0');
  const [serviceOrderId, setServiceOrderId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { taskEntity, serviceUsers, employees, serviceOrders, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/task' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getServiceUsers();
    props.getEmployees();
    props.getServiceOrders();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.dateCreated = convertDateTimeToServer(values.dateCreated);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...taskEntity,
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
          <h2 id="carePlannerApp.task.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.task.home.createOrEditLabel">Create or edit a Task</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : taskEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="task-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="task-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="taskNameLabel" for="task-taskName">
                  <Translate contentKey="carePlannerApp.task.taskName">Task Name</Translate>
                </Label>
                <AvField
                  id="task-taskName"
                  type="text"
                  name="taskName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="task-description">
                  <Translate contentKey="carePlannerApp.task.description">Description</Translate>
                </Label>
                <AvField id="task-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="dateOfTaskLabel" for="task-dateOfTask">
                  <Translate contentKey="carePlannerApp.task.dateOfTask">Date Of Task</Translate>
                </Label>
                <AvField
                  id="task-dateOfTask"
                  type="date"
                  className="form-control"
                  name="dateOfTask"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="startTimeLabel" for="task-startTime">
                  <Translate contentKey="carePlannerApp.task.startTime">Start Time</Translate>
                </Label>
                <AvField
                  id="task-startTime"
                  type="text"
                  name="startTime"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endTimeLabel" for="task-endTime">
                  <Translate contentKey="carePlannerApp.task.endTime">End Time</Translate>
                </Label>
                <AvField id="task-endTime" type="text" name="endTime" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="task-status">
                  <Translate contentKey="carePlannerApp.task.status">Status</Translate>
                </Label>
                <AvInput
                  id="task-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && taskEntity.status) || 'ASSSIGNED'}
                >
                  <option value="ASSSIGNED">{translate('carePlannerApp.TaskStatus.ASSSIGNED')}</option>
                  <option value="INPROGRESS">{translate('carePlannerApp.TaskStatus.INPROGRESS')}</option>
                  <option value="CANCELLED_BY_CLIENT">{translate('carePlannerApp.TaskStatus.CANCELLED_BY_CLIENT')}</option>
                  <option value="CANCELLED_BY_EMPLOYEE">{translate('carePlannerApp.TaskStatus.CANCELLED_BY_EMPLOYEE')}</option>
                  <option value="MISSED">{translate('carePlannerApp.TaskStatus.MISSED')}</option>
                  <option value="COMPLETED">{translate('carePlannerApp.TaskStatus.COMPLETED')}</option>
                  <option value="REJECTED">{translate('carePlannerApp.TaskStatus.REJECTED')}</option>
                  <option value="AVAILABLE">{translate('carePlannerApp.TaskStatus.AVAILABLE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="dateCreatedLabel" for="task-dateCreated">
                  <Translate contentKey="carePlannerApp.task.dateCreated">Date Created</Translate>
                </Label>
                <AvInput
                  id="task-dateCreated"
                  type="datetime-local"
                  className="form-control"
                  name="dateCreated"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.taskEntity.dateCreated)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="task-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.task.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="task-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.taskEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tenantIdLabel" for="task-tenantId">
                  <Translate contentKey="carePlannerApp.task.tenantId">Tenant Id</Translate>
                </Label>
                <AvField
                  id="task-tenantId"
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
                <Label for="task-serviceUser">
                  <Translate contentKey="carePlannerApp.task.serviceUser">Service User</Translate>
                </Label>
                <AvInput id="task-serviceUser" type="select" className="form-control" name="serviceUserId">
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
                <Label for="task-assignedTo">
                  <Translate contentKey="carePlannerApp.task.assignedTo">Assigned To</Translate>
                </Label>
                <AvInput id="task-assignedTo" type="select" className="form-control" name="assignedToId">
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
                <Label for="task-serviceOrder">
                  <Translate contentKey="carePlannerApp.task.serviceOrder">Service Order</Translate>
                </Label>
                <AvInput id="task-serviceOrder" type="select" className="form-control" name="serviceOrderId">
                  <option value="" key="0" />
                  {serviceOrders
                    ? serviceOrders.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.title}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="task-createdBy">
                  <Translate contentKey="carePlannerApp.task.createdBy">Created By</Translate>
                </Label>
                <AvInput id="task-createdBy" type="select" className="form-control" name="createdById">
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
                <Label for="task-allocatedBy">
                  <Translate contentKey="carePlannerApp.task.allocatedBy">Allocated By</Translate>
                </Label>
                <AvInput id="task-allocatedBy" type="select" className="form-control" name="allocatedById">
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
              <Button tag={Link} id="cancel-save" to="/task" replace color="info">
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
  serviceOrders: storeState.serviceOrder.entities,
  taskEntity: storeState.task.entity,
  loading: storeState.task.loading,
  updating: storeState.task.updating,
  updateSuccess: storeState.task.updateSuccess,
});

const mapDispatchToProps = {
  getServiceUsers,
  getEmployees,
  getServiceOrders,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TaskUpdate);
