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
import { IBranch } from 'app/shared/model/branch.model';
import { getEntities as getBranches } from 'app/entities/branch/branch.reducer';
import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './service-user.reducer';
import { IServiceUser } from 'app/shared/model/service-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServiceUserUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceUserUpdate = (props: IServiceUserUpdateProps) => {
  const [userId, setUserId] = useState('0');
  const [branchId, setBranchId] = useState('0');
  const [registeredById, setRegisteredById] = useState('0');
  const [activatedById, setActivatedById] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { serviceUserEntity, users, branches, employees, loading, updating } = props;

  const { profilePhoto, profilePhotoContentType } = serviceUserEntity;

  const handleClose = () => {
    props.history.push('/service-user' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getBranches();
    props.getEmployees();
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
    values.lastVisitDate = convertDateTimeToServer(values.lastVisitDate);
    values.startDate = convertDateTimeToServer(values.startDate);
    values.activatedDate = convertDateTimeToServer(values.activatedDate);
    values.lastUpdatedDate = convertDateTimeToServer(values.lastUpdatedDate);

    if (errors.length === 0) {
      const entity = {
        ...serviceUserEntity,
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
          <h2 id="carePlannerApp.serviceUser.home.createOrEditLabel">
            <Translate contentKey="carePlannerApp.serviceUser.home.createOrEditLabel">Create or edit a ServiceUser</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : serviceUserEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="service-user-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="service-user-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="service-user-title">
                  <Translate contentKey="carePlannerApp.serviceUser.title">Title</Translate>
                </Label>
                <AvInput
                  id="service-user-title"
                  type="select"
                  className="form-control"
                  name="title"
                  value={(!isNew && serviceUserEntity.title) || 'MR'}
                >
                  <option value="MR">{translate('carePlannerApp.Title.MR')}</option>
                  <option value="MRS">{translate('carePlannerApp.Title.MRS')}</option>
                  <option value="MS">{translate('carePlannerApp.Title.MS')}</option>
                  <option value="MISS">{translate('carePlannerApp.Title.MISS')}</option>
                  <option value="OTHER">{translate('carePlannerApp.Title.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="firstNameLabel" for="service-user-firstName">
                  <Translate contentKey="carePlannerApp.serviceUser.firstName">First Name</Translate>
                </Label>
                <AvField
                  id="service-user-firstName"
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
                <Label id="middleNameLabel" for="service-user-middleName">
                  <Translate contentKey="carePlannerApp.serviceUser.middleName">Middle Name</Translate>
                </Label>
                <AvField
                  id="service-user-middleName"
                  type="text"
                  name="middleName"
                  validate={{
                    maxLength: { value: 100, errorMessage: translate('entity.validation.maxlength', { max: 100 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastNameLabel" for="service-user-lastName">
                  <Translate contentKey="carePlannerApp.serviceUser.lastName">Last Name</Translate>
                </Label>
                <AvField
                  id="service-user-lastName"
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
                <Label id="preferredNameLabel" for="service-user-preferredName">
                  <Translate contentKey="carePlannerApp.serviceUser.preferredName">Preferred Name</Translate>
                </Label>
                <AvField
                  id="service-user-preferredName"
                  type="text"
                  name="preferredName"
                  validate={{
                    maxLength: { value: 25, errorMessage: translate('entity.validation.maxlength', { max: 25 }) },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="emailLabel" for="service-user-email">
                  <Translate contentKey="carePlannerApp.serviceUser.email">Email</Translate>
                </Label>
                <AvField id="service-user-email" type="text" name="email" />
              </AvGroup>
              <AvGroup>
                <Label id="serviceUserCodeLabel" for="service-user-serviceUserCode">
                  <Translate contentKey="carePlannerApp.serviceUser.serviceUserCode">Service User Code</Translate>
                </Label>
                <AvField
                  id="service-user-serviceUserCode"
                  type="text"
                  name="serviceUserCode"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateOfBirthLabel" for="service-user-dateOfBirth">
                  <Translate contentKey="carePlannerApp.serviceUser.dateOfBirth">Date Of Birth</Translate>
                </Label>
                <AvField
                  id="service-user-dateOfBirth"
                  type="date"
                  className="form-control"
                  name="dateOfBirth"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastVisitDateLabel" for="service-user-lastVisitDate">
                  <Translate contentKey="carePlannerApp.serviceUser.lastVisitDate">Last Visit Date</Translate>
                </Label>
                <AvInput
                  id="service-user-lastVisitDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastVisitDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.serviceUserEntity.lastVisitDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="startDateLabel" for="service-user-startDate">
                  <Translate contentKey="carePlannerApp.serviceUser.startDate">Start Date</Translate>
                </Label>
                <AvInput
                  id="service-user-startDate"
                  type="datetime-local"
                  className="form-control"
                  name="startDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.serviceUserEntity.startDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="supportTypeLabel" for="service-user-supportType">
                  <Translate contentKey="carePlannerApp.serviceUser.supportType">Support Type</Translate>
                </Label>
                <AvInput
                  id="service-user-supportType"
                  type="select"
                  className="form-control"
                  name="supportType"
                  value={(!isNew && serviceUserEntity.supportType) || 'COMPLEX_CARE_LIVE_IN'}
                >
                  <option value="COMPLEX_CARE_LIVE_IN">{translate('carePlannerApp.SupportType.COMPLEX_CARE_LIVE_IN')}</option>
                  <option value="DOMICILIARY_CARE">{translate('carePlannerApp.SupportType.DOMICILIARY_CARE')}</option>
                  <option value="EXTRA_CARE">{translate('carePlannerApp.SupportType.EXTRA_CARE')}</option>
                  <option value="PRIVATE">{translate('carePlannerApp.SupportType.PRIVATE')}</option>
                  <option value="REABLEMENT">{translate('carePlannerApp.SupportType.REABLEMENT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="serviceUserCategoryLabel" for="service-user-serviceUserCategory">
                  <Translate contentKey="carePlannerApp.serviceUser.serviceUserCategory">Service User Category</Translate>
                </Label>
                <AvInput
                  id="service-user-serviceUserCategory"
                  type="select"
                  className="form-control"
                  name="serviceUserCategory"
                  value={(!isNew && serviceUserEntity.serviceUserCategory) || 'HIV_AIDS'}
                >
                  <option value="HIV_AIDS">{translate('carePlannerApp.ServiceUserCategory.HIV_AIDS')}</option>
                  <option value="LEARNING_DISABILITIES">{translate('carePlannerApp.ServiceUserCategory.LEARNING_DISABILITIES')}</option>
                  <option value="MENTAL_HEALTH">{translate('carePlannerApp.ServiceUserCategory.MENTAL_HEALTH')}</option>
                  <option value="OLDER_PEOPLE">{translate('carePlannerApp.ServiceUserCategory.OLDER_PEOPLE')}</option>
                  <option value="PHYSICAL_SENSORY_IMPAIRMENT">
                    {translate('carePlannerApp.ServiceUserCategory.PHYSICAL_SENSORY_IMPAIRMENT')}
                  </option>
                  <option value="OTHER">{translate('carePlannerApp.ServiceUserCategory.OTHER')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="vulnerabilityLabel" for="service-user-vulnerability">
                  <Translate contentKey="carePlannerApp.serviceUser.vulnerability">Vulnerability</Translate>
                </Label>
                <AvInput
                  id="service-user-vulnerability"
                  type="select"
                  className="form-control"
                  name="vulnerability"
                  value={(!isNew && serviceUserEntity.vulnerability) || 'HIV_AIDS'}
                >
                  <option value="HIV_AIDS">{translate('carePlannerApp.Vulnerability.HIV_AIDS')}</option>
                  <option value="LEARNING_DISABILITIES">{translate('carePlannerApp.Vulnerability.LEARNING_DISABILITIES')}</option>
                  <option value="MENTAL_HEALTH">{translate('carePlannerApp.Vulnerability.MENTAL_HEALTH')}</option>
                  <option value="OLDER_PEOPLE">{translate('carePlannerApp.Vulnerability.OLDER_PEOPLE')}</option>
                  <option value="PHYSICAL_IMPAIRMENT">{translate('carePlannerApp.Vulnerability.PHYSICAL_IMPAIRMENT')}</option>
                  <option value="SENSORY_IMPAIRMENT">{translate('carePlannerApp.Vulnerability.SENSORY_IMPAIRMENT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="servicePriorityLabel" for="service-user-servicePriority">
                  <Translate contentKey="carePlannerApp.serviceUser.servicePriority">Service Priority</Translate>
                </Label>
                <AvInput
                  id="service-user-servicePriority"
                  type="select"
                  className="form-control"
                  name="servicePriority"
                  value={(!isNew && serviceUserEntity.servicePriority) || 'HIGH'}
                >
                  <option value="HIGH">{translate('carePlannerApp.ServicePriority.HIGH')}</option>
                  <option value="LOW">{translate('carePlannerApp.ServicePriority.LOW')}</option>
                  <option value="MEDIUM">{translate('carePlannerApp.ServicePriority.MEDIUM')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="sourceLabel" for="service-user-source">
                  <Translate contentKey="carePlannerApp.serviceUser.source">Source</Translate>
                </Label>
                <AvInput
                  id="service-user-source"
                  type="select"
                  className="form-control"
                  name="source"
                  value={(!isNew && serviceUserEntity.source) || 'PRIVATE_SERVICE_USER'}
                >
                  <option value="PRIVATE_SERVICE_USER">{translate('carePlannerApp.Source.PRIVATE_SERVICE_USER')}</option>
                  <option value="SOCIAL_SERVICES_REFERRAL">{translate('carePlannerApp.Source.SOCIAL_SERVICES_REFERRAL')}</option>
                  <option value="REABLEMENT_REFERRAL">{translate('carePlannerApp.Source.REABLEMENT_REFERRAL')}</option>
                  <option value="UNKNOWN">{translate('carePlannerApp.Source.UNKNOWN')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="service-user-status">
                  <Translate contentKey="carePlannerApp.serviceUser.status">Status</Translate>
                </Label>
                <AvInput
                  id="service-user-status"
                  type="select"
                  className="form-control"
                  name="status"
                  value={(!isNew && serviceUserEntity.status) || 'ACTIVE'}
                >
                  <option value="ACTIVE">{translate('carePlannerApp.ServiceUserStatus.ACTIVE')}</option>
                  <option value="DEACTIVE">{translate('carePlannerApp.ServiceUserStatus.DEACTIVE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="firstLanguageLabel" for="service-user-firstLanguage">
                  <Translate contentKey="carePlannerApp.serviceUser.firstLanguage">First Language</Translate>
                </Label>
                <AvField
                  id="service-user-firstLanguage"
                  type="text"
                  name="firstLanguage"
                  validate={{
                    maxLength: { value: 25, errorMessage: translate('entity.validation.maxlength', { max: 25 }) },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="interpreterRequiredLabel">
                  <AvInput id="service-user-interpreterRequired" type="checkbox" className="form-check-input" name="interpreterRequired" />
                  <Translate contentKey="carePlannerApp.serviceUser.interpreterRequired">Interpreter Required</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="activatedDateLabel" for="service-user-activatedDate">
                  <Translate contentKey="carePlannerApp.serviceUser.activatedDate">Activated Date</Translate>
                </Label>
                <AvInput
                  id="service-user-activatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="activatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.serviceUserEntity.activatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="profilePhotoLabel" for="profilePhoto">
                    <Translate contentKey="carePlannerApp.serviceUser.profilePhoto">Profile Photo</Translate>
                  </Label>
                  <br />
                  {profilePhoto ? (
                    <div>
                      {profilePhotoContentType ? (
                        <a onClick={openFile(profilePhotoContentType, profilePhoto)}>
                          <img src={`data:${profilePhotoContentType};base64,${profilePhoto}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {profilePhotoContentType}, {byteSize(profilePhoto)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('profilePhoto')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_profilePhoto" type="file" onChange={onBlobChange(true, 'profilePhoto')} accept="image/*" />
                  <AvInput type="hidden" name="profilePhoto" value={profilePhoto} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label id="profilePhotoUrlLabel" for="service-user-profilePhotoUrl">
                  <Translate contentKey="carePlannerApp.serviceUser.profilePhotoUrl">Profile Photo Url</Translate>
                </Label>
                <AvField id="service-user-profilePhotoUrl" type="text" name="profilePhotoUrl" />
              </AvGroup>
              <AvGroup>
                <Label id="lastRecordedHeightLabel" for="service-user-lastRecordedHeight">
                  <Translate contentKey="carePlannerApp.serviceUser.lastRecordedHeight">Last Recorded Height</Translate>
                </Label>
                <AvField id="service-user-lastRecordedHeight" type="text" name="lastRecordedHeight" />
              </AvGroup>
              <AvGroup>
                <Label id="lastRecordedWeightLabel" for="service-user-lastRecordedWeight">
                  <Translate contentKey="carePlannerApp.serviceUser.lastRecordedWeight">Last Recorded Weight</Translate>
                </Label>
                <AvField id="service-user-lastRecordedWeight" type="text" name="lastRecordedWeight" />
              </AvGroup>
              <AvGroup check>
                <Label id="hasMedicalConditionLabel">
                  <AvInput id="service-user-hasMedicalCondition" type="checkbox" className="form-check-input" name="hasMedicalCondition" />
                  <Translate contentKey="carePlannerApp.serviceUser.hasMedicalCondition">Has Medical Condition</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="medicalConditionSummaryLabel" for="service-user-medicalConditionSummary">
                  <Translate contentKey="carePlannerApp.serviceUser.medicalConditionSummary">Medical Condition Summary</Translate>
                </Label>
                <AvField id="service-user-medicalConditionSummary" type="text" name="medicalConditionSummary" />
              </AvGroup>
              <AvGroup>
                <Label id="lastUpdatedDateLabel" for="service-user-lastUpdatedDate">
                  <Translate contentKey="carePlannerApp.serviceUser.lastUpdatedDate">Last Updated Date</Translate>
                </Label>
                <AvInput
                  id="service-user-lastUpdatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastUpdatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.serviceUserEntity.lastUpdatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="clientIdLabel" for="service-user-clientId">
                  <Translate contentKey="carePlannerApp.serviceUser.clientId">Client Id</Translate>
                </Label>
                <AvField
                  id="service-user-clientId"
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
                <Label for="service-user-user">
                  <Translate contentKey="carePlannerApp.serviceUser.user">User</Translate>
                </Label>
                <AvInput id="service-user-user" type="select" className="form-control" name="userId">
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
                <Label for="service-user-branch">
                  <Translate contentKey="carePlannerApp.serviceUser.branch">Branch</Translate>
                </Label>
                <AvInput id="service-user-branch" type="select" className="form-control" name="branchId">
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
              <AvGroup>
                <Label for="service-user-registeredBy">
                  <Translate contentKey="carePlannerApp.serviceUser.registeredBy">Registered By</Translate>
                </Label>
                <AvInput id="service-user-registeredBy" type="select" className="form-control" name="registeredById">
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
                <Label for="service-user-activatedBy">
                  <Translate contentKey="carePlannerApp.serviceUser.activatedBy">Activated By</Translate>
                </Label>
                <AvInput id="service-user-activatedBy" type="select" className="form-control" name="activatedById">
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
              <Button tag={Link} id="cancel-save" to="/service-user" replace color="info">
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
  branches: storeState.branch.entities,
  employees: storeState.employee.entities,
  serviceUserEntity: storeState.serviceUser.entity,
  loading: storeState.serviceUser.loading,
  updating: storeState.serviceUser.updating,
  updateSuccess: storeState.serviceUser.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getBranches,
  getEmployees,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceUserUpdate);
