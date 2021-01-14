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
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

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
                <Label id="availableForWorkLabel">
                  <AvInput
                    id="employee-availability-availableForWork"
                    type="checkbox"
                    className="form-check-input"
                    name="availableForWork"
                  />
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableForWork">Available For Work</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="availableMondayLabel">
                  <AvInput id="employee-availability-availableMonday" type="checkbox" className="form-check-input" name="availableMonday" />
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableMonday">Available Monday</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="availableTuesdayLabel">
                  <AvInput
                    id="employee-availability-availableTuesday"
                    type="checkbox"
                    className="form-check-input"
                    name="availableTuesday"
                  />
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableTuesday">Available Tuesday</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="availableWednesdayLabel">
                  <AvInput
                    id="employee-availability-availableWednesday"
                    type="checkbox"
                    className="form-check-input"
                    name="availableWednesday"
                  />
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableWednesday">Available Wednesday</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="availableThursdayLabel">
                  <AvInput
                    id="employee-availability-availableThursday"
                    type="checkbox"
                    className="form-check-input"
                    name="availableThursday"
                  />
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableThursday">Available Thursday</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="availableFridayLabel">
                  <AvInput id="employee-availability-availableFriday" type="checkbox" className="form-check-input" name="availableFriday" />
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableFriday">Available Friday</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="availableSaturdayLabel">
                  <AvInput
                    id="employee-availability-availableSaturday"
                    type="checkbox"
                    className="form-check-input"
                    name="availableSaturday"
                  />
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableSaturday">Available Saturday</Translate>
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="availableSundayLabel">
                  <AvInput id="employee-availability-availableSunday" type="checkbox" className="form-check-input" name="availableSunday" />
                  <Translate contentKey="carePlannerApp.employeeAvailability.availableSunday">Available Sunday</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="preferredShiftLabel" for="employee-availability-preferredShift">
                  <Translate contentKey="carePlannerApp.employeeAvailability.preferredShift">Preferred Shift</Translate>
                </Label>
                <AvInput
                  id="employee-availability-preferredShift"
                  type="select"
                  className="form-control"
                  name="preferredShift"
                  value={(!isNew && employeeAvailabilityEntity.preferredShift) || 'MORNING_SHIFT'}
                >
                  <option value="MORNING_SHIFT">{translate('carePlannerApp.Shift.MORNING_SHIFT')}</option>
                  <option value="AFTERNOON_SHIFT">{translate('carePlannerApp.Shift.AFTERNOON_SHIFT')}</option>
                  <option value="EVENING_SHIFT">{translate('carePlannerApp.Shift.EVENING_SHIFT')}</option>
                  <option value="NIGHTS_SHIFT">{translate('carePlannerApp.Shift.NIGHTS_SHIFT')}</option>
                  <option value="AVAILABLE_ANY_SHIFT">{translate('carePlannerApp.Shift.AVAILABLE_ANY_SHIFT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup check>
                <Label id="hasExtraDataLabel">
                  <AvInput id="employee-availability-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.employeeAvailability.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="employee-availability-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.employeeAvailability.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="employee-availability-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeAvailabilityEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="employee-availability-clientId">
                  <Translate contentKey="carePlannerApp.employeeAvailability.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="employee-availability-clientId"
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
