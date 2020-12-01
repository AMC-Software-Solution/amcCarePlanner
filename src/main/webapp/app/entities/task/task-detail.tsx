import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './task.reducer';
import { ITask } from 'app/shared/model/task.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITaskDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TaskDetail = (props: ITaskDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { taskEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.task.detail.title">Task</Translate> [<b>{taskEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="taskName">
              <Translate contentKey="carePlannerApp.task.taskName">Task Name</Translate>
            </span>
          </dt>
          <dd>{taskEntity.taskName}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.task.description">Description</Translate>
            </span>
          </dt>
          <dd>{taskEntity.description}</dd>
          <dt>
            <span id="dateOfTask">
              <Translate contentKey="carePlannerApp.task.dateOfTask">Date Of Task</Translate>
            </span>
          </dt>
          <dd>{taskEntity.dateOfTask ? <TextFormat value={taskEntity.dateOfTask} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="carePlannerApp.task.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>{taskEntity.startTime}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="carePlannerApp.task.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{taskEntity.endTime}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="carePlannerApp.task.status">Status</Translate>
            </span>
          </dt>
          <dd>{taskEntity.status}</dd>
          <dt>
            <span id="dateCreated">
              <Translate contentKey="carePlannerApp.task.dateCreated">Date Created</Translate>
            </span>
          </dt>
          <dd>{taskEntity.dateCreated ? <TextFormat value={taskEntity.dateCreated} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.task.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {taskEntity.lastUpdatedDate ? <TextFormat value={taskEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.task.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{taskEntity.clientId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.task.serviceUser">Service User</Translate>
          </dt>
          <dd>{taskEntity.serviceUserServiceUserCode ? taskEntity.serviceUserServiceUserCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.task.assignedTo">Assigned To</Translate>
          </dt>
          <dd>{taskEntity.assignedToEmployeeCode ? taskEntity.assignedToEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.task.serviceOrder">Service Order</Translate>
          </dt>
          <dd>{taskEntity.serviceOrderTitle ? taskEntity.serviceOrderTitle : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.task.createdBy">Created By</Translate>
          </dt>
          <dd>{taskEntity.createdByEmployeeCode ? taskEntity.createdByEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.task.allocatedBy">Allocated By</Translate>
          </dt>
          <dd>{taskEntity.allocatedByEmployeeCode ? taskEntity.allocatedByEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/task" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/task/${taskEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ task }: IRootState) => ({
  taskEntity: task.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TaskDetail);
