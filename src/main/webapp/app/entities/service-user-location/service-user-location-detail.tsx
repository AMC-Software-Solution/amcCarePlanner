import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-user-location.reducer';
import { IServiceUserLocation } from 'app/shared/model/service-user-location.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceUserLocationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceUserLocationDetail = (props: IServiceUserLocationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceUserLocationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.serviceUserLocation.detail.title">ServiceUserLocation</Translate> [
          <b>{serviceUserLocationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="latitude">
              <Translate contentKey="carePlannerApp.serviceUserLocation.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{serviceUserLocationEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="carePlannerApp.serviceUserLocation.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{serviceUserLocationEntity.longitude}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.serviceUserLocation.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserLocationEntity.createdDate ? (
              <TextFormat value={serviceUserLocationEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.serviceUserLocation.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {serviceUserLocationEntity.lastUpdatedDate ? (
              <TextFormat value={serviceUserLocationEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.serviceUserLocation.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{serviceUserLocationEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.serviceUserLocation.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{serviceUserLocationEntity.hasExtraData ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.serviceUserLocation.employee">Employee</Translate>
          </dt>
          <dd>{serviceUserLocationEntity.employeeEmployeeCode ? serviceUserLocationEntity.employeeEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-user-location" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-user-location/${serviceUserLocationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serviceUserLocation }: IRootState) => ({
  serviceUserLocationEntity: serviceUserLocation.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceUserLocationDetail);
