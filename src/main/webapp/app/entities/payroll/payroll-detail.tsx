import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './payroll.reducer';
import { IPayroll } from 'app/shared/model/payroll.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPayrollDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PayrollDetail = (props: IPayrollDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { payrollEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.payroll.detail.title">Payroll</Translate> [<b>{payrollEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="paymentDate">
              <Translate contentKey="carePlannerApp.payroll.paymentDate">Payment Date</Translate>
            </span>
          </dt>
          <dd>
            {payrollEntity.paymentDate ? <TextFormat value={payrollEntity.paymentDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="payPeriod">
              <Translate contentKey="carePlannerApp.payroll.payPeriod">Pay Period</Translate>
            </span>
          </dt>
          <dd>{payrollEntity.payPeriod}</dd>
          <dt>
            <span id="totalHoursWorked">
              <Translate contentKey="carePlannerApp.payroll.totalHoursWorked">Total Hours Worked</Translate>
            </span>
          </dt>
          <dd>{payrollEntity.totalHoursWorked}</dd>
          <dt>
            <span id="grossPay">
              <Translate contentKey="carePlannerApp.payroll.grossPay">Gross Pay</Translate>
            </span>
          </dt>
          <dd>{payrollEntity.grossPay}</dd>
          <dt>
            <span id="netPay">
              <Translate contentKey="carePlannerApp.payroll.netPay">Net Pay</Translate>
            </span>
          </dt>
          <dd>{payrollEntity.netPay}</dd>
          <dt>
            <span id="totalTax">
              <Translate contentKey="carePlannerApp.payroll.totalTax">Total Tax</Translate>
            </span>
          </dt>
          <dd>{payrollEntity.totalTax}</dd>
          <dt>
            <span id="payrollStatus">
              <Translate contentKey="carePlannerApp.payroll.payrollStatus">Payroll Status</Translate>
            </span>
          </dt>
          <dd>{payrollEntity.payrollStatus}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.payroll.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {payrollEntity.lastUpdatedDate ? (
              <TextFormat value={payrollEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.payroll.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{payrollEntity.clientId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.payroll.employee">Employee</Translate>
          </dt>
          <dd>{payrollEntity.employeeEmployeeCode ? payrollEntity.employeeEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.payroll.timesheet">Timesheet</Translate>
          </dt>
          <dd>{payrollEntity.timesheetDescription ? payrollEntity.timesheetDescription : ''}</dd>
        </dl>
        <Button tag={Link} to="/payroll" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payroll/${payrollEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ payroll }: IRootState) => ({
  payrollEntity: payroll.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PayrollDetail);
