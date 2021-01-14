import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee-location.reducer';
import { IEmployeeLocation } from 'app/shared/model/employee-location.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeLocationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EmployeeLocationDetail = (props: IEmployeeLocationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { employeeLocationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.employeeLocation.detail.title">EmployeeLocation</Translate> [
          <b>{employeeLocationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="latitude">
              <Translate contentKey="carePlannerApp.employeeLocation.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{employeeLocationEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="carePlannerApp.employeeLocation.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{employeeLocationEntity.longitude}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.employeeLocation.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeLocationEntity.createdDate ? (
              <TextFormat value={employeeLocationEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.employeeLocation.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {employeeLocationEntity.lastUpdatedDate ? (
              <TextFormat value={employeeLocationEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.employeeLocation.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{employeeLocationEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.employeeLocation.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{employeeLocationEntity.hasExtraData ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.employeeLocation.employee">Employee</Translate>
          </dt>
          <dd>{employeeLocationEntity.employeeEmployeeCode ? employeeLocationEntity.employeeEmployeeCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/employee-location" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/employee-location/${employeeLocationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ employeeLocation }: IRootState) => ({
  employeeLocationEntity: employeeLocation.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EmployeeLocationDetail);
