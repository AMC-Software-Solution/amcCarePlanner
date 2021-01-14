import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee-availability.reducer';
import { IEmployeeAvailability } from 'app/shared/model/employee-availability.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeAvailabilityDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeAvailabilityDetail = (props: IEmployeeAvailabilityDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeAvailabilityEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.employeeAvailability.detail.title">EmployeeAvailability</Translate> [
          <b>{employeeAvailabilityEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="availableForWork">
              <Translate contentKey="carePlannerApp.employeeAvailability.availableForWork">Available For Work</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.availableForWork ? 'true' : 'false'}</dd>
          <dt>
            <span id="availableMonday">
              <Translate contentKey="carePlannerApp.employeeAvailability.availableMonday">Available Monday</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.availableMonday ? 'true' : 'false'}</dd>
          <dt>
            <span id="availableTuesday">
              <Translate contentKey="carePlannerApp.employeeAvailability.availableTuesday">Available Tuesday</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.availableTuesday ? 'true' : 'false'}</dd>
          <dt>
            <span id="availableWednesday">
              <Translate contentKey="carePlannerApp.employeeAvailability.availableWednesday">Available Wednesday</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.availableWednesday ? 'true' : 'false'}</dd>
          <dt>
            <span id="availableThursday">
              <Translate contentKey="carePlannerApp.employeeAvailability.availableThursday">Available Thursday</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.availableThursday ? 'true' : 'false'}</dd>
          <dt>
            <span id="availableFriday">
              <Translate contentKey="carePlannerApp.employeeAvailability.availableFriday">Available Friday</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.availableFriday ? 'true' : 'false'}</dd>
          <dt>
            <span id="availableSaturday">
              <Translate contentKey="carePlannerApp.employeeAvailability.availableSaturday">Available Saturday</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.availableSaturday ? 'true' : 'false'}</dd>
          <dt>
            <span id="availableSunday">
              <Translate contentKey="carePlannerApp.employeeAvailability.availableSunday">Available Sunday</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.availableSunday ? 'true' : 'false'}</dd>
          <dt>
            <span id="preferredShift">
              <Translate contentKey="carePlannerApp.employeeAvailability.preferredShift">Preferred Shift</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.preferredShift}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.employeeAvailability.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.hasExtraData ? 'true' : 'false'}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.employeeAvailability.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeAvailabilityEntity.lastUpdatedDate ? (
              <TextFormat value={employeeAvailabilityEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.employeeAvailability.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.clientId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.employeeAvailability.employee">Employee</Translate>
          </dt>
          <dd>{employeeAvailabilityEntity.employeeEmployeeCode ? employeeAvailabilityEntity.employeeEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-availability" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-availability/${employeeAvailabilityEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employeeAvailability }: IRootState) => ({
  employeeAvailabilityEntity: employeeAvailability.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeAvailabilityDetail);
