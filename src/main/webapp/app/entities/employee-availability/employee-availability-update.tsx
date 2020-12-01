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
import { getEntity, updateEntity, createEntity, reset } from './employee-availability.reducer';
import { IEmployeeAvailability } from 'app/shared/model/employee-availability.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeAvailabilityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeAvailabilityUpdate = (props: IEmployeeAvailabilityUpdateProps) => {
  const [employeeId, setEmployeeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { employeeAvailabilityEntity, employees, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/employee-availability' + props.location.search);
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
    if (errors.length === 0) {
      const entity = {
        ...employeeAvailabilityEntity,
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
          <h2 id="carePlannerApp.employeeAvailability.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.employeeAvailability.home.createOrEditLabel">
              Create or edit a EmployeeAvailability
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : employeeAvailabilityEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="employee-availability-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="employee-availability-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup check>
                <Label id="isAvailableForWorkWeekDaysLabel">
                  <AvInput
                    id="employee-availability-isAvailableForWorkWeekDays"
                    type="checkbox"
                    className="form-check-input"
                    name="isAvailableForWorkWeekDays"
                  />
                  <Translate contentKey="carePlannerApp.employeeAvailability.isAvailableForWorkWeekDays">
                    Is Available For Work Week Days
                  </Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="minimumHoursPerWeekWeekDaysLabel" for="employee-availability-minimumHoursPerWeekWeekDays">
                  <Translate contentKey="carePlannerApp.employeeAvailability.minimumHoursPerWeekWeekDays">
                    Minimum Hours Per Week Week Days
                  </Translate>
                </Label>
                <AvField
                  id="employee-availability-minimumHoursPerWeekWeekDays"
                  type="string"
                  className="form-control"
                  name="minimumHoursPerWeekWeekDays"
                />
              </AvGroup>
              <AvGroup>
                <Label id="maximumHoursPerWeekWeekDaysLabel" for="employee-availability-maximumHoursPerWeekWeekDays">
                  <Translate contentKey="carePlannerApp.employeeAvailability.maximumHoursPerWeekWeekDays">
                    Maximum Hours Per Week Week Days
                  </Translate>
                </Label>
                <AvField
                  id="employee-availability-maximumHoursPerWeekWeekDays"
                  type="string"
                  className="form-control"
                  name="maximumHoursPerWeekWeekDays"
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isAvailableForWorkWeekEndsLabel">
                  <AvInput
                    id="employee-availability-isAvailableForWorkWeekEnds"
                    type="checkbox"
                    className="form-check-input"
                    name="isAvailableForWorkWeekEnds"
                  />
                  <Translate contentKey="carePlannerApp.employeeAvailability.isAvailableForWorkWeekEnds">
                    Is Available For Work Week Ends
                  </Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="minimumHoursPerWeekWeekEndsLabel" for="employee-availability-minimumHoursPerWeekWeekEnds">
                  <Translate contentKey="carePlannerApp.employeeAvailability.minimumHoursPerWeekWeekEnds">
                    Minimum Hours Per Week Week Ends
                  </Translate>
                </Label>
                <AvField
                  id="employee-availability-minimumHoursPerWeekWeekEnds"
                  type="string"
                  className="form-control"
                  name="minimumHoursPerWeekWeekEnds"
                />
              </AvGroup>
              <AvGroup>
                <Label id="maximumHoursPerWeekWeekEndsLabel" for="employee-availability-maximumHoursPerWeekWeekEnds">
                  <Translate contentKey="carePlannerApp.employeeAvailability.maximumHoursPerWeekWeekEnds">
                    Maximum Hours Per Week Week Ends
                  </Translate>
                </Label>
                <AvField
                  id="employee-availability-maximumHoursPerWeekWeekEnds"
                  type="string"
                  className="form-control"
                  name="maximumHoursPerWeekWeekEnds"
                />
              </AvGroup>
              <AvGroup>
                <Label id="leastPreferredShiftLabel" for="employee-availability-leastPreferredShift">
                  <Translate contentKey="carePlannerApp.employeeAvailability.leastPreferredShift">Least Preferred Shift</Translate>
                </Label>
                <AvInput
                  id="employee-availability-leastPreferredShift"
                  type="select"
                  className="form-control"
                  name="leastPreferredShift"
                  value={(!isNew && employeeAvailabilityEntity.leastPreferredShift) || 'MORNING_SHIFT'}
                >
                  <option value="MORNING_SHIFT">{translate('carePlannerApp.Shift.MORNING_SHIFT')}</option>
                  <option value="AFTERNOON_SHIFT">{translate('carePlannerApp.Shift.AFTERNOON_SHIFT')}</option>
                  <option value="EVENING_SHIFT">{translate('carePlannerApp.Shift.EVENING_SHIFT')}</option>
                  <option value="NIGHTS_SHIFT">{translate('carePlannerApp.Shift.NIGHTS_SHIFT')}</option>
                  <option value="AVAILABLE_ANY_SHIFT">{translate('carePlannerApp.Shift.AVAILABLE_ANY_SHIFT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="employee-availability-employee">
                  <Translate contentKey="carePlannerApp.employeeAvailability.employee">Employee</Translate>
                </Label>
                <AvInput id="employee-availability-employee" type="select" className="form-control" name="employeeId">
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
              <Button tag={Link} id="cancel-save" to="/employee-availability" replace color="info">
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
  employeeAvailabilityEntity: storeState.employeeAvailability.entity,
  loading: storeState.employeeAvailability.loading,
  updating: storeState.employeeAvailability.updating,
  updateSuccess: storeState.employeeAvailability.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeAvailabilityUpdate);
