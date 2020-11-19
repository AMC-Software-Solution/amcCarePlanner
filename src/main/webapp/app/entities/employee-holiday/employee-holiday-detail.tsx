import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee-holiday.reducer';
import { IEmployeeHoliday } from 'app/shared/model/employee-holiday.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeHolidayDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeHolidayDetail = (props: IEmployeeHolidayDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeHolidayEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.employeeHoliday.detail.title">EmployeeHoliday</Translate> [<b>{employeeHolidayEntity.id}</b>
          ]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="description">
              <Translate contentKey="carePlannerApp.employeeHoliday.description">Description</Translate>
            </span>
          </dt>
          <dd>{employeeHolidayEntity.description}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="carePlannerApp.employeeHoliday.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeHolidayEntity.startDate ? (
              <TextFormat value={employeeHolidayEntity.startDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="carePlannerApp.employeeHoliday.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeHolidayEntity.endDate ? (
              <TextFormat value={employeeHolidayEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="employeeHolidayType">
              <Translate contentKey="carePlannerApp.employeeHoliday.employeeHolidayType">Employee Holiday Type</Translate>
            </span>
          </dt>
          <dd>{employeeHolidayEntity.employeeHolidayType}</dd>
          <dt>
            <span id="approvedDate">
              <Translate contentKey="carePlannerApp.employeeHoliday.approvedDate">Approved Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeHolidayEntity.approvedDate ? (
              <TextFormat value={employeeHolidayEntity.approvedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="requestedDate">
              <Translate contentKey="carePlannerApp.employeeHoliday.requestedDate">Requested Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeHolidayEntity.requestedDate ? (
              <TextFormat value={employeeHolidayEntity.requestedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="approved">
              <Translate contentKey="carePlannerApp.employeeHoliday.approved">Approved</Translate>
            </span>
          </dt>
          <dd>{employeeHolidayEntity.approved ? 'true' : 'false'}</dd>
          <dt>
            <span id="note">
              <Translate contentKey="carePlannerApp.employeeHoliday.note">Note</Translate>
            </span>
          </dt>
          <dd>{employeeHolidayEntity.note}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.employeeHoliday.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeHolidayEntity.lastUpdatedDate ? (
              <TextFormat value={employeeHolidayEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tenantId">
              <Translate contentKey="carePlannerApp.employeeHoliday.tenantId">Tenant Id</Translate>
            </span>
          </dt>
          <dd>{employeeHolidayEntity.tenantId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.employeeHoliday.employee">Employee</Translate>
          </dt>
          <dd>{employeeHolidayEntity.employeeEmployeeCode ? employeeHolidayEntity.employeeEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.employeeHoliday.approvedBy">Approved By</Translate>
          </dt>
          <dd>{employeeHolidayEntity.approvedByEmployeeCode ? employeeHolidayEntity.approvedByEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-holiday" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-holiday/${employeeHolidayEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employeeHoliday }: IRootState) => ({
  employeeHolidayEntity: employeeHoliday.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeHolidayDetail);
