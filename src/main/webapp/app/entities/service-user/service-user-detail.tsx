import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-user.reducer';
import { IServiceUser } from 'app/shared/model/service-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceUserDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceUserDetail = (props: IServiceUserDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceUserEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.serviceUser.detail.title">ServiceUser</Translate> [<b>{serviceUserEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">
              <Translate contentKey="carePlannerApp.serviceUser.title">Title</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.title}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="carePlannerApp.serviceUser.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.firstName}</dd>
          <dt>
            <span id="middleName">
              <Translate contentKey="carePlannerApp.serviceUser.middleName">Middle Name</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.middleName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="carePlannerApp.serviceUser.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.lastName}</dd>
          <dt>
            <span id="preferredName">
              <Translate contentKey="carePlannerApp.serviceUser.preferredName">Preferred Name</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.preferredName}</dd>
          <dt>
            <span id="serviceUserCode">
              <Translate contentKey="carePlannerApp.serviceUser.serviceUserCode">Service User Code</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.serviceUserCode}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="carePlannerApp.serviceUser.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserEntity.dateOfBirth ? (
              <TextFormat value={serviceUserEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastVisitDate">
              <Translate contentKey="carePlannerApp.serviceUser.lastVisitDate">Last Visit Date</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserEntity.lastVisitDate ? (
              <TextFormat value={serviceUserEntity.lastVisitDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="carePlannerApp.serviceUser.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserEntity.startDate ? <TextFormat value={serviceUserEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="supportType">
              <Translate contentKey="carePlannerApp.serviceUser.supportType">Support Type</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.supportType}</dd>
          <dt>
            <span id="serviceUserCategory">
              <Translate contentKey="carePlannerApp.serviceUser.serviceUserCategory">Service User Category</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.serviceUserCategory}</dd>
          <dt>
            <span id="vulnerability">
              <Translate contentKey="carePlannerApp.serviceUser.vulnerability">Vulnerability</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.vulnerability}</dd>
          <dt>
            <span id="servicePriority">
              <Translate contentKey="carePlannerApp.serviceUser.servicePriority">Service Priority</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.servicePriority}</dd>
          <dt>
            <span id="source">
              <Translate contentKey="carePlannerApp.serviceUser.source">Source</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.source}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="carePlannerApp.serviceUser.status">Status</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.status}</dd>
          <dt>
            <span id="firstLanguage">
              <Translate contentKey="carePlannerApp.serviceUser.firstLanguage">First Language</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.firstLanguage}</dd>
          <dt>
            <span id="interpreterRequired">
              <Translate contentKey="carePlannerApp.serviceUser.interpreterRequired">Interpreter Required</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.interpreterRequired ? 'true' : 'false'}</dd>
          <dt>
            <span id="activatedDate">
              <Translate contentKey="carePlannerApp.serviceUser.activatedDate">Activated Date</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserEntity.activatedDate ? (
              <TextFormat value={serviceUserEntity.activatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="profilePhoto">
              <Translate contentKey="carePlannerApp.serviceUser.profilePhoto">Profile Photo</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserEntity.profilePhoto ? (
              <div>
                {serviceUserEntity.profilePhotoContentType ? (
                  <a onClick={openFile(serviceUserEntity.profilePhotoContentType, serviceUserEntity.profilePhoto)}>
                    <img
                      src={`data:${serviceUserEntity.profilePhotoContentType};base64,${serviceUserEntity.profilePhoto}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {serviceUserEntity.profilePhotoContentType}, {byteSize(serviceUserEntity.profilePhoto)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="profilePhotoUrl">
              <Translate contentKey="carePlannerApp.serviceUser.profilePhotoUrl">Profile Photo Url</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.profilePhotoUrl}</dd>
          <dt>
            <span id="lastRecordedHeight">
              <Translate contentKey="carePlannerApp.serviceUser.lastRecordedHeight">Last Recorded Height</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.lastRecordedHeight}</dd>
          <dt>
            <span id="lastRecordedWeight">
              <Translate contentKey="carePlannerApp.serviceUser.lastRecordedWeight">Last Recorded Weight</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.lastRecordedWeight}</dd>
          <dt>
            <span id="hasMedicalCondition">
              <Translate contentKey="carePlannerApp.serviceUser.hasMedicalCondition">Has Medical Condition</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.hasMedicalCondition ? 'true' : 'false'}</dd>
          <dt>
            <span id="medicalConditionSummary">
              <Translate contentKey="carePlannerApp.serviceUser.medicalConditionSummary">Medical Condition Summary</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.medicalConditionSummary}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.serviceUser.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserEntity.lastUpdatedDate ? (
              <TextFormat value={serviceUserEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tenantId">
              <Translate contentKey="carePlannerApp.serviceUser.tenantId">Tenant Id</Translate>
            </span>
          </dt>
          <dd>{serviceUserEntity.tenantId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.serviceUser.user">User</Translate>
          </dt>
          <dd>{serviceUserEntity.userLogin ? serviceUserEntity.userLogin : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.serviceUser.branch">Branch</Translate>
          </dt>
          <dd>{serviceUserEntity.branchName ? serviceUserEntity.branchName : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.serviceUser.registeredBy">Registered By</Translate>
          </dt>
          <dd>{serviceUserEntity.registeredByEmployeeCode ? serviceUserEntity.registeredByEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.serviceUser.activatedBy">Activated By</Translate>
          </dt>
          <dd>{serviceUserEntity.activatedByEmployeeCode ? serviceUserEntity.activatedByEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-user" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-user/${serviceUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serviceUser }: IRootState) => ({
  serviceUserEntity: serviceUser.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceUserDetail);
