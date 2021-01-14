import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './timesheet.reducer';
import { ITimesheet } from 'app/shared/model/timesheet.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITimesheetDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TimesheetDetail = (props: ITimesheetDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { timesheetEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.timesheet.detail.title">Timesheet</Translate> [<b>{timesheetEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.timesheet.description">Description</Translate>
            </span>
          </dt>
          <dd>{timesheetEntity.description}</dd>
          <dt>
            <span id="timesheetDate">
              <Translate contentKey="carePlannerApp.timesheet.timesheetDate">Timesheet Date</Translate>
            </span>
          </dt>
          <dd>
            {timesheetEntity.timesheetDate ? (
              <TextFormat value={timesheetEntity.timesheetDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="startTime">
              <Translate contentKey="carePlannerApp.timesheet.startTime">Start Time</Translate>
            </span>
          </dt>
          <dd>{timesheetEntity.startTime}</dd>
          <dt>
            <span id="endTime">
              <Translate contentKey="carePlannerApp.timesheet.endTime">End Time</Translate>
            </span>
          </dt>
          <dd>{timesheetEntity.endTime}</dd>
          <dt>
            <span id="hoursWorked">
              <Translate contentKey="carePlannerApp.timesheet.hoursWorked">Hours Worked</Translate>
            </span>
          </dt>
          <dd>{timesheetEntity.hoursWorked}</dd>
          <dt>
            <span id="breakStartTime">
              <Translate contentKey="carePlannerApp.timesheet.breakStartTime">Break Start Time</Translate>
            </span>
          </dt>
          <dd>{timesheetEntity.breakStartTime}</dd>
          <dt>
            <span id="breakEndTime">
              <Translate contentKey="carePlannerApp.timesheet.breakEndTime">Break End Time</Translate>
            </span>
          </dt>
          <dd>{timesheetEntity.breakEndTime}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="carePlannerApp.timesheet.note">Note</Translate>
            </span>
          </dt>
          <dd>{timesheetEntity.note}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.timesheet.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {timesheetEntity.createdDate ? <TextFormat value={timesheetEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.timesheet.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {timesheetEntity.lastUpdatedDate ? (
              <TextFormat value={timesheetEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.timesheet.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{timesheetEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.timesheet.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{timesheetEntity.hasExtraData ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.timesheet.task">Task</Translate>
          </dt>
          <dd>{timesheetEntity.taskTaskName ? timesheetEntity.taskTaskName : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.timesheet.serviceUser">Service User</Translate>
          </dt>
          <dd>{timesheetEntity.serviceUserServiceUserCode ? timesheetEntity.serviceUserServiceUserCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.timesheet.careProvider">Care Provider</Translate>
          </dt>
          <dd>{timesheetEntity.careProviderEmployeeCode ? timesheetEntity.careProviderEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/timesheet" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/timesheet/${timesheetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ timesheet }: IRootState) => ({
  timesheetEntity: timesheet.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TimesheetDetail);
