import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './carer-client-relation.reducer';
import { ICarerClientRelation } from 'app/shared/model/carer-client-relation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICarerClientRelationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CarerClientRelationDetail = (props: ICarerClientRelationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { carerClientRelationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="carePlannerApp.carerClientRelation.detail.title">CarerClientRelation</Translate> [
          <b>{carerClientRelationEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="relationType">
              <Translate contentKey="carePlannerApp.carerClientRelation.relationType">Relation Type</Translate>
            </span>
          </dt>
          <dd>{carerClientRelationEntity.relationType}</dd>
          <dt>
            <span id="reason">
              <Translate contentKey="carePlannerApp.carerClientRelation.reason">Reason</Translate>
            </span>
          </dt>
          <dd>{carerClientRelationEntity.reason}</dd>
          <dt>
            <span id="count">
              <Translate contentKey="carePlannerApp.carerClientRelation.count">Count</Translate>
            </span>
          </dt>
          <dd>{carerClientRelationEntity.count}</dd>
          <dt>
            <span id="lastUpdatedDate">
              <Translate contentKey="carePlannerApp.carerClientRelation.lastUpdatedDate">Last Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {carerClientRelationEntity.lastUpdatedDate ? (
              <TextFormat value={carerClientRelationEntity.lastUpdatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="clientId">
              <Translate contentKey="carePlannerApp.carerClientRelation.clientId">Client Id</Translate>
            </span>
          </dt>
          <dd>{carerClientRelationEntity.clientId}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.carerClientRelation.employee">Employee</Translate>
          </dt>
          <dd>{carerClientRelationEntity.employeeEmployeeCode ? carerClientRelationEntity.employeeEmployeeCode : ''}</dd>
          <dt>
            <Translate contentKey="carePlannerApp.carerClientRelation.serviceUser">Service User</Translate>
          </dt>
          <dd>{carerClientRelationEntity.serviceUserServiceUserCode ? carerClientRelationEntity.serviceUserServiceUserCode : ''}</dd>
        </dl>
        <Button tag={Link} to="/carer-client-relation" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/carer-client-relation/${carerClientRelationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ carerClientRelation }: IRootState) => ({
  carerClientRelationEntity: carerClientRelation.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CarerClientRelationDetail);
