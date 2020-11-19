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
import { ICountry } from 'app/shared/model/country.model';
import { getEntities as getCountries } from 'app/entities/country/country.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './employee.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeUpdate = (props: IEmployeeUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [nationalityId, setNationalityId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { employeeEntity, users, countries, loading, updating } = props;

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
    props.getCountries();
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
                  <option value="MR">{translate('carePlannerApp.Title.MR')}</option>
                  <option value="MRS">{translate('carePlannerApp.Title.MRS')}</option>
                  <option value="MSS">{translate('carePlannerApp.Title.MSS')}</option>
                  <option value="OTHER">{translate('carePlannerApp.Title.OTHER')}</option>
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
                  <option value="MALE">{translate('carePlannerApp.Gender.MALE')}</option>
                  <option value="FEMALE">{translate('carePlannerApp.Gender.FEMALE')}</option>
                  <option value="OTHER">{translate('carePlannerApp.Gender.OTHER')}</option>
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
                <Label id="socialSecurityNumberLabel" for="employee-socialSecurityNumber">
                  <Translate contentKey="carePlannerApp.employee.socialSecurityNumber">Social Security Number</Translate>
                </Label>
                <AvField id="employee-socialSecurityNumber" type="text" name="socialSecurityNumber" />
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
                  <option value="CAR">{translate('carePlannerApp.TravelMode.CAR')}</option>
                  <option value="BUS">{translate('carePlannerApp.TravelMode.BUS')}</option>
                  <option value="TRAIN">{translate('carePlannerApp.TravelMode.TRAIN')}</option>
                  <option value="PLANE">{translate('carePlannerApp.TravelMode.PLANE')}</option>
                  <option value="SHIP">{translate('carePlannerApp.TravelMode.SHIP')}</option>
                  <option value="WALK">{translate('carePlannerApp.TravelMode.WALK')}</option>
                  <option value="OTHER">{translate('carePlannerApp.TravelMode.OTHER')}</option>
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
                <Label id="tenantIdLabel" for="employee-tenantId">
                  <Translate contentKey="carePlannerApp.employee.tenantId">Tenant Id</Translate>
                </Label>
                <AvField
                  id="employee-tenantId"
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
                <Label for="employee-user">
                  <Translate contentKey="carePlannerApp.employee.user">User</Translate>
                </Label>
                <AvInput id="employee-user" type="select" className="form-control" name="userId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.login}
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
  countries: storeState.country.entities,
  employeeEntity: storeState.employee.entity,
  loading: storeState.employee.loading,
  updating: storeState.employee.updating,
  updateSuccess: storeState.employee.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getCountries,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeUpdate);
