import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEligibilityType } from 'app/shared/model/eligibility-type.model';
import { getEntities as getEligibilityTypes } from 'app/entities/eligibility-type/eligibility-type.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, reset } from './eligibility.reducer';
import { IEligibility } from 'app/shared/model/eligibility.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEligibilityUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EligibilityUpdate = (props: IEligibilityUpdateProps) => {
  const [eligibilityTypeId, setEligibilityTypeId] = useState('0');
  const [employeeId, setEmployeeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { eligibilityEntity, eligibilityTypes, employees, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/eligibility' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getEligibilityTypes();
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
        ...eligibilityEntity,
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
          <h2 id="carePlannerApp.eligibility.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.eligibility.home.createOrEditLabel">Create or edit a Eligibility</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : eligibilityEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="eligibility-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="eligibility-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="noteLabel" for="eligibility-note">
                  <Translate contentKey="carePlannerApp.eligibility.note">Note</Translate>
                </Label>
                <AvField id="eligibility-note" type="text" name="note" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="eligibility-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.eligibility.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="eligibility-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.eligibilityEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="tenantIdLabel" for="eligibility-tenantId">
                  <Translate contentKey="carePlannerApp.eligibility.tenantId">Tenant Id</Translate>
                </Label>
                <AvField
                  id="eligibility-tenantId"
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
                <Label for="eligibility-eligibilityType">
                  <Translate contentKey="carePlannerApp.eligibility.eligibilityType">Eligibility Type</Translate>
                </Label>
                <AvInput id="eligibility-eligibilityType" type="select" className="form-control" name="eligibilityTypeId">
                  <option value="" key="0" />
                  {eligibilityTypes
                    ? eligibilityTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.eligibilityType}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="eligibility-employee">
                  <Translate contentKey="carePlannerApp.eligibility.employee">Employee</Translate>
                </Label>
                <AvInput id="eligibility-employee" type="select" className="form-control" name="employeeId">
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
              <Button tag={Link} id="cancel-save" to="/eligibility" replace color="info">
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
  eligibilityTypes: storeState.eligibilityType.entities,
  employees: storeState.employee.entities,
  eligibilityEntity: storeState.eligibility.entity,
  loading: storeState.eligibility.loading,
  updating: storeState.eligibility.updating,
  updateSuccess: storeState.eligibility.updateSuccess,
});

const mapDispatchToProps = {
  getEligibilityTypes,
  getEmployees,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EligibilityUpdate);
