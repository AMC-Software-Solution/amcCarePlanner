import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEmployeeDesignation } from 'app/shared/model/employee-designation.model';
import { getEntities as getEmployeeDesignations } from 'app/entities/employee-designation/employee-designation.reducer';
import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { IBranch } from 'app/shared/model/branch.model';
import { getEntities as getBranches } from 'app/entities/branch/branch.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeUpdate = (props: IEmployeeUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [designationId, setDesignationId] = useState('0');
  const [nationalityId, setNationalityId] = useState('0');
  const [branchId, setBranchId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { employeeEntity, users, employeeDesignations, countries, branches, loading, updating } = props;

  const { photo, photoContentType } = employeeEntity;

  const handleClose = () => {
    props.history.push('/employee' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getEmployeeDesignations();
    props.getCountries();
    props.getBranches();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...employeeEntity,
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
          <h2 id="carePlannerApp.employee.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.employee.home.createOrEditLabel">Create or edit a Employee</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : employeeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="employee-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="employee-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="employee-title">
                  <Translate contentKey="carePlannerApp.employee.title">Title</Translate>
                </Label>
                <AvInput
                  id="employee-title"
                  type="select"
                  className="form-control"
                  name="title"
                  value={(!isNew && employeeEntity.title) || 'MR'}
                >
                  <option value="MR">{translate('carePlannerApp.EmployeeTitle.MR')}</option>
                  <option value="MRS">{translate('carePlannerApp.EmployeeTitle.MRS')}</option>
                  <option value="MS">{translate('carePlannerApp.EmployeeTitle.MS')}</option>
                  <option value="MISS">{translate('carePlannerApp.EmployeeTitle.MISS')}</option>
                  <option value="OTHER">{translate('carePlannerApp.EmployeeTitle.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="firstNameLabel" for="employee-firstName">
                  <Translate contentKey="carePlannerApp.employee.firstName">First Name</Translate>
                </Label>
                <AvField
                  id="employee-firstName"
                  type="text"
                  name="firstName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 1, errorMessage: translate('entity.validation.minlength', { min: 1 }) },
                    maxLength: { value: 25, errorMessage: translate('entity.validation.maxlength', { max: 25 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="middleInitialLabel" for="employee-middleInitial">
                  <Translate contentKey="carePlannerApp.employee.middleInitial">Middle Initial</Translate>
                </Label>
                <AvField
                  id="employee-middleInitial"
                  type="text"
                  name="middleInitial"
                  validate={{
                    maxLength: { value: 1, errorMessage: translate('entity.validation.maxlength', { max: 1 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastNameLabel" for="employee-lastName">
                  <Translate contentKey="carePlannerApp.employee.lastName">Last Name</Translate>
                </Label>
                <AvField
                  id="employee-lastName"
                  type="text"
                  name="lastName"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 1, errorMessage: translate('entity.validation.minlength', { min: 1 }) },
                    maxLength: { value: 25, errorMessage: translate('entity.validation.maxlength', { max: 25 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="preferredNameLabel" for="employee-preferredName">
                  <Translate contentKey="carePlannerApp.employee.preferredName">Preferred Name</Translate>
                </Label>
                <AvField
                  id="employee-preferredName"
                  type="text"
                  name="preferredName"
                  validate={{
                    maxLength: { value: 25, errorMessage: translate('entity.validation.maxlength', { max: 25 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="genderLabel" for="employee-gender">
                  <Translate contentKey="carePlannerApp.employee.gender">Gender</Translate>
                </Label>
                <AvInput
                  id="employee-gender"
                  type="select"
                  className="form-control"
                  name="gender"
                  value={(!isNew && employeeEntity.gender) || 'MALE'}
                >
                  <option value="MALE">{translate('carePlannerApp.EmployeeGender.MALE')}</option>
                  <option value="FEMALE">{translate('carePlannerApp.EmployeeGender.FEMALE')}</option>
                  <option value="OTHER">{translate('carePlannerApp.EmployeeGender.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="employeeCodeLabel" for="employee-employeeCode">
                  <Translate contentKey="carePlannerApp.employee.employeeCode">Employee Code</Translate>
                </Label>
                <AvField
                  id="employee-employeeCode"
                  type="text"
                  name="employeeCode"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="phoneLabel" for="employee-phone">
                  <Translate contentKey="carePlannerApp.employee.phone">Phone</Translate>
                </Label>
                <AvField
                  id="employee-phone"
                  type="text"
                  name="phone"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="employee-email">
                  <Translate contentKey="carePlannerApp.employee.email">Email</Translate>
                </Label>
                <AvField
                  id="employee-email"
                  type="text"
                  name="email"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="nationalInsuranceNumberLabel" for="employee-nationalInsuranceNumber">
                  <Translate contentKey="carePlannerApp.employee.nationalInsuranceNumber">National Insurance Number</Translate>
                </Label>
                <AvField id="employee-nationalInsuranceNumber" type="text" name="nationalInsuranceNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="employeeContractTypeLabel" for="employee-employeeContractType">
                  <Translate contentKey="carePlannerApp.employee.employeeContractType">Employee Contract Type</Translate>
                </Label>
                <AvInput
                  id="employee-employeeContractType"
                  type="select"
                  className="form-control"
                  name="employeeContractType"
                  value={(!isNew && employeeEntity.employeeContractType) || 'ZERO_HOURS_CONTRACT'}
                >
                  <option value="ZERO_HOURS_CONTRACT">{translate('carePlannerApp.EmployeeContractType.ZERO_HOURS_CONTRACT')}</option>
                  <option value="SALARIED_STAFF">{translate('carePlannerApp.EmployeeContractType.SALARIED_STAFF')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="pinCodeLabel" for="employee-pinCode">
                  <Translate contentKey="carePlannerApp.employee.pinCode">Pin Code</Translate>
                </Label>
                <AvField id="employee-pinCode" type="string" className="form-control" name="pinCode" />
              </AvGroup>
              <AvGroup>
                <Label id="transportModeLabel" for="employee-transportMode">
                  <Translate contentKey="carePlannerApp.employee.transportMode">Transport Mode</Translate>
                </Label>
                <AvInput
                  id="employee-transportMode"
                  type="select"
                  className="form-control"
                  name="transportMode"
                  value={(!isNew && employeeEntity.transportMode) || 'CAR'}
                >
                  <option value="CAR">{translate('carePlannerApp.EmployeeTravelMode.CAR')}</option>
                  <option value="BUS">{translate('carePlannerApp.EmployeeTravelMode.BUS')}</option>
                  <option value="TRAIN">{translate('carePlannerApp.EmployeeTravelMode.TRAIN')}</option>
                  <option value="PLANE">{translate('carePlannerApp.EmployeeTravelMode.PLANE')}</option>
                  <option value="SHIP">{translate('carePlannerApp.EmployeeTravelMode.SHIP')}</option>
                  <option value="WALK">{translate('carePlannerApp.EmployeeTravelMode.WALK')}</option>
                  <option value="OTHER">{translate('carePlannerApp.EmployeeTravelMode.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="addressLabel" for="employee-address">
                  <Translate contentKey="carePlannerApp.employee.address">Address</Translate>
                </Label>
                <AvField
                  id="employee-address"
                  type="text"
                  name="address"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="countyLabel" for="employee-county">
                  <Translate contentKey="carePlannerApp.employee.county">County</Translate>
                </Label>
                <AvField id="employee-county" type="text" name="county" />
              </AvGroup>
              <AvGroup>
                <Label id="postCodeLabel" for="employee-postCode">
                  <Translate contentKey="carePlannerApp.employee.postCode">Post Code</Translate>
                </Label>
                <AvField id="employee-postCode" type="text" name="postCode" />
              </AvGroup>
              <AvGroup>
                <Label id="dateOfBirthLabel" for="employee-dateOfBirth">
                  <Translate contentKey="carePlannerApp.employee.dateOfBirth">Date Of Birth</Translate>
                </Label>
                <AvField
                  id="employee-dateOfBirth"
                  type="date"
                  className="form-control"
                  name="dateOfBirth"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="photoLabel" for="photo">
                    <Translate contentKey="carePlannerApp.employee.photo">Photo</Translate>
                  </Label>
                  <br />
                  {photo ? (
                    <div>
                      {photoContentType ? (
                        <a onClick={openFile(photoContentType, photo)}>
                          <img src={`data:${photoContentType};base64,${photo}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {photoContentType}, {byteSize(photo)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('photo')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_photo" type="file" onChange={onBlobChange(true, 'photo')} accept="image/*" />
                  <AvInput type="hidden" name="photo" value={photo} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="photoUrlLabel" for="employee-photoUrl">
                  <Translate contentKey="carePlannerApp.employee.photoUrl">Photo Url</Translate>
                </Label>
                <AvField id="employee-photoUrl" type="text" name="photoUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="employee-status">
                  <Translate contentKey="carePlannerApp.employee.status">Status</Translate>
                </Label>
                <AvInput
                  id="employee-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && employeeEntity.status) || 'ACTIVE'}
                >
                  <option value="ACTIVE">{translate('carePlannerApp.EmployeeStatus.ACTIVE')}</option>
                  <option value="DEACTIVE">{translate('carePlannerApp.EmployeeStatus.DEACTIVE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="employeeBioLabel" for="employee-employeeBio">
                  <Translate contentKey="carePlannerApp.employee.employeeBio">Employee Bio</Translate>
                </Label>
                <AvField id="employee-employeeBio" type="text" name="employeeBio" />
              </AvGroup>
              <AvGroup>
                <Label id="acruedHolidayHoursLabel" for="employee-acruedHolidayHours">
                  <Translate contentKey="carePlannerApp.employee.acruedHolidayHours">Acrued Holiday Hours</Translate>
                </Label>
                <AvField id="employee-acruedHolidayHours" type="string" className="form-control" name="acruedHolidayHours" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="employee-createdDate">
                  <Translate contentKey="carePlannerApp.employee.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="employee-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="employee-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.employee.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="employee-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.employeeEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="employee-clientId">
                  <Translate contentKey="carePlannerApp.employee.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="employee-clientId"
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
                  <AvInput id="employee-hasExtraData" type="checkbox" className="form-check-input" name="hasExtraData" />
                  <Translate contentKey="carePlannerApp.employee.hasExtraData">Has Extra Data</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="employee-user">
                  <Translate contentKey="carePlannerApp.employee.user">User</Translate>
                </Label>
                <AvInput id="employee-user" type="select" className="form-control" name="userId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.email}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="employee-designation">
                  <Translate contentKey="carePlannerApp.employee.designation">Designation</Translate>
                </Label>
                <AvInput id="employee-designation" type="select" className="form-control" name="designationId">
                  <option value="" key="0" />
                  {employeeDesignations
                    ? employeeDesignations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.designation}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="employee-nationality">
                  <Translate contentKey="carePlannerApp.employee.nationality">Nationality</Translate>
                </Label>
                <AvInput id="employee-nationality" type="select" className="form-control" name="nationalityId">
                  <option value="" key="0" />
                  {countries
                    ? countries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.countryName}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="employee-branch">
                  <Translate contentKey="carePlannerApp.employee.branch">Branch</Translate>
                </Label>
                <AvInput id="employee-branch" type="select" className="form-control" name="branchId">
                  <option value="" key="0" />
                  {branches
                    ? branches.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/employee" replace color="info">
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
  users: storeState.userManagement.users,
  employeeDesignations: storeState.employeeDesignation.entities,
  countries: storeState.country.entities,
  branches: storeState.branch.entities,
  employeeEntity: storeState.employee.entity,
  loading: storeState.employee.loading,
  updating: storeState.employee.updating,
  updateSuccess: storeState.employee.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEmployeeDesignations,
  getCountries,
  getBranches,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeUpdate);
