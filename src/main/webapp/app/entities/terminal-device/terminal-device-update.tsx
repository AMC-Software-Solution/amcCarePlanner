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
import { getEntity, updateEntity, createEntity, reset } from './terminal-device.reducer';
import { ITerminalDevice } from 'app/shared/model/terminal-device.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITerminalDeviceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TerminalDeviceUpdate = (props: ITerminalDeviceUpdateProps) => {
  const [employeeId, setEmployeeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { terminalDeviceEntity, employees, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/terminal-device' + props.location.search);
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
    values.registeredDate = convertDateTimeToServer(values.registeredDate);
    values.userStartedUsingFrom = convertDateTimeToServer(values.userStartedUsingFrom);
    values.deviceOnLocationFrom = convertDateTimeToServer(values.deviceOnLocationFrom);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...terminalDeviceEntity,
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
          <h2 id="carePlannerApp.terminalDevice.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.terminalDevice.home.createOrEditLabel">Create or edit a TerminalDevice</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : terminalDeviceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="terminal-device-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="terminal-device-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="deviceNameLabel" for="terminal-device-deviceName">
                  <Translate contentKey="carePlannerApp.terminalDevice.deviceName">Device Name</Translate>
                </Label>
                <AvField id="terminal-device-deviceName" type="text" name="deviceName" />
              </AvGroup>
              <AvGroup>
                <Label id="deviceModelLabel" for="terminal-device-deviceModel">
                  <Translate contentKey="carePlannerApp.terminalDevice.deviceModel">Device Model</Translate>
                </Label>
                <AvField id="terminal-device-deviceModel" type="text" name="deviceModel" />
              </AvGroup>
              <AvGroup>
                <Label id="registeredDateLabel" for="terminal-device-registeredDate">
                  <Translate contentKey="carePlannerApp.terminalDevice.registeredDate">Registered Date</Translate>
                </Label>
                <AvInput
                  id="terminal-device-registeredDate"
                  type="datetime-local"
                  className="form-control"
                  name="registeredDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.terminalDeviceEntity.registeredDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="imeiLabel" for="terminal-device-imei">
                  <Translate contentKey="carePlannerApp.terminalDevice.imei">Imei</Translate>
                </Label>
                <AvField id="terminal-device-imei" type="text" name="imei" />
              </AvGroup>
              <AvGroup>
                <Label id="simNumberLabel" for="terminal-device-simNumber">
                  <Translate contentKey="carePlannerApp.terminalDevice.simNumber">Sim Number</Translate>
                </Label>
                <AvField id="terminal-device-simNumber" type="text" name="simNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="userStartedUsingFromLabel" for="terminal-device-userStartedUsingFrom">
                  <Translate contentKey="carePlannerApp.terminalDevice.userStartedUsingFrom">User Started Using From</Translate>
                </Label>
                <AvInput
                  id="terminal-device-userStartedUsingFrom"
                  type="datetime-local"
                  className="form-control"
                  name="userStartedUsingFrom"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.terminalDeviceEntity.userStartedUsingFrom)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="deviceOnLocationFromLabel" for="terminal-device-deviceOnLocationFrom">
                  <Translate contentKey="carePlannerApp.terminalDevice.deviceOnLocationFrom">Device On Location From</Translate>
                </Label>
                <AvInput
                  id="terminal-device-deviceOnLocationFrom"
                  type="datetime-local"
                  className="form-control"
                  name="deviceOnLocationFrom"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.terminalDeviceEntity.deviceOnLocationFrom)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="operatingSystemLabel" for="terminal-device-operatingSystem">
                  <Translate contentKey="carePlannerApp.terminalDevice.operatingSystem">Operating System</Translate>
                </Label>
                <AvField id="terminal-device-operatingSystem" type="text" name="operatingSystem" />
              </AvGroup>
              <AvGroup>
                <Label id="noteLabel" for="terminal-device-note">
                  <Translate contentKey="carePlannerApp.terminalDevice.note">Note</Translate>
                </Label>
                <AvField id="terminal-device-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="ownerEntityIdLabel" for="terminal-device-ownerEntityId">
                  <Translate contentKey="carePlannerApp.terminalDevice.ownerEntityId">Owner Entity Id</Translate>
                </Label>
                <AvField id="terminal-device-ownerEntityId" type="string" className="form-control" name="ownerEntityId" />
              </AvGroup>
              <AvGroup>
                <Label id="ownerEntityNameLabel" for="terminal-device-ownerEntityName">
                  <Translate contentKey="carePlannerApp.terminalDevice.ownerEntityName">Owner Entity Name</Translate>
                </Label>
                <AvField id="terminal-device-ownerEntityName" type="text" name="ownerEntityName" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="terminal-device-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.terminalDevice.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="terminal-device-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.terminalDeviceEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tenantIdLabel" for="terminal-device-tenantId">
                  <Translate contentKey="carePlannerApp.terminalDevice.tenantId">Tenant Id</Translate>
                </Label>
                <AvField
                  id="terminal-device-tenantId"
                  type="string"
                  className="form-control"
                  name="tenantId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="terminal-device-employee">
                  <Translate contentKey="carePlannerApp.terminalDevice.employee">Employee</Translate>
                </Label>
                <AvInput id="terminal-device-employee" type="select" className="form-control" name="employeeId">
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
              <Button tag={Link} id="cancel-save" to="/terminal-device" replace color="info">
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
  terminalDeviceEntity: storeState.terminalDevice.entity,
  loading: storeState.terminalDevice.loading,
  updating: storeState.terminalDevice.updating,
  updateSuccess: storeState.terminalDevice.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(TerminalDeviceUpdate);
