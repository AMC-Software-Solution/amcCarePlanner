import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
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
            <span id="isAvailableForWork">
              <Translate contentKey="carePlannerApp.employeeAvailability.isAvailableForWork">Is Available For Work</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.isAvailableForWork ? 'true' : 'false'}</dd>
          <dt>
            <span id="minimumHoursPerWeek">
              <Translate contentKey="carePlannerApp.employeeAvailability.minimumHoursPerWeek">Minimum Hours Per Week</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.minimumHoursPerWeek}</dd>
          <dt>
            <span id="maximumHoursPerWeek">
              <Translate contentKey="carePlannerApp.employeeAvailability.maximumHoursPerWeek">Maximum Hours Per Week</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.maximumHoursPerWeek}</dd>
          <dt>
            <span id="leastPreferredShift">
              <Translate contentKey="carePlannerApp.employeeAvailability.leastPreferredShift">Least Preferred Shift</Translate>
            </span>
          </dt>
          <dd>{employeeAvailabilityEntity.leastPreferredShift}</dd>
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
