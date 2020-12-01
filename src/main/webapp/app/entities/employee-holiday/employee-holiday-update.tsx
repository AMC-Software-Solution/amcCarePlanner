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
import { getEntity, updateEntity, createEntity, reset } from './employee-holiday.reducer';
import { IEmployeeHoliday } from 'app/shared/model/employee-holiday.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeHolidayUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeHolidayUpdate = (props: IEmployeeHolidayUpdateProps) => {
  const [employeeId, setEmployeeId] = useState('0');
  const [approvedById, setApprovedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { employeeHolidayEntity, employees, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/employee-holiday' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEmployees();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);
    values.approvedDate = convertDateTimeToServer(values.approvedDate);
    values.requestedDate = convertDateTimeToServer(values.requestedDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...employeeHolidayEntity,
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
          <h2 id="carePlannerApp.employeeHoliday.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.employeeHoliday.home.createOrEditLabel">Create or edit a EmployeeHoliday</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : employeeHolidayEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="employee-holiday-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="employee-holiday-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descriptionLabel" for="employee-holiday-description">
                  <Translate contentKey="carePlannerApp.employeeHoliday.description">Description</Translate>
                </Label>
                <AvField
                  id="employee-holiday-description"
                  type="text"
                  name="description"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="startDateLabel" for="employee-holiday-startDate">
                  <Translate contentKey="carePlannerApp.employeeHoliday.startDate">Start Date</Translate>
                </Label>
                <AvInput
                  id="employee-holiday-startDate"
                  type="datetime-local"
                  className="form-control"
                  name="startDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeHolidayEntity.startDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="employee-holiday-endDate">
                  <Translate contentKey="carePlannerApp.employeeHoliday.endDate">End Date</Translate>
                </Label>
                <AvInput
                  id="employee-holiday-endDate"
                  type="datetime-local"
                  className="form-control"
                  name="endDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeHolidayEntity.endDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="employeeHolidayTypeLabel" for="employee-holiday-employeeHolidayType">
                  <Translate contentKey="carePlannerApp.employeeHoliday.employeeHolidayType">Employee Holiday Type</Translate>
                </Label>
                <AvInput
                  id="employee-holiday-employeeHolidayType"
                  type="select"
                  className="form-control"
                  name="employeeHolidayType"
                  value={(!isNew && employeeHolidayEntity.employeeHolidayType) || 'ANNUAL_HOLIDAY'}
                >
                  <option value="ANNUAL_HOLIDAY">{translate('carePlannerApp.EmployeeHolidayType.ANNUAL_HOLIDAY')}</option>
                  <option value="PUBLIC_HOLIDAY">{translate('carePlannerApp.EmployeeHolidayType.PUBLIC_HOLIDAY')}</option>
                  <option value="PARENTAL_LEAVE">{translate('carePlannerApp.EmployeeHolidayType.PARENTAL_LEAVE')}</option>
                  <option value="SICKNESS_ABSENCE">{translate('carePlannerApp.EmployeeHolidayType.SICKNESS_ABSENCE')}</option>
                  <option value="UNPAID_HOLIDAY">{translate('carePlannerApp.EmployeeHolidayType.UNPAID_HOLIDAY')}</option>
                  <option value="OTHER">{translate('carePlannerApp.EmployeeHolidayType.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="approvedDateLabel" for="employee-holiday-approvedDate">
                  <Translate contentKey="carePlannerApp.employeeHoliday.approvedDate">Approved Date</Translate>
                </Label>
                <AvInput
                  id="employee-holiday-approvedDate"
                  type="datetime-local"
                  className="form-control"
                  name="approvedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeHolidayEntity.approvedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="requestedDateLabel" for="employee-holiday-requestedDate">
                  <Translate contentKey="carePlannerApp.employeeHoliday.requestedDate">Requested Date</Translate>
                </Label>
                <AvInput
                  id="employee-holiday-requestedDate"
                  type="datetime-local"
                  className="form-control"
                  name="requestedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeHolidayEntity.requestedDate)}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="approvedLabel">
                  <AvInput id="employee-holiday-approved" type="checkbox" className="form-check-input" name="approved" />
                  <Translate contentKey="carePlannerApp.employeeHoliday.approved">Approved</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="employee-holiday-note">
                  <Translate contentKey="carePlannerApp.employeeHoliday.note">Note</Translate>
                </Label>
                <AvField id="employee-holiday-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="employee-holiday-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.employeeHoliday.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="employee-holiday-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeHolidayEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="employee-holiday-clientId">
                  <Translate contentKey="carePlannerApp.employeeHoliday.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="employee-holiday-clientId"
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
                <Label for="employee-holiday-employee">
                  <Translate contentKey="carePlannerApp.employeeHoliday.employee">Employee</Translate>
                </Label>
                <AvInput id="employee-holiday-employee" type="select" className="form-control" name="employeeId">
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
                <Label for="employee-holiday-approvedBy">
                  <Translate contentKey="carePlannerApp.employeeHoliday.approvedBy">Approved By</Translate>
                </Label>
                <AvInput id="employee-holiday-approvedBy" type="select" className="form-control" name="approvedById">
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
              <Button tag={Link} id="cancel-save" to="/employee-holiday" replace color="info">
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
  employeeHolidayEntity: storeState.employeeHoliday.entity,
  loading: storeState.employeeHoliday.loading,
  updating: storeState.employeeHoliday.updating,
  updateSuccess: storeState.employeeHoliday.updateSuccess,
});

const mapDispatchToProps = {
  getEmployees,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeHolidayUpdate);
