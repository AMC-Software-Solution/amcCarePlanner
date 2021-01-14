import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-user-contact.reducer';
import { IServiceUserContact } from 'app/shared/model/service-user-contact.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceUserContactDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceUserContactDetail = (props: IServiceUserContactDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceUserContactEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.serviceUserContact.detail.title">ServiceUserContact</Translate> [
          <b>{serviceUserContactEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="address">
              <Translate contentKey="carePlannerApp.serviceUserContact.address">Address</Translate>
            </span>
          </dt>
          <dd>{serviceUserContactEntity.address}</dd>
          <dt>
            <span id="cityOrTown">
              <Translate contentKey="carePlannerApp.serviceUserContact.cityOrTown">City Or Town</Translate>
            </span>
          </dt>
          <dd>{serviceUserContactEntity.cityOrTown}</dd>
          <dt>
            <span id="county">
              <Translate contentKey="carePlannerApp.serviceUserContact.county">County</Translate>
            </span>
          </dt>
          <dd>{serviceUserContactEntity.county}</dd>
          <dt>
            <span id="postCode">
              <Translate contentKey="carePlannerApp.serviceUserContact.postCode">Post Code</Translate>
            </span>
          </dt>
          <dd>{serviceUserContactEntity.postCode}</dd>
          <dt>
            <span id="telephone">
              <Translate contentKey="carePlannerApp.serviceUserContact.telephone">Telephone</Translate>
            </span>
          </dt>
          <dd>{serviceUserContactEntity.telephone}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.serviceUserContact.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserContactEntity.createdDate ? (
              <TextFormat value={serviceUserContactEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.serviceUserContact.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserContactEntity.lastUpdatedDate ? (
              <TextFormat value={serviceUserContactEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.serviceUserContact.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{serviceUserContactEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.serviceUserContact.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{serviceUserContactEntity.hasExtraData ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.serviceUserContact.serviceUser">Service User</Translate>
          </dt>
          <dd>{serviceUserContactEntity.serviceUserServiceUserCode ? serviceUserContactEntity.serviceUserServiceUserCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-user-contact" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-user-contact/${serviceUserContactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serviceUserContact }: IRootState) => ({
  serviceUserContactEntity: serviceUserContact.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceUserContactDetail);
