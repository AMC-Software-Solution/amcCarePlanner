import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './emergency-contact.reducer';
import { IEmergencyContact } from 'app/shared/model/emergency-contact.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmergencyContactDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmergencyContactDetail = (props: IEmergencyContactDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { emergencyContactEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.emergencyContact.detail.title">EmergencyContact</Translate> [
          <b>{emergencyContactEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="carePlannerApp.emergencyContact.name">Name</Translate>
            </span>
          </dt>
          <dd>{emergencyContactEntity.name}</dd>
          <dt>
            <span id="contactRelationship">
              <Translate contentKey="carePlannerApp.emergencyContact.contactRelationship">Contact Relationship</Translate>
            </span>
          </dt>
          <dd>{emergencyContactEntity.contactRelationship}</dd>
          <dt>
            <span id="isKeyHolder">
              <Translate contentKey="carePlannerApp.emergencyContact.isKeyHolder">Is Key Holder</Translate>
            </span>
          </dt>
          <dd>{emergencyContactEntity.isKeyHolder ? 'true' : 'false'}</dd>
          <dt>
            <span id="infoSharingConsentGiven">
              <Translate contentKey="carePlannerApp.emergencyContact.infoSharingConsentGiven">Info Sharing Consent Given</Translate>
            </span>
          </dt>
          <dd>{emergencyContactEntity.infoSharingConsentGiven ? 'true' : 'false'}</dd>
          <dt>
            <span id="preferredContactNumber">
              <Translate contentKey="carePlannerApp.emergencyContact.preferredContactNumber">Preferred Contact Number</Translate>
            </span>
          </dt>
          <dd>{emergencyContactEntity.preferredContactNumber}</dd>
          <dt>
            <span id="fullAddress">
              <Translate contentKey="carePlannerApp.emergencyContact.fullAddress">Full Address</Translate>
            </span>
          </dt>
          <dd>{emergencyContactEntity.fullAddress}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.emergencyContact.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {emergencyContactEntity.createdDate ? (
              <TextFormat value={emergencyContactEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.emergencyContact.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {emergencyContactEntity.lastUpdatedDate ? (
              <TextFormat value={emergencyContactEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.emergencyContact.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{emergencyContactEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.emergencyContact.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{emergencyContactEntity.hasExtraData ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.emergencyContact.serviceUser">Service User</Translate>
          </dt>
          <dd>{emergencyContactEntity.serviceUserServiceUserCode ? emergencyContactEntity.serviceUserServiceUserCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/emergency-contact" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/emergency-contact/${emergencyContactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ emergencyContact }: IRootState) => ({
  emergencyContactEntity: emergencyContact.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmergencyContactDetail);
