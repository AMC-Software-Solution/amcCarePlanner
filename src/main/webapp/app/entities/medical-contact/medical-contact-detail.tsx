import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './medical-contact.reducer';
import { IMedicalContact } from 'app/shared/model/medical-contact.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMedicalContactDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MedicalContactDetail = (props: IMedicalContactDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { medicalContactEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.medicalContact.detail.title">MedicalContact</Translate> [<b>{medicalContactEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="doctorName">
              <Translate contentKey="carePlannerApp.medicalContact.doctorName">Doctor Name</Translate>
            </span>
          </dt>
          <dd>{medicalContactEntity.doctorName}</dd>
          <dt>
            <span id="doctorSurgery">
              <Translate contentKey="carePlannerApp.medicalContact.doctorSurgery">Doctor Surgery</Translate>
            </span>
          </dt>
          <dd>{medicalContactEntity.doctorSurgery}</dd>
          <dt>
            <span id="doctorAddress">
              <Translate contentKey="carePlannerApp.medicalContact.doctorAddress">Doctor Address</Translate>
            </span>
          </dt>
          <dd>{medicalContactEntity.doctorAddress}</dd>
          <dt>
            <span id="doctorPhone">
              <Translate contentKey="carePlannerApp.medicalContact.doctorPhone">Doctor Phone</Translate>
            </span>
          </dt>
          <dd>{medicalContactEntity.doctorPhone}</dd>
          <dt>
            <span id="lastVisitedDoctor">
              <Translate contentKey="carePlannerApp.medicalContact.lastVisitedDoctor">Last Visited Doctor</Translate>
            </span>
          </dt>
          <dd>
            {medicalContactEntity.lastVisitedDoctor ? (
              <TextFormat value={medicalContactEntity.lastVisitedDoctor} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="districtNurseName">
              <Translate contentKey="carePlannerApp.medicalContact.districtNurseName">District Nurse Name</Translate>
            </span>
          </dt>
          <dd>{medicalContactEntity.districtNurseName}</dd>
          <dt>
            <span id="districtNursePhone">
              <Translate contentKey="carePlannerApp.medicalContact.districtNursePhone">District Nurse Phone</Translate>
            </span>
          </dt>
          <dd>{medicalContactEntity.districtNursePhone}</dd>
          <dt>
            <span id="careManagerName">
              <Translate contentKey="carePlannerApp.medicalContact.careManagerName">Care Manager Name</Translate>
            </span>
          </dt>
          <dd>{medicalContactEntity.careManagerName}</dd>
          <dt>
            <span id="careManagerPhone">
              <Translate contentKey="carePlannerApp.medicalContact.careManagerPhone">Care Manager Phone</Translate>
            </span>
          </dt>
          <dd>{medicalContactEntity.careManagerPhone}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.medicalContact.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {medicalContactEntity.lastUpdatedDate ? (
              <TextFormat value={medicalContactEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tenantId">
              <Translate contentKey="carePlannerApp.medicalContact.tenantId">Tenant Id</Translate>
            </span>
          </dt>
          <dd>{medicalContactEntity.tenantId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.medicalContact.serviceUser">Service User</Translate>
          </dt>
          <dd>{medicalContactEntity.serviceUserServiceUserCode ? medicalContactEntity.serviceUserServiceUserCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/medical-contact" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/medical-contact/${medicalContactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ medicalContact }: IRootState) => ({
  medicalContactEntity: medicalContact.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MedicalContactDetail);
