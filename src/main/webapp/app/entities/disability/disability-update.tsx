import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDisabilityType } from 'app/shared/model/disability-type.model';
import { getEntities as getDisabilityTypes } from 'app/entities/disability-type/disability-type.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, reset } from './disability.reducer';
import { IDisability } from 'app/shared/model/disability.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDisabilityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DisabilityUpdate = (props: IDisabilityUpdateProps) => {
  const [disabilityTypeId, setDisabilityTypeId] = useState('0');
  const [employeeId, setEmployeeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { disabilityEntity, disabilityTypes, employees, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/disability' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getDisabilityTypes();
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
        ...disabilityEntity,
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
          <h2 id="carePlannerApp.disability.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.disability.home.createOrEditLabel">Create or edit a Disability</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : disabilityEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="disability-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="disability-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="noteLabel" for="disability-note">
                  <Translate contentKey="carePlannerApp.disability.note">Note</Translate>
                </Label>
                <AvField id="disability-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="disability-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.disability.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="disability-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.disabilityEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tenantIdLabel" for="disability-tenantId">
                  <Translate contentKey="carePlannerApp.disability.tenantId">Tenant Id</Translate>
                </Label>
                <AvField
                  id="disability-tenantId"
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
                <Label for="disability-disabilityType">
                  <Translate contentKey="carePlannerApp.disability.disabilityType">Disability Type</Translate>
                </Label>
                <AvInput id="disability-disabilityType" type="select" className="form-control" name="disabilityTypeId">
                  <option value="" key="0" />
                  {disabilityTypes
                    ? disabilityTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.disability}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="disability-employee">
                  <Translate contentKey="carePlannerApp.disability.employee">Employee</Translate>
                </Label>
                <AvInput id="disability-employee" type="select" className="form-control" name="employeeId">
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
              <Button tag={Link} id="cancel-save" to="/disability" replace color="info">
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
  disabilityTypes: storeState.disabilityType.entities,
  employees: storeState.employee.entities,
  disabilityEntity: storeState.disability.entity,
  loading: storeState.disability.loading,
  updating: storeState.disability.updating,
  updateSuccess: storeState.disability.updateSuccess,
});

const mapDispatchToProps = {
  getDisabilityTypes,
  getEmployees,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DisabilityUpdate);
