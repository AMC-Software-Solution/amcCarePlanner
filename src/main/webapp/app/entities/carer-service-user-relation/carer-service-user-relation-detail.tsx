import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './carer-service-user-relation.reducer';
import { ICarerServiceUserRelation } from 'app/shared/model/carer-service-user-relation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICarerServiceUserRelationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CarerServiceUserRelationDetail = (props: ICarerServiceUserRelationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { carerServiceUserRelationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.carerServiceUserRelation.detail.title">CarerServiceUserRelation</Translate> [
          <b>{carerServiceUserRelationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="reason">
              <Translate contentKey="carePlannerApp.carerServiceUserRelation.reason">Reason</Translate>
            </span>
          </dt>
          <dd>{carerServiceUserRelationEntity.reason}</dd>
          <dt>
            <span id="count">
              <Translate contentKey="carePlannerApp.carerServiceUserRelation.count">Count</Translate>
            </span>
          </dt>
          <dd>{carerServiceUserRelationEntity.count}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="carePlannerApp.carerServiceUserRelation.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {carerServiceUserRelationEntity.createdDate ? (
              <TextFormat value={carerServiceUserRelationEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.carerServiceUserRelation.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {carerServiceUserRelationEntity.lastUpdatedDate ? (
              <TextFormat value={carerServiceUserRelationEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.carerServiceUserRelation.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{carerServiceUserRelationEntity.clientId}</dd>
          <dt>
            <span id="hasExtraData">
              <Translate contentKey="carePlannerApp.carerServiceUserRelation.hasExtraData">Has Extra Data</Translate>
            </span>
          </dt>
          <dd>{carerServiceUserRelationEntity.hasExtraData ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.carerServiceUserRelation.relationType">Relation Type</Translate>
          </dt>
          <dd>{carerServiceUserRelationEntity.relationTypeRelationType ? carerServiceUserRelationEntity.relationTypeRelationType : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.carerServiceUserRelation.employee">Employee</Translate>
          </dt>
          <dd>{carerServiceUserRelationEntity.employeeEmployeeCode ? carerServiceUserRelationEntity.employeeEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.carerServiceUserRelation.serviceUser">Service User</Translate>
          </dt>
          <dd>
            {carerServiceUserRelationEntity.serviceUserServiceUserCode ? carerServiceUserRelationEntity.serviceUserServiceUserCode : ''}
          </dd>
        </dl>
        <Button tag={Link} to="/carer-service-user-relation" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/carer-service-user-relation/${carerServiceUserRelationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ carerServiceUserRelation }: IRootState) => ({
  carerServiceUserRelationEntity: carerServiceUserRelation.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CarerServiceUserRelationDetail);
