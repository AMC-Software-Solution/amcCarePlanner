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
import { ITimesheet } from 'app/shared/model/timesheet.model';
import { getEntities as getTimesheets } from 'app/entities/timesheet/timesheet.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payroll.reducer';
import { IPayroll } from 'app/shared/model/payroll.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPayrollUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PayrollUpdate = (props: IPayrollUpdateProps) => {
  const [employeeId, setEmployeeId] = useState('0');
  const [timesheetId, setTimesheetId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { payrollEntity, employees, timesheets, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/payroll' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEmployees();
    props.getTimesheets();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.paymentDate = convertDateTimeToServer(values.paymentDate);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...payrollEntity,
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
          <h2 id="carePlannerApp.payroll.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.payroll.home.createOrEditLabel">Create or edit a Payroll</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : payrollEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="payroll-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="payroll-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="paymentDateLabel" for="payroll-paymentDate">
                  <Translate contentKey="carePlannerApp.payroll.paymentDate">Payment Date</Translate>
                </Label>
                <AvInput
                  id="payroll-paymentDate"
                  type="datetime-local"
                  className="form-control"
                  name="paymentDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.payrollEntity.paymentDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="payPeriodLabel" for="payroll-payPeriod">
                  <Translate contentKey="carePlannerApp.payroll.payPeriod">Pay Period</Translate>
                </Label>
                <AvField
                  id="payroll-payPeriod"
                  type="text"
                  name="payPeriod"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="totalHoursWorkedLabel" for="payroll-totalHoursWorked">
                  <Translate contentKey="carePlannerApp.payroll.totalHoursWorked">Total Hours Worked</Translate>
                </Label>
                <AvField
                  id="payroll-totalHoursWorked"
                  type="string"
                  className="form-control"
                  name="totalHoursWorked"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="grossPayLabel" for="payroll-grossPay">
                  <Translate contentKey="carePlannerApp.payroll.grossPay">Gross Pay</Translate>
                </Label>
                <AvField
                  id="payroll-grossPay"
                  type="text"
                  name="grossPay"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="netPayLabel" for="payroll-netPay">
                  <Translate contentKey="carePlannerApp.payroll.netPay">Net Pay</Translate>
                </Label>
                <AvField
                  id="payroll-netPay"
                  type="text"
                  name="netPay"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="totalTaxLabel" for="payroll-totalTax">
                  <Translate contentKey="carePlannerApp.payroll.totalTax">Total Tax</Translate>
                </Label>
                <AvField id="payroll-totalTax" type="text" name="totalTax" />
              </AvGroup>
              <AvGroup>
                <Label id="payrollStatusLabel" for="payroll-payrollStatus">
                  <Translate contentKey="carePlannerApp.payroll.payrollStatus">Payroll Status</Translate>
                </Label>
                <AvInput
                  id="payroll-payrollStatus"
                  type="select"
                  className="form-control"
                  name="payrollStatus"
                  value={(!isNew && payrollEntity.payrollStatus) || 'CREATED'}
                >
                  <option value="CREATED">{translate('carePlannerApp.PayrollStatus.CREATED')}</option>
                  <option value="PROCESSING">{translate('carePlannerApp.PayrollStatus.PROCESSING')}</option>
                  <option value="PAID">{translate('carePlannerApp.PayrollStatus.PAID')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="payroll-createdDate">
                  <Translate contentKey="carePlannerApp.payroll.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="payroll-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.payrollEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="payroll-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.payroll.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="payroll-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.payrollEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="payroll-clientId">
                  <Translate contentKey="carePlannerApp.payroll.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="payroll-clientId"
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
                  <AvInput id="payroll-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.payroll.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="payroll-employee">
                  <Translate contentKey="carePlannerApp.payroll.employee">Employee</Translate>
                </Label>
                <AvInput id="payroll-employee" type="select" className="form-control" name="employeeId">
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
                <Label for="payroll-timesheet">
                  <Translate contentKey="carePlannerApp.payroll.timesheet">Timesheet</Translate>
                </Label>
                <AvInput id="payroll-timesheet" type="select" className="form-control" name="timesheetId">
                  <option value="" key="0" />
                  {timesheets
                    ? timesheets.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.description}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/payroll" replace color="info">
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
  timesheets: storeState.timesheet.entities,
  payrollEntity: storeState.payroll.entity,
  loading: storeState.payroll.loading,
  updating: storeState.payroll.updating,
  updateSuccess: storeState.payroll.updateSuccess,
});

const mapDispatchToProps = {
  getEmployees,
  getTimesheets,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PayrollUpdate);
